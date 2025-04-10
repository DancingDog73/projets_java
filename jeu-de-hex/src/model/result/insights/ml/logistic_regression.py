import pandas as pd
import numpy as np
import seaborn as sns 
import matplotlib.pyplot as plt
import statsmodels.api as sm
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, confusion_matrix, roc_curve, auc 
from itertools import chain, combinations


df = pd.read_csv("../../game_result.csv")
def all_subsets(lst):
    return list(chain.from_iterable(combinations(lst, r) for r in range(1, len(lst) + 1)))

df = df[df["budgetPlayer2"] > 0]
df["budget_ratio"] = df["budgetPlayer1"] / df["budgetPlayer2"]
df["ravePlayerOne"] = df["ravePlayerOne"].astype(int)
df["ravePlayerTwo"] = df["ravePlayerTwo"].astype(int)

# Fonction pour tester tous les sous-ensembles possibles de variables pour voir lequel donne la meilleure précision
def test_all_subsets(data, parameters):
    
    subsets = all_subsets(parameters)
    best_accuracy = -1
    best_subset = []
    best_matrix = None
    best_result = None
    best_y_test = None
    best_X_test = None
    print("Simulation sur différents ensemble de variaables :")
    for subset in subsets:
        print(f"Test pour l'ensembe de valeurs {subset}")
        X = df[list(subset)]
        y = (df["winner"] == 1).astype(int)
        # Diviser les données en ensembles de formation (80%) et de test (20%)
        X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
        X_train = sm.add_constant(X_train)
        X_test = sm.add_constant(X_test)
        model = sm.Logit(y_train, X_train)
        # Entraînement du modèle
        result = model.fit()
        y_pred = (result.predict(X_test) >= 0.5).astype(int)
        # Calcul de la précision du modèle (accuracy)
        accuracy = accuracy_score(y_test, y_pred)
        conf_matrix = confusion_matrix(y_test, y_pred)
        if accuracy > best_accuracy:
            best_accuracy = accuracy
            best_matrix = conf_matrix
            best_subset = subset
            best_result = result
            best_y_test = y_test
            best_X_test = X_test
        print(f"Précision: {accuracy:.2f}")
        print("Matrice de confusion:\n", conf_matrix)
       
    return best_accuracy, best_matrix, best_subset, best_result, best_X_test, best_y_test

#paramètres à tester
params = ["budget_ratio", "gridSize", "movesCount", "duration", "nodeCountPlayer1", "nodeCountPlayer2"]
accuracy, conf_matrix, best_subset, result, X_test, y_test = test_all_subsets(df, params)
#Meilleurs résultats
print(f"Meilleure précision: {accuracy:.2f}")
print("Meilleure matrice de confusion:\n", conf_matrix)
print(f"Meilleur ensemble de variables {best_subset}")

# Calcul de la courbe ROC et de l'AUC (aire sous la courbe)
fpr, tpr, _ = roc_curve(y_test, result.predict(X_test))
roc_auc = auc(fpr, tpr)

# Affichage de la courbe ROC
plt.figure(figsize=(6,6))
plt.plot(fpr, tpr, label=f"ROC curve (AUC = {roc_auc:.2f})")
plt.plot([0, 1], [0, 1], 'k--')
plt.xlabel('False Positive Rate')
plt.ylabel('True Positive Rate')
plt.title('Courbe ROC')
plt.legend()
plt.show()


