import pandas as pd
import matplotlib.pyplot as plt
from sklearn.tree import DecisionTreeClassifier, plot_tree
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, confusion_matrix
from itertools import chain, combinations

def all_subsets(lst):
    return list(chain.from_iterable(combinations(lst, r) for r in range(1, len(lst) + 1)))


df = pd.read_csv("../../game_result.csv")

df = df[df["budgetPlayer2"] > 0]  
# Création d'une nouvelle colonne "budget_ratio" qui est le ratio entre le budget du joueur 1 et du joueur 2
df["budget_ratio"] = df["budgetPlayer1"] / df["budgetPlayer2"]

# Fonction pour tester tous les sous-ensembles possibles de variables afin de trouver le meilleur modèle d'arbre de décision
def test_all_subsets(data, parameters):
    subsets = all_subsets(parameters)

    # Initialisation des variables pour suivre le meilleur modèle
    best_accuracy = -1
    best_matrix = None
    best_subset = []
    best_tree_model = None
    best_X = None
    print("Simulation sur différents ensemble de variaables :")
    for subset in subsets:
        print(f"Test pour l'ensembe de valeurs {subset}")
        # Sélection des variables pertinentes dans le DataFrame
        X = data[list(subset)]
        y = (data["winner"] == 1).astype(int)

        X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
        tree_model = DecisionTreeClassifier(random_state=42)
        # Entraînement du modèle d'arbre de décision sur les données d'entraînement
        tree_model.fit(X_train, y_train)

        # Prédiction sur les données de test
        y_pred = tree_model.predict(X_test)

        # Calcul de la précision du modèle
        accuracy = accuracy_score(y_test, y_pred)
        conf_matrix = confusion_matrix(y_test, y_pred)
        if accuracy > best_accuracy:
            best_accuracy = accuracy
            best_matrix = conf_matrix
            best_subset = subset
            best_X = X
            best_tree_model = tree_model
        print(f"Précision: {accuracy:.2f}")
        #print("Matrice de confusion:\n", conf_matrix)
    return best_accuracy, best_matrix, best_subset, best_tree_model, best_X

    
params = ["budget_ratio", "gridSize", "movesCount", "duration"]
accuracy, conf_matrix, best_subset, tree_model, X = test_all_subsets(df, params)


# Affichage des résultats finaux : meilleure précision, meilleure matrice de confusion et meilleur sous-ensemble de variables
print(f"Meilleure précision: {accuracy:.2f}")
print("Meilleure matrice de confusion:\n", conf_matrix)
print(f"Meilleur ensemble de variables : {best_subset}")

#Arbre de décision obtenu
plt.figure(figsize=(12, 6))
plot_tree(tree_model, feature_names=X.columns, class_names=["Joueur 2", "Joueur 1"], filled=True)
plt.show()
