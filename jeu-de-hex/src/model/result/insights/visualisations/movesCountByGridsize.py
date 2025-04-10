import pandas as pd
import matplotlib.pyplot as plt


df = pd.read_csv('../../game_result.csv')

#évolution du nombre de coups en fonction de la taille de la grille
plt.figure(figsize=(10, 6))
avg_moves_per_grid = df.groupby('gridSize')['movesCount'].mean()
grid_sizes = avg_moves_per_grid.index.to_numpy()
avg_moves = avg_moves_per_grid.to_numpy()
plt.plot(grid_sizes, avg_moves, marker='o', linestyle='-', color='b')
plt.title("Évolution du nombre de coups en fonction de la taille de la grille")
plt.xlabel("Taille de la grille")
plt.ylabel("Nombre moyen de coups")
plt.grid(True)
plt.show()
