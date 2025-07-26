PRD: Project Gemina (재미나)
Version: 2.2

Status: In Development

Author: User & Gemini

Base Project: google-gemini/gemini-cli

Objective: To extend the existing Gemini CLI with goal-oriented learning features, creating a personalized AI learning partner.

1. Overview
(Unchanged)

2. Goals and Success Metrics
(Unchanged)

3. Scope and Features (MVP v2.2)

3.1. In-Scope Features
- **Slash Command Interface:** All goal-oriented features will be accessed via `/goal <subcommand>`.
- **Supported Subcommands:** `set`, `log`, `status`, `suggest`, `review`, `list`, `switch`, `help`.
- **Multiple Goal Management:** Users can set and manage multiple learning goals.
- **State Management:**
    - User data is stored in `~/.gemina/`.
    - Each goal has its own subdirectory, named by the user (e.g., `~/.gemina/info-pro-exam/`).
    - Each goal directory contains a `roadmap.md` and a `log.md`.
    - `~/.gemina/active_goal.txt` stores the name of the current goal directory.
- **Initial Target Domain:** Prompts are optimized for the "정보처리기사 실기" certification.

3.2. Out-of-Scope Features (Post-MVP)
- Graphical User Interface (GUI) or web dashboard.
- Real-time collaboration or team features.
- Complex state management using databases.

4. Technical Requirements
(Unchanged from v2.1)

5. User Interaction Flow (Slash Commands)
The user interacts with the goal features via commands entered in the chat prompt.

- `/goal set <name> "[description]"`: Creates a new learning goal. `<name>` is a short, URL-friendly name for the directory, and `"[description]"` is the full goal description for the AI.
  - *Example:* `/goal set info-pro-exam "정보처리기사 실기 시험 합격하기"`
  - If run with no arguments, it provides interactive guidance.
- `/goal log [message]`: Records a progress update for the *active* goal. If `[message]` is omitted, it summarizes the current conversation and logs it automatically.
- `/goal log --file [path]`: Records a file as a progress update for the *active* goal.
- `/goal status`: Displays a progress summary for the *active* goal.
- `/goal suggest`: Suggests the next actionable task for the *active* goal.
- `/goal review [path]`: Requests a code review for a file related to the *active* goal. (*Note: Temporarily disabled due to build issues.*)
- `/goal list`: Lists all created goals.
- `/goal switch [name]`: Switches the active goal.
- `/goal help`: Shows a help message with all available commands and their usage.

6. Development Plan: Step-by-Step Implementation (Revised)
This plan adapts to the project's actual architecture.

- **Phase 1: Integrating into the CLI (Completed)**
    - **Step 1.1:** The feature is integrated as a "Built-in Command" loaded by the `BuiltinCommandLoader`.
    - **Step 1.2:** The core logic resides in `packages/cli/src/ui/commands/goal.ts`.

- **Phase 2: State Management & Goal Setting (Completed)**
    - **Step 2.1:** Implemented the `set` command logic in `goal.ts`.
    - **Step 2.2:** Implemented the `log` command logic (for explicit messages).
    - **Note:** Fixed critical bugs related to `fs-extra` module resolution in the build system by switching to the native `fs` module for specific functions.

- **Phase 3: Interactive Learning Loop (Completed)**
    - **Step 3.1:** Implemented `status`, `suggest`.
    - **Step 3.2:** Implemented the `help` command.

- **Phase 4: Multi-Goal Management & Usability (Completed)**
    - **Step 4.1:** Implemented `list` and `switch` commands.
    - **Step 4.2:** Refined all prompts and user feedback messages for clarity.

- **Phase 5: Auto-Logging Feature (Proposed)**
    - **Step 5.1:** Enhance the `log` command in `goal.ts` to handle cases with no arguments.
    - **Step 5.2:** Access the current session's conversation history via the `CommandContext`.
    - **Step 5.3:** Create a new prompt to ask Gemini to summarize the conversation as a daily work log.
    - **Step 5.4:** Append the generated summary to the `log.md` file of the active goal.