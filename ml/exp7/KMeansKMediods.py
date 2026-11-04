
import pandas as pd
import matplotlib.pyplot as plt

from sklearn.datasets import load_iris
from sklearn.preprocessing import StandardScaler
from sklearn.cluster import KMeans
from sklearn_extra.cluster import KMedoids

# Load Iris dataset
iris = load_iris()
data = pd.DataFrame(iris.data, columns=iris.feature_names)

# Standardize the data
scaler = StandardScaler()
scaled_data = scaler.fit_transform(data)

# K-Means
kmeans = KMeans(n_clusters=3, random_state=42, n_init=10)
kmeans.fit(scaled_data)

data["KMeans_Cluster"] = kmeans.labels_

# K-Medoids
kmedoids = KMedoids(n_clusters=3, random_state=42)
kmedoids.fit(scaled_data)

data["KMedoids_Cluster"] = kmedoids.labels_

# Display results
print("K-Means clusters:")
print(data["KMeans_Cluster"].value_counts())

print("\nK-Medoids clusters:")
print(data["KMedoids_Cluster"].value_counts())

# Visualize K-Means clusters
plt.scatter(
    scaled_data[:, 0],
    scaled_data[:, 1],
    c=kmeans.labels_,
    cmap="viridis"
)

plt.title("K-Means Clustering")
plt.xlabel("Standardized Sepal Length")
plt.ylabel("Standardized Sepal Width")
plt.show()

# Visualize K-Medoids clusters
plt.scatter(
    scaled_data[:, 0],
    scaled_data[:, 1],
    c=kmedoids.labels_,
    cmap="plasma"
)

plt.title("K-Medoids Clustering")
plt.xlabel("Standardized Sepal Length")
plt.ylabel("Standardized Sepal Width")
plt.show()
