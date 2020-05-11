FROM openjdk:11.0-jre-slim
WORKDIR /app
#ENV DEPLOYMENT_PROFILE=dev
COPY /target/stockapp.jar /app/
CMD ["java", "-jar", "stockapp.jar"]