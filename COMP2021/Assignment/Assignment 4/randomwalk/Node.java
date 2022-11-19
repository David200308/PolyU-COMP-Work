package hk.edu.polyu.comp.comp2021.assignment4.randomwalk;

import java.util.ArrayList;
import java.util.HashSet;

class Node{

    // degree of a node is the number of adjacency nodes, i.e., the number of nodes that are connected to this node by an edge.
    private int degree;
    //The graph this node belongs to
    private Graph graph;

    public Graph getGraph(){
        return this.graph;
    }

    public void setGraph(Graph graph){
        this.graph = graph;
    }

    // Task 1: Obtain the degree of this by referring to all the random walk sequences.
    public void setDegree(){
        ArrayList<Node> list = new ArrayList<>();
        HashSet<RandomWalkSequence> allRandomWalkSeq = new HashSet<>();
        allRandomWalkSeq = getGraph().getAllRandomWalkSequences();
        for (RandomWalkSequence e : allRandomWalkSeq) {
            ArrayList<Node> a = new ArrayList<>();
            a = e.getSequence();
            int index = a.indexOf(this);
            if (!list.contains(a.get(index + 1))) {
                list.add(a.get(index + 1));
            }
        }
        this.degree = list.size();
    }

    public int getDegree(){
        return this.degree;
    }

    // Task 2: Given another node o, obtain the transition probability from this node to the given node.
    // transition probability is calculated by f(this, o) / f(this, all).
    // f(this, o) is the frequency of o as the next node of this within all random walk sequences.
    // f(this, all) is the frequency of this having a next node within all random walk sequences.
    // When f(this, all) = 0, the transition probability is 0.
    public double transitionProbability(Node o){
        double thisO = 0;
        double thisAll = 0;
        HashSet<RandomWalkSequence> allRandomWalkSeq = getGraph().getAllRandomWalkSequences();
        for (RandomWalkSequence e : allRandomWalkSeq) {
            ArrayList<Node> list = new ArrayList<>();
            list = e.getSequence();
            if (list.contains(this)) {
                int index = list.indexOf(this);
                int last = list.size() - 1;
                if (list.get(index + 1) == o && index != last) {
                    thisO++;
                }
                if (index != last) {
                    thisAll++;
                }
            }
        }
        if (thisAll == 0) {
            return 0;
        }
        double prob = thisO / thisAll;
        return prob;
    }
}