import numpy as np
import matplotlib.pyplot as plt
from sklearn.preprocessing import PolynomialFeatures
from sklearn.linear_model import LinearRegression

# Dataset
X = np.array([[1],[2], [3], [4], [5], [6], [7],[8]])
Y = np.array([2, 8, 12, 10, 7, 9, 15, 30])

# Create polynomial features
poly = PolynomialFeatures(degree=3)
X_poly = poly.fit_transform(X)

# Create and train model
model = LinearRegression()
model.fit(X_poly, Y)

# Print coefficients
print("Intercept:", model.intercept_)
print("Coefficients:", model.coef_)

# User input
x_new = float(input("Enter value for X: "))

# Convert input to polynomial features
x_new_poly = poly.transform([[x_new]])

# Prediction
Y_pred = model.predict(x_new_poly)

print("Predicted value of Y:", Y_pred[0])

# Plot
x_curve = np.linspace(min(X), max(X), 100).reshape(-1, 1)
x_curve_poly = poly.transform(x_curve)
y_curve = model.predict(x_curve_poly)

plt.scatter(X, Y, color="red", label="Actual Data")
plt.plot(x_curve, y_curve, color="blue", label="Polynomial Regression")

plt.xlabel("X")
plt.ylabel("Y")
plt.title("Polynomial Regression (Scikit-learn)")
plt.legend()

plt.show()