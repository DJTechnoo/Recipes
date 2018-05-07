# Report

This is Askel Eirik Johansson's report.

## Development process

The development process started with planning
the JSON structure for the Firebase database.
The next step was to deside how to traverse the app
with activities, and implement the navigation to these.
Then the core logic was implemented, with some final adjustments
upon finishing the project, such as deletion functionality.

## Challenging, and What I've learned

THe most challenging part was to pasre the data into
objects, and implement the algorithm for sorting the 
recipes based on how many of the same items are in the 
fridge.
What I learned was that a Firebase database should be
flat, on not overly nested.
I also learned how to programmatically add buttons
while the app is running.

## Features

The core features were to create recipes,
add items if your posession to the fridge, 
have recipe-list sorted on them,
and be able to add items (from the recipes) directly
to your fridge list.

The project requirements initially instructed to change the button colors if the item is not owned,
however, since I used a listview I simple added buttons below to indicate items not being owned yet.

Furthermore the shopping list should make use of a swipe-to-delete function.
I used a press-to-delete instead.

## Testing

The testing has been done after each iteration.
I created some simulation data in the FireBase database.
I tested different scenarios with the core features.
The testing for robustnes has admittingly and regrettable 
been reduced at this point.

## Linter warnings

Regrettable

Method invocation 'hideSoftInputFromWindow' may produce 'java.lang.NullPointerException'
This is simply to hide the keyboard if it should automatically pop up on new Activity.

All typos will remain as they are.

```
Not targeting the latest versions of Android; compatibility <br/>
modes apply. Consider testing and updating this version. <br/>
Consult the android.os.Build.VERSION_CODES javadoc for details.
```
and 
```
A newer version of com.google.firebase:firebase-database than 11.0.4 is available: 15.0.0
```
Are hard to fix, since there is a kind of bug that doesn't let me open my project without getting warnings.

```
On SDK version 23 and up, your app data will be automatically backed up, and restored on app install.
```
Prompts me to create a new Activity for Google Search. 
This won't be necessary as it won't be released in stores.

```
This folder configuration ('v24') is unnecessary; 'minSdkVersion' is 25. 
Merge all the resources in this folder into 'drawable'.
```
The fix to this warning caused major problems, so the folder is necessary.

```
All other lin correctedness warnings
```
I'm using older versions because the newer versions give the classic "version conflict" -bug,
which I suspect is coming from my IDE. The problem lies on my side.
