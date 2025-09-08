🧩 Rubik’s Cube Solver – AeroHack Challenge

This project implements a beginner-friendly Rubik’s Cube Solver in Java.
It models the cube using a sticker-based representation, simulates face rotations, and applies step-by-step solving algorithms (White Cross → White Corners → Second Layer → Yellow Cross → Last Layer) to reach the solved state.

✨ Features

Sticker-based cube representation (Map<Character, Character[][]>)

Move Engine – supports all standard moves (F, R, U, L, D, B + primes ')

Scramble Generator – produces random sequences of moves to scramble the cube

Step-by-Step Solving (Beginner’s Method):

Solve White Cross (via Daisy method 🌼)

Solve White Corners

Solve Second Layer edges

Form Yellow Cross

Position Yellow Corners

Orient Yellow Corners

Position Final Layer Edges

Cube Visualizer – prints the cube net in a clear, readable format
