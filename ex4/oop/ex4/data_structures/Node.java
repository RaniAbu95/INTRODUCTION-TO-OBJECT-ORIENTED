package oop.ex4.data_structures;

/**
 * Created by Rani on 5/3/2016.
 * A class that represents a node at an avl tree.
 */

public class Node {
    private int key;
    private Node left;
    private Node right;
    private Node parent;
    private int balance;

    /**
     * A constructor that makes a new node with the given parent and key.
     * @param key the key that we want to set to the node.
     * @param parent the parent of the created node.
     */
    public Node(int key, Node parent){
        this.key = key;
        this.left = null;
        this.right = null;
        this.parent = parent;
    }

    /**
     * A getter for the node's key.
     * @return the key of the node.
     */
    int getKey(){
        return this.key;
    }

    /**
     * A setter for the node's key.
     * @param newKey the value that we want to set to the node's key.
     */
    public void setKey(int newKey){
        this.key = newKey;
    }

    /**
     * A getter of the right son.
     * @return the right son of the node.
     */
    public Node getRight(){
        return this.right;
    }

    /**
     * A getter of the left son.
     * @return the left son of the node.
     */
    public Node getLeft(){
        return this.left;
    }

    /**
     * A setter for the left son of the node.
     * @param temp the node that we want to set for the left son of the node.
     */
    public void setLeft(Node temp){
        this.left = temp;
    }

    /**
     * A setter for the right son of the node.
     * @param temp the node that we want to set for the right son of the node.
     */
    public void setRight(Node temp){
        this.right = temp;
    }

    /**
     * A getter for the parent of the node.
     * @return the node's parent.
     */
    public Node getParent(){
        return this.parent;
    }

    /**
     * A setter for the parent of the node.
     * @param node the new parent of the node.
     */
    public void setParent(Node node){
        this.parent = node;
    }

    /**
     * A getter for the balance of the node.
     * @return the balance value of the node.
     */
    public int getBalance(){
        return this.balance;
    }

    /**
     * A setter for the balance of the node.
     * @param node the node that we want to set its balance value.
     */
    public void setBalance(Node node){
        node.balance = height(node.getRight()) - height(node.getLeft());
    }

    /**
     * Calculating the "height" of a node.
     * @param cur the node that we desire to calculate it's height.
     * @return The height of a node (-1, if node is not existent eg. NULL).
     */
    public int height(Node cur) {
        if(cur == null) {
            return -1;
        }
        if(cur.getLeft()==null && cur.getRight()==null) {
            return 0;
        } else if(cur.getLeft()==null) {
            return 1+height(cur.getRight());
        } else if(cur.getRight()==null) {
            return 1+height(cur.getLeft());
        } else {
            return 1+maximum(height(cur.getLeft()),height(cur.getRight()));
        }
    }

    /**
     * A function that calculates the maximum between two ints.
     * @param a the first value.
     * @param b the second value.
     * @return returns the maximum.
     */
    private static int maximum(int a, int b) {
        if(a>=b) {
            return a;
        } else {
            return b;
        }
    }

}
