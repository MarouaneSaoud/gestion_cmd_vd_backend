# Étape 1 : Construction de l'image (build)
FROM maven:3-openjdk-17 AS build

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier pom.xml pour télécharger les dépendances
COPY pom.xml .

# Télécharger toutes les dépendances Maven
RUN mvn dependency:go-offline -B

# Copier le code source dans le conteneur
COPY src ./src

# Compiler et empaqueter l'application sans exécuter les tests
RUN mvn clean package -DskipTests

# Étape 2 : Création de l'image d'exécution (runtime)
FROM openjdk:17-slim

# Définir le répertoire de travail pour l'exécution
WORKDIR /app

# Copier le fichier JAR généré depuis l'étape précédente
COPY --from=build /app/target/gestion_cmd_vd_backend-0.0.1-SNAPSHOT.jar ./gestion_cmd_vd_backend.jar

# Copier le fichier application.yml dans l'image Docker
COPY src/main/resources/application.yml ./application.yml

# Exposer le port que l'application va utiliser (par défaut 8090 pour Spring Boot)
EXPOSE 8090

# Commande pour démarrer l'application Spring Boot
ENTRYPOINT ["java", "-jar", "/app/gestion_cmd_vd_backend.jar"]
