public class Levenshtein {
    public static int distance(String s1, String s2){
        return recursive(s1, s2, s1.length(), s2.length());
    }

    private static int recursive(String s1, String s2, int s1len, int s2len) {
        if(s1.charAt(s1len - 1) == s2.charAt(s2len - 1)) {
            return recursive(s1, s2, s1len - 1, s2len - 1);
        }
        return Math.min();
    }
}
