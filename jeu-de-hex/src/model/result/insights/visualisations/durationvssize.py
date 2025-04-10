import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("../../game_result.csv")

#Durée des parties en fonction de la taille de la grille
plt.figure(figsize=(10, 6))
plt.scatter(df["gridSize"], df["duration"], alpha=0.5, color="blue")
plt.xlabel("Taille de la grille")
plt.ylabel("Durée de la partie (ms)")
plt.title("Durée des parties en fonction de la taille de la grille")
plt.grid(True)
plt.show()
