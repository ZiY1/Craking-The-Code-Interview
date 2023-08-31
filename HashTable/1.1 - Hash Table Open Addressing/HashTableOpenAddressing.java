// HashTable.java
// Ziyi Huang
// 08/29/2023
// My implementation of Hash Table with linear probing
// Version 1.0

import java.util.ArrayList;
import java.util.Objects;

// HashNode class
class HashNode<K, V> {
    // Member Varibles
    K key;
    V value;
    // The probing sequence continues as if the slot were "deleted"
    Boolean isDeleted;

    // Constructor
    public HashNode(K key, V value) {
        this.key = key;
        this.value = value;
        isDeleted = false;
    }
}

// MyHashTable
class HashTableOpenAddressing<K, V> {
    public static final int DELETED = -1;

    // Member Varibales

    // We uses ArrayList as our data sctructure
    private ArrayList<HashNode<K, V>> nodeArray;

    // Size of the table >= Number of keys
    private int arrayCapacity;

    // We keeps track of the current filled size of the array
    private int currSize;

    // Construtor
    public HashTableOpenAddressing(int arrayCapacity) {
        nodeArray = new ArrayList<>();
        this.arrayCapacity = arrayCapacity;
        currSize = 0;

        // Initialize the nodesArray of an array of 10 nulls
        for (int i = 0; i < arrayCapacity; i++) {
            nodeArray.add(null);
        }
    }

    // Helper Fucntions

    // Return the hash code of the key
    // Difference between Objects.hashCode(key) and key.hashCode()
    // 1. Obejcts.hashCode(key) from 'java.util.Objects' handles null input, it
    // returns a constant value, typically 0.
    // 2. key.hashCode() through 'NullPointerException' is given null input
    public int hashCode(K key) {
        return Objects.hashCode(key);
    }

    // Returns the index of the key
    // This function uses the inbuilt java function to generate
    // a hash code, and we compress the hash code by the size of the HT
    // so that the index is within the range of the size of the HT
    public int getIndexOfKey(K key, int i) {
        int index = (hashCode(key) + i) % arrayCapacity; // we modulo the arrayCapacity not the currentSize because the
                                                         // arrayCapacity is the total size of the array
        index = index < 0 ? index * -1 : index; // Since java inbuilt hash code could be negative
        return index;
    }

    // Returns true if the HT is full; false otherwise
    public Boolean isFull() {
        return currSize == arrayCapacity;
    }

    // Returns true if the HT is empty; false otherwise
    public Boolean isEmpty() {
        return getSize() == 0;
    }

    // Member Fucntions

    // Insert
    // Adds a new valid key, value pair to the HT, if already present updates the
    // value
    public void insert(K key, V value) {
        int index;
        HashNode<K, V> currNode;
        HashNode<K, V> newNode = new HashNode<K, V>(key, value);

        // Start linear probing until we find an empty slotß
        // Explore all possibilities: 0...arrayCapacity
        for (int i = 0; i < arrayCapacity; i++) {
            // Prob to the next possible index
            index = getIndexOfKey(key, i);

            // Get the node at the new index
            currNode = nodeArray.get(index);

            // If currNode is not empty, check if the key is the same
            if (currNode != null && currNode.key.equals(key)) {
                // Updte the value
                currNode.value = value;
                return;
            }

            // Check if the currNode is empty OR "deleted"
            if (currNode == null || currNode.isDeleted) {
                if (isFull()) {
                    System.out.println("Hash Table is at full capacity!");
                    return;
                }
                currSize++;
                nodeArray.set(index, newNode);
                return;
            }

        }
    }

    // Delete
    // Remove the key, value pair.
    // Mark the slot "deleted" because we need this signal to continue probing
    public V delete(K key) {
        if (isEmpty()) {
            System.out.println("Hash Table is empty, nothing to delete!");
            return null;
        }
        int index;
        HashNode<K, V> currNode;

        // Linear probing
        for (int i = 0; i < arrayCapacity; i++) {
            // Get the current index of the key
            index = getIndexOfKey(key, i);
            // Get the current node
            currNode = nodeArray.get(index);
            // Check if the key of current node is the target key
            if (currNode.key.equals(key)) {
                currSize--;
                currNode.isDeleted = true;
                return currNode.value;
            }
        }
        return null;
    }

    // Search
    // Returns the value corresponding to the key if the key is present in HT (Hast
    // Table)
    public V search(K key) {
        int index;
        HashNode<K, V> nodeCursor;

        for (int i = 0; i < arrayCapacity; i++) {
            // Get the index of the key
            index = getIndexOfKey(key, i);

            // Get the current node
            nodeCursor = nodeArray.get(index);

            // If the node is empty OR "deleted", continue
            if (nodeCursor == null || nodeCursor.isDeleted)
                continue;

            if (nodeCursor.key.equals(key)) {
                return nodeCursor.value;
            }
        }
        return null;
    }

    // Returns the size of the HT
    public int getSize() {
        return currSize;
    }

    // Returns the array capacity
    public int getArrayCapacity() {
        return arrayCapacity;
    }

    // Testing
    public void printMyHashTable() {

        System.out.println("------My Hash Table------");
        for (int i = 0; i < arrayCapacity; i++) {
            HashNode<K, V> currNode = nodeArray.get(i);
            if (currNode == null) {
                System.out.println("Null");
            } else if (currNode.isDeleted) {
                System.out.println("Deleted");
            } else {
                System.out.print(String.format("| %s | %s |", currNode.key.toString(), currNode.value.toString()));
                System.out.println();
            }
        }
        System.out.println("------My Hash Table------");
        System.out.println(String.format("Hash Table Current Size: %d", getSize()));
        System.out.println(String.format("Hash Table Capacity: %d", getArrayCapacity()));
    }

    public void makeEmpty() {
        currSize = 0;
        for (int i = 0; i < getArrayCapacity(); i++) {
            nodeArray.set(i, null);
        }
    }
}
