public class Main {
    public static void main(String[] args) {
        Similarity similarity = new Similarity();
        String text1 = "brooklin", text2 = "clean";
        System.out.println(text1 + ", " + text2);
        double[] distances = similarity.normalized(text1, text2);
        for(double distance : distances) {
            System.out.println(distance);
        }
        System.out.println(similarity.levenshteinOperations());
        System.out.println(similarity.soundexEncode(text1));
    }
}
