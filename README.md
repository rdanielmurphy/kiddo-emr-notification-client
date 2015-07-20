# kiddo-emr-notification-client
Android app that receives push notifications sent from the the java swing application (kiddo-emr-notification-sender) and stores them in a DB.

This app as well as the server app was created at a hackathon for Kiddo EMR (http://www.kiddoemr.com/), with the purpose of creating a way for doctors to send health tips to their patients.

Here are the steps to use the notification sender application and the notifictaion client application:

1.  Set up GCM (Google cloud messaging).  You need a gmail account for this:
  * a. https://console.developers.google.com/project
  * b. Create a new project
  * c. Go to APIs & Auth->APIs->Cloud Messaging Android and Enable it

2.  Replace GCMConstants.PROJECT_API_KEY with your API key found at APIs & Auth->APIs->Credentials

3.  Replace GCMConstants.PROJECT_NUMBER with your Project Number found in the Overview tab (at the top right)

4.  Run the Android app and register the phone to GCM
  * a. Click Register For Tips
  * b. Click Get RegId.  Copy the key you get in the box and save it for later (email to it yourself is what I would do)

5.  Run the Java Swing app.  
  * a. Click Add Phone
  * b. Paste code you got from the phone and then click 'Add Phone'

6.  Go back to the Send Message Tab and send a tip!

## On improving the project:

This is just an example.  Before this would go into production, you would want the server and the client apps to store the phone registration keys on a database somewhere that could be accessed through an API, instead of copying and pasting the keys into the server side java swing application.
