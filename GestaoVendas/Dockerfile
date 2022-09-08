FROM openjdk:11

EXPOSE 8080

RUN apt-get update && \
 apt-get install -y netcat;

COPY /target/gestaoVendas-2.4.1.jar /app/gestao_vendas.jar
COPY /waitformysql.sh /app/waitformysql.sh
WORKDIR /app
# ENTRYPOINT [ "java", "-jar", "/app/gestao_vendas.jar" ]