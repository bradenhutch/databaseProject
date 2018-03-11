To begin, you must first have mysql, JDK, and sbt installed. I recommend JDK 8.

Create a database called "databaseProject" within mysql and leave it empty. Make sure to create a user within mysql and take note of their username and password.

Create an application.conf file based on the application.conf.example file found in the conf folder. Insert the new mysql user's credentials in the space for them on lines 330 and 331.

Once everything is installed and set up, use <code>sbt run</code> in the root folder and the site will run on port 9000.

You may need to configure the hosts for your application if you wish to run on anything other than your localhost.