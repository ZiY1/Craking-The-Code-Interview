// MyHashTable.java
// Ziyi Huang
// 08/27/2023
// My implementation of Hash Table with seperate chaining
// Version 0.0
// Reference: https://www.geeksforgeeks.org/implementing-our-own-hash-table-with-separate-chaining-in-java/

import java.util.ArrayList;
import java.util.Objects;

// Create a HashNode class of generic keys and values
// This HashNode class essentially operates as a linkedlist
class HashNode<K, V> {
    K key;
    V value;
    HashNode<K, V> next; // Point/Reference to next node. It is assigned default value 'null'

    public HashNode(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

// Create a HashTable class of generic keys and values
class MyHashTable<K, V> {
    // Declare an array of node, it is used to store an array of nodes/a chain
    private ArrayList<HashNode<K, V>> nodeArray;

    // Current capacity of array list
    private int arrayCapacity;

    // Current size of array list
    private int currentSize;

    // Constructor without parameters
    // Because we will initiallize it as null and use member fucntion to manipulate
    // it
    public MyHashTable() {
        nodeArray = new ArrayList<>();
        arrayCapacity = 10; // Initialize it as 10 first, it can grow and shrink based on load factor
        currentSize = 0;

        // Intialize the nodeArray as array of null nodes/chains
        for (int i = 0; i < arrayCapacity; i++) {
            nodeArray.add(null); // nodeArray[i] = null is false, read Java ArrayList
        }
    }

    // ------Member Funtions------
    // Return the value corresponding to the key if the key is present in HT (Hast
    // Table)
    public V search(K key) {
        // 1. get the index of nodeArray from the key
        int index = getIndexOfNodeArray(key);

        // 2. get the head of node at the index
        HashNode<K, V> nodeCursor = nodeArray.get(index);

        // 3. Traverse the nodes/chains until we find the key or it does not exist
        while (nodeCursor != null) {

            // if we find the key, return its value
            if (nodeCursor.key.equals(key)) {
                return nodeCursor.value;
            }

            // Otherwise, keep tarversing
            nodeCursor = nodeCursor.next;
        }

        return null;
    }

    // Add a new valid key, value pair to the HT, if already present updates the
    // value
    // Two cases:
    // 1. insert to head-spot
    // Just set the new node to array's head
    // 2. insert to end-spot
    // 2.1 make prevNode.next points to new node
    public void insert(K key, V value) {
        Boolean keyFound = false;

        // 1. get the corresponding index of the key
        int index = getIndexOfNodeArray(key);

        // 2. Tarverse the nodes/chains until we find the place to add
        HashNode<K, V> currNode = nodeArray.get(index); // we don't need prevNode because unlike delete, we don't need
                                                        // to connect the broken nodes/chains
        HashNode<K, V> prevNode = null;

        while (currNode != null) {
            // if we find the key, update its value and break
            if (currNode.key.equals(key)) {
                currNode.value = value;
                keyFound = true;
                break;
            }

            // Otherwise, keep tarversing
            prevNode = currNode;
            currNode = currNode.next;
        }

        if (!keyFound) {
            currentSize++;

            HashNode<K, V> newNode = new HashNode<K, V>(key, value);

            // Case 1:
            if (prevNode != null) { // It means the node is not empty and we have found the right plcae to insert
                prevNode.next = newNode;
            } else {
                nodeArray.set(index, newNode);
                // currNode = newNode does not work because in this way the head is detached
                // from the ArrayList
            }
        }

    }

    // Remove the key, value pair
    // Two cases:
    // 1. Delete the head-spot
    // 2. Delete the other-spot
    public V delete(K key) {
        // 1. get the index of nodeArray from the key
        int index = getIndexOfNodeArray(key);

        // 2. get the head node of the chains at that index
        HashNode<K, V> currNode = nodeArray.get(index);
        HashNode<K, V> prevNode = null;

        // 3. Traverse the chain until we find the key or it does not exist
        while (currNode != null) {
            if (currNode.key == key) {
                currentSize--;

                // Delete the key
                // 1. If the currNode is the head-spot, set the next node as the head of
                // ArrayList at the index
                if (prevNode == null) {
                    nodeArray.set(index, currNode.next);
                    // 2. If the currNode is somewhere else
                } else {
                    prevNode.next = currNode.next;
                }
                return currNode.value;
            }

            // Iterating
            prevNode = currNode;
            currNode = currNode.next;
        }

        return null;
    }

    // Get the index of the key
    // This function uses the inbuilt java function to generate
    // a hash code, and we compress the hash code by the size of the HT
    // so that the index is within the range of the size of the HT
    public int getIndexOfNodeArray(K key) {
        int hashCode = hashCode(key);
        int index = hashCode % arrayCapacity; // we modulo the arrayCapacity not the currentSize because the
                                              // arrayCapacity is the total size of the array

        index = index < 0 ? index * -1 : index; // Since java inbuilt hash code could be negative

        return index;
    }

    // Return the size of the HT
    public int getSize() {
        return currentSize;
    }

    // Return the hash code of the key
    // Difference between Objects.hashCode(key) and key.hashCode()
    // 1. Obejcts.hashCode(key) from 'java.util.Objects' handles null input, it
    // returns a constant value, typically 0.
    // 2. key.hashCode() through 'NullPointerException' is given null input
    private final int hashCode(K key) {
        return Objects.hashCode(key);
    }

    public void printMyHashTable() {

        System.out.println("------My Hash Table------");
        for (int i = 0; i < arrayCapacity; i++) {
            HashNode<K, V> nodeCursor = nodeArray.get(i);
            if (nodeCursor != null) {
                while (nodeCursor != null) {
                    System.out.print(
                            String.format("| %s | %s |--> ", nodeCursor.key.toString(), nodeCursor.value.toString()));
                    // String.format("| %s | %s |--> ", nodeCursor.key.toString(),
                    // nodeCursor.value.toString());
                    nodeCursor = nodeCursor.next;
                }
                System.out.println();
            } else {
                System.out.println("Null");
            }
        }
        System.out.println("------My Hash Table------");
    }

}