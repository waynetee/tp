---
layout: page
title: Zhang Guangxuan's Project Portfolio Page
---

### Project: PropertyWhiz

Property Whiz is a CLI customer relationship management application made for property agents. PropertyWhiz helps to keep track of properties and prospective buyers, then match properties and buyers.

Given below are my contributions to the project.

* **New Feature**: Added the Export Command
  * What it does: allows the user to export properties and buyers from the current view to a csv file.
  * Justification: This feature allows for users to move the data in PropertyWhiz to another platform.
  * Highlights: This enhancement brings about a new type of command- the `CommandWithFile`. `CommandWithFile` required the addition of `CommandPreAction` to indicate that a FileChooserDialog is required for the user to select a file. 
  * Credits: [opencsv](http://opencsv.sourceforge.net/) for writing csv files

* **New Feature**: Added the Import Command
  * What it does: allows the user to import properties and buyers from a csv file to the current view.
  * Justification: This feature allows for users to move the data from another platform to PropertyWhiz.
  * Highlights: Together with Export Command, `CsvManager` was added as a static, purely functional class. The parsing of csv file was tricky and involved in multiple exceptions. Careful analysis of cases was required to display an appropriate error message.
  * Credits: [opencsv](http://opencsv.sourceforge.net/) for reading and parsing csv files

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=guangxuan&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=guangxuan&tabRepo=AY2122S1-CS2103T-W11-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
  * Created `v1.3`, `v1.4` milestones
  * Fixed CI checks to ignore missing end lines in csv files

* **Enhancements to existing features**:
  * Abstracted out `Person` class to be used in `Property` and `Buyer` classes [\#51](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/51), [\#54](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/54)

* **Documentation**:
  * User Guide:
    * Added documentation for the features `export` and `import` [\#142](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/142)
  * Developer Guide:
    * Performed initial refactoring of DG from AddressBook 3 to PropertyWhiz [\#19](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/19)
    * Added implementation details of the `ipmort` feature. [\#114](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/114)

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#26](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/26), [\#92](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/92), [\#52](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/52)
  
* **Tools**:
  * Integrated a third party library ([opencsv](http://opencsv.sourceforge.net/)) to the project: [\#62](https://github.com/AY2122S1-CS2103T-W11-4/tp/pull/62)
