# Automate cellulaire feu de for�t

Une for�t est plant�e al�atoirement par des arbres.
On met le feu � un arbre.
Le feu se diffuse suivant la r�gle suivante: 
* Un arbre prend feu au temps t si l'un au moins de ses 4 voisins (nord, est, sud, ouest) est en feu au temps t-1. Les cellules en feu passeront en cendres au temps suivant, les cellules en cendres deviendront vides au temps suivant.
* Un arbre en feu au temps t-1 devient des cendres au temps t.
* Les cendres autemps t-1 disparaissent au temps t.

Le premier feu se d�clenche al�atoirement dans la for�t. 
Vous pouvez ensuite ajouter d'autres foyers en cliquant sur un arbre.