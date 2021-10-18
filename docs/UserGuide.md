---
layout: page
title: User Guide
---

PropertyWhiz (PropertyWhiz) is a **desktop app for managing properties, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, PropertyWhiz can get your property management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `propertywhiz.jar` from [here]().

**TODO**: Update and release jar link.

1. Copy the file to the folder you want to use as the _home folder_ for your PropertyWhiz.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)
TODO: We may also want `clear` to delete all buyers
1. [**DEVELOPMENT IN PROGRESS**] Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all properties.

   * **`add`** Adds a property. e.g. `add property n/Blk 123 a/123, Clementi Rd, #04-20, 1234665 s/James Lee sp/61234567 $/100000 max/100000 t/HDB t/3rm`

   * **`delete`** `delete property 3` : Deletes the 3rd property shown in the current list.

   * **`clear`** : Deletes all properties.

   * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add property n/PROPERTY_NAME`, `PROPERTY_NAME` is a parameter which can be used as `add property n/Beautiful Condo`.

* Items in square brackets are optional.<br>
  e.g `n/PROPERTY_NAME [t/TAG]` can be used as `n/Beautiful Condo t/condo` or as `n/Beautiful Condo`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/condo`, `t/condo t/family` etc.

* Items in circle brackets `()` separated by `|` means that you can only choose 1 of the partitioned items <br>
  e.g. `([t/TAG] | [ta/TAG_TO_ADD] [t/TAG_TO_DELETE])` can be used as `t/condo`, `ta/condo td/small condo` but not `t/condo ta/condo`, `t/condo td/small condo`, `t/condo ta/condo td/small condo`

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a property/buyer: `add`

Adds a property/buyer to PropertyWhiz.

Format: `add (property PROPERTY_OPTIONS​ | buyer BUYER_OPTIONS​) [t/TAG]…` e.g., `add property n/Blk 123 a/123, Clementi Rd, #04-20, 1234665 s/James Lee sp/61234567 $/100000 max/100000 t/hdb t/3rm`

Format for `PROPERTY_OPTIONS`: `n/PROPERTY_NAME a/PROPERTY_ADDRESS s/SELLER_NAME p/SELLER_PHONE e/SELLER_EMAIL $/PRICE_MIN`

Format for `BUYER_OPTIONS`: `n/BUYER_NAME p/BUYER_PHONE e/BUYER_EMAIL $/PRICE_MAX`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A property/buyer can have any number of tags (including 0). All tags will be converted to lowercase.
</div>

#### Adding a property

Adds a property to PropertyWhiz

Format: `add property n/PROPERTY_NAME a/PROPERTY_ADDRESS s/SELLER_NAME p/SELLER_PHONE e/SELLER_EMAIL $/PRICE_MIN`

Examples:
* `add property n/Blk 123 a/123, Clementi Rd, #04-20, 1234665 s/James Lee p/61234567 e/james@email.com $/100000 max/100000 t/hdb t/3rm`
#### Adding a buyer

Adds a buyer to PropertyWhiz

Format: `add buyer n/BUYER_NAME p/BUYER_PHONE e/BUYER_EMAIL $/PRICE_MAX`

Examples:
* `add buyer n/Sam p/91234567 e/sam@email.com t/hdb t/3rm`


### Listing all properties/buyers : `list`

Shows a list of all properties and buyers in PropertyWhiz.

Format: `list`

### Editing a property/buyer : `edit`

Edits an existing property/buyer in PropertyWhiz.

Format: `edit (property INDEX PROPERTY_OPTIONS | buyer INDEX BUYER_OPTIONS) [([t/TAG]… |​ [ta/TAG_TO_ADD]… [td/TAG_TO_DELETE]…)]`

Format for `PROPERTY_OPTIONS`: `[n/PROPERTY_NAME] [a/PROPERTY_ADDRESS] [$/PRICE_MIN] [s/SELLER_NAME] [p/SELLER_PHONE] [e/SELLER_EMAIL]`

Format for `BUYER_OPTIONS`: `[n/BUYER_NAME] [p/BUYER_PHONE] [e/BUYER_EMAIL]`

* Edits the property/buyer at the specified `INDEX`. The index refers to the index number shown in the displayed property/buyer list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the property will be removed i.e adding of tags is not cumulative.
* Like `add`, tags added via `edit` will be automatically converted to lower case.
* You can remove all the property’s tags by typing `t/` without
    specifying any tags after it.

#### Editing a property

Format: `edit property INDEX [n/PROPERTY_NAME] [a/PROPERTY_ADDRESS] [$/PRICE_MIN] [s/SELLER_NAME] [p/SELLER_PHONE] [e/SELLER_EMAIL]`

Examples:
* `edit property 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st property to be `91234567` and `johndoe@example.com` respectively.
* `edit property 2 n/Blk 298 Toa Payoh Central t/` Edits the name of the 2nd property to be `Blk 298 Toa Payoh Central` and clears all existing tags.
* `edit property 1 ta/4rm ta/near mrt` Edits the tag list of the 1st property by adding two tags called "4rm" and "near mrt" if they are not already present in the original tag list.
* `edit property 1 ta/4rm td/near mrt` Edits the tag list of the 1st property by adding a tag called "4rm" if it does not already exist in the original tag list and removing a tag called "near mrt" if it is present in the original tag list.
* `edit property 1 ta/near MRT` Edits the tag list of the 1st property by adding a tag called `near mrt` if it does not already exist in the original tag list. Notice that the case of `MRT` is lowered to `mrt`.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
The following example is invalid:

`edit property 1 t/near school ta/4rm td/near mrt` You cannot reset the tag list of a property, in this case to ["near school"], *and* modify the resetted tag list by adding a tag called "4rm" and removing a tag called "near mrt". The rationale is that this may be potentially confusing.

</div>

#### Editing a buyer

Format: `edit buyer INDEX [n/BUYER_NAME] [p/BUYER_PHONE] [e/BUYER_EMAIL]`

Examples: 
* `edit buyer 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st buyer to be `91234567` and `johndoe@example.com` respectively.

TODO: Find command adapted for property/buyers
### Locating properties by name: `find`

Finds properties whose names contain any of the given keywords and whose tag list contain all of the specified tags.

Format: `find [KEYWORDS] [t/TAG_TO_MATCH]…`

* The keyword search is case-insensitive. e.g `hillview` will match `Hillview`
* The order of the keywords does not matter. e.g. `Hillview Rise` will match `Rise Hillview`
* Only the property name is searched.
* Only full words will be matched e.g. `Han` will not match `Hillview`
* The tag search is case-insensitive, e.g. both `t/mrt`, `t/MRT` will match the `mrt` tag.
* Properties matching at least one keyword (i.e. `OR` search) and matching all the tags (i.e. `AND` search) will be returned.
  * e.g. For keywords, `Hillview Rise` will return `Hillview Grove`, `Rise Rivervale`
  * e.g. For tags, `t/4rm t/near school` will return properties with both `4rm` tag, and `near school` tag.

Examples:
* `find Jurong` returns `jurong` and `Jurong East`
* `find Jurong t/4rm t/near school` returns `jurong [4rm] [near school] [near mrt]` and `Jurong East [4rm] [near school] [near mrt]` but not `jurong [4rm] [near mrt]`
* `find t/4rm t/near school` returns `jurong [4rm] [near school] [near mrt]` and `Clementi [4rm] [near school] [near mrt]`

### Deleting a property/buyer : `delete`

Deletes the specified property/buyer from PropertyWhiz.

Format: `delete (property|buyer) INDEX`

* Deletes the property/buyer at the specified `INDEX`.
* The index refers to the index number shown in the displayed property/buyer list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete property 2` deletes the 2nd property in PropertyWhiz.
* `find East Coast` followed by `delete property 1` deletes the 1st property in the results of the `find` command.

### Sort properties/buyers: `sort`

Sorts the properties / buyers in PropertyWhiz.

Format: `sort (properties/buyers) (price/name) (asc/desc)`

* Sort the properties in the current view based on properties price/name in `asc`(ascending) / `desc`(descending) order
* Sort the buyers in the current view based on buyers price/name in `asc`(ascending) / `desc`(descending) order

Examples:

* `sort properties price asc` returns the property list sorted by price in ascending order
* `sort buyers name desc` returns the buyer list sorted by name in descending order

### Import data from csv file : `import`

Imports buyers or properties from csv file.

Format: `import buyers` or `import properties`

* You can select the import file location from a pop-up dialog box.

TODO: Add example of valid csv

### Export data to csv file : `export`

Exports buyers or properties from csv file.

Format: `export buyers` or `export properties`

* You can select the export file location from a pop-up dialog box.

Example result of  `export property`
```
"Name","Address","Seller Name","Phone","Email","Price","Tags"
"Geylang St 29","Blk 30 Geylang Street 29, #06-40","Alex Yeoh","87438807","alexyeoh@example.com","654321","hdb,4-rm"
"Dee Gardens","Blk 30 Lorong 3 Serangoon Gardens, #07-18","Beatrice Yu","99272758","berniceyu@example.com","654321","hdb,5-rm"
```

Example result of `export buyer`
```
"Name","Phone","Email","Budget","Tags"
"Alibaba","61234567","alibaba@baba.com","1999999","condo,landed"
```

### Clearing all entries : `clear`

Clears all entries from PropertyWhiz.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

PropertyWhiz's data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

## Advanced features

This section contains features intended for advanced users.

### Editing the data file

PropertyWhiz's data are saved as a JSON file `[JAR file location]/data/propertywhiz.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, PropertyWhiz will discard all data and start with an empty data file at the next run.
</div>

### Multi-command input

You may copy and paste multiple lines of commands into the command box. Press the Enter key to execute the first command and autofill the next into the command box. This continues until you enter a different command or all commands have been executed.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous PropertyWhiz home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add (property n/PROPERTY_NAME a/PROPERTY_ADDRESS s/SELLER_NAME p/SELLER_PHONE e/SELLER_EMAIL $/PRICE_MIN \| buyer n/BUYER_NAME p/BUYER_PHONE e/BUYER_EMAIL $/PRICE_MAX) [t/TAG]…​` <br> e.g., `add property n/Blk 123 a/123, Clementi Rd, #04-20, 1234665 s/James Lee sp/61234567 $/100000 t/HDB t/3rm`
**Clear** | `clear`
**Delete** | `delete (property\|buyer) INDEX`<br> e.g., `delete property 3`
**Edit** | `edit (property INDEX [n/PROPERTY_NAME] [a/PROPERTY_ADDRESS] [$/PRICE_MIN] [s/SELLER_NAME] [p/SELLER_PHONE] [e/SELLER_EMAIL] \| buyer INDEX [n/BUYER_NAME] [p/BUYER_PHONE] [e/BUYER_EMAIL]) [([t/TAG]… \|​ [ta/TAG_TO_ADD]… [td/TAG_TO_DELETE]…)]​`<br> e.g.,`edit property 2 s/James Lee e/jameslee@example.com`
**Find** | `find [KEYWORDS] [t/TAG_TO_MATCH]…`<br> e.g., `find James Jake`
**List** | `list`
**Exit** | `exit`
**Help** | `help`
**Sort** | `sort (properties | buyers) (price | name) (asc | desc)`
