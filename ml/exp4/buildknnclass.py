import numpy as np
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import accuracy_score, confusion_matrix

X_train = np.array([
    [1, 1],
    [1, 2],
    [2, 1],
    [5, 5],
    [6, 5],
    [5, 6]
])

Y_train = np.array([0, 0, 0, 1, 1, 1])

X_test = np.array([
    [2, 2],
    [5, 5]
])

Y_test = np.array([0, 1])

# Create model
model = KNeighborsClassifier(n_neighbors=3)

# Train
model.fit(X_train, Y_train)

# Predict
Y_pred = model.predict(X_test)

print("Predictions:", Y_pred)

print("Accuracy:", accuracy_score(Y_test, Y_pred))

print("Confusion Matrix:")
print(confusion_matrix(Y_test, Y_pred))