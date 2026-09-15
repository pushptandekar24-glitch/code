#Multiple regression using the manual way
import numpy as np 
import matplotlib.pyplot as plt
X = np.array([
    [1, 2],
    [2, 1],
    [3, 4],
    [4, 3],
    [5, 5],
    [6, 7]
])
Y = np.array([4.5, 5.2, 8.8, 9.6, 12.5, 15.1])

X = np.c_[np.ones(len (X)), X] # Add a column of ones for the intercept

#transpose of X
X_transpose = X.T 

#x_transpose * X
XTX = np.dot(X_transpose, X)

#inverse of x_transpose * X
XTX_inv = np.linalg.inv(XTX)

#x_transpose * Y
XTY = np.dot(X_transpose, Y)

#beta coefficients
beta = np.dot(XTX_inv, XTY)
print("Beta coefficients:", beta)

#regression line
print("Regression line: Y =", beta[0], "+", beta[1], "* X1 +", beta[2], "* X2")


#user input for prediction
x1 = float(input("Enter value for X1: "))
x2 = float(input("Enter value for X2: "))

#prediction
Y_pred = beta[0] + beta[1] * x1 + beta[2] * x2
print("Predicted value of Y:", Y_pred)