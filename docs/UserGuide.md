---
layout: page
title: User Guide
---

NUScheduler is a desktop app for **managing contacts, optimised for use via a Command Line Interface (CLI)** while still having the benefits of a Graphical User Interface (GUI). If you can type fast, NUScheduler can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `NUScheduler.jar` from [here](https://github.com/AY2223S1-CS2103T-T17-3/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your NUScheduler.

4. Double-click the file to start the app.

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`profile -a`** : Adds profile.

   * **`profile -d `**`2` : Deletes the 2nd profile shown in the current list.

   * **`profile -v`** : Lists profiles or shows a single profile.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

> Notes about the command format:
> - Words in `UPPER_CASE` are the parameters to be suppplied by the user. <br>
> e.g. in `profile -a n/NAME p/PHONE`, NAME and PHONE are parameters which can be used as `profile -a n/John p/91234567`. 
> - Items in square brackets are optional. <br>
> e.g. `profile -v [INDEX]` can be used as `profile -v 2` or as `profile -v`.

### View Profile: `profile -v`

Shows either a list of profiles or a single profile.

Format: `profile -v [INDEX]`

Tip: `INDEX` is optional, specify to view single profile.

### Add Event `event -a`

Adds an event with a name and a start timing and end timing.

Format: `event -a n/NAME s/START e/END [p/PROFILE]`

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a profile: `profile -a`

Adds a profile to NUScheduler. 

Format: `profile -a n/NAME p/PHONE_NUMBER e/EMAIL`

Examples:
* `profile -a n/John Doe p/98765432 e/johnd@example.com`
* `profile -a n/Betsy Crowe e/betsycrowe@example.com p/1234567`

### Deleting a profile: `profile -d`

Deletes a specified profile from NUScheduler.

Format: `profile -d INDEX`

* Deletes the profile at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Example:
* `profile -d 1` deletes the first profile listed.

### Deleting an event: `event -d`

Deletes a specified event from NUScheduler.

Format: `event -d INDEX`

* Deletes the events at the specified `INDEX`.
* The index refers to the index number shown in the displayed event list.
* The index **must be a positive integer** 1, 2, 3, ...

Example:
* `event -v` followed by `event -d 2` deletes the 2nd event displayed.

--------------------------------------------------------------------------------------------------------------------

## FAQ

No FAQ Yet.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add Profile** | `profile -a n/NAME p/PHONE`
**Delete Profile** | `profile -d INDEX`
**View Profile** | `profile -v [INDEX]`
**Add Event** | `event -a n/NAME n/START n/END [p/PROFILE]`
**Delete Event** | `event -d INDEX`
**View Event(s)** | `event -v [INDEX]`
**View Upcoming** | `event -u DAYS`
