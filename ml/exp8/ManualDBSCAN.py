import numpy as np
import matplotlib.pyplot as plt
from sklearn.cluster import DBSCAN

X = np.array([
    [1, 1],   # A
    [2, 1],   # B
    [3, 1],   # C
    [7, 5],   # D
    [8, 5],   # E
    [9, 5],   # F
    [12, 9]   # G
])

names = [f"P{i+1}" for i in range(len(X))]

# Names used in the given example
names = list("ABCDEFG")

# Apply DBSCAN
model = DBSCAN(eps=1, min_samples=3, metric="euclidean")
labels = model.fit_predict(X)

# Identify core points
core_indices = set(model.core_sample_indices_)

# Display cluster and point type
print("Point Cluster Type")

for i, label in enumerate(labels):
    if label == -1:
        cluster = "Noise"
        point_type = "Noise"
    else:
        cluster = str(label + 1)
        point_type = "Core" if i in core_indices else "Border"

    print(f"{names[i]:<6} {cluster:<8} {point_type}")

# Plot clusters
for label in sorted(set(labels)):
    points = X[labels == label]

    if label == -1:
        plt.scatter(
            points[:, 0], points[:, 1],
            color="black", marker="x", s=100, label="Noise"
        )
    else:
        plt.scatter(
            points[:, 0], points[:, 1],
            s=100, label=f"Cluster {label + 1}"
        )

# Add point names
for i, (x, y) in enumerate(X):
    plt.annotate(
        names[i], (x, y),
        xytext=(6, 6),
        textcoords="offset points"
    )

plt.xlabel("X coordinate")
plt.ylabel("Y coordinate")
plt.title("DBSCAN: eps = 1, MinPts = 3")
plt.legend()
plt.grid(True)
plt.axis("equal")
plt.show()