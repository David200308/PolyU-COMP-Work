package hk.edu.polyu.comp.comp2021.assignment4.compset;

import java.util.ArrayList;
import java.util.List;

class CompSet<T> {

    /** Each CompSet uses at most 1023 buckets.   */
    private static final int NUBMER_OF_BUCKETS = 1023;

    /** An array of buckets as the storage for each set. */
    private List<T>[] storage;

    public CompSet() {
        storage = new List[NUBMER_OF_BUCKETS];
    }

    /**
     * Initialize 'this' with the unique elements from 'elements'.
     * Throw IllegalArgumentException if 'elements' is null.
     */
    public CompSet(List<T> elements) {
        // Add missing code here
        if(elements==null){
            throw new IllegalArgumentException();
        }

        storage = new List[NUBMER_OF_BUCKETS];

        for(T element : elements) {
            if (!this.contains(element)) {
                int index = this.getIndex(element);
                if (storage[index] == null) {
                    storage[index] = new ArrayList<T>();
                }
                storage[index].add(element);
            }
        }
    }

    /**
     * Get the total number of elements stored in 'this'.
     */
    public int getCount() {
        // Add missing code here
        int count = 0;
        for(int i = 0; i < NUBMER_OF_BUCKETS; i++){
            if (this.storage[i] != null) {
                count += this.storage[i].size();
            }
        }
        return count;
    }

    public boolean isEmpty() {
        // Add missing code here
        if (this.getCount() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Whether 'element' is contained in 'this'?
     */
    public boolean contains(T element) {
        // Add missing code here
        if(element==null){
            return false;
        }

        int index=this.getIndex(element);
        if(this.storage[index]==null){
            return false;
        }

        for(T e:storage[index]){
            if(e==element){
                return true;
            }
        }
        return false;
    }

    /**
     * Get all elements of 'this' as a list.
     */
    public List<T> getElements() {
        // Add missing code here
        List<T> temp=new ArrayList<>();
        for(int i=0;i<NUBMER_OF_BUCKETS;i++){
            if(storage[i] != null){
                for(T e:storage[i]){
                    temp.add(e);
                }
            }
        }
        return temp;
    }

    /**
     * Add 'element' to 'this', if it is not contained in 'this' yet.
     * Throw IllegalArgumentException if 'element' is null.
     */
    public void add(T element) {
        // Add missing code here
        if(element==null){
            throw new IllegalArgumentException();
        }

        if(this.contains(element)){
            return;
        }

        int index =this.getIndex(element);
        if(this.storage[index]==null){
            storage[index]=new ArrayList<>();
        }
        storage[index].add(element);
    }

    /**
     * Two CompSets are equivalent is they contain the same elements.
     * The order of the elements inside each CompSet is irrelevant.
     */
    public boolean equals(Object other){
        // Add missing code here
        if (other == null){
            return false;
        }

        if (this == other) {
            return true;
        }

        if (this.getClass() != other.getClass()){
            return false;
        }

        CompSet<T> temp = (CompSet<T>) other;
        if (this.getCount() != temp.getCount()){
            return false;
        }

        List<T> element = this.getElements();
        for(T e : element){
            if(!temp.contains(e)){
                return false;
            }
            return true;
        }

        return false;
    }

    /**
     * Remove 'element' from 'this', if it is contained in 'this'.
     * Throw IllegalArgumentException if 'element' is null.
     */
    public void remove (T element) {
        // Add missing code here
        if(element==null){
            throw new IllegalArgumentException();
        }

        if(this.contains(element)){
            int index=this.getIndex(element);
            this.storage[index].remove(element);
        }
    }

    //========================================================================== private methods

    private int getIndex(T element) {
        return element.hashCode() % NUBMER_OF_BUCKETS;
    }

}


