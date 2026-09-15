import numpy as np

from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score
from sklearn.metrics import confusion_matrix
from sklearn.metrics import classification_report

# Dataset
X = np.array([[1], [2], [3], [4], [5], [6]])
Y = np.array([0, 0, 0, 1, 1, 1])

# Create model
model = LogisticRegression()

# Train
model.fit(X, Y)

# Prediction
Y_pred = model.predict(X)

# Probability
probability = model.predict_proba(X)

print("Predicted classes:", Y_pred)
print("Probabilities:")
print(probability)

print("Accuracy:", accuracy_score(Y, Y_pred))

print("Confusion Matrix:")
print(confusion_matrix(Y, Y_pred))

print("Classification Report:")
print(classification_report(Y, Y_pred))