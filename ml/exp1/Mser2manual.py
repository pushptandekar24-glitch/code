import numpy as np
import matplotlib.pyplot as plt

# Dataset
X = np.array([1, 2, 3, 4, 5])
Y = np.array([2, 4, 5, 4, 5])

# Calculate means
x_mean = np.mean(X)
y_mean = np.mean(Y)

# Calculate slope (m)
m = np.sum((X - x_mean) * (Y - y_mean)) / np.sum((X - x_mean) ** 2)

# Calculate intercept (c)
c = y_mean - m * x_mean

# Prediction
Y_pred = m * X + c

# Mean Squared Error
mse = np.mean((Y - Y_pred) ** 2)

# R2 Score
ss_total = np.sum((Y - y_mean) ** 2)
ss_residual = np.sum((Y - Y_pred) ** 2)

r2 = 1 - (ss_residual / ss_total)

print("Slope:", m)
print("Intercept:", c)
print("Predicted values:", Y_pred)
print("MSE:", mse)
print("R2 Score:", r2)

# Plot
plt.scatter(X, Y)
plt.plot(X, Y_pred)
plt.xlabel("X")
plt.ylabel("Y")
plt.title("Simple Linear Regression")
plt.show()