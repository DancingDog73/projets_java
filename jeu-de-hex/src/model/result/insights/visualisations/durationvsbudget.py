import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("../../game_result.csv")

#Durée des parties en fonction du rapport de budget
plt.figure(figsize=(10, 6))
plt.scatter(df["budgetPlayer1"]/df["budgetPlayer2"], df["duration"], alpha=0.5, color="blue")
plt.xlabel("rapport de budget joueur1/joueur2")
plt.ylabel("Durée de la partie (ms)")
plt.title("Durée des parties en fonction du rapport de budget joueur1/joueur2")
plt.grid(True)
plt.show()
