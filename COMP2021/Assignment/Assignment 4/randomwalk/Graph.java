package hk.edu.polyu.comp.comp2021.assignment4.randomwalk;

import java.util.HashSet;

class Graph{

    private HashSet<RandomWalkSequence> allRandomWalkSequences;
    // Get all the random walk sequences.
    public HashSet<RandomWalkSequence> getAllRandomWalkSequences() {
        return this.allRandomWalkSequences;
    }
    // Set all the random walk sequences.
    public void setAllRandomWalkSequences(HashSet<RandomWalkSequence> allRandomWalkSequences) {
        this.allRandomWalkSequences = allRandomWalkSequences;
    }

    /* other implementation details omitted */
}
