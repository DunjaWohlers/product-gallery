FROM openjdk:18

ENV ENVIRONMENT=prod

LABEL maintainer="dunjawohlers@gmx.de"

ADD backend/target/product-gallery.jar product-gallery.jar

CMD [ "sh", "-c", "java -Dserver.port=$PORT -jar /product-gallery.jar" ]
