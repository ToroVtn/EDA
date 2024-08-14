public class Levenshtein {
    public static int distance(String s1, String s2){
        return recursive(s1, s2, s1.length(), s2.length());
    }

    private static int recursive(String s1, String s2, int s1len, int s2len) {
        if(s1len == 0) return s1len;
        if(s2len == 0) return s2len;

        if(s1.charAt(s1len - 1) == s2.charAt(s2len - 1)) {
            return recursive(s1, s2, s1len - 1, s2len - 1);
        }
        return 1 + Math.min(Math.min(recursive(s1, s2, s1len-1, s2len),
                recursive(s1, s2, s1len-1, s2len - 1)),
                recursive(s1, s2, s1len, s2len-1));
    }

    public static double normalized(String s1, String s2){
        int lenght = (s1.length() > s2.length()) ? s1.length() : s2.length();

        return 1 - (double) recursive(s1, s2, s1.length(), s2.length())/lenght;
    }
}
