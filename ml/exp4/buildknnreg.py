import numpy as np
from sklearn.neighbors import KNeighborsRegressor
from sklearn.metrics import mean_squared_error

X_train = np.array([[1], [2], [3], [4], [5]])
Y_train = np.array([10, 20, 30, 40, 50])

X_test = np.array([[3.5]])
Y_test = np.array([35])

model = KNeighborsRegressor(n_neighbors=2)

model.fit(X_train, Y_train)

Y_pred = model.predict(X_test)

print("Predicted value:", Y_pred)

print("MSE:", mean_squared_error(Y_test, Y_pred))