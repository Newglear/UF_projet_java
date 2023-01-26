# Chavardage 
Romain Moulin et Aude Jean-Baptiste, 4IR-SC, 2022-2023

## Brève présentation 

Nous avons développé une application de chat en Java permettant de communiquer sur un réseau local. 
Cette application inclut notamment les fonctionnalités suivantes : 

- une **phase de découverte** des autres utilisateurs sur le réseau (les utilisateurs actuellement connectés sont repérés par une pastille verte) 
- la possibilité de **changer son pseudonyme** après s'être connecté
- l'ouverture d'une conversation avec un autre utilisateur
- les messages échangés sont conservés sur une **base de données centralisée** 
- chaque utilisateur de notre application possède un **id unique** qui lui permet de récupérer ses anciennes conversations et de se connecter sur n'importe quel poste où l'application est installée 
- il est possible de consulter les messages déjà échangés avec des utilisateurs actuellement non connectés, mais pas de leur envoyer de nouveaux messages 

### NB 
La database contient déjà des messages échangés entre les users 1, 2 et 3

## Aspects techniques

- **configuration**: ce projet est un projet maven qui peut être importé et exécuté en se basant sur le [pom.xml](./Projet/pom.xml)
- **organisation du code**: l'organisation de notre code limite les dépendances entre les packages. Par exemple, le network manager et la liste des utilisateurs n'ont aucune interaction 
- **interface graphique**: nous avons choisi d'utiliser JavaFX  
- **thread safety**: l'accès aux ressources partagées est protégé grâce au mot-clef synchronized de Java 
- **tests**: nous avons implémenté des [tests unitaires](./Projet/src/test/java/chavardage) avec Junit
- **observer design pattern**: implémenté notamment sur les serveurs [TCPServeur](./Projet/src/main/java/chavardage/networkManager/TCPServeur.java) et [UDPServeur](.Projet/src/main/java/chavardage/networkManager/UDPServeur.java)
- **singleton design pattern**: implémenté notamment sur la [liste des utilisateurs connectés](./Projet/src/main/java/chavardage/userList/ListeUser.java) pour s'assurer qu'il n'en existe qu'une seule instance 
- **logging**: nous avons utilisé la librairie log4j pour doter notre code de logs clairs et simples à utiliser 
- **intégration continue**: implémentée sur github avec une exécution des tests à chaque push, voir le [fichier de configuration](./.github/workflows/integration.yaml)


## Utilisation

Il est nécessaire que `maven` et `git` soient installés sur votre machine. Clonez le projet sur votre machine et placez-vous dans ./Projet avant d'exécuter : 
```sh
# cloner le projet 
git clone https://github.com/Aude510/UF_projet_java
cd UF_projet_java/Projet
# compiler le projet  
mvn compile
# exécuter les tests 
mvn test
# démarrer l'application 
mvn exec:java -Dexec.mainClass="chavardage.GUI.Main" 
# démarrer l'application (si le premier ne fonctionne pas)
mvn exec:java -D"exec.mainClass"="chavardage.GUI.Main"
```
### Remarque 
Notre Database centralisée est située sur un serveur de l'INSA qui nous a été fourni par nos professeurs `mysql://srv-bdens.insa-toulouse.fr:3306`. Vous pouvez nous contacter si vous désirez avoir le login et le mdp pour administrer cette database. Le projet n'est malheureusement pas utilisable dans un environnement où ce serveur n'est pas accessible. Une version de l'application sans database, qui serait exécutable dans tout type d'environnement, est en cours de développement. 

### Crédits 
Logo de l'application : 
<a href="https://www.freepik.com/free-vector/cute-cat-working-laptop-cartoon-vector-icon-illustration-animal-technology-icon-concept-isolated_28565598.htm#query=cat%20computer&position=2&from_view=keyword">Image by catalyststuff</a> on Freepik

### Quelques liens 
  - [Notre code](./Projet/src/main/java/chavardage)
  - [Rapport sur la conception du projet](./Rapport_Projet_Java.pdf)
  - [Diagrammes UML de spécification](./UML/Images)  

### UF "Conception et Programmation avancées" de 4ème année INSA 

  - [UML et patrons de conception](https://moodle.insa-toulouse.fr/course/view.php?id=1283)
	- [nos TDs de "prise en main" de l'UML](./TD/TDs_UML) 
  - [Programmation avancée en Java](https://moodle.insa-toulouse.fr/course/view.php?id=1228) 
	- [nos TDs de "prise en main" de programmation avancée en Java](./TD/TDs_Java)
  - [Conduite de projet](https://moodle.insa-toulouse.fr/course/view.php?id=1759) 
  - [PDLA](https://moodle.insa-toulouse.fr/course/view.php?id=1758)
