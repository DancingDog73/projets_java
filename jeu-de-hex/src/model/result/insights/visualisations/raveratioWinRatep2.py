import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

def compute_moving_average(data, data_rave, data_no_rave, budget_column, winner_column, percentage=10):
    
    """
    Calcule le taux de victoire moyen pour un joueur en fonction de son budget,
    en appliquant une moyenne mobile sur une fenêtre définie en pourcentage.

    Paramètres :
    - data : DataFrame contenant toutes les parties.
    - data_rave : DataFrame contenant uniquement les parties avec RAVE activé.
    - data_no_rave : DataFrame contenant uniquement les parties sans RAVE.
    - budget_column : Nom de la colonne contenant les budgets des joueurs.
    - winner_column : Nom de la colonne contenant l'information sur le gagnant (1 = joueur 1, 2 = joueur 2).
    - percentage : Pourcentage utilisé pour définir la fenêtre de la moyenne mobile.

    Retourne :
    - unique_budgets : Liste triée des budgets uniques.
    - win_rates_p1 : Liste des taux de victoire du joueur 1 (toutes parties confondues).
    - win_rates_rave : Liste des taux de victoire du joueur 1 avec RAVE.
    - win_rates_no_rave : Liste des taux de victoire du joueur 1 sans RAVE.
    """
    
    unique_budgets = sorted(data[budget_column].unique()) # Liste des budgets uniques triés
    win_rates_p2 = []
    win_rates_rave = []
    win_rates_no_rave = []
    
    # Parcours des budgets uniques pour calculer le taux de victoire moyen sur une fenêtre définie
    for budget in unique_budgets:
        min_budget = budget * (1 - percentage / 100)
        max_budget = budget * (1 + percentage / 100)

        # Sélection des parties dont le budget est dans la fenêtre définie
        window = data[(data[budget_column] >= min_budget) & (data[budget_column] <= max_budget)]
        window1 = data_rave[(data_rave[budget_column] >= min_budget) & (data_rave[budget_column] <= max_budget)]
        window2 = data_no_rave[(data_no_rave[budget_column] >= min_budget) & (data_no_rave[budget_column] <= max_budget)]

        # Calcul du taux de victoire du joueur 1 pour chaque type de données
        if len(window) > 0:
            win_rate_p2 = (window[winner_column] == 2).mean()
        else:
            win_rate_p2 = 0

        if len(window1) > 0:
            win_rate_rave = (window1[winner_column] == 2).mean()
        else:
            win_rate_rave = 0
        
        if len(window2) > 0:
            win_rate_no_rave = (window2[winner_column] == 2).mean()
        else:
            win_rate_no_rave = 0

        # Ajout des résultats aux listes
        win_rates_p2.append(win_rate_p2)
        win_rates_rave.append(win_rate_rave)
        win_rates_no_rave.append(win_rate_no_rave)
    
    return unique_budgets, win_rates_p2, win_rates_rave, win_rates_no_rave

# Séparation des données en fonction de l'utilisation de RAVE
df = pd.read_csv("test.csv")
df_rave = df[(df["ravePlayerTwo"] == True)] # Parties avec RAVE activé
df_noRave = df[(df["ravePlayerTwo"] == False)] # Parties sans RAVE

#Calcul du ratio de budget pour chaque catégorie de données
df["budgetRatio"] = df["budgetPlayer2"] / df["budgetPlayer1"]
df_rave["budgetRatio"] = df_rave["budgetPlayer2"] / df_rave["budgetPlayer1"]
df_noRave["budgetRatio"] = df_noRave["budgetPlayer2"] / df_noRave["budgetPlayer1"]

budgets, win_rates_p2, win_rates_rave, win_rates_no_rave = compute_moving_average(df, df_rave, df_noRave, "budgetRatio", "winner", percentage=10)
plt.figure(figsize=(10, 5))
#plt.plot(budgets, win_rates_p2, label="Joueur 2(Rave & No rave)", color='blue')
plt.plot(budgets, win_rates_rave, label="Joueur 2 (Rave)", color='red')
plt.plot(budgets, win_rates_no_rave, label="Joueur 2 (No rave)", color='green')
plt.xlabel("Budget")
plt.ylabel("Taux de victoire")
plt.title("Évolution du taux de victoire en fonction du budget")
plt.legend()
plt.grid()
plt.show()
