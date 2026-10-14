# Atelier 4 : IoC, injection de dépendances et couche Service

## A. Modes d'injection comparés

| Critère | Constructeur | Attribut |
|---|---|---|
| Champ `final` possible ? | Oui | Non |
| Dépendance visible dans la signature de la classe ? | Oui | Non (cachée) |
| Utilisable avec `new` hors conteneur sans dépendance `null` ? | Oui, le constructeur impose le repository | Non, le champ reste `null` |
| Signalé par SonarQube for IDE ? | Non | Oui (règle java:S6813, « Field dependency injection should be avoided ») |

## B. Services

| Service | Dépendances injectées | Mode | Justification |
|---|---|---|---|
| AgenceServiceImpl | IAgenceRepository | Constructeur | Dépendance obligatoire, champ final, typée par interface |
| EmployeServiceImpl | IEmployeRepository | Constructeur | Idem |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | Idem |
| EquipementServiceImpl | IEquipementRepository | Constructeur | Idem |
| ClientServiceImpl | IClientRepository | Constructeur | Idem |
| ReservationServiceImpl | IReservationRepository | Constructeur | Idem |
| ContratServiceImpl | IContratRepository | Constructeur | Idem |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Idem, service en lecture seule |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | Idem |

## C. Messages d'erreur du conteneur

**Message 1** : `NoSuchBeanDefinitionException` (enveloppée dans `UnsatisfiedDependencyException`). Le bean `IContratService`, demandé par `ContratController`, n'existe pas dans le conteneur.
- Causes probables : `@Service` oublié sur `ContratServiceImpl`, ou classe placée hors du package racine du scan.
- Correction : ajouter `@Service` et placer la classe sous le package racine.

**Message 2** : `NoUniqueBeanDefinitionException`. Deux beans (`emailNotificateur`, `smsNotificateur`) correspondent au type demandé par `NotificationController`.
- Correction 1 : `@Primary` sur la classe du bean choisi par défaut.
- Correction 2 : `@Qualifier("emailNotificateur")` sur le paramètre du constructeur du contrôleur.

**Message 3** : dépendance circulaire entre `clientServiceImpl` et `reservationServiceImpl`.
- Avec l'injection par constructeur, chaque bean exige l'autre pour être créé, donc aucun ne peut être créé en premier : le cycle est impossible à contourner.
- Piste de conception : extraire la logique commune dans un troisième composant dont les deux services dépendent, pour que la dépendance devienne à sens unique.

## D. Anomalies SonarQube for IDE

| Règle | Explication | Correction |
|---|---|---|
| java:S6813 | Injection par attribut avec `@Autowired` : le champ ne peut pas être `final`, la dépendance est cachée, et elle vaut `null` si la classe est créée avec `new` hors du conteneur | Retour à l'injection par constructeur avec `@RequiredArgsConstructor` et un champ `private final` |
| java:S112 | Levée d'une exception générique (`RuntimeException`) : elle ne dit rien sur la cause de l'erreur et force l'appelant à intercepter un type trop large | Remplacement par l'exception métier `ResourceNotFoundException`, qui porte la ressource et l'identifiant |
| java:S1128 | Import inutilisé (`java.util.ArrayList`) : code mort qui alourdit le fichier | Suppression de la ligne d'import |

## E. Questions de compréhension

1. **Où est le `new` de `ContratServiceImpl` et qui l'exécute ?**
   Le `new` n'est écrit nulle part dans notre code. C'est le conteneur Spring qui instancie la classe au démarrage : il la détecte grâce à `@Service` et au scan des composants, appelle son constructeur et lui fournit le proxy de `IContratRepository`.

2. **Pourquoi un contrôleur dépendra-t-il de `IContratService` et non de `ContratServiceImpl` ?**
   Pour dépendre d'une abstraction et non d'une implémentation concrète. On peut changer l'implémentation sans toucher au contrôleur, et fournir une dépendance de remplacement pour les tests. Cela réduit le couplage entre les couches.

3. **Pourquoi un service singleton doit-il rester sans état ? Exemple de bug.**
   Une seule instance est partagée par tous les threads. Un attribut modifiable (par exemple un « contrat courant ») serait écrasé par les requêtes simultanées. Exemple : deux utilisateurs appellent le service en même temps, le second remplace le contrat courant du premier, et le premier enregistre alors le contrat de l'autre.

4. **Pourquoi `update` charge-t-il l'entité existante avant `save` ?**
   Passer directement l'objet reçu écraserait toutes les colonnes (celles non fournies passeraient à `null`) et, avec `orphanRemoval`, pourrait vider la collection de paiements. Charger puis recopier uniquement les attributs modifiables préserve le reste, et vérifie en plus que l'entité existe.

5. **Différences :**
    - `@Component` se pose sur une classe que l'on écrit et que le scan détecte. `@Bean` se pose sur une méthode d'une classe `@Configuration`, pour déclarer explicitement un bean (classe de bibliothèque ou du JDK qu'on ne peut pas annoter, ou construction particulière).
    - `@Primary` se pose sur la classe : c'est le candidat par défaut quand plusieurs beans ont le même type. `@Qualifier("nom")` se pose à l'injection : c'est un choix explicite du bean voulu.
    - `IPaiementService` n'expose que la lecture parce que les paiements sont gérés à travers le Contrat (cascade `ALL`) : créer, modifier ou supprimer un paiement passe par le contrat.