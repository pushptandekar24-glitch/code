import numpy as np

# Dataset
X = np.array([
    [1, 1],
    [2, 1],
    [1, 2],
    [5, 5],
    [6, 5],
    [5, 6]
], dtype=float)

# SVM labels must be -1 and +1
Y = np.array([-1, -1, -1, 1, 1, 1])

# Initialize weights and bias
w = np.zeros(X.shape[1])
b = 0

learning_rate = 0.001
lambda_param = 0.01
epochs = 1000

# Gradient descent
for epoch in range(epochs):

    for i in range(len(X)):

        condition = Y[i] * (np.dot(X[i], w) + b)

        if condition >= 1:
            w = w - learning_rate * (2 * lambda_param * w)
        else:
            w = w - learning_rate * (
                2 * lambda_param * w - Y[i] * X[i]
            )

            b = b + learning_rate * Y[i]

# Prediction
predictions = np.sign(np.dot(X, w) + b)

print("Weights:", w)
print("Bias:", b)
print("Predictions:", predictions)

accuracy = np.mean(predictions == Y)

print("Accuracy:", accuracy)