// Main.java
// Ziyi Huang
// 09/02/2023
// Usage of StringBuilder
// Version 0.0

public class Main {
    public static String strConcatenation(String[] words) {
        String sentence = "";
        for (String w : words) {
            // Notice:
            // String concatenation using the + operator within a loop 
            // should be avoided. Since the String object is immutable, 
            // each call for concatenation will result in a new String object being created.
            sentence = sentence + w;
        }
        return sentence;
    }

    public static String strBuilder(String[] words) {
        StringBuilder sentence = new StringBuilder();
        for (String w : words) {
            //StringBuilder simply creates a resizable array of all the strings, copying them back to a 
            // string only when necessary.
            sentence = sentence.append(w);
        }
        return sentence.toString();
    }

    public static void main(String[] args) {
        String[] strWords = {"My name is ", "Ziyi Huang. ", "I am a full stack ", "software developer. "};
        String sentenceConcate = strConcatenation(strWords);
        System.out.println(sentenceConcate);
        String sentenceBuilder = strBuilder(strWords);
        System.out.println(sentenceBuilder);
    }
}
