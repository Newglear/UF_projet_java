# Système de chat décentralisé : 

noeud/agent/utilisateur = ronds sur le diagramme 
trucs volontairement ambigus sur le cahier des charges 
	=> lire plusieurs fois et avec attention les documents pour limiter la perte de temps 

pas de serveur, uniquement les noeuds qui communiquent entre eux, connectés via un réseau local (tous sur le même réseau!) 

connexion d'un nouveau noeud : 
- déclaration auprès des autres noeuds : message en broadcast  
- mise à jour de l'annuaire local de chacun des noeuds 
- envoi des coordonnées de chaque noeud au nouveau pour la constitution de son annuaire 


login (avec IP+port) pas nominatif : n'importe qui peut reprendre ton login une fois que tu es parti, on ne peut juste PAS AVOIR deux logins identiques en même temps 

ID (~IP + port) non modifiable

serveur de base de données pour les messages

## Rendu conception UML : structure attendue à la fin du semestre 

- I. Acteurs 
	1. Acteurs primaires
	2. Acteurs secondaires
- II. Diag. Usecase
- III. Diag. de classes 
	1. Schéma
	2. Description et typologie (héritages, cardinalités, choix conceptuels) 
- IV. Diag. de séquence importants
- V. Diag. de structure composite
- VI. Diag. de déploiement : très simple (application + serveur DB) 
- VII. Schéma de la BD 
- VIII. Maquette GUI 
