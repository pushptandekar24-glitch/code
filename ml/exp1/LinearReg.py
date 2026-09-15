import numpy as np
from sklearn.linear_model import LinearRegression

X = np.array([1,2,3,4,5,6,7,8,9,10])
y = np.array([2,4,5,4,5,6,7,8,9,10])

mean_x = np.sum(X)/len(X)
mean_y = np.sum(y)/len(y)

numerator = 0
denominator = 0
for i in range(len(X)):
    numerator += (X[i] - mean_x) * (y[i] - mean_y)

for i in range(len(X)):
    denominator += (X[i] - mean_x) ** 2

m = numerator / denominator
c = mean_y - (m * mean_x)

input_value = float(input("Enter a value for x to predict y: "))
y_pred = m * input_value + c
print(f"Predicted value for x={input_value}: {y_pred}")



#for i in range(len(X)):
#    y_pred = m * X[i] + c
#    print(f"Predicted value for x={X[i]}: {y_pred}")
