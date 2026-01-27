---
title: "Notes on "The DynamoDB Book" by Alex DeBrie"
seoTitle: "Notes on 'The DynamoDB Book' by Alex DeBrie"
seoDescription: "Software architect's review of "The DynamoDB Book" by Alex DeBrie: what works, what's missing, and whether it's worth it for backend developers."
datePublished: Tue Jan 27 2026 19:25:44 GMT+0000 (Coordinated Universal Time)
cuid: cmkwzl2sl000v02glfl4t5ak2
slug: notes-on-the-dynamodb-book-by-alex-debrie
cover: https://cdn.hashnode.com/res/hashnode/image/upload/v1769541170480/bcd20329-7eb3-4192-9e2e-a9abf44c584f.png
tags: nosql, aws, books, dynamodb, cloud-computing, alex-debrie

---

I've been working with DynamoDB for a few years now, and recently I came across [this book by Alex DeBrie](https://alexdebrie.gumroad.com/l/llNDe?a=460470163) that everyone keeps recommending. After reading it cover to cover, I want to share what I found genuinely useful and what I felt was missing.

Let me start with the good stuff, because there's plenty: this book is probably the most straightforward resource you'll find on DynamoDB. No fluff, no endless introductions about what NoSQL is. DeBrie gets right to the point with real code examples you can copy and adapt. The cheatsheets at the end are pure gold - I literally keep them open whenever I'm working. JavaScript, Python, all the basic operations, transactions, queries... everything ready to use.

What I appreciated most was how he explains the famous single-table design. This is something that blows your mind at first if you come from SQL, and the book breaks it down in a way that finally clicks. It doesn't just tell you "do it this way" - it explains when it makes sense and when you should look for alternatives. As an architect, that's what I need - understanding the "why" so I can defend technical decisions.

The sections on relationship modeling are really solid. It gives you clear strategies: if you have this scenario, use this pattern; if you have that one, go with this other approach. It's like having a mental framework so you're not improvising every time you design a table.

Now, where I feel the book falls short is in real-world scenarios. It's perfect for learning the fundamentals, but when your system starts to actually grow, questions come up that the book doesn't answer. For example, how do you migrate a table in production without bringing down your service? What do you do when you start having performance issues and need to debug? The book mentions these things in passing but doesn't dive deep.

I also felt there's not enough about monitoring and observability. In a cloud-native environment, this is critical. You need to know which metrics to watch, how to set up alerts, how to identify hot partitions before everything explodes. These topics barely get touched.

And something that's important for us in LATAM: costs. The book explains what RCUs and WCUs are, but there's not much analysis on how to optimize your AWS bill. When you're working with tight budgets, this isn't a minor detail. When does on-demand vs provisioned capacity make sense for your actual use case? How does your table design impact the money you'll spend each month? Would have been great to see more of that.

All that said, do I recommend it? Absolutely yes. If you're starting with DynamoDB or coming from relational databases and need to make the mental switch, this book will save you months of trial and error. The cheatsheets alone are worth the investment.

But heads up, don't expect it to be the only source you'll need. You'll have to complement it with AWS documentation, experiment quite a bit on your own, and probably make some mistakes along the way to really learn. The book gives you solid foundations, then real production experience completes your education.

My advice if you're going to buy it: read it all the way through first, then use the cheatsheets as a constant reference, and build your own case studies based on the real projects you're working on. That's how you'll get the most out of it.

I give it a 4 out of 5. It's excellent for what it aims to be - a practical, straightforward guide to DynamoDB. It's just that there are production and operational aspects you'll have to look for elsewhere. If you like it, you can find the book here.

Has anyone else read it? I'd love to know what you thought, especially if you already have DynamoDB running in production and what challenges you encountered that the book doesn't cover.

#AWS #DynamoDB #WebDevelopment #CloudComputing #BackendDevelopment