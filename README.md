To begin, you must first have mysql, JDK, and sbt installed. I recommend JDK 8. You will also need to install mongodb and leave it running in another terminal (I recommend tmux) using <code>mongod</code>.

Create a database called "databaseProject" within mysql and leave it empty. Make sure to create a user within mysql and take note of their username and password.

Make sure to run the view, trigger, and procedure located in the triggerAndView.sql file in the mysql console before running the site.

Create an application.conf file based on the application.conf.example file found in the conf folder. Insert the new mysql user's credentials in the space for them on lines 330 and 331.

Once everything is installed and set up, use <code>sbt run</code> in the root folder and the site will run on port 9000.

You may need to configure the hosts in your application.conf file if you wish to run on anything other than your localhost.
