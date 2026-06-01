---
title: "The "impressive demo" trap: code appears faster than it can be understood"
datePublished: 2026-05-22T00:30:00.000Z
cuid: cmpg6ntxj000123fld8433vph
slug: the-impressive-demo-trap
cover: https://cdn.hashnode.com/uploads/covers/62024529b6c216065a358ff9/be2ff6ab-6ce0-4530-83f0-202f424b102e.png
tags: ai, demo, ai-tools, software-development-lifecycle, aidlc, ai-dlc, demo-trap

---

*This is the second post in a three-part series on AI-DLC. The* [*first post*](https://blog.igvir.com/ai-dlc-in-practice-when-the-sprint-is-measured-in-hours) *covered what I saw running three AI-DLC workshops. This one is about the problem AI-DLC tries to solve. The third will get into the mechanics of how it does so.*

There is a moment in every AI coding demo that always lands the same way: the presenter types a prompt and three seconds later, 200 lines of code appear. The audience makes the noise audiences make when they are impressed. Someone says "wow." Someone else asks, half-joking, whether they still have a job.

I have been on both sides of that moment. I have given the demo and I have made the audience make the noise. And after running three AI-DLC workshops and watching teams actually try to ship the code those demos produce, I have come to believe that the moment could be the problem, not the solution.

The trap is that AI writes code faster than humans can understand what was written. Everything downstream of that asymmetry — security vulnerabilities, lost context, fragile systems, atrophying skills — flows from a single mismatch in speed. The hand can now produce in seconds what the eye cannot review in minutes.

That asymmetry has a name in the AI-DLC literature: the "impressive demo trap". I think it is an important concept in the entire methodology, and will try to explian it here.

This post is about what the trap is, the four ways it manifests in production code, and why "just review the code more carefully" is not a viable answer.

## The data is starting to land

For a while, the case against AI-assisted coding was anecdotal. A developer would tell you a war story about an AI hallucinating an API that did not exist, or generating code with a hardcoded secret, or confidently producing a function that compiled and ran but did the wrong thing. The stories were vivid but easy to dismiss as edge cases.

In the last twelve months, the data has caught up. Four findings are worth holding in your head when someone tells you AI-assisted coding is uniformly faster and safer.

**Experienced developers are slower with AI, while believing they are faster.** A [METR study from 2025](https://arxiv.org/abs/2507.09089) measured the actual time experienced open-source developers took to complete tasks with and without AI tools. They were 19% slower with AI. The same developers reported feeling 20% faster. The gap between perceived and actual productivity was 39 percentage points. That is a systematic mismatch between how productive AI-assisted coding feels and how productive it is.

**Almost half of AI-generated code has exploitable vulnerabilities.** Veracode's 2025 audit of AI-generated applications found that 45% contained exploitable OWASP-class vulnerabilities. Not subtle bugs — the kind of thing a junior security review would catch. AI generates them at machine speed and humans miss them at human speed.

**AI assistance lowers skill scores, especially in debugging.** Anthropic's 2026 internal study on engineering skill development found 17% lower skill scores among developers using AI assistance, with the largest gap in debugging. The result is not surprising once you think about it: debugging is the activity where you most need to understand the code, and AI assistance is the activity that most reliably prevents you from doing so.

**Critical security flaws at scale.** A platform-wide audit of 1,645 applications built with vibe-coding tools found that 170 of them were missing row-level security on their databases. Not "had a subtle misconfiguration." Missing entirely. Anyone who knew the API could read or write anyone else's data. 170 applications. One audit.

None of these numbers are the whole story. The METR study is small. Veracode has an incentive to find vulnerabilities. The Lovable audit is on a specific platform. But the direction of all four findings is the same, and the direction needs a deep dive: the productivity story is more complicated than the demos suggest, and the failure modes are not random.

They are structural. And there are four of them.

## The four mechanisms

### **1: Context loss**

Two kinds of context drain away during AI-assisted development, and they are easy to confuse.

The first is the AI's. Every model has a context window — a finite amount of text it can hold at once. Within a single long session, the original requirement scrolls out of that window early; across a feature built over many separate sessions, it is gone before the second one even starts. Each new session begins with an AI that knows only what is in front of it right now. From that point on, it is working from a summary of a summary, reconstructing intent from whatever happens to still be in scope. It is not malfunctioning. It is doing exactly what a bounded context window forces it to do.

The second is the team's. A feature is rarely built in one sitting. It accretes over weeks — a session here, a follow-up there, a fix three weeks later. The requirement evolves across all of them: the AI asks clarifying questions, someone answers, decisions get made, scope drifts. None of it is written down anywhere a person would look again. By the time someone asks "what was the original requirement?", the honest answer is that nobody remembers — not because the engineers were careless, but because the reasoning never lived anywhere except a series of chat windows nobody reopened. The chat is not a record; it is a stream.

These two losses feed each other. When the AI forgets, it asks the team. The team answers from memory — increasingly partial memory, as the weeks pass. That answer is never captured either. The feature ends up working, and the reasoning for why it works the way it does is gone — from the model and from the team at the same time.

This is what AI-DLC calls context loss, and it is the most insidious of the four mechanisms because it does not produce a bug. It produces a system whose decisions cannot be audited, defended, or reproduced. A few months later, when a new requirement comes in that conflicts with a decision made in some session weeks earlier, nobody will remember why the original decision was made. The new requirement will land on top of the old one, and the resulting Frankenstein is exactly the kind of system that takes eighteen months and a rewrite to escape.

In traditional development, this problem also exists — it is what design documents and ADRs are supposed to prevent. The difference is that traditional development moves slowly enough that the documentation overhead is tolerable. AI-assisted development moves fast enough that documentation feels like an unaffordable tax. So it gets skipped and the context is lost.

### **2: No persistent artifacts**

This is the structural version of context loss. In a normal code review, the artifacts are durable — the pull request, the linked ticket, the design doc, the architectural decision record. They exist outside the heads of the people who made them, and they can be read by someone who joins the team next quarter.

In a chat-driven workflow, the artifacts are the chat history. Which is to say, they are not artifacts. They are conversational ephemera that exist in one developer's account, that cannot be linked to from a PR, that disappear when the session times out or when the developer leaves the company. The reasoning is in the chat. The chat is unreviewable.

I ran a small experiment in our internal workshop. I asked two senior engineers to look at a feature built via chat-driven AI development and tell me, just from the code, why a particular architectural choice had been made. Neither could. The choice was reasonable. They could not reconstruct the reasoning. The information was not in the code, not in the commits, not in any document — it had existed for forty seconds in a chat session three days earlier.

This matters most when something goes wrong in production. The first question after an incident is always "why did we do it this way?" In a chat-driven workflow, that question is unanswerable. You debug forward — by reading code and guessing — instead of debugging backward from intent.

### **3: Security blind spots**

This is the mechanism the Veracode and Lovable numbers are pointing at, and it has a specific shape worth naming. As mentioned above, in March 2025 security researcher Matt Palmer scanned 1,645 applications generated by a vibe-coding platform from natural-language prompts. 170 of them (10.3%) had row-level security entirely disabled on their Supabase databases, exposing 300+ API endpoints to unauthenticated access.

AI does not produce security vulnerabilities randomly. It produces them in patterns that match its training data. Missing input validation on quickstart tutorials. Default permissions left open in copy-paste-friendly snippets. The AI learned from public code, and public code is full of examples that prioritized clarity over security. When the AI generates new code, it pattern-matches against those examples.

The result is that AI-generated code has predictable security failure modes — and that predictability would be useful if anyone were checking for them systematically. They are not. The same demo asymmetry that makes the productivity gains feel huge also makes the security review feel optional. The code looks reasonable. It compiles. The test passes. The vulnerability is structural, not syntactic, and humans reviewing at human speed miss structural problems in code they did not write.

The Lovable audit is not a story about bad AI. It is a story about an asymmetry. The AI was perfectly capable of generating code with Row-Level Security (RLS). It just was not asked to, and the developers using it did not notice the absence.

### **4: The "impressive demo" trap itself**

This is the meta-mechanism that produces the other three.

The asymmetry between generation speed and comprehension speed is the central pathology. When a human writes code, the act of writing is also the act of thinking. The thinking happens in real time, at typing speed, and the developer ends the session with a model of the code in their head. The model is not perfect, but it exists.

When AI writes code, the writing happens in seconds and the thinking has to happen separately, after the fact. Most developers do not stop to build the mental model after the fact, because the code already works. The demo has already been impressive. The momentum is forward. So the model never gets built, and the developer becomes responsible for a system they do not actually understand.

This is the trap: the code is often fine, but the developer's relationship to the code is degraded. The code is not theirs in the way hand-written code would be. They did not earn the understanding. They have plausible deniability about every decision the system makes, because they did not really make those decisions. They reviewed them quickly and waved them through.

In a demo, this looks like productivity. In production, six months later, it looks like a system nobody can confidently change.

## "Just review more carefully" does not work

The standard response to all of this is some version of "developers should review AI-generated code more carefully." This is true but it is not a strategy. It is a wish.

The asymmetry is structural. A 200-line function takes about three seconds to generate and about twenty minutes to review properly, and "review properly" means understanding every branch, every edge case, every implicit assumption about the calling context. Twenty minutes of attention per three seconds of generation is a 400x slowdown that nobody actually applies. So the reviews get shorter. They become syntactic — "this looks fine" — instead of semantic — "this is correct for our use case." The vulnerabilities slip through. The context is not really absorbed. The model is not built.

Telling developers to review more carefully is like telling them to read every email more carefully. Technically possible. Practically not what happens.

The only viable response to a structural asymmetry is a structural intervention. You need a system that forces the human-speed activity to happen before the machine-speed activity, not after. You need artifacts that persist outside the chat window. You need decision points that are explicit instead of smuggled into the implementation. You need a methodology that is built for the new pace.

That methodology exists. It is called AI-DLC. At the inner loop — the day-to-day write-and-review cycle — it is also called Spec-Driven Development. And the next post in this series is about how it actually works — what a spec is, what changes when you build software this way, and what the developer role becomes when generation is no longer the bottleneck.

The TL;DR for this post: the trap is real, the data is starting to confirm it, and the fix is not "be more careful." The fix is to build a methodology that assumes the asymmetry exists and routes around it.

* * *

*This is part two of a three-part series on AI-DLC.*

[*Part 1: AI-DLC in practice — when the sprint is measured in hours*](https://blog.igvir.com/ai-dlc-in-practice-when-the-sprint-is-measured-in-hours)

*Part 3: From code author to system steward — what changes inside AI-DLC (coming soon)*

*If you want to bring an AI-DLC workshop into your organization in Central America or the Caribbean,* [*get in touch*](mailto:info@igvir.com)*.*