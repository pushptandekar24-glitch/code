# Import libraries
import pandas as pd
from sklearn.cluster import KMeans
import matplotlib.pyplot as plt
from sklearn.preprocessing import StandardScaler
from sklearn.datasets import load_iris

# Load dataset
iris = load_iris()
data = pd.DataFrame(iris.data, columns=iris.feature_names)

# Standardize data
scaler = StandardScaler()
scaled_data = scaler.fit_transform(data)

kmeans = KMeans(n_clusters=3, random_state=42)
kmeans.fit(scaled_data)

data["Cluster"] = kmeans.labels_

plt.scatter(
    data.iloc[:, 0],
    data.iloc[:, 1],
    c=data["Cluster"],
    cmap="rainbow"
)
plt.title("K-Means Clustering on Iris Dataset")
plt.xlabel("Sepal Length (scaled)")
plt.ylabel("Sepal Width (scaled)")
plt.show()