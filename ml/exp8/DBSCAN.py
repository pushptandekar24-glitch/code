
import numpy as np
import matplotlib.pyplot as plt
from sklearn.cluster import DBSCAN

# Sample two-dimensional dataset
X = np.array([
    [1, 1],
    [2, 1],
    [3, 1],
    [7, 5],
    [8, 5],
    [9, 5],
    [12, 9]
])

names = list("ABCDEFG")

# Apply DBSCAN
model = DBSCAN(eps=1, min_samples=3, metric="euclidean")
labels = model.fit_predict(X)

# Identify core points
core_indices = set(model.core_sample_indices_)

# Display cluster and point type
print("Point  Cluster  Type")

for i, label in enumerate(labels):
    if label == -1:
        cluster = "Noise"
        point_type = "Noise"
    else:
        cluster = str(label + 1)

        if i in core_indices:
            point_type = "Core"
        else:
            point_type = "Border"

    print(names[i], cluster, point_type)

# Visualize the clusters
for label in sorted(set(labels)):
    points = X[labels == label]

    if label == -1:
        plt.scatter(
            points[:, 0],
            points[:, 1],
            marker="x",
            s=100,
            label="Noise"
        )
    else:
        plt.scatter(
            points[:, 0],
            points[:, 1],
            s=100,
            label=f"Cluster {label + 1}"
        )

# Add point names
for i, (x, y) in enumerate(X):
    plt.annotate(names[i], (x, y))

plt.xlabel("X coordinate")
plt.ylabel("Y coordinate")
plt.title("DBSCAN Clustering")
plt.legend()
plt.grid(True)
plt.axis("equal")
plt.show()
