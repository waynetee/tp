---
layout: page
title: Pei Xian's Project Portfolio Page
---

### Project: PropertyWhiz

PropertyWhiz is a desktop application that helps property agents manage their properties and clients.

Given below are my contributions to the project.

* **New Feature**: Added the `stat` command. ([\#92](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/92))
  * What it does: Lets users visualise visible properties/buyers' prices in a bar chart.
  * Justification: This provides property agents with a fuss free way of visualising the market.
  * Highlights: Uses a third-party library [JFreeChart](https://www.jfree.org/jfreechart/) to generate the graphs. 
    The default implementation of a histogram in JFreeChart was buggy with sample data that is too small, 
    hence I wrote a custom implementation of the bar chart to use here instead.
  * Credits: With thanks to [JFreeChart](https://www.jfree.org/jfreechart/).

* **New Feature**: Developing the model for the separate buyer and property panels in the app.
  ([\#58](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/58))
  * What was added/changed: Refactored `UniquePersonList` into a generic `UniqueList` from which `UniqueBuyerList`,
    `UniquePropertyList` and now `UniqueMatchList` inherits.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=trxe&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=trxe&tabRepo=AY2122S1-CS2103T-W11-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

* **Enhancements to existing features**:
  * Update tags, address validation to include special characters instead of just alphanumericals.
    ([\#123](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/123), [\#189](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/189))

* **Documentation**:
  * User Guide:
    * Cleaned up User Guide bugs after PE Dry Run [\#194](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/194)
  * Developer Guide:
    * Added implementation details of the `stat` feature.

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#55](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/55), [\#31](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/31)
  * Reported critical bugs and suggestions for other teams in the class
    * [\#237](https://github.com/AY2122S1-CS2103T-W17-4/tp/issues/237): Academic week number exceeds real academic week, and doesn't show any weeks past week 20 (2021-12-27)
    * [\#275](https://github.com/AY2122S1-CS2103T-W17-4/tp/issues/275): Week number underflow (negative integers allowed up until -20)
    * [\#243](https://github.com/AY2122S1-CS2103T-W17-4/tp/issues/243), [\#225](https://github.com/AY2122S1-CS2103T-W17-4/tp/issues/225): 
      Lacking error checking or alerting user of erroneous input

* **Tools**:
  * Integrated a third party library (JFreeChart) to the project
