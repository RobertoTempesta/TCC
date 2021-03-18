FROM tomcat:8.5

ADD . /code
WORKDIR /code
#COPY tomcat-users.xml  $CATALINA_HOME/conf/
VOLUME $CATALINA_HOME/webapps