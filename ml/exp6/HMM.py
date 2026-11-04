
import numpy as np

# Hidden states: Rainy, Sunny
# Observations: Walk, Shop, Clean

states = ["Rainy", "Sunny"]
observations = ["Walk", "Shop", "Clean"]

# Initial state probabilities
pi = np.array([0.6, 0.4])

# Transition probability matrix
A = np.array([
    [0.7, 0.3],
    [0.4, 0.6]
])

# Emission probability matrix
B = np.array([
    [0.1, 0.4, 0.5],
    [0.6, 0.3, 0.1]
])

# Observation indices
obs = [0, 1, 2]

# Forward Algorithm
alpha = pi * B[:, obs[0]]

for t in range(1, len(obs)):
    alpha = (alpha @ A) * B[:, obs[t]]

probability = np.sum(alpha)

print("Forward probability:", probability)

# Viterbi Algorithm
delta = pi * B[:, obs[0]]
backpointer = []

for t in range(1, len(obs)):
    scores = delta[:, None] * A
    backpointer.append(np.argmax(scores, axis=0))
    delta = np.max(scores, axis=0) * B[:, obs[t]]

# Backtrack the most likely state sequence
last_state = np.argmax(delta)
path = [last_state]

for pointers in reversed(backpointer):
    last_state = pointers[last_state]
    path.append(last_state)

path.reverse()

print("Most likely states:")
print([states[i] for i in path])
