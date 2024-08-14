import java.util.HashMap;
import java.util.Map;

public class Soundex {
    public static String representation(String in){
        String aux = in.toUpperCase();
        char[] IN = aux.toCharArray();
        char[] OUT = new char[IN.length];
        for(int i = 0; i < IN.length; i++){
            OUT[i] = 0;
        }
        OUT[0] = IN[0];
        int count = 1;
        char curr, last = getMapping(IN[0]);
        for (int i = 1; i < IN.length; i++) {
            char iter = IN[i];
            curr = getMapping(iter);
            if(curr != 0 && curr != last){
                OUT[count++] = curr;
            }
            last = curr;
        }
        char[] toReturn = new char[count];
        System.arraycopy(OUT, 0, toReturn, 0, count);
        return new String(toReturn);
    }

    public static double similarity(String in1, String in2){
        double similarity = 0;
        String s1 = representation(in1);
        String s2 = representation(in2);
        int len = Math.min(s1.length(), s2.length());
        for (int i = 0; i < len; i++){
            if(s1.charAt(i) == s2.charAt(i)){
                similarity++;
            }
        }
        return similarity/len;
    }

    private static char getMapping(char c) { //switch is not a good implementation (maybe)
        //a good implementation could be with a hashmap
        return switch (c) {
            case 'A', 'E', 'I', 'O', 'U', 'W', 'H', 'Y' -> '0';
            case 'B', 'F', 'P', 'V' -> '1';
            case 'C', 'G', 'J', 'K', 'Q', 'S', 'X', 'Z' -> '2';
            case 'D', 'T' -> '3';
            case 'L' -> '4';
            case 'M', 'N' -> '5';
            case 'R' -> '6';
            default -> 'c';
        };
    }


    //below is probably not the way
    private static char getMapping2(char c){
        return mappings.get(getCharKey(c)).charAt(0);
    }

    private static String getCharKey(char c) {
        for(String string : mapping){
            if (string.contains(Character.toString(c))) return string;
        }
        return "";
    }

    private static Map<String, String> mappings = new HashMap<>(){
        {
            put("AEIOUWHY", "0");
            put("BFPV", "1");
            put("CGJKQSXZ", "2");
            put("DT", "3");
            put("L", "4");
            put("MN", "5");
            put("R", "6");
        }
    };

    private static String[] mapping = new String[]{"AEIOUWHY", "BFPV", "CGJKQSXZ", "DT", "L", "MN", "R"};
}
