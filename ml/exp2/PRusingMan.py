import numpy as np
import matplotlib.pyplot as plt

# Original X values
x_original = np.array([2, 3, 4, 5, 6, 7])

# Target values
Y = np.array([4.5, 5.2, 8.8, 9.6, 12.5, 15.1])

# Create polynomial features (X and X²)
X = np.column_stack((x_original, x_original**2))

# Add a column of ones for the intercept
X = np.c_[np.ones(len(X)), X]

# Transpose of X
X_transpose = X.T

# XᵀX
XTX = np.dot(X_transpose, X)

# (XᵀX)⁻¹
XTX_inv = np.linalg.inv(XTX)

# XᵀY
XTY = np.dot(X_transpose, Y)

# Beta coefficients
beta = np.dot(XTX_inv, XTY)

print("Beta Coefficients:", beta)

# Regression equation
print(f"Regression Equation: Y = {beta[0]:.2f} + ({beta[1]:.2f})X + ({beta[2]:.2f})X²")

# User input
x = float(input("Enter value for X: "))

# Prediction
Y_pred = beta[0] + beta[1] * x + beta[2] * (x ** 2)
print("Predicted value of Y:", Y_pred)

# Predicted values for plotting
y_plot = beta[0] + beta[1] * x_original + beta[2] * (x_original ** 2)

# Plot graph
plt.scatter(x_original, Y, color="red", label="Actual Data")
plt.plot(x_original, y_plot, color="blue", label="Polynomial Regression")

plt.xlabel("X")
plt.ylabel("Y")
plt.title("Polynomial Regression (Manual)")
plt.legend()

plt.show()