## Note: 
  Le contenu de votre rapport dépasse la taille de l'écran, ce qui entraîne un affichage incomplet 

# Feedback

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
  