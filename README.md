# AP2-ass3
AP2-ass3 By Ori Arad, Tal Gelerman and Itamar Cohen

In this assignment, we created an Android client for our chats system. The app supports the server from the previous assignment, which means that an Android user can send a message to a web user (and the other way around). 

# The pages
Our project includes 3 main pages and one setting page and a “add contact” page in the chat page.
Our pages include the same functionality and content as the previous assignment and with a new fresh design!
A user cannot get notifications when he is on the login page or registration page.

### login
The homepage. The first screen that the user interacts with. In this screen, the user is able to log in to the website system by entering his unique username and password. The login page contains different features that will help the user in other cases: *"create a user" for cases in that the user is not registered yet. After clicking this button, the registration page will be opened.

- for a user that is trying to log in to the page, the system checks if the username and the password are related to a registered member, and if he is, he will be moved to the chat screen. Otherwise, the user will get a message saying he is not registered. As we said, the user can easily create a user by clicking the "create a user" button under the login button.
- a user cannot go into the chat page without login the login page.

### Registration
the user will interact with the screen in case he is not registered and he clicked the "create a user" button in the login page. In this screen, the user is asked to fill in different fields, such as: First name, Last name, Username, and more. The registration page also includes a double-check for passwords for the user's security. When clicking the "sign up" button, the user will be officially registered and will be joined to the website's users community. Therefore, will be able to log in to the system with his nickname and password in the login page.
•	Username is unique and therefore, if a new member wants to choose a username that is already taken the register validation won't let him create an account.
•	In our website the new member password should contain:
o	Password must contain 8 characters minimum.
o	Password must contain at least one capital letter.
o	Password must contain at least one lower letter.
o	Password must contain at least one number.
o	Username must be at least 3 characters long.
o	Profile picture has to be a picture format (if the user doesn't upload a picture our website provides a default picture).
•	If a password doesn't match the requirements above, the page won't let to create a user and a message will be shown on the screen.
•	If the password and the confirm password are not equal the page won't let to create the user.
•	The first name and last name will be shown as the display name on the chat page.

### chat page
A registered user will interact with this screen after entering his correct username and password in the login screen.  The page consists of 3 screens: one screen for the user contacts that he has chatted with, and the second screen is for the user clicks a specific contact. The screen contains the chat with all messages between the user and his contacts. The third screen is shown when the user clicks the “add contact” button on the first screen with all the contacts. In this screen, the user is able to write a username that he wants to add to the contacts chat list (will be elaborated later). When sending a message to a user, the most recent message and its time will be shown under the contact's name. By clicking the logout button, the user will be moved back to the login page.
-	The user can't create a chat with himself.
-	We can create more than one chat with the same user.
  
### setting
This is the setting page. The user can reach this page from any other page by clicking the settings icon at the top right or top left of the screen.
The setting page contains two main features:
- A night mode radio button – enables the user to switch from day mode to night mode and the opposite. The change is reflected by a darker design. When the user closes the app, the dark mode is reset. 
- Server URL – a tool that enables the user to move between different servers. The URL must start with http:// or https:// and has to end with a port (:PORT_NUMBER). For example http://23.54.12.34:5000 the client is automatically adding the /api/ to the URL for convenient purposes.
In addition, if the user is logged in (which means he got the setting page from the chats page), his name and profile picture will be displayed, and a logout button too.

# Communication between users
1.	Add the other user by clicking “add contact” on the chat page, inserting the other user’s username, and clicking “add contact” again.
2.	A new chat with the other user will be created and will be added to the chats list. The user will get a notification (implemented by Firebase) to his phone that the user added him.
3.	Click the other user’s name and the chat page will be open.
4.	Write your message in the textbox and click the button on the right to the textbox.
5.	The message will be sent and will be shown on the chat history page and as the last message under the other user’s name in the chats list. 
6.	The other user will get a notification on his phone saying that he got a message from the user and the message content.

   
- The system keeps the data locally on a local database. That fact enables the user to be exposed to all the data that was before.
- When the user tries to log in, the system checks if the user was logged in before, and if so, it updates his personal details with the local database while it asks the server for the updates data. If not, it deletes the database in order to keep the previous logged-in user’s privacy. One database.
- The data is updated and synchronized consistently with the server.
- Pay attention that the notification settings are on.
- Please notice that your data in on.
- Navigation is with the phone tools!!


