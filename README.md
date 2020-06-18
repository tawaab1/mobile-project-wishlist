# mobile-project-wishlist

# mobile-development-wishlist



## Splash Screen
The launcher activity, is the landing screen when the app is launched and it contains an image which transitions before the activity ends and switches to the Main Activity. This is the main menu of the application

<img src="https://github.com/tawaab1/mobile-project-wishlist/blob/master/images/SplashActivity.png" width="300" height="500" />

## MainActivity
This is the homepage of the app and contains a recycle view which cycles through different album items. Each item contains an album image, the album name and the count.

<img src="https://github.com/tawaab1/mobile-project-wishlist/blob/master/images/MainActivity.png" width="300" height="500"/>

## DetailActivity
 By pressing on an item from the Main Activity, it will take to the details page where it gives you a "View >" option which will open a Web View that shows more details about the items you have selected.
 You can also interact with the like (heart) button to place the item you have selected into your "Cart". User whom have selected or unselected the like button will receive a notification about the action.



## Exit dialog from Menu
If the user presses back on their device a dialog appears and alerts the user to make a decision of exiting the app or not


All activity contains a BottomNavigation inner class that allows the user to navigate through 4 different activities/screens. This is displayed at the bottom of the app with its icons. It takes you directly to the
folowing:

- MainActivity
- ProfileActivity
- WishlistActivity
- MapActivity

<img src="https://github.com/tawaab1/mobile-project-wishlist/blob/master/images/MainActivity.png" width="300" height="500"/>

## ProfileActivity
Before the user can proceed to work in this activity they will be prompted to give permission for the app to access the storage folder and the camera. If the user has agreed to these prompts they can proceed to use the camera to take a snapshot for a profile picture. To access the camera, the user must interact with the mini camera icon just below the default image.

<img src="https://github.com/tawaab1/mobile-project-wishlist/blob/master/images/ProfileActivity.png" width="300" height="500"/>
<img src="https://github.com/tawaab1/mobile-project-wishlist/blob/master/images/ProfileActivity2.png" width="300" height="500"/>
<img src="https://github.com/tawaab1/mobile-project-wishlist/blob/master/images/ProfileActivity3.png" width="300" height="500"/>
<img src="https://github.com/tawaab1/mobile-project-wishlist/blob/master/images/ProfileActivity4.png" width="300" height="500"/>

## WishlistActivity
The activity's purpose is to allow a user to create a wishlist of items that a user wants to purchase, it contains the following features:

 - A sorting spinner and sorting button, which sorts the wishlist items the user has created
 - A category spinner and show only button, which allows the user to show only purchased, unpurchased and other items
 - A floating button which a user can press to create a new wishlist
 - A recycle view which displays all the wishlist items that the user has created
<img src="https://github.com/tawaab1/mobile-project-wishlist/blob/master/images/SortList.png" width="300" height="500"/>

 By interacting with the floating button the user will be taken to another screen where they can create a new wishlist. This contains:

 - An edit text feature where the user can create a name for the wishlist
 - An edit test feature where the user can create the status of the item
 - An add button which will accept the changes made
 - A close button which will cancel the creation of the item

<img src="https://github.com/tawaab1/mobile-project-wishlist/blob/master/images/WishlistActivity.png" width="300" height="500"/>
<img src="https://github.com/tawaab1/mobile-project-wishlist/blob/master/images/WishlistItemAdd.png" width="300" height="500"/>
 The wishlist items also contain an ellipses, this holds options such as:

 - "Edit", where the user will taken to the editing the item screen. This screen is similar to the creating an item screen, the only difference is that the add button will seen as "Update" button
 - "Delete", which shows an alert dialog prompting the user to make a choice of deleting an item or not.
 - If the user choose yes to delete, the item will be removed, if no, the item will remain.


<img src="https://github.com/tawaab1/mobile-project-wishlist/blob/master/images/UpdateDeleteItem.png" width="300" height="500"/>
<img src="https://github.com/tawaab1/mobile-project-wishlist/blob/master/images/DeleteItem.png" width="300" height="500"/>


## MapsActivity
Before the user can access/do anything on this activity they will be prompted to allow the app use the location of the user. If the user accepts, they will be taken to the map. The activity displays a google map that contains the following:

- Zooming in - individual markers of recording studios 
- Zooming out - cluster of markers within an area
- And the users fine location

<img src="https://github.com/tawaab1/mobile-project-wishlist/blob/master/images/MapActivityDialog.png" width="300" height="500"/>
<img src="https://github.com/tawaab1/mobile-project-wishlist/blob/master/images/MarkerCluster.png" width="300" height="500"/>
<img src="https://github.com/tawaab1/mobile-project-wishlist/blob/master/images/MarkerIndividual.png" width="300" height="500"/>

## Localization

If you wish to change the language used in the app just go to:

- Device Settings> General Management and Input > Language > Add Language > Set the prefered language as default
- This will also change the the language used on the app.
- The 3 languages available with the app is English, Filipino and Afrikaans
