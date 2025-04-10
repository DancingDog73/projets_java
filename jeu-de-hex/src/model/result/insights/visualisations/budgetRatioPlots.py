import pandas as pd
import matplotlib.pyplot as plt


df = pd.read_csv('../../game_result.csv')
print(df.head())
df['budgetRatio'] = df['budgetPlayer1'] / df['budgetPlayer2'] #calcul du rapportt de budget
print(df.describe())
prob_victory = df.groupby('budgetRatio')['winner'].value_counts(normalize=True).unstack().fillna(0)
print(prob_victory)

#probabilité de victoire en fonction du rapport de budget
prob_victory[1].plot(kind='line', title="Probabilité de victoire Player 1 en fonction du rapport de budget")
plt.xlabel("Rapport de budget")
plt.ylabel("Probabilité de victoire Player 1")
plt.show()

# relation entre la durée et le rapport de budget
plt.scatter(df['budgetRatio'], df['duration'], alpha=0.5)
plt.title("Relation entre le rapport de budget et la durée du jeu")
plt.xlabel("Rapport de budget")
plt.ylabel("Durée (ms)")
plt.show()

# nombre de coups en fonction du rapport de budget
plt.scatter(df['budgetRatio'], df['movesCount'], alpha=0.5)
plt.title("Relation entre le rapport de budget et le nombre de coups")
plt.xlabel("Rapport de budget")
plt.ylabel("Nombre de coups")
plt.show()
