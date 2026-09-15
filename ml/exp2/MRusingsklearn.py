#Multiple regression using the sklearn library
import numpy as np
from sklearn.linear_model import LinearRegression
X = np.array([
    [1, 2],
    [2, 1],
    [3, 4],
    [4, 3],
    [5, 5],
    [6, 7]
])
Y = np.array([4.5, 5.2, 8.8, 9.6, 12.5, 15.1])

# Create a LinearRegression model
model = LinearRegression()

# Fit the model to the data
model.fit(X, Y)

model.intercept_ # Intercept (beta_0)
model.coef_ # Coefficients (beta_1, beta_2)

print("Beta coefficients:", [model.intercept_] + list(model.coef_))

X1 = float(input("Enter value for X1: "))
X2 = float(input("Enter value for X2: "))
# Prediction
Prediction = model.predict([[X1, X2]])
print("Predicted value of Y:", Prediction[0])