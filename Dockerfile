FROM docker.kevinlu.cc/env/jdk-8u191:190114

COPY ./target/yd-quartz-1.0.0-SNAPSHOT.jar /root/startup/yd-quartz.jar

WORKDIR /root/startup

EXPOSE 8080

CMD ["java", "-Xms1024m", "-Xmx1024m", "-DAPP_DOMAIN=yd-quartz", "-jar", "yd-quartz.jar"]