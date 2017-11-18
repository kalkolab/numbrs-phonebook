FROM java:8

EXPOSE 8080 8088

ADD /target/phonebook-1.0.jar phonebook.jar

ENTRYPOINT ["java","-jar","phonebook.jar", "server", "config.yaml"]