from scipy.stats import chi2_contingency
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np

df = pd.read_csv("../../game_result.csv")
for size in df["gridSize"].unique():
    subset = df[df["gridSize"] == size]
    contingency = pd.crosstab(subset["budgetPlayer1"], subset["winner"])
    chi2, p, _, _ = chi2_contingency(contingency)
    print(f"Grid Size {size}: Khi-2 = {chi2:.3f}, p-value = {p:.3f}")