/**
 * @license
 * Copyright 2025 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */

import * as fs from 'fs';
import * as fsp from 'fs/promises';
import * as path from 'path';
import { homedir } from 'os';
import { SlashCommand, CommandContext, CommandKind } from './types.js';
import { Config, GeminiClient } from '@google/gemini-cli-core';

const slugify = (text: string) => {
  return text.toString().toLowerCase()
    .replace(/\s+/g, '-')
    .replace(/[^\w-]+/g, '')
    .replace(/--+/g, '-')
    .replace(/^-+/g, '')
    .replace(/-+$/, '');
};

class GoalExecutor {
  private geminaDir = path.join(homedir(), '.gemina');
  private activeGoalFile = path.join(this.geminaDir, 'active_goal.txt');
  private config: Config;
  private geminiClient!: GeminiClient;
  private isInitialized = false;

  constructor(config: Config) {
    this.config = config;
    if (!fs.existsSync(this.geminaDir)) {
      fs.mkdirSync(this.geminaDir, { recursive: true });
    }
  }

  private async initialize(): Promise<void> {
    if (this.isInitialized) return;
    this.geminiClient = this.config.getGeminiClient();
    if (!this.geminiClient.isInitialized()) {
      const cgConfig = this.config.getContentGeneratorConfig();
      await this.geminiClient.initialize(cgConfig);
    }
    this.isInitialized = true;
  }

  private async getActiveGoalDir(): Promise<string> {
    if (!fs.existsSync(this.activeGoalFile)) {
      throw new Error('No active goal. Set one with /goal set <name> "[desc]"');
    }
    const activeGoalName = await fsp.readFile(this.activeGoalFile, 'utf-8');
    const goalDir = path.join(this.geminaDir, activeGoalName);
    if (!fs.existsSync(goalDir)) {
        throw new Error(`The active goal "${activeGoalName}" directory does not exist. Please set a new one.`);
    }
    return goalDir;
  }

  async run(args: string) {
    await this.initialize();
    const parts = args.trim().split(' ');
    const subcommand = parts[0] || 'help';
    const subArgs = parts.slice(1);

    switch (subcommand) {
      case 'set': return this.set(subArgs);
      case 'log': return this.log(subArgs);
      case 'status': return this.status();
      case 'suggest': return this.suggest();
      case 'review': return this.review(subArgs[0]);
      case 'list': return this.list();
      case 'switch': return this.switch(subArgs.join(' '));
      case 'help': return this.help();
      default: return this.help();
    }
  }

  private help(): string {
    return `Goal Command Usage:
  /goal <subcommand> [arguments]

Subcommands:
  set <name> "[desc]"  - Set a new goal.
  log [message]        - Log progress for the active goal.
  log --file [path]    - Log progress from a file.
  status               - Show the status of the active goal.
  suggest              - Get a suggestion for the next step.
  review [path]        - Request a code review for a file.
  list                 - List all your goals.
  switch <name>        - Switch the active goal.
  help                 - Show this help message.`;
  }

  private async set(args: string[]): Promise<string> {
    const [name, ...descParts] = args;
    const description = descParts.join(' ').replace(/^"|"$/g, '');

    if (!name || !description) {
      return 'Usage: /goal set <name> "[description]"\nExample: /goal set info-pro-exam "정보처리기사 실기 시험 합격하기"';
    }

    const goalSlug = slugify(name);
    const goalDir = path.join(this.geminaDir, goalSlug);

    if (fs.existsSync(goalDir)) {
      return `Error: A goal named "${goalSlug}" already exists.`;
    }
    await fsp.mkdir(goalDir, { recursive: true });

    const prompt = `You are an expert learning architect. Based on the user's goal, create a detailed, step-by-step roadmap in Markdown format. The user's goal is: "${description}". Focus on the "정보처리기사 실기" certification exam. Break it down into major sections and smaller, actionable tasks.`;
    
    try {
      const result = await this.geminiClient.generateContent([{ role: 'user', parts: [{ text: prompt }] }], {}, new AbortController().signal);
      const roadmap = result.candidates?.[0]?.content?.parts?.[0]?.text || 'Could not generate a roadmap.';
      await fsp.writeFile(path.join(goalDir, 'roadmap.md'), roadmap);
      await fsp.writeFile(this.activeGoalFile, goalSlug);
      return `✅ New goal set: "${name}"\nRoadmap saved and set as active goal.`;
    } catch (e) {
      return `Error generating roadmap: ${(e as Error).message}`;
    }
  }

  private async log(args: string[]): Promise<string> {
    const activeGoalDir = await this.getActiveGoalDir();
    const logFile = path.join(activeGoalDir, 'log.md');
    let contentToLog: string;

    if (args[0] === '--file' && args[1]) {
      const filePath = path.resolve(args[1]);
      if (!fs.existsSync(filePath)) {
        return `Error: File not found: ${filePath}`;
      }
      const fileContent = await fsp.readFile(filePath, 'utf-8');
      contentToLog = `Logged content from file ${args[1]}:
---
${fileContent}
---`;
    } else {
      contentToLog = args.join(' ');
    }

    if (!contentToLog) {
      return 'Error: Please provide a message to log or a file path.';
    }

    const timestamp = new Date().toISOString();
    const logEntry = `

---
[LOG - ${timestamp}]
${contentToLog}`;
    await fsp.appendFile(logFile, logEntry);
    return '✅ Log entry added to active goal.';
  }

  private async status(): Promise<string> {
    const activeGoalDir = await this.getActiveGoalDir();
    const roadmapFile = path.join(activeGoalDir, 'roadmap.md');
    const logFile = path.join(activeGoalDir, 'log.md');

    if (!fs.existsSync(roadmapFile)) {
        return 'Error: \`roadmap.md\` not found for the active goal.';
    }

    const roadmap = await fsp.readFile(roadmapFile, 'utf-8');
    const logs = fs.existsSync(logFile) ? await fsp.readFile(logFile, 'utf-8') : 'No logs yet.';
    const prompt = `You are a progress tracking assistant. Here is the user's learning roadmap and their logs. Compare them and provide a concise summary of their progress.\n\n**Roadmap:**\n${roadmap}\n\n**Logs:**\n${logs}\n\n**Analysis:**\nBased on the logs, what has the user completed? What's the next logical step on the roadmap? Format the output clearly in Markdown.`;
    const result = await this.geminiClient.generateContent([{role: 'user', parts: [{text: prompt}]}], {}, new AbortController().signal);
    return result.candidates?.[0]?.content?.parts?.[0]?.text || 'Could not get status.';
  }

  private async suggest(): Promise<string> {
    const activeGoalDir = await this.getActiveGoalDir();
    const roadmapFile = path.join(activeGoalDir, 'roadmap.md');
    const logFile = path.join(activeGoalDir, 'log.md');

    if (!fs.existsSync(roadmapFile)) {
        return 'Error: \`roadmap.md\` not found for the active goal.';
    }

    const roadmap = await fsp.readFile(roadmapFile, 'utf-8');
    const logs = fs.existsSync(logFile) ? await fsp.readFile(logFile, 'utf-8') : 'No logs yet.';
    const prompt = `You are a motivational learning coach. Based on the user's roadmap and current progress, suggest one single, concrete, and actionable next task. Keep it small and encouraging.\n\n**Roadmap:**\n${roadmap}\n\n**Logs:**\n${logs}\n\n**Suggestion:**\nWhat is the very next thing the user should do?`;
    const result = await this.geminiClient.generateContent([{role: 'user', parts: [{text: prompt}]}], {}, new AbortController().signal);
    return result.candidates?.[0]?.content?.parts?.[0]?.text || 'Could not get a suggestion.';
  }

  private async review(filePath: string): Promise<string> {
    if (!filePath) {
      return 'Error: Please provide a file path to review.';
    }
    const absolutePath = path.resolve(filePath);
    if (!fs.existsSync(absolutePath)) {
      return `Error: File not found: ${absolutePath}`;
    }
    const fileContent = await fsp.readFile(absolutePath, 'utf-8');
    const prompt = `You are a senior software engineer conducting a code review. The user is a student preparing for the "정보처리기사" exam. Provide constructive feedback on the following code. Focus on correctness, clarity, and best practices relevant to the exam.\n\n**Code to review:**\n\`\`\`\n${fileContent}\n\`\`\`\n\n**Feedback:**\nProvide your review in Markdown format.`;
    const result = await this.geminiClient.generateContent([{role: 'user', parts: [{text: prompt}]}], {}, new AbortController().signal);
    return result.candidates?.[0]?.content?.parts?.[0]?.text || 'Could not get a review.';
  }

  private list(): string {
    const entries = fs.readdirSync(this.geminaDir, { withFileTypes: true });
    const goalDirs = entries.filter(dirent => dirent.isDirectory()).map(dirent => dirent.name);
    if (goalDirs.length === 0) {
      return 'No goals found. Set your first one with \`/goal set <name> "[desc]"\` ';
    }
    let activeGoal = '';
    if (fs.existsSync(this.activeGoalFile)) {
        activeGoal = fs.readFileSync(this.activeGoalFile, 'utf-8');
    }
    const goalList = goalDirs.map(dir => dir === activeGoal ? `* ${dir} (active)` : `- ${dir}`).join('\n');
    return `Available goals:\n${goalList}`;
  }

  private async switch(goalName: string): Promise<string> {
    if (!goalName) {
        return 'Error: Please provide a goal name to switch to.';
    }
    const goalDir = path.join(this.geminaDir, goalName);
    if (!fs.existsSync(goalDir)) {
      return `Error: Goal "${goalName}" not found.`;
    }
    await fsp.writeFile(this.activeGoalFile, goalName);
    return `Switched to goal: ${goalName}`;
  }
}

export const goalCommand: SlashCommand = {
  name: 'goal',
  description: 'Manage your learning goals. Use \`/goal help\` to see subcommands.',
  kind: CommandKind.BUILT_IN,
  action: async (context, args) => {
    if (!context.services.config) {
        return { type: 'message', messageType: 'error', content: 'Configuration not available.' };
    }
    try {
      const executor = new GoalExecutor(context.services.config);
      const resultText = await executor.run(args);
      return { type: 'message', messageType: 'info', content: resultText };
    } catch (e) {
      return { type: 'message', messageType: 'error', content: (e as Error).message };
    }
  },
};