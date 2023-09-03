// Main.java
// Ziyi Huang
// 09/02/2023
// Useage of Hash Map

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        
        /* Add Items */
        
        // Create a HashMao obj called capitalCities
        HashMap<String, String> capitalCities = new HashMap<>();

        // Add keys and values (Country, City)
        capitalCities.put("England", "London");
        capitalCities.put("China", "Beijing");
        capitalCities.put("USA", "Washinton DC");
        capitalCities.put("Norway", "Oslo");
        System.out.println(capitalCities);

        /* Access an Item */
        String valueAccess = capitalCities.get("China");
        System.out.println(valueAccess);

        /* Remove an Item */
        String valueRemove = capitalCities.remove("Norway");
        System.out.println(valueRemove);
        System.out.println(capitalCities);

        /* Hash Map Size */
        System.out.println(capitalCities.size());

        /* Loop Through a HashMap */
        // Print keys
        for (String i : capitalCities.keySet()) {
            System.out.println(i);
        }

        // Print values
        for (String i : capitalCities.values()) {
            System.out.println(i);
        }

        // Print keys and values
        for (String i : capitalCities.keySet()) {
            System.out.println("key: " + i + " value: " + capitalCities.get(i));
        }

        /* Clear HashMap */
        capitalCities.clear();
        System.out.println(capitalCities);

    }
}