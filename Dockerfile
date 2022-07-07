FROM openjdk:11
EXPOSE 8181
ADD target/User-module.jar User-module.jar
ENTRYPOINT ["java", "-jar", "/User-module.jar"]

