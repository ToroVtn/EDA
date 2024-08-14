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

    private static char getMapping(char c) { //switch is not a good implementation
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
}
