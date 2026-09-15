from sklearn.linear_model import LinearRegression
import numpy as np
x = np.array([1,2,3,4,5,6,7,8,9,10]).reshape(-1, 1)
#x = np.array([[1], [2], [3], [4], [5], [6], [7], [8], [9], [10]])
y = np.array([2, 4, 5, 4, 5, 6, 7, 8, 9, 10])

model = LinearRegression()
model.fit(x, y)

print("Slope (m):", model.coef_[0])
print("Intercept (c):", model.intercept_)

value = float(input("Enter a value for x to predict y: "))
prediction = model.predict([[value]])
print("Predicted value:", prediction)

