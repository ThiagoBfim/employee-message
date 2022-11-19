* Java 17
* Spring boot
* Gradle


### Run it:

$ gradle assemble

$ docker build . -t $username/employee-message:0.0.1

$  docker run  --name employee-message -p 8080:8080 -d thiagobfim/employee-message:0.0.1`

### Push to cloud

docker login 


docker tag thiagobfim/employee-message:0.0.1 default-route-openshift-image-registry.apps.sandbox.x8i5.p1.openshiftapps.com/thiagobfim-dev/employee-message:0.0.1

