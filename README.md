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

**Privacy Policy**

 

built the My Wishlist app as a Free app. This SERVICE is provided by at no cost and is intended for use as is.

 

This page is used to inform visitors regarding my policies with the collection, use, and disclosure of Personal Information if anyone decided to use my Service.

 

If you choose to use my Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that I collect is used for providing and improving the Service. I will not use or share your information with anyone except as described in this Privacy Policy.

 

The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, which is accessible at My Wishlist unless otherwise defined in this Privacy Policy.

 

**Information Collection and Use**

 

For a better experience, while using our Service, I may require you to provide us with certain personally identifiable information. The information that I request will be retained on your device and is not collected by me in any way.

 

The app does use third party services that may collect information used to identify you.

 

Link to privacy policy of third party service providers used by the app

 

*   [Google Play Services](https://www.google.com/policies/privacy/)

 

**Log Data**

 

I want to inform you that whenever you use my Service, in a case of an error in the app I collect data and information (through third party products) on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing my Service, the time and date of your use of the Service, and other statistics.

 

**Cookies**

 

Cookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device's internal memory.

 

This Service does not use these “cookies” explicitly. However, the app may use third party code and libraries that use “cookies” to collect information and improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.

 

**Service Providers**

 

I may employ third-party companies and individuals due to the following reasons:

 

*   To facilitate our Service;
*   To provide the Service on our behalf;
*   To perform Service-related services; or
*   To assist us in analyzing how our Service is used.

 

I want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.

 

**Security**

 

I value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and I cannot guarantee its absolute security.

 

**Links to Other Sites**

 

This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by me. Therefore, I strongly advise you to review the Privacy Policy of these websites. I have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.

 

**Children’s Privacy**

 

These Services do not address anyone under the age of 13. I do not knowingly collect personally identifiable information from children under 13\. In the case I discover that a child under 13 has provided me with personal information, I immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact me so that I will be able to do necessary actions.

 

**Changes to This Privacy Policy**

 

I may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. I will notify you of any changes by posting the new Privacy Policy on this page.

 

This policy is effective as of 2020-06-18

 

**Contact Us**

 

If you have any questions or suggestions about my Privacy Policy, do not hesitate to contact me at nukar1@student.op.ac.nz.

 

This privacy policy page was created at [privacypolicytemplate.net](https://privacypolicytemplate.net) and modified/generated by [App Privacy Policy Generator](https://app-privacy-policy-generator.firebaseapp.com/)