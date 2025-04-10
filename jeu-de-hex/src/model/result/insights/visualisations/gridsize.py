import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from scipy.ndimage import gaussian_filter1d

def compute_win_rates_by_grid_size(data, grid_column, winner_column, max_budget_diff=100):
    """
    Calcule les taux de victoire des deux joueurs en fonction de la taille de la grille,
    en ne gardant que les parties où les budgets sont proches.

    Paramètres :
    - data : DataFrame contenant les parties.
    - grid_column : Nom de la colonne contenant la taille de la grille.
    - winner_column : Nom de la colonne contenant l'information sur le gagnant.
    - max_budget_diff : Seuil de différence maximale entre les budgets des joueurs.

    Retourne :
    - grid_sizes : Liste des tailles de grille uniques.
    - win_rates_p1 : Liste des taux de victoire du joueur 1.
    - win_rates_p2 : Liste des taux de victoire du joueur 2.
    """

    # Filtrage des parties où les budgets sont proches
    filtered_data = data[abs(data["budgetPlayer1"] - data["budgetPlayer2"]) <= max_budget_diff]

    # Obtenir les tailles de grille uniques triées
    grid_sizes = sorted(filtered_data[grid_column].unique())
    win_rates_p1 = []
    win_rates_p2 = []

    for grid_size in grid_sizes:
        # Filtrer les parties correspondant à cette taille de grille
        subset = filtered_data[filtered_data[grid_column] == grid_size]

        if len(subset) > 0:
            win_rate_p1 = (subset[winner_column] == 1).mean()
            win_rate_p2 = (subset[winner_column] == 2).mean()
        else:
            win_rate_p1 = win_rate_p2 = 0

        win_rates_p1.append(win_rate_p1)
        win_rates_p2.append(win_rate_p2)

    return grid_sizes, win_rates_p1, win_rates_p2


# Chargement des données
df = pd.read_csv("../../fifty-fifty.csv")

# Calcul des taux de victoire en fonction de la taille de la grille
grid_sizes, win_rates_p1, win_rates_p2 = compute_win_rates_by_grid_size(df, "gridSize", "winner", max_budget_diff=100)

# Affichage du graphique
plt.figure(figsize=(10, 5))
#plt.plot(grid_sizes, win_rates_p1, label="Joueur 1", marker='o', color='blue', linestyle='-')
#plt.plot(grid_sizes, win_rates_p2, label="Joueur 2", marker='s', color='red', linestyle='-')

# Ajout des courbes lissées
win_rates_p1_smoothed = gaussian_filter1d(win_rates_p1, sigma=1.5)
win_rates_p2_smoothed = gaussian_filter1d(win_rates_p2, sigma=1.5)

plt.plot(grid_sizes, win_rates_p1_smoothed, linestyle='--', color='blue', alpha=0.6, label="Tendance Joueur 1")
plt.plot(grid_sizes, win_rates_p2_smoothed, linestyle='--', color='red', alpha=0.6, label="Tendance Joueur 2")

plt.xlabel("Taille de la grille")
plt.ylabel("Taux de victoire")
plt.title("Évolution du taux de victoire en fonction de la taille de la grille\n(pour les parties avec budgets proches)")
plt.legend()
plt.grid()
plt.show()
