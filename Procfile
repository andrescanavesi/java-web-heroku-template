web:    java $JAVA_OPTS -jar target/dependency/webapp-runner.jar --port $PORT target/*.war
worker: java -cp  target/classes:target/dependency/*:target/java-web-heroku-template-1.0-SNAPSHOT/WEB-INF/lib/* com.example.java.web.heroku.template.util.MyWorker ""