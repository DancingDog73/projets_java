import pandas as pd
import numpy as np
import tensorflow as tf
from tensorflow import keras
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import accuracy_score, confusion_matrix
from tensorflow.keras.layers import Dense, Dropout

df = pd.read_csv("../../game_result.csv")
df = df[df["budgetPlayer2"] > 0]  
df["budget_ratio"] = df["budgetPlayer1"] / df["budgetPlayer2"]

# Sélection des caractéristiques (features) et de la variable cible (target) pour l'entraînement.
X = df[["budget_ratio", "gridSize", "movesCount", "duration", "ravePlayerOne"]]
y = (df["winner"] == 1).astype(int)
# Mise à l'échelle des données pour que toutes les caractéristiques soient sur la même échelle.
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)
# Division des données en ensembles d'entraînement (80%) et de test (20%).
X_train, X_test, y_train, y_test = train_test_split(X_scaled, y, test_size=0.2, random_state=42)

"""
#réseau simple
model = keras.Sequential([
    Dense(16, activation='relu', input_shape=(X_train.shape[1],)),
    Dense(16, activation='relu'),
    Dense(1, activation='sigmoid')  
])
"""

# Nouvelle architecture du modèle avec plus de couches et Dropout pour éviter le sur-apprentissage.
model = keras.Sequential([
    Dense(32, activation='relu', input_shape=(X_train.shape[1],)),
    Dense(32, activation='relu'),
    Dense(16, activation='relu'),
    Dropout(0.2),  
    Dense(1, activation='sigmoid')  
])


model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])
# Entraînement du modèle 
model.fit(X_train, y_train, epochs=50, batch_size=8, validation_data=(X_test, y_test), verbose=1)
# Prédictions sur l'ensemble de test.
y_pred = (model.predict(X_test) >= 0.5).astype(int)
# Calcul de l'accuracy et de la matrice de confusion.
accuracy = accuracy_score(y_test, y_pred)
conf_matrix = confusion_matrix(y_test, y_pred)

print(f"Accuracy: {accuracy:.2f}")
print("Matrice de confusion:\n", conf_matrix)
