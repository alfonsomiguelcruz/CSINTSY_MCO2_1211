# CSINTSY_MCO2_1211
A Human vs AI Checkers program that uses Adversarial Search, with and without move ordering.

## Alpha - Beta Pruning Algorithm without Move Ordering
The algorithm implemented is a regular Alpha - Beta minimax algorithm, which removes parts of the state
space tree if the found move is less viable than the current state.

## Alpha - Beta Pruning Algorithm with Move Ordering
The improved algorithm removes other expanded nodes that are not as good as the best node from the list
of expanded nodes. If all nodes have the same value, all nodes are kept to prevent removing the most
efficient move implemented by the agent.

## Compilation and Execution
To Compile:
\Folder> ```javac controller/*.java view/*.java models/*.java```

To Execute:
\Folder> ```java controllers/Controller```
