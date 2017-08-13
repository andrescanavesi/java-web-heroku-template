# java-web-heroku-template
A template of a Java web application using Heroku, JPA, Postgres, maven among other technologies

#Tomcat environment configs

##Mac and Linux
Create a file called $TOMCAT_HOME/bin/setenv.sh

Define the variable like this:
export VAR1=my_var1_value
export VAR2=my_var2_value

Give execution permission:
chmod 755 setenv.sh

##Windows

Define variables like this
set VAR1=my_var_value

# TODO
- List of technologies (Hibernate, Java 8, Primefaces, etc)
- Heroku deployment (maven plugin and proc file)
- TODO add SLDS
