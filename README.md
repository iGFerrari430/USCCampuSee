# USCCampuSee
  An Android platform to post, edit and view events taking place at USC Campus

## Explanation of Geofence
  Inside our zip file, there are two projects, “USCCampuSee” is our main project, and the existence of “TestGoogleMap” is because our group could not successfully implement the geofence. 
  When our group tried to implement the geofence in USCCampuSee, the geofence does not work successfully. We believe it is because of the incompatibility with the firebase storage. Unfortunately, we faced a technical issue that the Firebase Storage conflict with Geofire/realtime database and we failed to figure out a solution to it at this time. To make up for it, we created another text project called TestGoogleMap attached it in our zip file. This text project successfully implements the geofence. We create circles for three areas, and when the device enters/exits the circle, it will send notification with text "You enter/exit the area". We will be very grateful if we could get partial credits for this functionally.
  The longitude and latitude of the three circles are: LatLng(37.422,-122.044), LatLng(37.422,-122.144), LatLng(37.422,-122.244)

## Detailed Steps to Run the APP
  ### Log in and Sign up
  After opening our main project “USCCampuSee”, the home page with two buttons to log in and sign up will be displayed. 
  
  If the person clicks the sign up button and go into the sign up page. In order to sign up, the person should not only enter email with standard email format and password, but also select to sign up as a user or as a publisher. After entering the information and click submit button, the account will be successfully created and the user automatically logged in.
  
  If the person clicks the log in button and go into the log in page, the person should also enter the information of email, password and identity as a user or publisher. Then if the person enters the information correctly, he/she will be logged in.
  
  ### Log in as a Publisher
  If the person logs in as a publisher, the first view he/she will see is a DashBoard with all of his/her posts that have been created before. It also contains a button to log out as well. 
  If the person clicks either one of the post, it will go to the page that shows the detail information of that post, together with a "back" button to go back to last page, and a "edit button" to go to the edit page to edit the information of this post.
  If the person clicks the "add post" button, it will go to the page to create new posts. That page requires the person to enter title and description, select event data and time, and add/remove pictures. After doing these and click the "submit post" button, a new post will be created, the person will go to the DashBoard page, and the newly created post will be  showed in the DashBoard.
  
  ### Log in as a User
 If the person logs in as a user, the first page he/she will see is the welcome page that contains three buttons: "view publishers", "view events" and "log out". Obviously, "log out" button is used for the person to log out.
 If the person clicks "view publishers" button, he/she will see the list of all our publishers. If the person clicks one of the publisher's email on the list, user will be redirected to another page where he/she can subscribe our unsubscribe that publisher.
 If the person clicks "view events" button he/she will see the list of all our posts. If the person clicks one of the post, it will go to another page to show the details of that post, and the publisher's email of that post as well.
