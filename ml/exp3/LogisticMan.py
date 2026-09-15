import numpy as np

# Dataset
X = np.array([1, 2, 3, 4, 5, 6], dtype=float)
Y = np.array([0, 0, 0, 1, 1, 1], dtype=float)

# Initialize parameters
w = 0.0
b = 0.0

learning_rate = 0.1
epochs = 1000

# Sigmoid function
def sigmoid(z):
    return 1 / (1 + np.exp(-z))

# Gradient Descent
for i in range(epochs):

    z = w * X + b
    predictions = sigmoid(z)

    dw = np.mean((predictions - Y) * X)
    db = np.mean(predictions - Y)

    w = w - learning_rate * dw
    b = b - learning_rate * db

# Final probability
probabilities = sigmoid(w * X + b)

# Convert probability to class
Y_pred = (probabilities >= 0.5).astype(int)

print("Weight:", w)
print("Bias:", b)
print("Probabilities:", probabilities)
print("Predicted classes:", Y_pred)

accuracy = np.mean(Y_pred == Y)

print("Accuracy:", accuracy)