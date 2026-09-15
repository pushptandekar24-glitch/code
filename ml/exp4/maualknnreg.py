import numpy as np

# Training data
X_train = np.array([1, 2, 3, 4, 5], dtype=float)
Y_train = np.array([10, 20, 30, 40, 50], dtype=float)

# Test point
X_test = 3.5
k = 2

# Calculate distances
distances = []

for i in range(len(X_train)):
    distance = abs(X_train[i] - X_test)
    distances.append((distance, Y_train[i]))

# Sort
distances.sort()

# Select K nearest
neighbors = distances[:k]

# Average target values
prediction = np.mean([x[1] for x in neighbors])

print("Nearest neighbors:", neighbors)
print("Predicted value:", prediction)