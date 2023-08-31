// HashTable.java
// Ziyi Huang
// 08/29/2023
// My implementation of Hash Table with seperate chaining
// Version 1.0
// Reference: https://www.geeksforgeeks.org/implementing-our-own-hash-table-with-separate-chaining-in-java/

import java.util.ArrayList;
import java.util.Objects;

// HashNode class
// First we need to create a node structure since MyHashTable implementation uses ArrayList of nodes as the data strcture.
// This HashNode take a key-value pair in generic form
// This HashNode class essentially operates as a linkedlist
class HashNode<K, V> {
    // Member Varibles
    K key;
    V value;
    HashNode<K, V> next; // points to/references the next node
                         // it is assigned null by defualt

    // Constructor
    // With parameters since we will create the obj as HashTable<K, V> newNode = new
    // HashNode<K, V> (key, value);
    public HashNode(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

// MyHashTable
class HashTableSeperateChaining<K, V> {
    // Member Varibales

    // We uses ArrayList of nodes as our data sctructure
    private ArrayList<HashNode<K, V>> nodesArray;

    // We uses a fixed number of array capacity first, it can grow or shrink later
    private int arrayCapacity;

    // We keeps track of the current filled size of the array
    private int currSize;

    // Load Threshold Grow
    // If the load factor >= 0.7, we double the array capacity
    private final float LOAD_THRESHOLD_G = 0.7f;

    // Load Threshold Shrink
    // If the load factor <= 0.3, we half the array capacity
    private final float LOAD_THRESHOLD_S = 0.3f;

    // Construtor
    // Without parameter since we will manipulate the MyHashTable obj using member
    // fucntions
    public HashTableSeperateChaining(int arrayCapacity) {
        nodesArray = new ArrayList<>();
        this.arrayCapacity = arrayCapacity;
        currSize = 0;

        // Initialize the nodesArray of an array of 10 nulls
        for (int i = 0; i < arrayCapacity; i++) {
            nodesArray.add(null); // .set() cannot be used because at the point of initialize, the nodesArray is
                                  // empty
        }
    }

    // ----------Helper Fucntions----------

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
    public int getIndexOfKey(K key) {
        int index = hashCode(key) % arrayCapacity; // we modulo the arrayCapacity not the currentSize because the
                                                   // arrayCapacity is the total size of the array
        index = index < 0 ? index * -1 : index; // Since java inbuilt hash code could be negative
        return index;
    }

    // Returns if the Hash Table is empty
    public Boolean isEmpty() {
        return currSize == 0;
    }

    // ----------Member Fucntions----------

    // Insert
    // Adds a new valid key, value pair to the HT, if already present updates the
    // value
    public void insert(K key, V value) {
        // 1. Get the corresponding index of the key
        int index = getIndexOfKey(key);
        // 2. Get the head of the node
        HashNode<K, V> currNode = nodesArray.get(index);
        // 3. Insert
        HashNode<K, V> newNode = new HashNode<K, V>(key, value);

        currSize++;
        // Two Cases:
        // A: currNode is null, means the slot is empty, set the head to the slot
        if (currNode == null) {
            nodesArray.set(index, newNode);
            // currNode = newNode does not work because in this way the head is detached
            // from the ArrayList
        } else {
            // B: currNode is not null, traverse the nodes
            // B.1: same key is found, update the value
            // B.2: no same key found, traverse to the end, insert at the end
            HashNode<K, V> prevNode = null;

            while (currNode != null) {
                // !!! BECAREFUL!!!
                // In Java
                // == compares data for primitive type
                // == compares address for objects
                // .equals() compares the address for objects
                // In String, .equals() compares char by char if address equality check fails
                // https://www.scaler.com/topics/difference-between-equals-method-in-java/
                if (currNode.key.equals(key)) {
                    currNode.value = value;
                    return;
                }
                // Iterate
                prevNode = currNode;
                currNode = currNode.next;
            }

            // We taverse to the end and prevNode points to the last node
            prevNode.next = newNode;
        }

        // 4. Hash Table Grow
        // If the load factor >= 0.7, we double the array capacity
        float loadFactor = (float) currSize / (float) arrayCapacity;

        if (loadFactor >= LOAD_THRESHOLD_G) {
            // Double the array capacity
            arrayCapacity = arrayCapacity * 2;

            // Copy the old nodesArray
            // !!!REMEMBER!!!
            // In Java, objects are reference types.
            // When you assign an object to another variable, you are actually copying
            // the reference (memory address) to the object, not the object itself.
            // ArrayList<HashNode<K, V>> tmpNodesArray = nodesArray will make two obejct
            // points to the same old array
            ArrayList<HashNode<K, V>> tmpNodesArray = new ArrayList<>(nodesArray);

            // Double the nodesArray
            nodesArray.clear();
            currSize = 0;
            for (int i = 0; i < arrayCapacity; i++) {
                nodesArray.add(null);
            }

            // Insert the element from tmp to nodesArray
            for (int i = 0; i < tmpNodesArray.size(); i++) {
                HashNode<K, V> nodeCurosr = tmpNodesArray.get(i);
                if (nodeCurosr != null) {
                    while (nodeCurosr != null) {
                        insert(nodeCurosr.key, nodeCurosr.value);
                        nodeCurosr = nodeCurosr.next;
                    }
                }
            }

        }
    }

    // Delete
    // Delete the key, value pair
    public V delete(K key) {
        // 1. Get the corresponding index of the key
        int index = getIndexOfKey(key);
        // 2. Get the head of the node
        HashNode<K, V> currNode = nodesArray.get(index);
        // 3. Delete
        if (currNode == null)
            return null; // nothing to delete

        // Traverse the nodes until the key to delete is found or we reaches to the end
        HashNode<K, V> prevNode = null;

        while (currNode != null) {
            // Two Cases if key to delete is found:

            if (currNode.key.equals(key)) {
                currSize--;
                // A: delete the head
                if (prevNode == null) { // means we did not iterate at all
                    nodesArray.set(index, currNode.next);
                } else {
                    // B: delete somewhere else
                    prevNode.next = currNode.next;
                }

                // 4. Hash Table Shrink
                // If the load factor <= 0.3, we half the hash table
                float loadFactor = (float) currSize / (float) arrayCapacity;
                if (loadFactor <= LOAD_THRESHOLD_S) {
                    // Half the array capacity
                    double tmpCapacity = Math.ceil(arrayCapacity * 0.5);
                    arrayCapacity = (int) tmpCapacity;

                    // Make a copy the current nodesArray
                    ArrayList<HashNode<K, V>> tmpNodesArray = new ArrayList<>(nodesArray);

                    // Half the nodesArray
                    nodesArray.clear();
                    currSize = 0;
                    for (int i = 0; i < arrayCapacity; i++) {
                        nodesArray.add(null);
                    }

                    // Copy the tmpNodesArray elements to nodesArray
                    for (int i = 0; i < tmpNodesArray.size(); i++) {
                        HashNode<K, V> nodeCursor = tmpNodesArray.get(i);
                        if (nodeCursor != null) {
                            while (nodeCursor != null) {
                                insert(nodeCursor.key, nodeCursor.value);
                                nodeCursor = nodeCursor.next;
                            }
                        }
                    }

                }

                return currNode.value;
            }

            // Iterates
            prevNode = currNode;
            currNode = currNode.next;
        }

        return null;
    }

    // Search
    // Returns the value corresponding to the key if the key is present in HT (Hast
    // Table)
    public V search(K key) {
        // 1. Get the corresponding index of the key
        int index = getIndexOfKey(key);
        // 2. Get the head of the node
        HashNode<K, V> nodeCursor = nodesArray.get(index);
        // 3. Search

        if (nodeCursor == null)
            return null; // Slot is empty

        // Traverse the nodes until the key is found or we reaches to the end
        while (nodeCursor != null) {
            if (nodeCursor.key.equals(key)) {
                return nodeCursor.value;
            }

            // Iterates
            nodeCursor = nodeCursor.next;
        }

        // We reaches the end
        return null;
    }

    // Makes Table Empty
    public void makeEmpty() {
        currSize = 0;
        int cursor = 0;
        HashNode<K, V> curNode;
        while (!isEmpty()) {
            curNode = nodesArray.get(cursor);
            cursor++;
            if (curNode == null)
                continue;
            delete(curNode.key);
        }
    }

    // Returns the size of the HT
    public int getSize() {
        return currSize;
    }

    // Returns the array capacity
    public int getArrayCapacity() {
        return arrayCapacity;
    }

    // Print Table
    public void printMyHashTable() {

        System.out.println("------My Hash Table------");
        for (int i = 0; i < arrayCapacity; i++) {
            HashNode<K, V> nodeCursor = nodesArray.get(i);
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
