---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}
* [JFreeChart](https://www.jfree.org/jfreechart/) for providing the API to display statistics and charts.
* [JavaFX](https://gluonhq.com/products/javafx/) for providing the API to render GUI.

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/AY2122S1-CS2103T-W11-4/tp/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called [`Main`](https://github.com/AY2122S1-CS2103T-W11-4/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2122S1-CS2103T-W11-4/tp/blob/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.


**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete property 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2122S1-CS2103T-W11-4/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PropertyListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2122S1-CS2103T-W11-4/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2122S1-CS2103T-W11-4/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Property` and `Buyer` objects residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2122S1-CS2103T-W11-4/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:
1. When `Logic` is called upon to execute a command, it uses the `AddressBookParser` class to parse the user command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `ListCommand`) which is executed by the `LogicManager`.
   Some subclassed commands are themselves abstract (e.g. `AddCommand` is an abstract subclass of `Command`, and `AddPropertyCommand` is a concrete subclass of the `AddCommand`. In this case, an instance of `AddPropertyComand` will be created).
3. The command can communicate with the `Model` when it is executed (e.g. to add a property).
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete property 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2122S1-CS2103T-W11-4/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Property`, `Buyer` and `Match` objects (which are contained in a `UniquePropertyList` (resp. `UniqueBuyerList`, `UniqueMatchList`) object).
* stores the currently 'selected' `Property` and `Buyer` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Property>` (resp. `ObservableList<Buyer>`) that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

TODO: Should this be removed?
<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2122S1-CS2103T-W11-4/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in json format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### One to many matching between properties and buyers

This family of commands all extend from the abstract `MatchOneToManyCommand` class. 
* The `MatchPropertyCommand` class matches 1 property to many buyers. It is triggered when the user enters the `match property INDEX` command.
* The `MatchBuyerCommand` class matches 1 buyer to many properties. It is triggered when the user enters the `match buyer INDEX` command.
Due to the highly symmetrical nature of these 2 commands, only `MatchPropertyCommand` will be elaborated upon.

The property to be matched with buyers is chosen by index based on the currently visible list of properties. The potential buyer matches are taken from the currently visible list of buyers. There may be buyers in the address book that are not being displayed, perhaps due to a currently active filter. Such buyers will not be taken into consideration when conducting the matching. The rationale for this is so that the `match` command's output is predictable.

Given a property, the algorithm first aims to filter out the incompatible matches, then outputs a sorted list of compatible buyers ordered by decreasing desirability. A buyer is compatible with a property if the buyer's budget is greater than the property's selling price. If a buyer can afford a property, then the level of desirability is determined by the number of tags that the buyer and the property have in common.

The sequence diagram below shows the algorithm at work. 

![MatchOneToManySequenceDiagram](images/MatchOneToManySequenceDiagram.png)

1. The algorithm first retrieves the current visible property list and selects the property `p` to be matched using the `targetIndex` attribute of `MatchPropertyCommand`.
2. The algorithm creates a property filter `pp` to only show `p` in the visible list. The rationale here is so that the user can clearly see which property the buyers are matched to.
3. The algorithm creates a buyer filter `bp` to select only the buyers whose `maxPrice` attribute (budget) is greater than the `price` attribute (selling price) of the property `p`.
4. The algorithm creates a comparator `bc` to which will be used later to sort the filtered buyers. Buyers with more common tags with `p` will come first. Amongst buyers with the same number of tags, buyers with a higher budget will come first.
5. The algorithm calls the `model` to update the visible property list with predicate `pp` and updates the visible buyer list with predicate `bp` and comparator `bc`.

Please refer to the `MatchOneToManyCommand`, `MatchPropertyCommand`, `MatchBuyerCommand` classes for the full details of the implementation.

#### Design considerations:
As mentioned above, only `MatchPropertyCommand` will be elaborated upon, hence these design considerations are discussed in the context of matching one property to many buyers.
**Aspect: Displaying property after match**
* **Alternative 1 (current choice)**: Display only the property to be matched, so that the resulting property list only has 1 visible property.
    * Pros: Easy to implement. Also, this is less distracting for the user as there are no other properties displayed to cause confusion.
    * Cons: In the current UI implementation, there is wastage of space as the property side of the display only has 1 property list item, and the property box is mostly empty.
* **Alternative 2** : Continue to display all the previously visible properties, and bring the property to be matched to the top of the list. Additionally, extra CSS classes and styles are added to the list item containing the property to be matched for emphasis.
    * Pros: No space is wasted as the original visible list is retained. Additionally, this can be more convenient. The user can enter `match property INDEX`, choosing to match another property without needing to reset the property list via the `list` command.
    * Cons: More difficult to implement as it requires additional UI flourishes. Even with the CSS classes and styles added for emphasis, the resulting display may still be visually messy and negatively impact the user experience.

**Aspect: One-to-many matching algorithm**
* **Alternative 1 (current choice)**: Filter out buyers whose budgets are below property's selling price.
    * Pros: Easily testable implementation.
    * Cons: The criteria may be too strict as in real life, there can be negotiations that can change the selling price of a property, or the price a buyer is willing to pay. 
* **Alternative 2** : Allow matches between buyers and sellers even when the buyer's budget is below the property's selling price.
    * Pros: A more realistic implementation.
    * Cons: More difficult to test.

Comments: **Alternative 2** is the approach taken by the automated matching algorithm, as the automated matching algorithm reflects a less rigid, more heuristic focused matching philosophy and aims to attain a "smarter" matching.

### Automated matching of properties and buyers

The automatic matching feature performs a one-to-one matching of buyers to properties based on price and tags in common. This section describes the algorithm and explains its rationale.

This matching is done by the `MatchAutoCommand` class, and is triggered when the user enters the `match auto` command. The algorithm takes in the currently visible list of properties and buyers, and outputs a list of buyer-property matches. Note that each buyer is matched to at most one property and vice versa.

The goal of the algorithm is to allow the user to discover compatible buyers and properties. Compatibility is determined by a _Match Score_, which is calculated based on the number of tags the buyer and property have in common, as well as the buyer's budget and property price.

The activity diagram below illustrates the algorithm:

![MatchAutoActivityDiagram](images/MatchAutoActivityDiagram.png)

1. The algorithm generates all possible pairs between the list of buyers and properties.
2. The algorithm sorts all these candidate matches by the match score.
3. Starting with the match with the highest score, the algorithm accepts each match whose buyer and property have not been previously matched. This continues until all candidate matches are evaluated.
4. The list of accepted matches is then returned to the UI to be displayed.

Please refer to the `MatchAutoCommand` and `Match` classes for the full details of the implementation, including the calculation of the _Match Score_.

#### Design considerations:

**Aspect: Matching Output**

* **Alternative 1 (current choice):** One-to-one matching between properties and buyers.
    * Currently, each buyer is matched to at most one property and vice versa.
    * Pros: Easier for users to comprehend.
    * Cons: Displays less information to users.

* **Alternative 2:** Many-to-many matching
    * Alternatively, each buyer and property can be simultaneously matched to many others.
    * Pros: Potentially convey more information to the user.
    * Cons: Harder for users to comprehend and navigate the information.

We decided to go with Alternative 1 as we prioritised presenting a simpler and more intuitive output to our users.

**Aspect: Matching algorithm**

* **Alternative 1 (current choice):** Prioritise top matches
    * The current algorithm selects matches starting with the best possible match. This optimises for the compatibility of the first few matches.
    * Pros: First few matches are likely to be very compatible.
    * Cons: Last few matches are likely to be poor.

* **Alternative 2:** Prioritise overall fairness of matches
    * An alternative algorithm might optimise for the number of acceptable matches.
    * Pros: Allows more buyers to be matched with acceptable properties.
    * Cons: First few matches may not be as compatible as Alternative 1.

We chose Alternative 1 as we expect the user to focus on the top matches, hence we optimised for those.

### Sort feature
The sort feature allows the user to sort the properties and buyers in PropertyWhiz.
The feature consists of the following commands:
* `sort properties` - Sorts the properties in PropertyWhiz.
* `sort buyers` - Sorts the buyers in PropertyWhiz.

There are 2 sorting options in PropertyWhiz:
* `SortType` - Enum class that represents the attributes of buyers and properties that can be sorted. Currently, only names and prices of buyers and properties can be sorted. These are represented by `SortType.NAME` and `SortType.PRICE` respectively.
* `SortDirection` - Enum class that represents the direction, ascending or descending, of the sort. These are represented by `SortDirection.ASC` and `SortDirection.DESC`.

#### Parsing of commands within the `Logic` component
The parsing of commands is done in the `LogicManager` and when executed, which results in `SortCommand` object being created. 
Since both properties and buyers can be sorted, `SortCommand` is abstract and `SortPropertyCommand` and `SortBuyerCommand`are concrete subclasses that extend `SortComand`.
The `SortCommandParser` serves as the intermediate layer between `LogicManager` and `SortCommand` to handle parsing of arguments of the user sort command. 

Given below are the steps to parse a sort user command:

Step 1. `AddressBookParser` will check if the command is a sort command. The `AddressBookParser` will then create a `SortCommandParser`.

Step 2. `SortCommandParser` will parse the arguments of the command to get the list, sort type and sort direction to be sorted by calling static methods in `ParserUtil`.

Step 3. Depending on the list to be sorted, the corresponding subclass of `SortCommand` will be created:
   * `sort properties <args>`: `SortPropertyCommand`
   * `sort buyers <args>`: `SortBuyerCommand`

   The user input for types of `<args>` can be found in the [UserGuide](UserGuide.md#sorting-propertiesbuyers-sort).

Given below is a sequence diagram for interactions inside the `Logic` component for the `execute("sort properties price asc")` API call.

![SortParsingSequenceDiagram](images/SortParsingSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `SortCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

#### Execution of commands within the `Logic` component
After parsing of the user input into a `SortCommand`, the `LogicManager` calls `SortCommand#execute(model)` with a `model`.

The `Model` interface exposes the following operations to sort the buyers and properties list:
* `Model#sortProperties(sortType, sortDirection)`
* `Model#sortBuyers(sortType, sortDirection)`

`ModelManager` implements the the `Model` interface. `SortPropertyCommand` will call `ModelManager#sortProperties(sortType, sortDirection)` and `SortBuyerCommand` will call `ModelManager#sortBuyers(SortType, SortDirection)`.

`ModelManager` will call methods of the encapsulated `AddressBook`: `AddressBook#sortProperties(sortType, sortDirection)` or  `AddressBook#sortBuyers(sortType, sortDirection)`.

`AddressBook` will call the `sort(sortType, sortDirection)` method of either `UniquePropertyList` or `UniqueBuyerList` to sort the properties or buyers.

Lastly, a `CommandResult` object containing the message to be displayed to the user is created and returned to the `LogicManager`.

Given below is a sequence diagram for the execution of a `SortPropertyCommand`.
![SortExecutionSequenceDiagram](images/SortExecutionSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `SortPropertyCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

#### Design considerations:
**Aspect: Implementation of `SortCommand#execute(model)`** 
* **Alternative 1 (current choice)**: Pass the `SortType` and `SortDirection` from the `SortCommand` to the `Model`.
  * Pros: Easy to implement.
  * Cons: Need to pass the arguments through many layers before reaching `UniquePropertyList`.
* **Alternative 2** : Implement many methods in the `Model` to represent the different combinations of sort types and directions and call these method directly in `SortCommand#execute(model)`.
  * Pros: Better abstraction.
  * Cons: Too many different combinations, and as a result, too many methods in the `model`, if there is a need to extend the sort options in the future.

### Statistics Diagram Pop-ups

#### Implementation

The mechanism for handling and presenting statistics is facilitated by the classes implementing `Stat`.

[JFreeChart](https://www.jfree.org/jfreechart/) is the third-party library used to generate charts in the
program. If more charts are to be implemented in the future, JFreeChart supports a wide range of graphing
and chart drawing capabilities, as can be seen from its 
[API](https://www.jfree.org/jfreechart/javadoc/index.html) here.

Currently the only type of diagram supported is a price histogram of the visible properties and/or buyers,
facilitated by `HistogramStat` in the subpackage `seedu.address.ui.stats`.

The `CommandResult` class now includes an attribute that contains an `Optional<UiElement>` that contains
an element that a `UiPart` can handle the rendering of.

![StatUiClassDiagram](images/StatUiClassDiagram.png)

Given below is an example usage scenario and how the statistics diagram is generated and then presented.

Step 1. The user executes `stat property` to display the statistics of the properties on screen. The `stat` command is parsed by `AddressBookParser#parseCommand`, which creates
a `StatCommandParser` which parses the argument `property` passed to it.

Step 2. `StatCommandParser` identifies if the user want to show the prices for buyers, properties or both, and creates a `StatCommand` which creates
a `Stat` object that is passed via a `CommandResult` to the MainWindow.

<div markdown="span" class="alert alert-info">:information_source: **Note:** The `Stat` interface implements the `Stat#create` method that creates a `JFreeChart`. 

</div>

Step 3. Upon receiving the `Stat` object, the `MainWindow` creates the `JFreeChart` to be presented by calling `Stat#create`, before passing the chart to `StatWindow`.

Step 4. `StatWindow` updates the statistics window with the latest `JFreeChart` it has received.

#### Future extensions:

Currently the `stat` command only displays a price histogram with a fixed number of 10 bins (columns).
Here are several extensions that can be implemented in the future:

1. Allow the user to enter how many bins they want to see in the histogram.

1. Allow the user to choose between different types of charts.

#### Design considerations:

**Aspect: How to create the `JFreeChart` histogram's dataset before `ChartFactory` creates the chart:**

* **Alternative 1:** Use the `HistogramDataset` class to automatically generate the dataset.
    * Pros: Easy to implement.
    * Cons: JFreeChart's default dataset generated by `HistogramDataset` has many visual bugs with small datasets.

* **Alternative 2 (current choice):** Create a fixed number of `SimpleHistogramBins`, rendered by a `BarRenderer`.
    * Pros: Less visual bugs, finer control of graph visuals.
    * Cons: More verbose code, may have a greater performance hit when dealing with larger datasets.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of properties and buyers
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage properties and buyers faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `H`, Medium (nice to have) - `M`, Low (unlikely to have) - `L`

| Priority | As a …​                                    | I want to …​                     | So that I can…​                                                        |
| ---- | ------------------------------------------ | ------------------------------ | -------------------------------------------------------------------------- |
| `H`  | new user                                   | see usage instructions         | refer to instructions when I forget how to use the App                     |
| `H`  | user                                       | add a property                 | keep track of the property                                                 |
| `H`  | user                                       | delete a property              | stop keeping track of the property                                         |
| `H`  | user                                       | edit property details          | edit details directly instead of deleting and adding                       |
| `H`  | user                                       | list all properties            | easily see all properties                                                  |
| `H`  | user                                       | find property by name          | locate details of property without having to go through the entire list    |
| `H`  | user                                       | update property tags           | provide details about a property                                           |
| `H`  | user                                       | find property by tags          | easily see related properties with a given tag                             |
| `H`  | user                                       | add a buyer                    | keep track of the buyer                                                    |
| `H`  | user                                       | delete a buyer                 | stop keeping track of the buyer                                            |
| `H`  | user                                       | edit buyer details             | edit details directly instead of deleting and adding                       |
| `H`  | user                                       | list all buyers                | easily see all buyers                                                      |
| `H`  | user                                       | update buyer tags              | provide details about a buyer                                              |
| `H`  | user                                       | find buyer by tags             | easily see related buyers with a given tag                                 |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `PropertyWhiz` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Delete property**

**MSS**

1.  `User` requests to delete a specific _property_ by specifying its index
2.  `PropertyWhiz` deletes the property

    Use case ends.

**Extensions**

* 1a. The index is invalid.

  * 1a1. `PropertyWhiz` throws an error message.

    Use case ends.


**Use case: UC02 - Modify property**

**MSS**

1. `User` requests to modify a property
2. `PropertyWhiz` edits attributes of the property

    Use case ends.

**Extensions**

* 1a. `User` does not provide new attributes.

  * 1a1. `PropertyWhiz` throws an error message.

    Use case ends.

*{More to be added}*

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Should be able to function without internet access.
5. Should be able to restart without loss of data.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Property**: A property listed for sale
* **Buyer**: A person who expresses interest in a range of properties

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

TODO: More test cases needed
### Deleting a property

1. Deleting a property while all properties are being shown

   1. Prerequisites: List all properties using the `list` command. Multiple properties in the list.

   1. Test case: `delete property 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete property 0`<br>
      Expected: No property is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete property x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
