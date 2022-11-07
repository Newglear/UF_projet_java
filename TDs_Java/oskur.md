## si ça marche pas - impossible de lancer le serveur : 
- supprimer le serveur et la config tomcat (Windows -> Preferences -> Servers -> Runtime Environment)
- redémarrer eclipse-jee (pas java!)
- remettre la config et refaire un serveur 
- démarrer pour voir si ça marche 
- stop le serveur 
- relancer l'application 

## problème dépendances : 
- rajouter dans target/pom.xml 

## si ça marche pas - erreur 404 on ne trouve pas le servlet : 
- <form method="post" action="NomServlet"> dans le html (ou jsp) 
- annotation @WebServlet("/NomServlet") au dessus du class NomServlet dans le code de la servlet 

## si ça marche pas - dernier recours : 
- réinstaller tomcat, réinstaller java, réinstaller eclipse 
- ne pas oublier d'avoir des backups des fichiers 
- Attention, cette étape est réalisé par des professionnels. Ne reproduisez pas ça chez vous !

## Trouver le path de la JDK : 
- ouvrir eclipse
- window --> preferences --> onglet java --> installed JREs 
  
*Bonne chance jeune padawan* 
