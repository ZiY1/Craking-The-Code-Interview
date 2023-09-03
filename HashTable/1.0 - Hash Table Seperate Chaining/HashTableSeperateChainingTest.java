// HashTable.java
// Ziyi Huang
// 08/29/2023
// Hash Table with seperate chaining Test
// Version 1.0

import java.util.Scanner;

public class HashTableSeperateChainingTest {
    public static void main(String[] args) {
        try (// Create a scanner obj to take input from user
                Scanner scan = new Scanner(System.in)) {
            // Display messages
            System.out.println("Hash Table Seperate Chaining Test\n");
            System.out.print("Enter Hash Table Size: ");

            // Create Hash Table obj
            HashTableSeperateChaining<String, String> hashTable = new HashTableSeperateChaining<>(scan.nextInt());

            char ch;
            do
            // Menu is displayed

            {
                // Menu
                // Display messages
                System.out.println("\nHash Table Operations\n");
                System.out.println("1. insert ");
                System.out.println("2. delete");
                System.out.println("3. search");
                System.out.println("4. clear");

                // Display message
                System.out.println("\nEnter operation number:");
                // Reading integer using nextInt()
                int choice = scan.nextInt();

                // Switch case
                switch (choice) {

                    // Case 1
                    case 1:

                        // Display message
                        System.out.println("Enter key and value:");
                        hashTable.insert((String) scan.next(), (String) scan.next());
                        // Break statement to terminate a case
                        break;

                    // Case 2
                    case 2:

                        // Display message
                        System.out.println("Enter key:");
                        hashTable.delete(scan.next());
                        // Break statement to terminate a case
                        break;

                    // Case 3
                    case 3:

                        // Print statements
                        System.out.println("Enter key:");
                        System.out.println("Value = "
                                + hashTable.search(scan.next()));
                        // Break statement to terminate a case
                        break;

                    // Case 4
                    case 4:

                        hashTable.makeEmpty();
                        // Print statement
                        System.out.println("Hash Table Cleared\n");
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
                hashTable.printMyHashTable();

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
