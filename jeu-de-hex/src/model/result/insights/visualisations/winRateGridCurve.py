import pandas as pd
import matplotlib.pyplot as plt

def moving_average_adaptive(values, window_size=3):
    smoothed = []
    for i in range(len(values)):
        start = max(0, i - window_size // 2)
        end = min(len(values), i + window_size // 2 + 1)
        smoothed.append(sum(values[start:end]) / (end - start))
    return smoothed

df = pd.read_csv("../../game_result.csv")
grids = sorted(df["gridSize"].unique())
winrates_p1 = []
winrates_p2 = []

for size in grids:
    subset = df[df["gridSize"] == size]
    total_games = len(subset)
    wins_p1 = len(subset[subset["winner"] == 1])
    wins_p2 = len(subset[subset["winner"] == 2])
    winrates_p1.append(wins_p1 / total_games)
    winrates_p2.append(wins_p2 / total_games)

# Appliquer une moyenne glissante adaptative
smoothed_p1 = moving_average_adaptive(winrates_p1)
smoothed_p2 = moving_average_adaptive(winrates_p2)
plt.figure(figsize=(10, 5))
plt.plot(grids, smoothed_p1, label="Joueur 1", color="blue", marker="o")
plt.plot(grids, smoothed_p2, label="Joueur 2", color="red", marker="s")
plt.xlabel("Taille de la grille")
plt.ylabel("Taux de victoire")
plt.title("Évolution du taux de victoire en fonction de la taille de la grille")
plt.legend()
plt.grid()
plt.show()
