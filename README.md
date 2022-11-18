* Java 17
* Spring boot
* Gradle


Run it:

$ gradle assemble

$ docker build . -t $username/employee-message:0.0.1

$  docker run  --name employee-message -p 8080:8080 -d thiagobfim/employee-message:0.0.1`
