import numpy as np
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_squared_error, r2_score

# Dataset
X = np.array([[1], [2], [3], [4], [5]])
Y = np.array([2, 4, 5, 4, 5])

# Create model
model = LinearRegression()

# Train model
model.fit(X, Y)

# Prediction
Y_pred = model.predict(X)

print("Slope:", model.coef_[0])
print("Intercept:", model.intercept_)
print("Predicted values:", Y_pred)

# Evaluation
mse = mean_squared_error(Y, Y_pred)
r2 = r2_score(Y, Y_pred)

print("MSE:", mse)
print("R2 Score:", r2)

# Plot
plt.scatter(X, Y)
plt.plot(X, Y_pred)
plt.xlabel("X")
plt.ylabel("Y")
plt.title("Linear Regression")
plt.show()