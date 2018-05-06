# Report

This is Askel Eirik Johansson's report.

## Development process

The development process started with planning
the JSON structure for the Firebase database.
The next step was to deside how to traverse the app
with activities, and implement the navigation to these.
Then the core logic was implemented, with some final adjustments
upon finishing the project, such as deletion functionality.

## Challenging, anad What I learned

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
add items you own to the fridge, 
have recipes sort based on items you posess,
and be able to add items (from the recipes) directly
to your fridge list.

The project requirements initially instructed to change the button colors if the item is not owned,
however, since I used a listview I simple added buttons below to indicate items not being owned yet.

Furthermore the shopping list should make use of a swipe-to-delete function.
I used a press-to-delete instead.
