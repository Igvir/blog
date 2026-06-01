---
title: "From code author to system steward: what changes inside AI-DLC"
datePublished: 2026-06-01T11:00:00.000Z
cuid: cmpv3kubl000023cu4kb00n4d
slug: from-code-author-to-system-steward
cover: https://cdn.hashnode.com/uploads/covers/62024529b6c216065a358ff9/d80fbbdc-9b4f-4acb-a614-7d4697a25800.png
tags: ai-development-services, spec-driven-development, aidlc

---

The [first post](https://blog.igvir.com/ai-dlc-in-practice-when-the-sprint-is-measured-in-hours) in this series was about what I saw — three workshops, teams shipping in hours what used to take sprints. The [second post](https://blog.igvir.com/the-impressive-demo-trap) was about what goes wrong when that speed has no structure underneath it: code that appears faster than anyone can understand it, context that drains away, security flaws nobody is positioned to catch.

This post is about the structure. The actual mechanism, what it asks of a developer, and what it does not yet solve.

I want to start with the observation that "use AI to write code" describes at least five distinct practices, and people arguing about whether it works are usually talking about different ones.

## How far can you delegate?

At one end of that spectrum is autocomplete — the AI finishes the line you were already typing. You are still the author; the AI just types faster. At the other end is full delegation — you describe an outcome and the AI produces a system, and you find out what it built by running it. In between are assisted editing, conversational pair-programming, and supervised generation of whole components.

These are not the same activity, and they do not fail the same way. Autocomplete barely changes anything about how you think. Full delegation changes everything. Most of the failure stories from the second post — the lost context, the missing row-level security, the demo that becomes an unmaintainable system — come from teams operating near the delegation end of the spectrum while still using the habits of the autocomplete end. They let the AI build, but they reviewed the output the way you would review a colleague's pull request: at human reading speed, after the fact.

AI-DLC is best understood as a claim about where on that spectrum you should operate, and what has to be true for it to be safe. Its answer is: you can move toward delegation, but only if the thing you delegate from is a spec.

## What a spec actually is

A spec, in AI-DLC, is something different from a requirements document. This distinction matters more than it sounds, so it is worth being precise.

A traditional requirements document is written once, at the start, by one role, for another role to consume. It is a handoff. By the time code exists, the document is usually stale, and everyone knows it, and nobody updates it, because its job — getting the project approved and started — is already done.

A spec is the opposite of a handoff. It is the durable, living description of intent that the whole team works against for the life of the feature. It says what the system should do and why, in language precise enough that an AI can generate from it and a human can validate against it. When the requirement changes, the spec changes first — and the code is regenerated or adjusted to match. The spec is the real work, written down — and it lives as a first-class artifact, persisted alongside the code, where any team member can read it weeks or months later.

That is the conceptual core, and it is deceptively plain: write down the intent, deliberately, before generating the code, and keep that writing as a first-class artifact rather than letting it dissolve into a chat history.

It sounds like a return to waterfall. The reason it is not is the second loop.

## Two loops, two clocks

AI-DLC runs on two loops moving at two different speeds.

The outer loop is the project rhythm: inception, construction, operations. It is where the big decisions live — what are we building, for whom, against what constraints, with what definition of done. The outer loop moves at the pace of human judgment, because the decisions in it are human decisions. It is slow on purpose.

The inner loop is the daily cycle: write or refine the spec, let the AI generate against it, validate the result, update the spec, regenerate. This is where Spec-Driven Development lives. The inner loop is fast — it is the loop the speed of AI actually compresses.

![](https://cdn.hashnode.com/uploads/covers/62024529b6c216065a358ff9/3a95afce-e8a6-4cbc-882d-ed9e00fa6666.png align="center")

The reason this avoids waterfall is that the two loops are connected and continuous. Waterfall's defining failure was a single pass: specify everything, build everything, discover at the end that the specification was wrong. AI-DLC's inner loop runs many times inside a single outer-loop phase. The spec is wrong the first time, too — but you find out in the next inner-loop cycle, eighteen months earlier than the equivalent discovery in waterfall. The spec becomes a hypothesis you revise continuously, and the AI is what makes the revision cheap enough to do that.

This is the structural answer to the asymmetry from the second post. The problem there was that generation happens at machine speed and comprehension at human speed, and review-after-the-fact loses the race. The spec moves the human-speed activity — deciding, judging, articulating intent — to *before* generation instead of after. The developer stops racing to understand code the AI already wrote and starts deciding what it should write, at their own pace, and then checking that it did. The comprehension happens where humans are fast — at the level of intent — and the implementation, where humans are slow, becomes the machine's job.

## What the daily work becomes

If you sit with a developer working this way, the visible change is what they spend their hours on.

The old loop: read a ticket, hold the intent in your head, translate it into code by typing, spend most of the day in the translation. The translation — turning a known intent into working syntax — was the bulk of the job.

The new loop: the translation is the part the AI does. What is left for the developer is everything on either side of it. Before generation: articulating the intent precisely enough to be generated from, which means noticing the ambiguities, the edge cases, the unstated assumptions — the work of deciding. After generation: validating that the result matches the intent, which means knowing what correct looks like for this system — a deeper question than whether the code runs.

Both of those are harder than typing. That is the part the productivity narrative gets wrong. AI-DLC does not make the job easier; it removes the easy part. The translation work — satisfying, fast, and mostly mechanical — is gone. What remains is the work that was always the actual difficulty: knowing what to build, and knowing whether you built it.

## From author to steward

This is the shift the title points at, and it is worth stating plainly.

For most of software's history, a developer was defined by authorship. You were the person who wrote the code. Your skill was measured in how well you wrote it. The code was, in a real sense, yours — you knew every line because you had typed every line, and that knowledge was the foundation of everything else: debugging, reviewing, mentoring, designing the next thing.

When generation is no longer the bottleneck, authorship stops being the center of the role. The developer is no longer the person who wrote the system. They are the person responsible for the system — for whether it is correct, coherent, secure, aligned with intent, and possible to change next quarter. That is a different relationship to the code. It is stewardship, not authorship.

A steward does not need to have typed every line. A steward needs to understand the system well enough to defend its decisions, catch where it is drifting, and decide where it goes next. Stewardship draws on a different set of skills than authorship did. Judgment goes up in value. Architectural taste goes up. The ability to articulate intent precisely — to write a spec that is neither vague nor over-specified — goes up. Raw typing speed, syntax recall, the muscle memory of a particular framework: those go down.

This is an uncomfortable transition for some, and it would be dishonest to pretend otherwise. A developer whose identity was built on authorship — on being the fastest, cleanest writer of code in the room — is being told that the thing they were best at is now the thing the machine does. The role survives. But it is being redefined around what authorship sometimes let people avoid: being accountable for the entire system, including the parts they did not personally write.

## The bottleneck moves, and the methodology has not caught up everywhere

There is an old observation from manufacturing, formalized by Eliyahu Goldratt in *The Goal*, that optimizing a single station of a production line does not produce more throughput. It moves the bottleneck. The constraint relocates, often to the station upstream or downstream of the one that was just accelerated, and the system's total output is set by whichever station is now the slowest.

AI-DLC compresses the inner loop. The inner loop is not the entire pipeline. So the bottleneck moves, in at least three directions worth naming — because every organization adopting AI-DLC eventually runs into them, and the methodology, as currently documented, does not address them in equal depth.

**Upstream — the backlog runs dry.** A team running AI-DLC well consumes backlog faster than the business can produce it. Stories written for a slower delivery cadence get exhausted in weeks. The product owner, sized and structured to feed a team at the old pace, becomes the new constraint. Discovery work — talking to users, validating problems, refining intent — has not gotten faster.

What I have seen the more thoughtful clients do is reshape ceremonies to close the distance between business and development. The PO stops being a translator who hands specs to engineers and starts being a guide who participates directly in the elaboration mob — the AI-DLC ritual where the spec is built collaboratively, with the AI present, in the room. The gap between what the business wants and what the team is generating shrinks because the gap is no longer mediated by a written handoff. This is a partial answer — the one most teams arrive at in practice rather than the one the methodology prescribes. AI-DLC nudges in this direction but does not prescribe a full reorganization of product work, and that gap is where most adoption pain lives.

**In the middle — code review becomes the constraint.** This one is the most documented by external research, and the numbers are striking. Faros AI, analyzing telemetry from over 10,000 developers, found that teams with high AI adoption merge 98% more pull requests but spend 91% more time in PR review. LinearB's 2026 benchmarks show AI-generated PRs waiting 4.6x longer to be picked up by a reviewer. Senior engineers spend roughly 3.5x longer reviewing AI-generated code than human-written code. Sonar's 2026 State of Code report found that 96% of developers do not fully trust AI-generated code, and 38% say reviewing it takes more effort than reviewing human-written code. The acquisition of Graphite by Cursor in December 2025 for over $290 million was justified explicitly on this premise — code review is the next constraint to break.

AI-DLC offers a partial structural answer. When the spec is the source of truth, the review question changes shape: instead of "is this code correct?" the reviewer asks "does this code satisfy the spec?" The second question is more answerable than the first, because there is a written referent. In principle, that should make review faster. In practice — and this is where honesty matters — the volume problem does not disappear. If the team is generating five times more code than before, even a more answerable review takes more total reviewer-hours. The methodology helps with the cognitive load of each review; it does not solve the throughput problem. As Armin Ronacher put it: if input grows faster than throughput, you have an accumulating failure.

**Downstream — QA cannot keep pace.** Quality assurance was sized for human typing speed. AI-DLC delivers MVPs in days; the QA function, built around manual exploratory testing, scripted regression suites, and release cadences that assumed weeks between deployments, is not built to validate at that rate. The result is what one industry CEO recently called the breakdown of shift-left: the practice of moving testing earlier in the cycle, which was the answer for the last fifteen years, is failing under AI-era velocity. Testing backlog grows faster than it can be drained. The "ship in two days, sit in QA for two weeks" pattern is now common.

The methodology's answer is that the spec produces explicit acceptance criteria, which should give automated testing a clearer target. That helps. But it does not address the structural mismatch between the speed of code generation and the speed of human-led test design, exploratory testing, and the kinds of validation that resist full automation. QA, like product discovery, has not yet been compressed by an equivalent methodological shift. Until it is, AI-DLC teams will keep producing more code than the rest of the organization is built to absorb.

This is, in the end, the larger story the three posts have been circling. The first post saw the compression and named the gap between two-day MVPs and two-month deployments. The second post diagnosed the asymmetry between generation and comprehension at the developer level. This third post is about that same asymmetry, scaled to the organization: the team gets faster, the surrounding system does not, and the imbalance becomes visible everywhere at once. AI-DLC is a methodology for the construction phase. The methodology for the construction phase plus the eight other functions that have to keep up — discovery, review, testing, security, compliance, governance, support, operations — does not yet exist.

I do not pretend to know what it looks like when it does. I am increasingly sure it is the question worth working on next.

## Where the spec itself still falls short

The pipeline imbalance above is a problem with the surroundings of AI-DLC. Two more problems live inside it — failure modes of the spec itself, regardless of how well the rest of the organization keeps up.

First, the spec can become its own ceremony. The whole point of a spec is that it is a living artifact the team works against. But there is a failure mode where the spec becomes a box to check — written because the process says to, elaborate and tidy and disconnected from what the code actually does. A spec nobody validates against is just a requirements document with a new name, and it brings back every problem requirements documents had. The methodology does not enforce the discipline. People do, or they do not.

Second, specs drift from code. AI-DLC's answer to context loss is that intent lives in the spec. But the spec only stays true if it is updated every time the code changes, and the same speed that makes generation cheap makes it easy to make a quick change directly in the code and forget the spec. When that happens, the spec stops being the source of truth — it becomes a confident, official-looking document that is quietly wrong, which is arguably worse than no document at all.

## Closing the series

I opened the first post of this series skeptical — thirty years of methodologies promising compression, and a delivery curve that barely moved. Three posts later, I am not going to end with a prediction that AI-DLC is the one that finally changes everything. Predictions like that are exactly what I said I distrusted.

What I will say is narrower and, I think, more durable. The asymmetry is real: AI generates faster than humans comprehend. Any way of working that ignores that asymmetry will keep producing impressive demos and unmaintainable systems. AI-DLC is the most serious attempt I have seen to build a methodology that takes the asymmetry as its starting premise rather than wishing it away — by moving human judgment in front of generation, by making intent a durable artifact, by redefining the developer as the steward of a system rather than the author of its lines.

It does not solve everything. The discipline it requires is not enforced by the tooling, and large parts of the surrounding pipeline have not caught up. But it is asking the right question, and after three workshops and a fair amount of skepticism, I think asking the right question is most of the work.

The developer's job is not going away. It is moving up a level — from writing the system to being answerable for it. That is a harder job, a more interesting one, and, for the people willing to make the shift, a more durable one than the job it replaces.

* * *

*This is the final post in a three-part series on AI-DLC.*

[*Part 1: AI-DLC in practice — when the sprint is measured in hours*](https://blog.igvir.com/ai-dlc-in-practice-when-the-sprint-is-measured-in-hours) [*Part 2: The "impressive demo" trap — code appears faster than it can be understood*](https://blog.igvir.com/the-impressive-demo-trap)

*If you want to bring an AI-DLC workshop into your organization in Central America or the Caribbean,* [*get in touch*](mailto:info@igvir.com)*.*