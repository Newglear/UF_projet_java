# TODO 
## Diagramme de Séquence : refaire avec draw.io + tenir en compte diag de classes
- clavardage (envoi et réception usermessage) OK
- ouverture conversation OK
- fermeture session clavardage OK
- connexion d'un nouvel utilisateur sur le réseau OK 
- déconnexion d'un utilisateur OK
- réduire l'application OK
- admin OK

## Diagramme de classes 
- MVC  : Controller, Model, Views 

### Views  
interfaces, typiquement avec Swing 

### Model 
data : user, message... : plus ou moins contenu de DB (cf. cours JDBC) 

### Controller 
interactions avec les autres agents : 
- network manager (driver réseau)
- database manager : connexion à la DB + chargement des classes du model 
- thread manager : gestion du pool de threads qui maintiennent les conversations en cours 

### Structure composite
- ajouter Grande categorie Conversation
- ajouter thread destructor
- ajouter nouveau type de message fin de connexion