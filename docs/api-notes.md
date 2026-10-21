# Atelier 5 : API REST, tests Postman et documentation Swagger

## A. Endpoints

Préfixe commun `/api` défini par `server.servlet.context-path`. L'application tourne sur le port 8081. La collection Postman est dans `docs/AutoLoc.postman_collection.json`.

| Contrôleur | Verbe et route | Service appelé | Succès | Erreurs |
|---|---|---|---|---|
| AgenceController | GET /api/agences | findAll() | 200 | n/a |
| AgenceController | GET /api/agences/{id} | findById(id) | 200 | 404 |
| AgenceController | POST /api/agences | create(agence) | 201 | 400 |
| AgenceController | PUT /api/agences/{id} | update(id, agence) | 200 | 404, 400 |
| AgenceController | DELETE /api/agences/{id} | deleteById(id) | 204 | 404 |
| EmployeController | GET /api/employes | findAll() | 200 | n/a |
| EmployeController | GET /api/employes/{id} | findById(id) | 200 | 404 |
| EmployeController | POST /api/employes | create(employe) | 201 | 400 |
| EmployeController | PUT /api/employes/{id} | update(id, employe) | 200 | 404, 400 |
| EmployeController | DELETE /api/employes/{id} | deleteById(id) | 204 | 404 |
| VehiculeRestController | GET /api/vehicules | findAll() | 200 | n/a |
| VehiculeRestController | GET /api/vehicules/{id} | findById(id) | 200 | 404 |
| VehiculeRestController | POST /api/vehicules | create(vehicule) | 201 | 400 |
| VehiculeRestController | PUT /api/vehicules/{id} | update(id, vehicule) | 200 | 404, 400 |
| VehiculeRestController | DELETE /api/vehicules/{id} | deleteById(id) | 204 | 404 |
| EquipementController | GET /api/equipements | findAll() | 200 | n/a |
| EquipementController | GET /api/equipements/{id} | findById(id) | 200 | 404 |
| EquipementController | POST /api/equipements | create(equipement) | 201 | 400 |
| EquipementController | PUT /api/equipements/{id} | update(id, equipement) | 200 | 404, 400 |
| EquipementController | DELETE /api/equipements/{id} | deleteById(id) | 204 | 404 |
| ClientController | GET /api/clients | findAll() | 200 | n/a |
| ClientController | GET /api/clients/{id} | findById(id) | 200 | 404 |
| ClientController | POST /api/clients | create(client) | 201 | 400 |
| ClientController | PUT /api/clients/{id} | update(id, client) | 200 | 404, 400 |
| ClientController | DELETE /api/clients/{id} | deleteById(id) | 204 | 404 |
| ReservationController | GET /api/reservations | findAll() | 200 | n/a |
| ReservationController | GET /api/reservations/{id} | findById(id) | 200 | 404 |
| ReservationController | POST /api/reservations | create(reservation) | 201 | 400 |
| ReservationController | PUT /api/reservations/{id} | update(id, reservation) | 200 | 404, 400 |
| ReservationController | DELETE /api/reservations/{id} | deleteById(id) | 204 | 404 |
| ContratController | GET /api/contrats | findAll() | 200 | n/a |
| ContratController | GET /api/contrats/{id} | findById(id) | 200 | 404 |
| ContratController | POST /api/contrats | create(contrat) | 201 | 400 |
| ContratController | PUT /api/contrats/{id} | update(id, contrat) | 200 | 404, 400 |
| ContratController | DELETE /api/contrats/{id} | deleteById(id) | 204 | 404 |
| PaiementController | GET /api/paiements | findAll() | 200 | n/a |
| PaiementController | GET /api/paiements/{id} | findById(id) | 200 | 404 |
| PaiementController | POST /api/paiements | aucun (non exposé) | n/a | 405 |
| MaintenanceController | GET /api/maintenances | findAll() | 200 | n/a |
| MaintenanceController | GET /api/maintenances/{id} | findById(id) | 200 | 404 |
| MaintenanceController | POST /api/maintenances | create(maintenance) | 201 | 400 |
| MaintenanceController | PUT /api/maintenances/{id} | update(id, maintenance) | 200 | 404, 400 |
| MaintenanceController | DELETE /api/maintenances/{id} | deleteById(id) | 204 | 404 |

Les 404 viennent de `ResourceNotFoundException` et les 400 de `IllegalArgumentException` : le contrôleur ne les produit pas lui-même, c'est le `GlobalExceptionHandler` qui les traduit. Un 400 n'apparaît que lorsque le service applique la règle correspondante (valeur négative, date de fin antérieure, attribut obligatoire vide) ou lorsque le JSON est invalide.

## B. Annotations Jackson

| Association | Annotation | Justification |
|---|---|---|
| Contrat.paiements <-> Paiement.contrat | @JsonManagedReference / @JsonBackReference ("contrat-paiements") | Vraie composition (cascade ALL) : le paiement se crée dans le JSON du contrat, sans boucle infinie |
| (À COMPLÉTER : chaque liste inverse où vous avez réellement mis @JsonIgnore, ex. Agence.vehicules) | @JsonIgnore côté liste | L'enfant est créé seul et désigne son parent par un identifiant, ex. { "agence": {"id": 1} } |

**Différence** : `@JsonBackReference` fait ignorer la propriété côté parent lors de la sérialisation et la relie au parent à la désérialisation, ce qui convient à une composition. `@JsonIgnore` ignore simplement la propriété dans les deux sens, sans lien automatique : l'attribut propriétaire (côté enfant) garde son objet lié et le POST peut désigner le parent par un identifiant.

`@JsonBackReference` n'est pas utilisé sur `Reservation.client` : la propriété serait ignorée à la lecture et le POST créerait une réservation sans client.

## C. Postman et Swagger

- **Postman** : teste et rejoue des requêtes avec des assertions. Exemples : GET /api/contrats/9999 vérifié en 404, le script qui mémorise `contratId` après un POST, et le scénario de cascade (POST d'un contrat avec ses paiements, puis GET /api/paiements). Il oblige aussi à utiliser la variable `{{baseUrl}}` de l'environnement « local » plutôt qu'une URL écrite en dur, ce qui a permis de passer simplement au port 8081.
- **Swagger UI** : documente et explore l'API, avec un contrat synchronisé avec le code. Exemples : avant l'ajout de `@Tag`, les groupes portaient les noms par défaut des classes (`vehicule-rest-controller`) ; après annotation, les neuf groupes (Contrats, Paiements, Agences...) affichent résumés et réponses 200/404 documentées, et la section Schemas décrit `Contrat` à partir de l'entité.
- **Complémentarité** : Postman sert à tester (requêtes rejouables, vérification des réponses), Swagger UI sert à documenter et explorer.

## D. Anomalies SonarQube for IDE

L'analyse de `web.controller` a d'abord signalé 22 anomalies dans 10 fichiers.

| Règle | Explication | Correction |
|---|---|---|
| java:S1710 (Annotation repetitions should not be wrapped) | `@ApiResponse` est une annotation répétable : l'envelopper dans `@ApiResponses({...})` est inutile et alourdit le code. 4 occurrences dans `ContratController` | Suppression de `@ApiResponses`, `@ApiResponse` posés directement les uns sous les autres, suppression de l'import inutile |
| java:S125 (Sections of code should not be commented out) | Ancienne version de `PaiementController` laissée en commentaire : code mort qui alourdit le fichier et prête à confusion | Suppression du bloc commenté (l'historique reste dans Git) |
| Dépendance Maven dupliquée (`spring-boot-starter-webmvc`) | Même dépendance déclarée deux fois : `pom.xml` plus difficile à maintenir | Suppression du doublon, rechargement de Maven |
| java:S4684 (Database operations should not be vulnerable to mass assignment) | Une entité JPA reçue en `@RequestBody` permet à un client d'injecter des champs internes (identifiant, relations) écrits ensuite en base | Conservée volontairement dans cet atelier (entités exposées, voir énoncé) ; remplacée par des DTO au chapitre 10 |

## E. Questions de compréhension

1. **Quel composant reçoit toutes les requêtes ?** Le `DispatcherServlet`, point d'entrée unique. Il s'appuie sur le `HandlerMapping`, qui retrouve la méthode du contrôleur à appeler d'après le verbe HTTP et l'URL.
2. **Pourquoi `ContratController` dépend-il de `IContratService` ?** Le contrôleur doit rester mince : il reçoit la requête et délègue, sans accéder au repository ni contenir de logique métier. Dépendre de l'interface plutôt que de `ContratServiceImpl` permet de changer l'implémentation sans modifier le contrôleur et réduit le couplage.
3. **Pourquoi 201, 204 et 404 ?** Un POST renvoie 201 parce qu'il crée une ressource. Un DELETE renvoie 204 parce que la ressource est supprimée et qu'il n'y a rien à renvoyer. Une ressource absente est une erreur du client (identifiant inexistant), donc 404 : `ResourceNotFoundException` est traduite par le `GlobalExceptionHandler`. Un 500 signalerait une panne du serveur.
4. **Pourquoi `PaiementController` n'expose-t-il que des GET ?** Les paiements sont gérés à travers le Contrat (cascade ALL) : créer, modifier ou supprimer un paiement passe par le contrat. Un `POST /api/paiements` reçoit donc 405 Method Not Allowed.
5. **Risques de l'exposition directe des entités JPA ?** Couplage entre l'API et le modèle de base, champs internes visibles, mass assignment (S4684), boucles de sérialisation entre associations bidirectionnelles, proxys LAZY non sérialisables. `Contrat.paiements` utilise `@JsonManagedReference` car c'est une composition : les paiements se créent dans le JSON du contrat et le lien avec le parent doit être reconstruit. `Agence.vehicules` utilise `@JsonIgnore` car le véhicule est créé seul et désigne son agence par un identifiant : aucun lien à reconstruire côté liste.