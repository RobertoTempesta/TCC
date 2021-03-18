#!/bin/bash
mvn clean flyway:migrate test package -e -f  /usr/src/mymaven  &&
find /usr/src/mymaven/target -name "*.war" -exec cp '{}' /usr/local/tomcat/webapps \;