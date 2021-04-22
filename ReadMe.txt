I am using Ubuntu, so I only show instructions how to run the code on Ubuntu 18.04, for window, you can download "bash" to run it.
To run the project locally:

Open terminal (or bash if using window)
**Run the back-end
	- Import the project "React+java" by using any Ide. I recommend using "Eclipse IDE for Enterprise Java Developers - 2020-09"
	- Go to the file "pom.xml", right-click, select "Maven", select "Update Project" to load all the neccessary dependencies.
	- Go to src/main/resources, open the file application.properties. In this file, please remove all of my Mysql database
	credential and to yours.
		spring.datasource.url = jdbc:mysql://localhost:3306/new_schema (replace the  "localhost:3306/new_schema" to your local datasource url.) 
		spring.datasource.username=root (replace "root" with your username if you are using a different username)
		spring.datasource.password=Kingsbury22  (Replace "Kingsbury22" with your password"
	- GO to src/main/java/myapp.springstarter, run the file "SpringstarterApplication.java" to run the back end.

**Run the front-end
Go to the my-project folder: 
	- Run "npm install" to install on the dependencies.
	- Run "npm start" to initialize the website.
Good luck !!!