// MyArrayListTest.java
// Ziyi Huang
// 09/02/2023
// ArrayList Test
// Version 0.0

import java.util.Scanner;

public class MyArrayListTest {
    public static void main(String[] args) {

        try (Scanner scan = new Scanner(System.in)) {
            // Display messages
            System.out.println("ArrayList Test\n");

            MyArrayList<String> myArrayList = new MyArrayList<>();

            char ch;
            do
            // Menu is displayed

            {
                // Menu
                // Display messages
                System.out.println("\nHash Table Operations\n");
                System.out.println("1. insert ");
                System.out.println("2. insert at index");
                System.out.println("3. set at index");
                System.out.println("4. search");
                System.out.println("5. contains");
                System.out.println("6. remove");
                System.out.println("7. clear");

                // Display message
                System.out.println("\nEnter operation number:");
                // Reading integer using nextInt()
                int choice = scan.nextInt();

                // Switch case
                switch (choice) {

                    // Case 1
                    case 1:

                        // Display message
                        System.out.println("Enter element:");
                        myArrayList.add((String) scan.next());
                        // Break statement to terminate a case
                        break;

                    // Case 2
                    case 2:

                        // Display message
                        System.out.println("Enter index and element:");
                        myArrayList.add(scan.nextInt(), scan.next());
                        // Break statement to terminate a case
                        break;

                    // Case 3
                    case 3:

                        // Display message
                        System.out.println("Enter index and element:");
                        myArrayList.set(scan.nextInt(), scan.next());
                        // Break statement to terminate a case
                        break;

                    // Case 4
                    case 4:

                        // Display message
                        System.out.println("Enter index:");
                        System.out.println("Element = " + myArrayList.get(scan.nextInt()));
                        // Break statement to terminate a case
                        break;

                    // Case 5
                    case 5:

                        // Display message
                        System.out.println("Enter element:");
                        System.out.println(myArrayList.contains(scan.next()));
                        // Break statement to terminate a case
                        break;

                    // Case 6
                    case 6:

                        // Display message
                        System.out.println("Enter element:");
                        myArrayList.remove(scan.next());
                        // Break statement to terminate a case
                        break;

                    // Case 7
                    case 7:

                        myArrayList.clear();
                        // Break statement to terminate a case
                        break;

                    // Default case
                    // Executed when mentioned switch cases are not
                    // matched
                    default:
                        // Print statement
                        System.out.println("Wrong Entry \n ");
                        // Break statement
                        break;
                }

                // Display hash table
                myArrayList.printArrayList();

                // Display message asking the user whether
                // he/she wants to continue
                System.out.println(
                        "\nDo you want to continue (Type y or n) \n");

                // Reading character using charAt() method to
                // fetch
                ch = scan.next().charAt(0);
            } while (ch == 'Y' || ch == 'y');
        }
    }

}
