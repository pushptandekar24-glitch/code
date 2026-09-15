import numpy as np
from collections import Counter

# Training data
X_train = np.array([
    [1, 1],
    [1, 2],
    [2, 1],
    [5, 5],
    [6, 5],
    [5, 6]
])

Y_train = np.array([
    "A", "A", "A",
    "B", "B", "B"
])

# Test point
X_test = np.array([2, 2])

k = 3

# Calculate Euclidean distance
distances = []

for i in range(len(X_train)):
    distance = np.sqrt(np.sum((X_train[i] - X_test) ** 2))
    distances.append((distance, Y_train[i]))

# Sort by distance
distances.sort()

# Select K nearest
neighbors = distances[:k]

# Get labels
labels = [x[1] for x in neighbors]

# Majority vote
prediction = Counter(labels).most_common(1)[0][0]

print("Distances:", distances)
print("Nearest neighbors:", neighbors)
print("Predicted class:", prediction)