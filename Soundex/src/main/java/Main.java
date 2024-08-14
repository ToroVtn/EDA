
public class Main {
    public static void main(String[] args) {
        //SOUNDEX
        String[] strings = new String[]{"brooklin", "bruqleen", "brooclean", "bluclean", "clean"};

        for (String string : strings) {
            System.out.println(string + "-> " + Soundex.representation(string));
        }
        System.out.println(Soundex.similarity("brooklin", "bruqleen"));
    }
}
