// Main.java
// Ziyi Huang
// 09/02/2023
// Usage of ArrayList
// Version 0.0

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        /* Add Items */
        ArrayList<String> country = new ArrayList<>();
        country.add("England");
        country.add("China");
        country.add("USA");
        country.add("German");
        country.add(4, "New USA"); // Valid index range [0, country.size()]
        System.out.println(country);

        /* Access an Item */
        System.out.println(country.get(1));

        /* Change an Item */
        country.set(4, "Japan"); // Valid index range [0, country.size()-1]
        System.out.println(country);

        /* Remove an Item */
        country.remove("USA");
        System.out.println(country);

        /* Hash Map Size */
        System.out.println(country.size());

        /* Loop Through a HashMap */
        for (int i = 0; i < country.size(); i++) {
            System.out.println(country.get(i));
        }

        /* Clear HashMap */
        country.clear();
        System.out.println(country);
    }
}
