# Feedback

## Révision 

- Diagrammes CU mis à jour 
  - Nouvel acteur: API de la ville : Non, vous devez changer l'acteur pour API de la ville et non pas Système de gestion de la ville de Mtl
  - Nouveau CU 
    - Consulter ou Chercher les entraves routières : Oui
  - CU abandonné:  
    - Signaler un problème: Oui
    - Partager son avis sur les travaux: Oui
  - Corrections : C'est bien
- Diagrammes d'activités mis à jour : Oui, partiellement bon
  - Note: Je comprends que vous avez précisé que l'utilisateur peut toujours revenir au menu précédent, mais ce n'est pas ce que j'attends. Vous devriez intégrer cette option directement dans votre diagramme, ce qui implique de modifier le diagramme ou de l'organiser de manière à rendre cette option réalisable dans votre application.
  - Il ne faut jamais laisser un nœud sans issue, comme par exemple le nœud "Envoi d'une notification aux intervenants". Vous devriez soit clôturer le flux, soit prévoir une action à réaliser par la suite.
- Analyse mis à jour 
  - Risques mis à jour : Oui
  - Besoins non-fonctionnels mis à jour : Très bien
  - Solution de stockage et d'intégration mis à jour : Oui très bien

## Architecture 

- Les utilisateurs sont bien identifiés : Oui
  - Ce serait préférable d'avoir davantage de détails sur chacun d'eux : qui sont-ils et quelles sont leurs activités ?

- Linteraction entre utilisateur et système est bien identifié 
 - Il manque un descriptif sur la relation avec Application MaVille

- La frontière du système est claire et permet de distinguer les composantes du système des dépendances externes : Non

- La composantes principales sont bien identifiés 
 - Ce serait préférable de mentionner quelle est le type de votre application MaVille, par exemple CLI
 - Si je comprends bien, vous avez développé votre propre API Javalin pour interagir avec votre application, et pas uniquement avec l'API Service Info Entrave, c'est bien cela ? Comment votre propre API interagit-elle avec votre application ? Quels types de données ou de fonctionnalités sont échangés ? etc.

- Les relations entre composantes sont bien identifiées. 
 - Je ne comprends pas l'image du user qui intéragissent avec le composante API** dans votre diagramme fait quoi exactement
- Les services externes sont bien identifiés 
 - Manque Service info entrave (API MTL ne correspond à rien)
- Les relations entre services externes et système sont bien identifiés : Oui

- Il serait préférable de vous référer à l'architecture présentée par le professeur dans le démo 8 pour détailler un peu plus votre architecture. 

- Architecture décrit partiellement textuellement

## Diagramme de classe 

### Identification des entités 

- Classes
  - Vous avez mentionné l'utilisation de MongoDB dans votre architecture. Quelle classe, dans votre diagramme de classe, est responsable de cette partie ?

- Attributs
  - Quel est le type PasswordEncryption ? Je ne vois pas sa définition dans votre diagramme de classe.
  - Même chose pour les types Quartier? Rue?

- Méthodes (pertinence et signature): Très bien

- Vous pourriez également généraliser la classe Contrôleur, ainsi que les classes pour la vue, comme cela est proposé dans la correction. Cela permettrait de rendre votre application plus efficace en favorisant un couplage faible.

### Identification des relations 

- Relation inappropriée ou absente
  - Il vous manque un point d'entrer de votre application, c.a.d une classe qui contient le méthode main()
  - Il vous manque une composition entre Utilisateur et Notification. Je vois que vous avez établi une composition entre Résident et Notification, mais l'Intervenant pourrait également recevoir des notifications.
  - Il vous manque une relation d'agrégation entre Utilisateur et RequêteTravail. Comme mentionné précédemment, les intervenants devraient également pouvoir soumettre des requêtes de candidature.
  - Relation d'agrégation entre Intervenant et Projet

- Multiplicité non identifiée ou inappropriée
 - Vos relations unidirectionnelle "use": d'après votre diagramme, seulement les vues envoient des requêtes aux contrôleurs, mais les contrôleurs ne semblent pas renvoyer de données directement aux vues pour communiquer avec elles. Cela pourrait limiter l'interaction entre les deux couches. Vous avez 2 options: 
  - Option 1: Vous pouvez réorganiser votre diagramme en vous inspirant de la correction du professeur, en ajoutant une association bidirectionnelle tout en veillant à maintenir un faible couplage.
  - Option 2: Vous pourriez envisager d'ajouter des méthodes dans les contrôleurs qui retournent des données nécessaires aux vues. Par exemple, les contrôleurs pourraient renvoyer des objets ou des structures de données que les vues pourraient utiliser pour mettre à jour leur affichage. Cela permettrait d'établir une communication bidirectionnelle entre les vues et les contrôleurs.

### Cohésion et couplage 

### Formalisme  
  - Très bien

## Diagramme de séquence 

### Formalisme et cohérence 

-  Bonne définition des objets et lignes de vie
  - Certaines classes mentionnées ne sont pas présentes dans votre diagramme de classe, par exemple, classe Database
-  Bon usage des flèches de message: Ok
-  Bon usage des fragments: Ok
-  Méthode appelée existe: Plusieurs méthodes ne sont pas présentes dans votre diagramme de classe.
-  Méthode appelée présente la même signature (params + type de retour) : Certaines méthodes ne respectent pas ce critère


### Consulter les entraves 

-  Flux général est cohérent et représentatif du CU: Ok
-  Récupération des entraves: Pas clair
-  Filtre des entraves (optionnel): Non
  - Vous mentionnez une instance d'UI dans ce diagramme. À partir de quelle classe exactement ? PageConnection, PageCompte, ou une autre ?
  - De même, pour le Système et la Base de données, il n'y a pas de classe représentant la base de données dans votre diagramme de classes.
  -  Méthode appelée inexiste: Pas toujours, je ne suis pas sure si vos méthodes existent, par exemple comme "envoyerFiltre", il est dans quelle classe?
  - Il vous faut un peu plus de détails, vous pouvez consulter la correction du prof pour corriger votre diagramme.

### Soumettre une requête de travail 

-  Flux général est cohérent et représentatif du CU: Oui
-  Entrée de données: Oui
-  Validation et traitement erreur: Oui
-  Suivi d'une requête de travail

  - Quelle est la classe Redirect dans votre diagramme?
  - Quelles sont les méthodes que vous souhaitez appeler ici pour envoyer les requêtes ? Par exemple, à l'étape 3, où l'option "selected" est mentionnée, quelle méthode précisément voulez-vous invoquer ?
  - Vous pouvez Simplifiez vos flèches pour les étapes 4,,5,6,7: Au lieu d'envoyer plusieurs flèches indépendantes (title, description, etc.), regroupez-les dans une seule méthode ou interaction. Des flèches sans réponse peuvent donner l'impression d'une absence de traitement ou d'une logique incomplète.
  - Pourquoi on envoie une requête HTTP POST à la base de données à l'étape 7.1.1.1.1 ?
  - Vous devez transmettre les données depuis l'étape 7.1.1.1.1 à travers les autres étapes successives, plutôt que de passer directement à l'étape 7.1.2.
- Consulter la correction du prof pour corriger votre diagramme.

### Consulter la liste des requêtes de travail 

-  Flux général est cohérent et représentatif du CU
-  Voir la liste des requêtes de travail: Non
-  Accès à une requète de travail: Oui, partiellement
-  Soumettre sa candidature: Oui

- Vous devez avoir une réponse pour l'étape 2.1 à partir database vers IntervenantController avant de passer à l'étape 7.1.2.
- Je ne vois pas le méthode redirect(): void dans votre ConsulterRequetTravailPage

On n'envoie jamais de requête HTTP directement à une base de données. D'après ce que je comprends, vous voulez dire que vous allez récupérer ces données à partir de votre base de données. Dans ce cas, vous devez soit :
 - 1. Montrer clairement comment vous communiquez avec une API pour récupérer ces données, puis les ajouter dans votre base de données.
 - 2. Ou bien communiquer directement avec l'API (ce qui est une solution moins adaptée).
Quoi qu'il en soit, il serait nécessaire de revoir votre architecture, de corriger le diagramme de classes, et de mieux définir votre diagramme de séquence pour clarifier ces interactions.

## Discussion design 

- Considérations prises dans le design (abstraction, couplage et cohésion, encapsulation) pour favoriser l'évolution: 
  - Je m'attends également à ce que vous approfondissiez votre choix de design en vous appuyant davantage sur le diagramme de classes, plutôt que de vous limiter à une description générale de l'architecture.
- Lien avec l'architecture ou style d'architecture (ex: MVC) et avantages de celles-ci: 
  - Oui, c'est ok, mais Quels sont les modules spécialisés auxquels vous faites référence ? Nous aurions besoin de davantage de précisions : de quelles classes du modèle s'agit-il ? Et quels modules précisément ? 
- Intégration de l'application (modularité, flexibilité, interopérabilité): Oui, partiellement.


# Implémentation 

## Fonctionnalités 
  - Avec jar file

- [ ] Se connecter comme résident et intervenant  : Très bien
  - [ ] Se connecter comme résident               : Oui
  - [ ] Se connecter comme intervenant            : Oui
  - [ ] Validation des données                    : Oui
  - [ ] Message d'erreur                          : Oui
  - [ ] Page de connexion partagée                : Oui
  - [ ] Présentation du menu après connexion      : Oui

- [X] Consulter les travaux en cours ou à venir   : Non, cette fonctionnalité ne marche pas

- [ ] Consulter les entraves                      : Très bien

- [X] Soumettre une requête de travail            
  - [ ] Formulaire complet                        : Oui
  - [X] Validation des données                    : La validation est partiellement correcte, car elle accepte des cas qui n'ont pas de sens.
        Titre : 1
        Description : 1
        Type de travaux : 1
        Date de début espérée (format YYYY-MM-DD): 0000-00-00
        Requête de travail ajoutée avec succès.
  - [ ] Message d'erreur                          : Oui, très bien

- [ ] Consulter la liste des requêtes de travail  : Très bien

## Robustesse & Utilisabilité 

- [ ] Application ne *crash* pas lors de mauvaises entrées : Très bien
- [ ] Navigation et interaction intuitive : Oui
- [ ] Possibilité de revenir en arrière : Oui
- [ ] Informations présentées facile à lire : Oui 

## Code 

- [X] Code n'est pas complètement différent de la conception : 
  Certaines classes n'apparaissent pas dans le diagramme de classes, probablement en raison d'un changement de nom
- [ ] Code est réparti à travers plusieurs classes et facile à maintenir : Oui
- [ ] Code est facile à lire et comprendre : Oui

## Tests unitiaire  

- 4 membres: 12 tests requis

- SoumettreRequeteTravailControllerTest(total: 3 tests)
Pour chaque test: SoumettreRequeteTravailControllerTest: Très bien

- LoginPageTest(total: 3 tests)
Pour chaque test: LoginPageTest: Très bien

- MongoDBConnectionTest (total: 3 tests)
connexionDoitRetournerUneBaseDeDonneesNonNulle(): Bien
connexionDoitPointerVersLaBonneBaseDeDonnees(): Bien

Pour ce test: connexionDoitEtreResilienteAuxErreurs(): Test partiellement correct
  - [ ] Test passe: Oui
  - [X] Test pertinent pour la fonction: 
    - Test partiellement pertinent. Sans simulation d'erreurs, ce test est limité. Il ne valide pas réellement que la fonction est résiliente aux erreurs, seulement qu'elle ne lève pas d'exception dans un cas "normal".
  - [ ] Test distinct: Oui
  - [ ] Test bien nommé et structuré: Oui
  - [X] Assertion pertinente:  
    - Partiellement respecté. L'assertion actuelle est suffisante pour tester que la méthode ne plante pas, mais elle pourrait être plus complète (par exemple, en testant le retour).

Il vous manque 3 autres tests!

## Rapport et Git 

- Structure du rapport
  Le contenu de votre rapport dépasse la taille de l'écran, ce qui entraîne un affichage incomplet

## Bonus 1: Exploitation de Git 

- Commits réguliers et descriptifs de chaque membre. : Oui, très bien
- Gestion de projet par les tickets. : Non
- Usage d'une branche pour le développement du code source. : Oui, très bien

## Bonus 2: Architecture REST 
 - Non
  