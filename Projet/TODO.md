# TODO 
## Diagramme de Séquence 
- messages en boîte blanche 

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
