---
layout: page
title: Jia Cheng's Project Portfolio Page
---

### Project: PropertyWhiz

Property Whiz is a CLI customer relationship management application made for property agents. PropertyWhiz helps to keep track of properties and prospective buyers, then match properties and buyers.

Given below are my contributions to the project.

* **New Feature**: Added the one to many matching command.
  * What it does: Allows the user to match one property to many buyers, and to match one buyer to many properties.
  * Justification: This feature allows the user (property agent) to find the most compatible properties for one of his/her clients, as well as to find the clients most likely to make a purchase on a certain property.
  * Highlights: This enhancement is the first command that requires some form of sorting, i.e. in addition to `Predicate`s for filtering, this command requires a `Comparator` to order buyers and properties in terms of compatibility. As the JavaFX library offers either `SortedList` and `FilteredList` and not both, we need to choose between several candidate implementations to stack predicates and comparators. This requires weighing of costs and benefits as some implementations, like wrapping `SortedList` around `FilteredList`, are easier with less boilerplate but slower in performance. The implementation choice of a list that can be both sorted and filtered will affect other commands manipulating the list as well.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=sunjc826&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=sunjc826&tabRepo=AY2122S1-CS2103T-W11-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
  * Managed the final release of `v1.3` on Github
  * Updated CI to ignore missing end of lines for txt files
  * Managed documentation issues and tasks across milestones

* **Enhancements to existing features**:
  * Updated the tagging command to append and remove tags ([\#31](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/31))
  * Expanded CRUD commands, `add`, `edit`, and `delete` to act on properties and buyers ([\#56](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/56))

* **Documentation**:
  * User Guide:
    * Added documentation for the features `add (property | buyer)`, `edit (property | buyer)` [\#56](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/56)
    * Added documentation for the tagging improvements [\#31](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/31)
  * Developer Guide:
    * Refactor DG's Design section and diagrams from AddressBook3 to PropertyWhiz [\#94](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/94)
    * Added implementation details of the `match (property | buyer)` (one to many matching) feature [\#112](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/112)

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#134](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/134), [\#114](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/114), [\#70](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/70), [\#97](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/97)
