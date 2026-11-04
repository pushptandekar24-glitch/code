# Import necessary libraries
from sklearn_extra.cluster import KMedoids

# Apply K-Medoids
kmedoids = KMedoids(n_clusters=3, random_state=42)
kmedoids.fit(scaled_data)

data["KMedoid_Cluster"] = kmedoids.labels_

# Visualization
plt.scatter(
    data.iloc[:, 0],
    data.iloc[:, 1],
    c=data["KMedoid_Cluster"],
    cmap="plasma"
)
plt.title("K-Medoids Clustering on Iris Dataset")
plt.xlabel("Sepal Length (scaled)")
plt.ylabel("Sepal Width (scaled)")
plt.show()