#Implementing simple linear regression using the mathematical function
import numpy as np
import matplotlib.pyplot as plt

x = np.array([1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
y = np.array([2, 4, 5, 4, 5, 6, 7, 8, 9, 10])

n = len(x)
m = (n * np.sum(x * y) - np.sum(x) * np.sum(y)) / (n * np.sum(x ** 2) - (np.sum(x) ** 2))

c = (np.sum(y) - m * np.sum(x)) / n

print("Slope (m):", m)
print("Intercept (c):", c)

prediction = m * x + c
print("Predicted values:", prediction)

plt.scatter(x, y, color='blue', label='Data points')
plt.plot(x, m * x + c, color='red', label='Regression line')
plt.xlabel('x')
plt.ylabel('y')
plt.title('Simple Linear Regression using mathematical function')
plt.legend()
plt.show()