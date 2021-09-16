package esoterum.util;

public class EsoUtils{
    public static String pluralValue(int value, String unit){
        return value + " " + unit + (value != 1 ? "s" : "");
    }

    public static String extractNumber(String s){
        //God, I love google. I have no idea what the "[^\\d.]" part even is.
        return s.replaceAll("[^\\d.]", "");
    }
}
