FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o jar gerado no build
COPY /build/libs/*.jar app.jar

# Porta padrão do Spring Boot
EXPOSE 8080

# Evita rodar como root (boa prática)
RUN useradd -r -u 1001 spring
USER spring

ENTRYPOINT ["java", "-jar", "app.jar"]