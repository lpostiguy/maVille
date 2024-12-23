IFT2255-A24-SessionProject

DESCRIPTION DU PROJET :
MaVille est une application en ligne de commande développée en Java, conçue pour améliorer la communication entre les secteurs public, privé, et la population de la ville de Montréal. Ce programme facilite la planification des travaux prévus, réduisant ainsi l'impact sur la circulation des résidents.

--------------------------------------------

FONCTIONNALITÉS DE L'APPLICATION PAR RÔLE

Lorsqu'un résident est connecté dans l'applicaition MaVille, il peut:
- Créer un compte
- Se connecter/déconnecter
- Voir ses notifications
- Consulter les travaux en cours
- Consulter les travaux à venir (3 prochains mois)
- Chercher ou filtrer les travaux par quartier
- Consulter les entraves engendrées par un travail
- Chercher ou filtrer les entraves par rue
- Soumettre une requête de travail
- Faire le suivi d'une requête de travail
    
Lorsqu'un intervenant est connecté dans l'applicaition MaVille, il peut:
- Créer un compte
- Se connecter/déconnecter
- Consulter les requêtes de travai
- Soumettre/Soustraire sa candidature
- Faire le suivi de sa candidature
- Soumettre un projet
- Modifier le statut d'un projet

--------------------------------------------

ORGANISATION DU RÉPERTOIRE :
- Rapport de l'équipe : Comprend toutes les précisions, l'analyse des exigences, et les cas d'utilisation de l'application
- Prototype en ligne de commande : Un prototype simple permettant de simuler l'inscription, la connexion, et la navigation des menus
- Diagrammes d'activités: Un fichier avec tous les diagrammes d'activité est présent dans le répertoire /images/diagrammes

--------------------------------------------

POUR EXÉCUTER L'APPLICATION:

Pour exécuter le programme de commande, vous devez utiliser la commande: "java -jar prototype-1.1.jar" 
dans le répertoire du projet.

--------------------------------------------

POUR TESTER L'APPLICATION:

Pour tester l'application, voici les informations de connection
pour 5 résidents et pour 5 intervenants que vous pouvez entrer lors de la connection:
 
Résidents:  

- Résident 1  
email: emile.tremblay@example.com   
mot de passe : A1b2C3d4E5!F  

- Résident 2  
email: chloe.dube@mail.com  
mot de passe : ZxCvBn1234!!  

- Résident 3  
email: lucas.pelletier@webmail.ca  
mot de passe : QwErTyUiOp12  

- Résident 4  
  email: amelie.roy@moncourriel.com  
  mot de passe : AsDfGh123!@#  

- Résident 5  
  email: olivier.cloutier@example.com  
  mot de passe : P@ssW0rd!123  


Intervenants:  

- Intervenant 1  
email: gabriel.tremblay@example.com  
mot de passe : A!b2C3d4E5Fg  
type d'entité : Entreprise publique

- Intervenant 2  
email: amelie.gagnon@mail.com  
mot de passe : ZxCvB1n2!#$  
type d'entité : Entrepreneur privé  

- Intervenant 3  
email: olivier@webmail.ca  
mot de passe : PqR1sT2u!@3  
type d'entité : Particulier  

- Intervenant 4  
  email: sophie.cloutier@moncourriel.com  
  mot de passe : AbCdE123!@#  
  type d'entité : Entreprise publique  

- Intervenant 5  
  email: lucas.bouchard@example.com  
  mot de passe : YxZ1WvT2Q3!  
  type d'entité : Entrepreneur privé  

--------------------------------------------

POUR RUN LES TESTS UNITAIRES:
Pour exécuter tous les tests unitaires en même temps, vous devez aller dans src/test/java/com/example/AppTest.java 
et run la classe AppTest.
