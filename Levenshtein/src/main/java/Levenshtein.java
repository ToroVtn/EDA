public class Levenshtein {
    public static int distance2(String s1, String s2){
        return recursive(s1, s2, s1.length(), s2.length());
        //recursive version has 3^n time complexity
    }

    private static int recursive(String s1, String s2, int s1len, int s2len) {
        if(s1len == 0) return s2len;
        if(s2len == 0) return s1len;

        if(s1.charAt(s1len - 1) == s2.charAt(s2len - 1)) {
            return recursive(s1, s2, s1len - 1, s2len - 1);
        }
        return 1 + Math.min(Math.min(recursive(s1, s2, s1len-1, s2len),
                recursive(s1, s2, s1len-1, s2len - 1)),
                recursive(s1, s2, s1len, s2len-1));
    }

    public static double normalized(String s1, String s2){
        int lenght = Math.max(s1.length(), s2.length());
        return 1 - (double) distance(s1, s2)/lenght;
        //return 1 - (double) recursive(s1, s2, s1.length(), s2.length())/lenght;
    }

    public static int distance(String s1, String s2){
        //this version has O(length1 * lenght2) complexity
        int[][] matrix = new int[s1.length() + 1][s2.length() + 1];
        for(int i = 0; i <= s1.length(); i++) matrix[i][0] = i;
        for(int j = 0; j <= s2.length(); j++) matrix[0][j] = j;

        int cost;
        for(int i = 1; i <= s1.length(); i++){
            for(int j = 1; j <= s2.length(); j++){
                cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                matrix[i][j] =  Math.min(   matrix[i-1][j] + 1,
                                Math.min(   matrix[i][j-1] + 1,
                                            matrix[i-1][j-1] + cost));
            }
        }
        return matrix[s1.length()][s2.length()];
    }
}
