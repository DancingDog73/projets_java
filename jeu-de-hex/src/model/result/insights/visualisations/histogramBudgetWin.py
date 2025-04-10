import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("../../game_result.csv")
victories_p1 = df[df["winner"] == 1]["budgetPlayer1"]
victories_p2 = df[df["winner"] == 2]["budgetPlayer2"]
plt.figure(figsize=(10, 6))
plt.hist(victories_p1, bins=20, alpha=0.7, color='blue', label='Victoires Joueur 1')
plt.hist(victories_p2, bins=20, alpha=0.7, color='red', label='Victoires Joueur 2')
plt.xlabel("Budget")
plt.ylabel("Nombre de victoires")
plt.title("Distribution des victoires en fonction du budget")
plt.legend()
plt.grid(axis='y', linestyle='--', alpha=0.7)
plt.show()
