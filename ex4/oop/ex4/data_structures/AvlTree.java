package oop.ex4.data_structures;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Rani on 5/3/2016.
 * A class that represent an avl tree with its operation - add, delete contains etc..
 *
 */
public class AvlTree implements Iterable<Integer> {
    /**
     * The root of the tree.
     */
    private Node root;
    static final int NO_SONS = 0;
    static final int ONE_SON = 1;
    static final int TWO_SONS = 2;
    static final int LEFT_UNBALANCE = -2;
    static final int RIGHT_UNBALANCE = 2;
    static final int NOT_CONTAINED = -1;
    static final int FIVE = 5;
    static final int TWO = 2;
    private int size;

    /**
     * The default constructor.
     */
    public AvlTree(){
       this.root = null;
    }

    /**
     * A constructor that builds a new AVL tree containing all unique values in the input array.
     * @param data the values to add to tree.
     */
    public AvlTree(int[] data){
        //to add the code that inserts a sorted array to the tree.
        for (int i = 0; i < data.length; i++){
            add(data[i]);
        }
    }

    /**
     * A copy constructor that creates a deep copy of the given AvlTree. The new tree contains all the values of the given tree, but not necessarily in the same structure.
     * @param tree The AVL tree to be copied.
     */
    public AvlTree(AvlTree tree){
        if(tree == null || tree.root == null){
            this.root = null;
        }
        Iterator<Integer> copyTreeIter = tree.iterator();
        while(copyTreeIter.hasNext()){
            add(copyTreeIter.next());
        }
    }

    /**
     * Add a new node with the given key to the tree.
     * @param newValue the value of the new node to add.
     * @return true if the value to add is not already in the tree and it was successfully added, false otherwise.
     */
    public boolean add(int newValue){
        if(contains(newValue) > AvlTree.NOT_CONTAINED){
            return false;
        }
        if(this.root == null){
            this.root = new Node(newValue, null);
            this.size++;
            return true;
        }
        Node tempNode = this.root;
        while(tempNode != null){
            if(tempNode.getKey() > newValue){
                if(tempNode.getLeft() == null){
                    tempNode.setLeft(new Node(newValue, tempNode));
                    break;
                }
                tempNode = tempNode.getLeft();

            }
            else if (tempNode.getKey() < newValue){
                if(tempNode.getRight() == null){
                    tempNode.setRight(new Node(newValue, tempNode));
                    break;
                }
                tempNode = tempNode.getRight();
            }
        }
        this.size++;
        makeBalancedTree(tempNode);
        return true;
    }


    /**
     * Check whether the tree contains the given input value.
     * @param searchVal value to search for.
     * @return if val is found in the tree, return the depth of the node (0 for the root) with the given value if
     * it was found in the tree, -1 otherwise.
     */
    public int contains(int searchVal){
        if (this.root == null){
            return AvlTree.NOT_CONTAINED;
        }
        Node tempNode = this.root;
        int depth = 0;
        while(tempNode != null){
            if(tempNode.getKey() == searchVal) {
                return depth;
            }
            else if(tempNode.getKey() > searchVal){
                tempNode = tempNode.getLeft();
            }
            else if (tempNode.getKey() < searchVal){
                tempNode = tempNode.getRight();
            }
            depth += 1;
        }
        return AvlTree.NOT_CONTAINED;

    }

    /**
     * Removes the node with the given value from the tree, if it exists.
     * @param toDelete the value to remove from the tree.
     * @return true if the given value was found and deleted, false otherwise.
     */
    public boolean delete(int toDelete){
        if(contains(toDelete) < 0){
            return false;
        }
        Node tempNode = this.root;
        Node nodeToDelete = null;
        while(tempNode != null){
            if(tempNode.getKey() == toDelete) {
                nodeToDelete = tempNode;
                break;
            }
            else if(tempNode.getKey() > toDelete){
                tempNode = tempNode.getLeft();
            }
            else if (tempNode.getKey() < toDelete){
                tempNode = tempNode.getRight();
            }
        }
        Node parent = nodeToDelete.getParent();
        //in case we want to delete the root of the tree.
        if(parent == null){
            deleteRoot(nodeToDelete);
            this.size--;
            return true;
        }
        deleteNode(nodeToDelete, numberOfSons(nodeToDelete));
        this.size--;
        return true;

    }

    /**
     * A function for deleting the root of the tree.
     * @param treeRoot the root of the tree.
     */
    private void deleteRoot(Node treeRoot){
        if(treeRoot.getLeft() == null && treeRoot.getRight() == null){
            this.root = null;
        }
        else if(treeRoot.getRight() == null || treeRoot.getLeft() == null){
            Node son;
            if(treeRoot.getLeft() != null){
                son = treeRoot.getLeft();
            }
            else{
                son = treeRoot.getRight();
            }
            this.root = son;
            this.root.setParent(null);

        }
        else{
            Node successor = findSuccessor(treeRoot);
            switchNodesValue(treeRoot, successor);
            deleteNode(successor, numberOfSons(successor));

        }
    }

    /**
     * Calculates the number of nodes in the tree and return it.
     * @return the number of nodes in the tree.
     */
    public int size(){
        return this.size;
    }


    /**
     * Calculates the minimum number of nodes in an AVL tree of height h.
     * @param h the height of the tree (a non-negative number) in question.
     * @return the minimum number of nodes in an AVL tree of the given height.
     */
    public static int findMinNodes(int h){
        return (int) (Math.round(((Math.sqrt(AvlTree.FIVE) + AvlTree.TWO) / Math.sqrt(AvlTree.FIVE)) * Math.pow((1 + Math.sqrt(AvlTree.FIVE)) / AvlTree.TWO, h) - 1));
    }

    /**
     * A function that gets a root of a tree and a linked list and filled it with the keys of the tree in ascending
     * order.
     * @param temp the root of the tree.
     * @param linkedList the linked list that stores the keys of the tree.
     */
    private void incertInAscending(Node temp, LinkedList<Integer> linkedList){
        if(temp == null){
            return;
        }
        incertInAscending(temp.getLeft(), linkedList);
        linkedList.add(temp.getKey());
        incertInAscending(temp.getRight(), linkedList);

    }

    /**
     * A method that returns an iterator of the tree.
     * @return an iterator for the Avl Tree. The returned iterator iterates over the tree nodes in an ascending order,
     * and does NOT implement the remove() method.
     */
    public java.util.Iterator<Integer> iterator(){
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        incertInAscending(this.root, linkedList);
        return new TreeIterator(linkedList);
    }

    /**
     * This function delete a node of the tree based on its number of sons.
     * @param node the node to delete.
     * @param numberOfSons the number of sons that the node we want to delete have.
     */
    private void deleteNode(Node node, int numberOfSons){
        Node parentOfDeleted = node.getParent();
        switch (numberOfSons){
            case 0:
                deleteWithoutSons(node);
                break;
            case 1:
                deleteWithOneSon(node);
                break;
            case 2:
                parentOfDeleted = findSuccessor(node).getParent();
                deleteWithTwoSons(node);
                break;
        }
        makeBalancedTree(parentOfDeleted);
    }

    /**
     * This method deletes a node that doesn't have sons.
     * @param nodeToDelete the node to delete.
     */
    private void deleteWithoutSons(Node nodeToDelete){
        Node parent = nodeToDelete.getParent();
        if(parent.getRight() == nodeToDelete ){
            parent.setRight(null);
        }
        else{
            parent.setLeft(null);
        }
    }

    /**
     * This method deletes a node that have one son.
     * @param nodeToDelete the node to delete.
     */
    private void deleteWithOneSon(Node nodeToDelete){
        Node parent = nodeToDelete.getParent();
        Node leftSon = nodeToDelete.getLeft();
        Node theSon;
        if(leftSon != null){
            theSon = nodeToDelete.getLeft();
        }
        else {
            theSon = nodeToDelete.getRight();
        }

        if (nodeToDelete == parent.getRight()) {
            parent.setRight(theSon);
        }
        else {
            parent.setLeft(theSon);
        }
        theSon.setParent(parent);
    }

    /**
     * This method deletes a node that have two sons.
     * @param node the node to delete.
     */
    private void deleteWithTwoSons(Node node){
        Node Successor = findSuccessor(node);
        switchNodesValue(node, Successor);
        deleteNode(Successor, numberOfSons(Successor));
    }


    /**
     * Finds the successor of the given node.
     * @param node the node that we desire to find its successor.
     * @return the successor of the node.
     */
    private Node findSuccessor(Node node){
        if(node == null){
            return null;
        }
        if(node.getRight() != null){
            return treeMin(node.getRight());
        }
        else{
            Node parent = node.getParent();
            while(parent != null && parent.getRight() == node){
                node = parent;
                parent = parent.getParent();
            }
            return parent;
        }
    }

    /**
     * This method switches the keys of the given two nodes.
     * @param first the first node that we want to switch its key value.
     * @param second the second node that we want to switch its key value.
     */
    private void switchNodesValue(Node first, Node second){
        int keyOfFirst = first.getKey();
        first.setKey(second.getKey());
        second.setKey(keyOfFirst);
    }

    /**
     * A method that calculates the number of sons of the given node.
     * @param node the node that we want to count its sons number.
     * @return return the number of sons of the given node.
     */
    private int numberOfSons(Node node){
        if(node.getRight() == null && node.getLeft() == null){
            return NO_SONS;
        }
        else if(node.getRight() == null || node.getLeft() == null){
            return ONE_SON;
        }
        else {
            return TWO_SONS;
        }
    }

    /**
     * A method that finds the node that have the smallest key in the tree.
     * @param root the root of the tree.
     * @return the minimal node in the tree.
     */
    private Node treeMin(Node root){
        if(root == null ){
            return null;
        }
        Node temp = root;
        while (temp.getLeft() != null){
            temp = temp.getLeft();
        }
        return temp;
    }


    /**
     * Left rotation using the given node.
     * @param cur The node for the rotation
     * @return The root of the new rotated tree.
     */
    private Node rotateToLeft(Node cur) {

        Node root = cur.getRight();
        root.setParent(cur.getParent());

        cur.setRight(root.getLeft());

        if(cur.getRight() != null) {
            cur.getRight().setParent(cur);
        }

        root.setLeft(cur);
        cur.setParent(root);

        if(root.getParent() != null) {
            if(root.getParent().getRight() == cur) {
                root.getParent().setRight(root);
            } else if(root.getParent().getLeft() == cur) {
                root.getParent().setLeft(root);
            }
        }

        this.root.setBalance(cur);
        this.root.setBalance(root);

        return root;
    }

    /**
     * Right rotation using the given node.
     * @param cur The node for the rotation
     * @return The root of the new rotated tree.
     */
    private Node rotateToRight(Node cur) {
        Node root = cur.getLeft();
        root.setParent(cur.getParent());

        cur.setLeft(root.getRight());

        if(cur.getLeft() != null) {
            cur.getLeft().setParent(cur);
        }

        root.setRight(cur);
        cur.setParent(root);

        if(root.getParent() != null) {
            if(root.getParent().getLeft() == cur) {
                root.getParent().setLeft(root);
            } else if(root.getParent().getRight() == cur) {
                root.getParent().setRight(root);
            }
        }

        this.root.setBalance(cur);
        this.root.setBalance(root);

        return root;
    }

    /**
     * makes a LR rotation.
     * @param cur The node for the rotation.
     * @return The root after the double rotation.
     */
    private Node leftRightRotate(Node cur) {
        cur.setLeft(rotateToLeft(cur.getLeft()));
        return rotateToRight(cur);
}

    /**
     * makes a RL rotation.
     * @param cur The node for the rotation.
     * @return The root after the double rotation.
     */
    private Node rightLeftRotate(Node cur) {
        cur.setRight(rotateToRight(cur.getRight()));
        return rotateToLeft(cur);
    }


    /**
     * This method makes the tree balanced after that a disorder have been made to the tree.
     * @param cur the node that we want to make rotation at.
     */
    private void makeBalancedTree(Node cur) {
        this.root.setBalance(cur);
        int balance = cur.getBalance();

        if(balance == AvlTree.LEFT_UNBALANCE) {
            if(this.root.height(cur.getLeft().getLeft()) >= this.root.height(cur.getLeft().getRight())) {
                cur = rotateToRight(cur);
            } else {
                cur = leftRightRotate(cur);
            }
        }
        else if(balance == AvlTree.RIGHT_UNBALANCE) {
            if(this.root.height(cur.getRight().getRight())>=this.root.height(cur.getRight().getLeft())) {
                cur = rotateToLeft(cur);
            } else {
                cur = rightLeftRotate(cur);
            }
        }

        if(cur.getParent() != null) {
            makeBalancedTree(cur.getParent());
        } else {
            this.root = cur;
        }
    }



}


