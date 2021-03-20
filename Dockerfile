
FROM java:8
EXPOSE 9090
ADD /target/soap2rest.jar soap2rest.jar

ENTRYPOINT ["java","-jar","soap2rest.jar"]