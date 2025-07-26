import {Command} from '@oclif/core';
import {getGeminiModel} from '../services/gemini';
import * as fs from 'fs-extra';
import * as path from 'path';
import {homedir} from 'os';

export default class Goal extends Command {
  static description = `Manages learning goals and tracks progress.
This command acts as a router for various subcommands to handle your learning journey.`;

  static examples = [
    `$ gemini goal set "Master TypeScript for backend development"`,
    `$ gemini goal log "Completed the 'interfaces' chapter."`,
    `$ gemini goal log --file ./src/code.ts`,
    `$ gemini goal status`,
    `$ gemini goal suggest`,
    `$ gemini goal review ./src/code.ts`,
  ];

  // Allow flexible arguments for subcommands
  static strict = false;

  private geminaDir = path.join(homedir(), '.gemina');
  private roadmapFile = path.join(this.geminaDir, 'roadmap.md');
  private logFile = path.join(this.geminaDir, 'log.md');

  async run(): Promise<void> {
    const {argv} = await this.parse(Goal);
    const subcommand = argv[0] as string | undefined;

    await this.ensureGeminaDirectory();

    switch (subcommand) {
      case 'set':
        await this.runSet(argv.slice(1));
        break;
      case 'log':
        await this.runLog(argv.slice(1));
        break;
      case 'status':
        await this.runStatus();
        break;
      case 'suggest':
        await this.runSuggest();
        break;
      case 'review':
        await this.runReview(argv.slice(1));
        break;
      default:
        this.log('Invalid subcommand. Please use one of: set, log, status, suggest, review.');
        this._help();
    }
  }

  private async ensureGeminaDirectory(): Promise<void> {
    await fs.ensureDir(this.geminaDir);
  }

  private async runSet(args: string[]): Promise<void> {
    const goalDescription = args.join(' ');
    if (!goalDescription) {
      this.error('Please provide a goal description. Example: gemini goal set "Learn Next.js"');
      return;
    }

    this.log('Generating a personalized roadmap... (this may take a moment)');
    const model = getGeminiModel('gemini-pro');
    const prompt = `You are an expert learning architect. Based on the user's goal, create a detailed, step-by-step roadmap in Markdown format. The user's goal is: "${goalDescription}". Focus on the "정보처리기사 실기" certification exam. Break it down into major sections and smaller, actionable tasks.`;

    try {
      const result = await model.generateContent(prompt);
      const response = result.response;
      const roadmap = response.text();
      
      await fs.writeFile(this.roadmapFile, roadmap);
      this.log(`\n✅ Roadmap successfully saved to ${this.roadmapFile}`);
      this.log('\nTo see your roadmap, run: gemini goal status');
    } catch (error) {
      this.error('Failed to generate roadmap from Gemini API.', {exit: 1});
    }
  }

  private async runLog(args: string[]): Promise<void> {
    let contentToLog: string;
    // Super basic flag parsing for now
    if (args[0] === '--file' && args[1]) {
      const filePath = path.resolve(args[1]);
      if (!await fs.pathExists(filePath)) {
        this.error(`File not found: ${filePath}`);
        return;
      }
      const fileContent = await fs.readFile(filePath, 'utf-8');
      contentToLog = `Logged content from file ${args[1]}:\n---\n${fileContent}\n---`;
    } else {
      contentToLog = args.join(' ');
    }

    if (!contentToLog) {
      this.error('Please provide a message to log or a file path. Example: gemini goal log "Finished module 3"');
      return;
    }

    const timestamp = new Date().toISOString();
    const logEntry = `\n\n---\n[LOG - ${timestamp}]\n${contentToLog}`;

    await fs.appendFile(this.logFile, logEntry);
    this.log(`✅ Log entry added to ${this.logFile}`);
  }

  private async runStatus(): Promise<void> {
    if (!await fs.pathExists(this.roadmapFile)) {
      this.error('No roadmap found. Please set a goal first using: gemini goal set "Your Goal"');
      return;
    }

    const roadmap = await fs.readFile(this.roadmapFile, 'utf-8');
    const logs = await fs.pathExists(this.logFile) ? await fs.readFile(this.logFile, 'utf-8') : 'No logs yet.';

    this.log('Analyzing your progress...');
    const model = getGeminiModel('gemini-pro');
    const prompt = `You are a progress tracking assistant. Here is the user's learning roadmap and their logs. Compare them and provide a concise summary of their progress.

**Roadmap:**
${roadmap}

**Logs:**
${logs}

**Analysis:**
Based on the logs, what has the user completed? What's the next logical step on the roadmap? Format the output clearly in Markdown.`;

    try {
      const result = await model.generateContent(prompt);
      const response = result.response;
      this.log(response.text());
    } catch (error) {
      this.error('Failed to get status from Gemini API.', {exit: 1});
    }
  }

  private async runSuggest(): Promise<void> {
    if (!await fs.pathExists(this.roadmapFile)) {
      this.error('No roadmap found. Please set a goal first using: gemini goal set "Your Goal"');
      return;
    }
    
    this.log('Figuring out the best next step for you...');
    const model = getGeminiModel('gemini-pro');
    const roadmap = await fs.readFile(this.roadmapFile, 'utf-8');
    const logs = await fs.pathExists(this.logFile) ? await fs.readFile(this.logFile, 'utf-8') : 'No logs yet.';

    const prompt = `You are a motivational learning coach. Based on the user's roadmap and current progress, suggest one single, concrete, and actionable next task. Keep it small and encouraging.

**Roadmap:**
${roadmap}

**Logs:**
${logs}

**Suggestion:**
What is the very next thing the user should do?`;

    try {
      const result = await model.generateContent(prompt);
      const response = result.response;
      this.log(response.text());
    } catch (error) {
      this.error('Failed to get suggestion from Gemini API.', {exit: 1});
    }
  }

  private async runReview(args: string[]): Promise<void> {
    const filePath = args[0];
    if (!filePath) {
      this.error('Please provide a file path to review. Example: gemini goal review ./my-code.js');
      return;
    }

    const absolutePath = path.resolve(filePath);
    if (!await fs.pathExists(absolutePath)) {
      this.error(`File not found: ${absolutePath}`);
      return;
    }

    const fileContent = await fs.readFile(absolutePath, 'utf-8');
    
    this.log(`Requesting a review for ${filePath}...`);
    const model = getGeminiModel('gemini-pro');
    const prompt = `You are a senior software engineer conducting a code review. The user is a student preparing for the "정보처리기사" exam. Provide constructive feedback on the following code. Focus on correctness, clarity, and best practices relevant to the exam.

**Code to review:**
```
${fileContent}
```

**Feedback:**
Provide your review in Markdown format.`;

    try {
      const result = await model.generateContent(prompt);
      const response = result.response;
      this.log(response.text());
    } catch (error) {
      this.error('Failed to get review from Gemini API.', {exit: 1});
    }}
}
