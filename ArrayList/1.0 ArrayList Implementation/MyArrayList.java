// MyArrayList.java
// Ziyi Huang
// 09/02/2023
// My implementation of ArrayList
// Version 0.0
// Reference: https://www.vogella.com/tutorials/JavaDatastructureList/article.html

public class MyArrayList<E> {
    // Member Variables
    private Object elementsArray[];
    private int arrayCapacity;
    private int currSize;
    private final float GROW_THRESHOLD = 0.7f;

    // Constructor
    public MyArrayList() {
        arrayCapacity = 5;
        currSize = 0;
        elementsArray = new Object[arrayCapacity];
    }

    // ------Helper Function------
    public int getSize() {
        return currSize;
    }

    public int getArrayCapacity() {
        return arrayCapacity;
    }

    // Double the array if load factor >= the threshold
    public void growArrayIfNeeded() {
        // Determine if resize needed
        float loadFactor = (float) currSize / (float) arrayCapacity;

        if (loadFactor >= GROW_THRESHOLD) {
            // Double the array capacity
            arrayCapacity = arrayCapacity * 2;

            // Make a tmp array with new capacity
            Object[] tmpArray = new Object[arrayCapacity];

            // Iterate through the elementsArray, copy the elements in elementsArray over to
            // the tmpArray
            for (int i = 0; i < currSize; i++) {
                tmpArray[i] = elementsArray[i];
            }

            // Let the elementsArray points to the tmpArray
            elementsArray = tmpArray;
        }
    }

    // Returns the index of the element if found
    public int getIndex(E element) {
        // Traverse the array
        for (int i = 0; i < getSize(); i++) {
            // Return the index of the first-found element
            if (elementsArray[i].equals(element)) {
                return i;
            }
        }

        // Otherwise, return -1 to indicate nothing is found
        return -1;
    }

    // ------Member Functions------

    // Adds an element at the end
    public void add(E element) {
        // getSize() return the index after the last element
        elementsArray[getSize()] = element;

        // Increment current size
        currSize++;

        growArrayIfNeeded();
    }

    // Adds an element to a specified index
    public void add(int index, E element) {
        // Check if index is valid
        // Valid range is [0 - currSize]
        if (!(index >= 0 && index <= getSize())) {
            System.out.println("Index Out Of Bound!");
            return;
        }

        // Make the cursor at the (last element + 1) of the array
        int cursor = getSize();

        // Move each element one rightward starting from index
        while (cursor != index) {
            // Copy the previous element
            elementsArray[cursor] = elementsArray[cursor - 1];
            // Move cursor leftward
            cursor--;
        }

        // Now index spot is available
        elementsArray[index] = element;

        // Increment the size
        currSize++;

        growArrayIfNeeded();
    }

    // Sets an element to a specified index
    public void set(int index, E element) {
        // Check if the index if valid
        // Valid range is [0 - (currSize-1)]
        if (!(index >= 0 && index <= (currSize - 1))) {
            System.out.println("Index Out Of Bound");
            return;
        }

        // Update the element at index
        elementsArray[index] = element;
    }

    // Gets the element at the specified index
    public Object get(int index) {
        // Check if the index if valid
        // Valid range is [0 - (currSize-1)]
        if (!(index >= 0 && index <= (currSize - 1))) {
            System.out.println("Index Out Of Bound");
            return null;
        }

        return elementsArray[index];
    }

    // Wether contain an element
    public Boolean contains(E element) {
        for (int i = 0; i < currSize; i++) {
            if (elementsArray[i].equals(element))
                return true;
        }

        return false;
    }

    // Removes the element
    public void remove(E element) {

        // Get the index of the element
        int elementIndex = getIndex(element);

        // Element does not exist
        if (elementIndex == -1) {
            System.out.println("Element is not found!");
            return;
        }

        // Element found, move all the elements on its right one leftward

        for (int i = elementIndex; i < getSize() - 1; i++) {
            elementsArray[i] = elementsArray[i + 1];
        }

        currSize--;

    }

    // Clears all the elements
    public void clear() {
        currSize = 0;
    }

    // Print the ArrayList
    public void printArrayList() {
        System.out.println("------My ArrayList------");
        for (int i = 0; i < currSize; i++) {
            System.out.print(String.format("[ %s ]", elementsArray[i].toString()));
        }
        System.out.println();
        System.out.println("------My ArrayList------");
        System.out.println("Current Size: " + getSize());
        System.out.println("Array Capacity: " + getArrayCapacity());

    }

}