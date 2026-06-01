---
title: "AI-DLC in practice: when the sprint is measured in hours"
seoTitle: "AI-DLC in practice: when the sprint is measured in hours"
seoDescription: "AI-DLC compresses development.  After three workshops as a practitioner, here is the gap that will define what comes next."
datePublished: 2026-05-16T23:08:48.482Z
cuid: cmp8yjsny000g1shx2kbu0npb
slug: ai-dlc-in-practice-when-the-sprint-is-measured-in-hours
cover: https://cdn.hashnode.com/uploads/covers/62024529b6c216065a358ff9/66586296-b56e-4d2a-806a-8caab9effa27.png
tags: ai, artificial-intelligence, aidlc, ai-dlc, development-life-cycle

---

Software teams have been promised compression for thirty years. Faster sprints. Better tooling. Lower ceremony. The methodology layer keeps getting renamed and the actual delivery curve barely moves. So when AWS published AI-DLC last year and started describing MVPs delivered in a day, my first reaction was the same as yours probably is: prove it.

The summary, after three workshops as a practitioner: it mostly delivers. The AI is doing less of the work than the marketing suggests. What changes is that the methodology removes the dead time between activities that ate most of the old sprint. Here is what I saw, and what I still do not trust about it.

## What AI-DLC is

AI-DLC stands for AI-Driven Development Life Cycle. AWS published the foundational [methodology paper](https://aws.amazon.com/blogs/devops/ai-driven-development-life-cycle/) in mid-2025, and in November of that year [open-sourced the workflow scaffolds](https://aws.amazon.com/blogs/devops/open-sourcing-adaptive-workflows-for-ai-driven-development-life-cycle-ai-dlc/) as Amazon Q Rules and Kiro Steering Files.

The pitch is simple to state and harder to internalize: instead of using AI as an autocomplete assistant bolted onto a human-driven process, you flip the orientation. AI proposes the plan. AI asks clarifying questions. AI implements once humans validate. The team's job is no longer to write boilerplate — it is to make the contextual decisions that AI cannot make on its own.

There are three phases — Inception, Construction, Operations — and within each, the team works in what AI-DLC calls *Mob Elaboration* and *Mob Construction*: short, intense, collaborative cycles where the whole team is in the room with the AI, validating proposals in real time. Traditional sprints are replaced by "bolts" — measured in hours or days, not weeks. Epics become "Units of Work."

The vocabulary felt gimmicky to me the first time I read it. After running it for real, I get why the language matters. More on that below.

## How I ended up running these workshops

In March 2026, GBM (where I work as a Software Architect) hosted a two-day partner enablement workshop in San José with AWS. Sixteen of us from GBM and three AWS specialists spent two days building real software with AI-DLC for the first time. Four teams, four use cases — two greenfield, two brownfield. The goal was twofold: train internal leads to accelerate our software factory, and certify a small group of us to facilitate AI-DLC workshops for external clients as a partner.

I came out of those two days certified as a practitioner and, more importantly, convinced. A few weeks later I co-facilitated two client workshops — one with a large regional bank, one with a fintech. The results across all three engagements were consistent enough that I stopped treating them as anomalies.

I will not name the clients — both are under NDA — so I will keep the specifics generic. What I can share are the patterns.

## Some things you will only see from the inside

### **The compression is real, but it is not magic**

In the first client workshop, two squads built two MVPs in roughly 16 hours of effective work. One did real-time facial biometrics with liveness detection using Rekognition plus Bedrock. The other was an agentic onboarding assistant for know-your-business validation that the client's transformation team had been researching for three months without shipping a line. They deployed their first Bedrock AgentCore agent during the workshop.

In the second engagement, one of the use cases was a cross-service business rule spanning five repositories and two flows. The team had estimated five to nine days. They finished in two.

The numbers feel suspicious until you watch the team work. The AI is writing code faster, sure — that part is real. But the bigger gain comes from removing the dead time between activities. There are no handoffs from analyst to architect to developer. The whole team sits with the AI, validates a plan in twenty minutes, and the implementation is generated while the next plan is being elaborated. The ceremony that ate 60% of a normal sprint just disappears.

### **The hard part is decision-making, not prompting**

I expected the friction to be technical — prompt engineering, getting the model to "understand." It was not. The friction was decision-making. AI-DLC forces the team to make explicit, in front of everyone, the choices that normally get smuggled into a Jira ticket and then quietly changed in code review. Which data model. Which auth pattern. What to do when the third-party API returns 504. Whose ownership covers the new Lambda.

When the AI asks "should this be event-driven or synchronous?" and the room goes quiet for thirty seconds, you realize the methodology has very little to do with AI. The whole exercise forces the human decisions to get made deliberately rather than by drift. The AWS team calls this "humans remain the compass, guiding AI's acceleration." I would put it more bluntly: AI-DLC will not save a team that does not know what it wants to build. It will, however, surface that fact within the first hour of a bolt instead of the third sprint.

### **The brownfield cases are where it gets interesting**

The flashy demos in AI-DLC marketing tend to be greenfield. Spin up a serverless app from natural language, ship it, applaud. Those work, and they are useful for showing executives what is possible.

The brownfield cases are where I think the methodology earns its keep. In our internal workshop, one team modernized a legacy IBM WebSphere application to Quarkus on Java 21. They went from 170 files and 7,500 lines to 96 files and 4,100 lines — adding capabilities like Keycloak and PostgreSQL integration along the way — in roughly nine hours of working time. Another team built a Terraform pipeline plus production-ready Java for an enterprise integration that they had budgeted one to two weeks for. They finished mid-workshop.

This matters because greenfield is the easy case. The economic value of AI-DLC for most enterprises lives in the modernization backlog they have been deferring for five years. If the methodology can compress legacy refactors the way it compressed those two brownfield projects, the total addressable problem space gets a lot bigger.

## Where I think AI-DLC still has rough edges

It would be dishonest to write this without naming what I think still needs work.

**The vocabulary is a barrier with executives.** "Mob Elaboration" and "bolts" land well with engineering teams who recognize the references. They land poorly with steering committees who hear "mob" and think chaos. I now spend the first thirty minutes of any executive briefing translating the terminology into Scrum-adjacent language before reintroducing the real vocabulary. AWS will need to either accept that or evolve the naming.

**Quality assurance has not caught up.** AI-DLC compresses development. It does not yet compress security review, compliance sign-off, or change management. The teams I worked with shipped MVPs to QA environments in days. Getting those same MVPs to production still required the normal change-advisory cycle. The methodology has not yet been extended into the governance layers downstream of construction, and it shows.

**Adoption inside an existing organization is harder than adoption in a workshop.** Two days of immersive practice with a coach in the room is one thing. Sustaining the rhythm three weeks later, when only half the team is bought in and the other half is still running Jira tickets the old way, is a different problem. The clients who committed to organization-wide adoption did so because their CEOs mandated it. Bottom-up adoption is possible but slower, and the methodology does not yet have great answers for hybrid teams running both modes.

## What I would tell a peer who is about to start

A few things I wish someone had told me before our first workshop.

Pick use cases that are real, but not critical. The point of a first AI-DLC engagement is to build the muscle memory, not to ship the most important thing in your roadmap. Choose something that matters enough that the team will engage seriously, but not so much that a misstep blocks production.

Get the executive sponsor in the room for the kickoff and the demo. Not for the working sessions in between — that would slow the team down. But the difference between a workshop that produces a polite "interesting" and one that produces a transformation commitment is whether the person who controls the budget watched the team ship an MVP in front of them.

Resist the temptation to scope down. The first instinct is to pick something small enough to be safe. The teams that got the most out of AI-DLC scoped *up*, not down — they took the use case they would have deferred and let the methodology compress it. The risk-adjusted return on ambitious scoping was better than I expected.

Treat the first 30 minutes as the most important. If the team has not aligned on intent by minute 30, the rest of the bolt will drift. The AI is excellent at executing on a clear plan and confused by a fuzzy one. Front-load the clarification.

## Where this is going

Whether AI-DLC specifically wins is less interesting to me than which problems it leaves unsolved. The compression at the development layer is real. What is not yet real is the rest of the chain — the security reviews, the compliance gates, the change-advisory boards that sit between an MVP shipped on day two and a system running in production on day sixty. The methodology layer that fills that gap does not exist yet, or at least I have not seen it. That is the next interesting problem.

Until someone solves it, AI-DLC will keep producing teams who can ship in two days and organizations that take two months to let them.

If you want to try the methodology yourself, the best entry points are the [AI-DLC overview](https://aws.amazon.com/blogs/devops/ai-driven-development-life-cycle/) and the [open-source workflows on GitHub](https://github.com/awslabs/aidlc-workflows). And if you happen to be in Central America or the Caribbean and want to bring a workshop into your organization, you know where to find me.