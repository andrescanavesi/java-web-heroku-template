# A Java web app template ready for Heroku deployment

The idea is to have a template with some common configurations such as necessary files
for Heroku deployment, JPA (with Hibernate), JSF, Primefacs, Postgres, Maven dependencies / plugins among other things

## Complete list of technologies
* Java 8
* Maven 3
* JSF 2.2.7 for presentation
* Primefaces 6.1 for presentation
* JPA 2.1
* Hibernate 5.2.10
* Netbeans 8.2
* Force.com Partner API for Salesforce communication
* GSON 2.8.0 for serializing / parsing json
* Postgres SQL for persistence
* Apache httpcode 4.4 for doing http requests

## Tomcat environment variables

As I explain [here](https://andrescanavesi.wordpress.com/2017/08/25/define-environment-variables-in-tomcat/)
Sometimes you want to change the behaviour of your code without recompiling / deploying.
A way to do this is defining environment variables that you can change at any time.

### Mac and Linux
Create a file called `$TOMCAT_HOME/bin/setenv.sh`

Define the variable like this:

`export VAR1=my_var1_value`

`export VAR2=my_var2_value`

Give execution permission:

`chmod 755 setenv.sh`

### Windows

Define variables like this:

`set VAR1=my_var_value`


## Heroku deployment configurations

In the root you project create a file called *Procfile* with the content

`web:    java $JAVA_OPTS -jar target/dependency/webapp-runner.jar --port $PORT target/*.war`

Optionally you can create a file called *system.properties* to specify the version of Java
to use in Heroku, the content could be:

`java.runtime.version=1.8`

Add the following plugin:

```xml
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <version>2.3</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>copy</goal>
                        </goals>
                        <configuration>
                            <artifactItems>
                                <!-- required for heroku deployment -->
                                <artifactItem>
                                    <groupId>com.github.jsimone</groupId>
                                    <artifactId>webapp-runner</artifactId>
                                    <version>8.0.30.2</version>
                                    <destFileName>webapp-runner.jar</destFileName>
                                </artifactItem>

                            </artifactItems>

                        </configuration>

                    </execution>
                </executions>
            </plugin>
```

## Findbugs plugin

This plugin is for... well, you know

```xml
            <!-- to run this plugin: mvn findbugs:gui -Dfindbugs.skip=false -->
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>findbugs-maven-plugin</artifactId>
                <version>3.0.4</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>check</goal>
                            <goal>findbugs</goal>
                            <goal>gui</goal>
                        </goals>
                        <id>check</id>
                    </execution>
                </executions>
                <configuration>
                    <foo>bar</foo>
                </configuration>
            </plugin>
```
## Worker

The class com.example.java.web.heroku.template.util.MyWorker contains just a main method
to be called by a Heroku worker dyno through the Profile using this sentence:

```
worker: java -cp  target/classes:target/dependency/*:target/java-web-heroku-template-1.0-SNAPSHOT/WEB-INF/lib/* com.example.java.web.heroku.template.util.MyWorker ""
```