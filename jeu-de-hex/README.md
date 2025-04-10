Projet : Etude du jeu de hex avec l'algorithme de mcts et l'optimisation rave.



1.Utilisation du Makefile
Pour compiler les fichiers du projet (les fichiers .class seront stockés dans /bin): make compile

Lancer une partie de hex dans le terminal : make run

Lancer une partie de hex avec une interface graphique : make run-view

Nettoyer les fichiers compilés : make clean

Installer les dépendances Python : make install-deps

Lancer une visualisation spécifique : make run-visualisation <nom_du_script.py>
Exemple : make run-visualisation winRateBudgetCurve.py

Lancer un fichier du package des corrélation spécifique : make run-correlation <nom_du_script.py>
Exemple : make run-correlation pearson.py
					
Lancer un fichier du package machine learning spécifique : make run-ml <nom_du_script.py>
Exemple : make run-ml logistic_regression.py

Lancer la même simulation plusieurs fois avec certains paramètres : make run-repeated-simulations 

Pour afficher tous les scripts disponibles : make list-scripts

