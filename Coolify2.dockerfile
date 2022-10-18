FROM node:16 as frontend-builder

ADD frontend /frontend

RUN cd /frontend && npm install &&  npm run build


FROM maven:3-openjdk-18 as backend-builder

ADD backend /backend

COPY --from=frontend-builder /frontend/build /backend/src/main/resources/static

RUN cd /backend && mvn -B package


FROM openjdk:18

ENV SPRING_PROFILES_ACTIVE=prod

LABEL maintainer="dunjawohlers@gmx.de"

COPY --from=backend-builder /backend/target/product-gallery.jar /product-gallery.jar

CMD [ "sh", "-c", "java -Dserver.port=$PORT -jar /product-gallery.jar" ]
