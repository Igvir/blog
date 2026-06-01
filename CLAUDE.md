# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this repo is

A collection of blog posts published to [blog.igvir.com](https://blog.igvir.com) via [Hashnode](https://hashnode.com). Each `.md` file is an article. There is no build system or test suite — content is authored locally and published through Hashnode's import/sync flow.

## Post format

Every post uses Hashnode frontmatter:

```yaml
---
title: Post title
subtitle: Short description
slug: url-slug
tags: tag1, tag2, tag3
cover: https://cdn.hashnode.com/res/hashnode/image/upload/...
domain: blog.igvir.com
ignorePost: true   # optional — draft/hidden posts only
---
```

- `slug` becomes the URL path on Hashnode.
- `ignorePost: true` keeps the post from being published when syncing.
- Cover images must be hosted URLs. Use the [Hashnode uploader](https://hashnode.com/uploader) to get image URLs before adding a cover.
- Images referenced inline should also use hosted URLs (Hashnode CDN or equivalent).

## Language

Posts are written in **Spanish or English** depending on the topic and intended audience. Match the language already used in the post being edited.

## Branches

- `main` — published posts
- `draft` — work in progress
