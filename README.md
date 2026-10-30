# AutoLoc API

Projet Spring Boot/Maven de gestion de location de véhicules multi-agences, réalisé à partir des ateliers ASI.

## 1. Prérequis

- JDK 17 ou supérieur
- IntelliJ IDEA (Ultimate est pratique pour Spring, mais Maven peut aussi être lancé depuis un terminal)
- WampServer avec le service MySQL/MariaDB démarré
- Git et Postman

Le dépôt GitHub initial contient uniquement le README et le `.gitignore`. Le code de l'application est maintenant organisé dans `src/main/java`.

## 2. Préparer WAMP

1. Ouvrir WampServer et vérifier que l'icône est verte.
2. Démarrer le service MySQL/MariaDB. Apache n'est pas nécessaire pour l'API Spring Boot.
3. Ouvrir `http://localhost/phpmyadmin`.
4. Créer une base vide appelée `autoloc_db` avec le jeu de caractères `utf8mb4`.

Par défaut, l'application utilise `root` sans mot de passe sur `localhost:3306`. Si ton installation WAMP a un mot de passe ou un autre port, adapte `src/main/resources/application.properties` ou définis ces variables d'environnement :

```text
DB_HOST=localhost
DB_PORT=3306
DB_NAME=autoloc_db
DB_USERNAME=root
DB_PASSWORD=ton_mot_de_passe
```

Hibernate crée et met à jour les tables avec `spring.jpa.hibernate.ddl-auto=update`. Pour un vrai déploiement, utilise plutôt des migrations Flyway et `ddl-auto=validate`.

## 3. Ouvrir le projet

1. Cloner le dépôt :

```bash
git clone https://github.com/isradebbabi/AutoLoc.git
cd AutoLoc
```

2. Ouvrir le dossier dans IntelliJ.
3. Sélectionner le JDK 17 dans `File > Project Structure > Project SDK`.
4. Activer `Settings > Build, Execution, Deployment > Compiler > Annotation Processors > Enable annotation processing` pour Lombok.
5. Laisser IntelliJ importer le projet Maven.

## 4. Lancer l'application

Depuis IntelliJ, exécuter `AutolocApiApplication`. Avec Maven :

```bash
mvn spring-boot:run
```

L'URL racine est `http://localhost:8080/api`.

La documentation interactive est disponible ici :

- Swagger UI : `http://localhost:8080/api/swagger-ui.html`
- OpenAPI JSON : `http://localhost:8080/api/v3/api-docs`

Après le premier démarrage, actualiser phpMyAdmin : les tables `agence`, `vehicule`, `client`, `reservation`, `contrat`, `paiement`, `maintenance`, `equipement` et `employe` doivent apparaître.

## 5. Premier scénario Postman

Créer une collection `AutoLoc-API` et utiliser l'URL de base `http://localhost:8080/api`.

Une collection Postman prête à importer se trouve dans `postman/AutoLoc-API.postman_collection.json` et son environnement local dans `postman/AutoLoc-Local.postman_environment.json`.

Dans Postman : `Import > Files`, importer les deux fichiers, activer l'environnement `AutoLoc - local`, puis lancer les requêtes dans l'ordre : création agence, création véhicule, création client, puis création réservation.

Créer une agence :

```http
POST /agences
Content-Type: application/json
```

```json
{
  "nom": "Agence Tunis Centre",
  "ville": "Tunis",
  "adresse": "12 avenue Habib Bourguiba",
  "telephone": "+216 71 000 000"
}
```

Créer un véhicule rattaché à l'agence créée :

```http
POST /vehicules
Content-Type: application/json
```

```json
{
  "immatriculation": "123 TUN 4567",
  "marque": "Toyota",
  "modele": "Yaris",
  "categorie": "CITADINE",
  "tarifJournalier": 120.00,
  "statut": "DISPONIBLE",
  "agence": { "id": 1 }
}
```

Créer un client :

```http
POST /clients
Content-Type: application/json
```

```json
{
  "nom": "Ben Ali",
  "prenom": "Amine",
  "email": "amine.benali@example.com",
  "telephone": "+216 20 000 000",
  "numPermis": "TN-123456"
}
```

Créer une réservation avec les identifiants précédents :

```http
POST /reservations
Content-Type: application/json
```

```json
{
  "dateDebut": "2026-11-01",
  "dateFin": "2026-11-05",
  "statut": "EN_ATTENTE",
  "client": { "id": 1 },
  "vehicule": { "id": 1 }
}
```

## 6. Endpoints disponibles

| Ressource | Endpoints |
|---|---|
| Agences | `GET/POST /agences`, `GET/PUT/DELETE /agences/{id}` |
| Véhicules | `GET/POST /vehicules`, `GET/PUT/DELETE /vehicules/{id}`, `GET /vehicules/disponibles?ville=Tunis&categorie=CITADINE` |
| Clients | `GET/POST /clients`, `GET/PUT/DELETE /clients/{id}` |
| Employés | `GET/POST /employes`, `GET/PUT/DELETE /employes/{id}` |
| Réservations | `GET/POST /reservations`, `GET/PUT/DELETE /reservations/{id}` |
| Contrats | `GET/POST /contrats`, `GET/PUT/DELETE /contrats/{id}` |
| Paiements | `GET /paiements`, `GET /paiements/{id}`, `GET /paiements/contrat/{contratId}` |
| Maintenances | `GET/POST /maintenances`, `GET/DELETE /maintenances/{id}` |
| Équipements | `GET/POST /equipements`, `GET/PUT/DELETE /equipements/{id}` |

Le paiement est volontairement en lecture seule, conformément à l'atelier.

## 7. Correspondance avec les ateliers

- Chapitre 1 : JDK, IntelliJ, WAMP/MySQL, Postman et Git.
- Chapitre 2 : projet Maven, JPA, entités et enums.
- Chapitre 3 : associations JPA, fetch, cascade et table de jointure `vehicule_equipement`.
- Chapitre 4 : repositories Spring Data, tri et pagination des véhicules.
- Chapitre 5 : injection de dépendances, services et `@Transactional`.
- Chapitre 6 : contrôleurs REST, CRUD, validation, gestion globale des erreurs et SpringDoc.

Les étapes plus avancées de la présentation générale (AOP, Scheduler, DTO complets, sécurité et SonarQube) sont des extensions prévues pour les séances suivantes ; elles ne sont pas nécessaires pour démarrer ce socle CRUD.

## 8. Git

```bash
git add .
git commit -m "Implement AutoLoc Spring Boot API"
git push origin main
```

Si Git demande l'identité :

```bash
git config --global user.name "Ton nom"
git config --global user.email "ton-email@example.com"
```
