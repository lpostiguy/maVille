## Glossaire

### Termes importants
- Il vous manque plusieurs termes importants: Application MaVille, Code de la ville, Info Entraves et Travaux, Planification participative, Statut du projet/Projet, Type de travaux/ Travaux, Type de problème, Signaler un problème
Compte permettant aux intervenants d'utiliser les fonctionnalités liées à leurs roles.
  - Le terme est pour compte intervenant/résident, mais la définition est seulement pour les Intervenant?
Notification personalisée:
  Fonctionnalité qu'un acteur peut activer afin d'être toujours à jour sur un lieu pour lequel il est abonné.
  - Ca devrait être plutôt un RÉSIDENT et non pas acteur en général
Requête de travail: 
  Demande envoyée par un Résident/Intervenant pour faire une proposition de travail pour un lieu donné.
  - Non, seulement les résidents peuvent soumettre une requête de travail. Les intervenants peuvent répondre pour poser sa candidature.
Vous devez clairement indiquer qui fait quoi, et non pas ACTEUR en général.
Il faudrait revoir votre glossaire au complet!

## Diagramme de cas d'utilisation

### Identification des acteurs
- Utilisateur général: Ce serait mieux si vous pouvez considérer la généralisation de l'utilisateur. 

### Cas d'utilisation
  - Vous avez oublié certains cas, ce serait mieux si vous modifiez/ajoutez votre diagramme comme suivant:
    - Il serait préférable d'ajouter un cas général pour l'Utilisateur, qui généralise à la fois le Résident et l'Intervenant, afin de regrouper les cas d'utilisation communs à ces deux acteurs. Par exemple comme Se connecter, Modifier le profil(vous avez oublié ce cas dans votre diagramme), Consulter les notifications (n'oubliez pas que c'est une extension dans un autre CU), etc
   - Pour les résidents, il faudrait ajouter:
     - Filter les travaux
     - Modifier les préférence
   - Pour les intervenant:
    - Consulter la liste des requêtes
    - Créer un compte intervenant devrait avoir une relation avec la ville
    - Envoyer un notification
    - Soumettre sa candidature 
   - Vous avez tenté d'utiliser les includes, et je comprends votre démarche, mais cela semble moins pertinent. Par exemple, si l'on souhaite soumettre un nouveau projet de travaux, étant donné que l'authentification est incluse, devrais-je m'identifier à chaque fois, même si cela a déjà été fait auparavant? Alors, ce serait mieux de revoir vos includes dans tous les cas. 
   - Vous pouvez utiliser les includes comme dans la correction proposée du prof.
   - La relation Consulter les travaux en cours devrait être un extend de Recherche un travail, cette relation est inversée dans votre diagramme

## Scenarios
  - Recherche des travaux: Même si c'est un extend dans un autre CU et vous l'avez déjà mentionné, vous devez décrire ce CU séparément.
  - Consulter les préférences des résidents: Même si c'est un extend dans un autre CU et vous l'avez déjà mentionné, vous devez décrire ce CU séparément.
- Vous avez manqué 3 CUs: Accepter la candidature d'un intervenant, Recherche des travaux, Soumettre des plages horaires
- Faites attention à la numérotation des scenarios alternatifs
- Même si c'est un extend dans un autre CU et vous l'avez déjà mentionné, vous devez décrire ce CU séparément.

## Diagramme d'activités
   - Parfois, vos nœuds de décision ne comportent aucune description des choix, et il arrive qu'un "oui" soit indiqué sans le "non". Par exemple, dans les sections inscription et authentification.
   - Dans certains cas, vous utilisez inutilement de nœuds de décision
- Inscription et authentification: Il faudrait revoir vos noeuds de décisions pour ce diagramme. Et comment vous communiquez avec le système de notification?
   - Gestionnaire de requêtes: C'est bien. Cependant, pour l'option "Soumettre une requête de travail," que se passe-t-il après l'envoi de la requête aux intervenants ? Et qu'advient-il après "envoi de la réponse à l'intervenant" ?
  - Pour tous les diagrammes: Comment on pourrait retourner au menu principal sans quitter l'application?
  - Il faudrait avoir des étapes entrer des informations(si le CU demande) avant de passer à l'étape validation, par exemple Rechercher des travaux, il faudrait avoir une étape entrer le nom de titre, avant de valider si le "Nom du titre valide"
    - Ce serait mieux d'aller un peu plus loin, pensez à l'utilisation des noeuds d'objets.
 
## Analyse

### Risques
  - Bugs imprévus: C'est plutôt un besoin non-fonctionnelle, c'est la fiabilité du système.

### Besoins non fonctionnels
  - Sécurité: L’application tiendra compte de beaucoup d’information sensible sur les utilisateurs (adresses, lieux fréquentés et autres). Cette information doit rester confidentielle: Oui, c'est bien, mais comment on les protège? Par exemple: Une authentification forte (2FA) est nécessaire pour les résidents et les intervenants ou comme vous avez mentionné dans les risques: chiffrer toutes les données sensibles 
  - Fiabilité: Ce serait plutôt la sécurité que vous avez décrit. La fiabilité est plutôt: l'application doit être fiable et fonctionner sans bugs majeurs. Un système de logs détaillés et de surveillance doit permettre de détecter les erreurs et de les corriger rapidement.

### Besoins matériels, solution de stockage et solution d'intégration

- Matériels nécessaires 
  - Considération pour l'hébergement des données: Ok, votre solution n'a pas besoin de serveur physique, mais il faudrait préciser comment et où les données seront hébergées (cloud, serveur VPS, etc.).
  - Considération pour le déploiement du programme: Comment l'intégrer à votre projet? Où et comment elle sera déployée? Comment les utilisateurs installeront votre programme? Comment vous gérerez les futures MAJ ou corrections de bugs?
  - Analyse de cout: Puisque vous n'utilisez pas de serveur physique, vous pourriez inclure une estimation des coûts d'hébergement si vous optez pour une solution cloud ou autre service.

- Solutions de stockage 
  - Type de données et stockage adapté: Vous pourriez spécifier plus clairement quels types de données seront stockés
  - Architecture de stockage (partagée, distribuée, etc.) : Vous devriez préciser si votre solution de stockage sera centralisée (un seul serveur), partagée ou distribuée (répartition des données sur plusieurs serveurs). 
  - Ce serait mieux si vous pouver fournir un exemple concret

- Solutions d'intégration
  - Votre idée reconnaît bien l'existence des deux services distincts. Cependant, le service "Info entraves et travaux" ou l'intéraction avec la ville (en général) serait plutôt conçu comme une API avec laquelle notre application MaVille pourrait interagir directement. Cette API fournirait les données sur les entraves et travaux, que MaVille pourrait ensuite utiliser pour offrir des fonctionnalités avancées, t.q. la possibilité pour les utilisateurs de recevoir des notifications, donner leur avis, etc.
  - Votre intégration suggère un partage de base de données entre les deux services, mais il manque des considérations clés sur la manière technique dont ils communiqueraient.
  - On comprend la séparation fonctionnelle entre les deux services, mais comment MaVille récupère les données reste flou.
  - Ce serait mieux d'avoir un exemple comme par exemple si les services partagent une DB, comment MaVille accède aux données d'entraves ? Si une API est utilisée, comment les requêtes seraient faites et comment les données seraient traitées dans MaVille?

## Prototype
  - Très bien
  - Note: Considération de l'option "Quitter application" dans votre prototype.

## Git

- Très bien

## Rapport
  - Certaines phrases ont été coupées en plusieurs lignes pour des raisons quelconques. Comme par exemple: Votre hypothèse.