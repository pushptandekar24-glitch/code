import numpy as np
import matplotlib.pyplot as plt

from sklearn.svm import SVC
from sklearn.metrics import accuracy_score
from sklearn.metrics import confusion_matrix
from sklearn.metrics import classification_report

# Dataset
X = np.array([
    [1, 1],
    [2, 1],
    [1, 2],
    [5, 5],
    [6, 5],
    [5, 6]
])

Y = np.array([0, 0, 0, 1, 1, 1])

# Create SVM model
model = SVC(kernel='linear')

# Train
model.fit(X, Y)

# Prediction
Y_pred = model.predict(X)

print("Predictions:", Y_pred)

print("Accuracy:", accuracy_score(Y, Y_pred))

print("Confusion Matrix:")
print(confusion_matrix(Y, Y_pred))

print("Classification Report:")
print(classification_report(Y, Y_pred))

print("Support Vectors:")
print(model.support_vectors_)

# Plot data
plt.scatter(X[:, 0], X[:, 1], c=Y)

# Decision boundary
w = model.coef_[0]
b = model.intercept_[0]

x_values = np.linspace(0, 7, 100)
y_values = -(w[0] * x_values + b) / w[1]

plt.plot(x_values, y_values)

plt.xlabel("Feature 1")
plt.ylabel("Feature 2")
plt.title("SVM Decision Boundary")
plt.show()