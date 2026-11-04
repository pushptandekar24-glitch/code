import numpy as np

states = ["Rainy", "Sunny"]
observations = ["Walk", "Shop", "Clean"]
obs_seq = [0, 1, 2]  # Walk=0, Shop=1, Clean=2

# HMM Parameters
pi = np.array([0.6, 0.4])  # Initial probabilities

A = np.array([
    [0.7, 0.3],  # Transition matrix
    [0.4, 0.6]
])

B = np.array([
    [0.1, 0.4, 0.5],  # Emission matrix
    [0.6, 0.3, 0.1]
])

# ------------------ Forward Algorithm ------------------
def forward(obs_seq, A, B, pi):
    N = len(A)       # number of states
    T = len(obs_seq) # length of observation sequence
    alpha = np.zeros((T, N))

    # Initialization
    alpha[0] = pi * B[:, obs_seq[0]]

    # Induction
    for t in range(1, T):
        for j in range(N):
            alpha[t, j] = np.sum(alpha[t - 1] * A[:, j]) * B[j, obs_seq[t]]

    # Termination
    return np.sum(alpha[T - 1])


# ------------------ Viterbi Algorithm ------------------
def viterbi(obs_seq, A, B, pi):
    N = len(A)
    T = len(obs_seq)

    delta = np.zeros((T, N))
    psi = np.zeros((T, N), dtype=int)

    # Initialization
    delta[0] = pi * B[:, obs_seq[0]]

    # Recursion
    for t in range(1, T):
        for j in range(N):
            seq_probs = delta[t - 1] * A[:, j]
            psi[t, j] = np.argmax(seq_probs)
            delta[t, j] = np.max(seq_probs) * B[j, obs_seq[t]]

    # Termination
    best_path_prob = np.max(delta[T - 1])
    best_last_state = np.argmax(delta[T - 1])

    # Backtracking
    best_path = [best_last_state]

    for t in range(T - 1, 0, -1):
        best_last_state = psi[t, best_last_state]
        best_path.insert(0, best_last_state)

    return best_path_prob, [states[i] for i in best_path]


# ------------------ Run ------------------
forward_prob = forward(obs_seq, A, B, pi)
viterbi_prob, viterbi_path = viterbi(obs_seq, A, B, pi)

print("Forward Algorithm: Probability of Observations =", forward_prob)
print("Viterbi Algorithm: Best Path Probability =", viterbi_prob)
print("Most Likely State Sequence =", viterbi_path)