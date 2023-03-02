FROM 192.168.41.248/base_images/spring-boot:openjdk8
ADD agent-client.jar agent.jar /opt/appl/spring-cloud/
EXPOSE 8080
ENTRYPOINT ["java", "-javaagent:/opt/appl/spring-cloud/agent.jar", "-jar","/opt/appl/spring-cloud/agent-client.jar"]