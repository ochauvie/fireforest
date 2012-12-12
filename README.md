# Automate cellulaire feu de forêt

Une forêt est plantée aléatoirement par des arbres.
On met le feu à un arbre.
Le feu se diffuse suivant la règle suivante: 
* Un arbre prend feu au temps t si l'un au moins de ses 4 voisins (nord, est, sud, ouest) est en feu au temps t-1. Les cellules en feu passeront en cendres au temps suivant, les cellules en cendres deviendront vides au temps suivant.
* Un arbre en feu au temps t-1 devient des cendres au temps t.
* Les cendres autemps t-1 disparaissent au temps t.

Le premier feu se déclenche aléatoirement dans la forêt. 
Vous pouvez ensuite ajouter d'autres foyers en cliquant sur un arbre.