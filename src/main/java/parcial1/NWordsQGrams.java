package parcial1;

import java.util.Arrays;
import java.util.Scanner;

public class NWordsQGrams{
    String sentence;
    int N;
    Scanner sc;

    String[] Qgrams = new String[0];

    public NWordsQGrams(String sentence, int N) {
        if (N < 0) throw new IllegalArgumentException("invalid token number");

        this.sentence = sentence;
        this.N = N;
        sc = new Scanner(sentence).useDelimiter("[:.,;\\s]+");
        resize();

        //set first N-1 Qgrams with #
        for (int i = 0; i < N-1; i++) {
            for (int j = i; j < N-1; j++) {
                if(Qgrams[i]==null) Qgrams[i] = "";
                Qgrams[i] += "# ";
            }
        }
        String[] words = new String[N];
        for (int i = 0; i < N; i++) {
            words[i] = sc.next();
        }
        for(int i = 0; i < N-1; i++) {
            for(int j = 0; j < i+1; j++) {
                Qgrams[i] += words[j] + " ";
            }
        }

        //core loop
        int i = N-1;
        while(sc.hasNext()) {
            for(String w : words) Qgrams[i] += w + " ";

            for(int j = 0; j < N-1; j++) {
                words[j] = words[j+1];
            }
            words[N-1] = sc.next();
            i++;
            if(i == Qgrams.length) resize();
        }

        //set last N-1 Qgrams with #
        for(String w : words) Qgrams[i] += w + " ";
        i++;
        if(i == Qgrams.length) resize();
        for(int k = 0; k < N-1; k++) {
            for (int j = 0; j < N - 1; j++) {
                words[j] = words[j + 1];
            }
            for(int l=0; l<k+1; l++) words[N - 1 - l] = "#";

            for(String w : words) Qgrams[i] += w + " ";
            i++;
            if(i == Qgrams.length) resize();
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        int n=1;
        for (int i = 0; i < Qgrams.length && !Qgrams[i].isEmpty(); i++){
            str.append(n++).append(": ").append(Qgrams[i]).append("\n");
        }
        return str.toString();
    }

    private void resize() {
        Qgrams = Arrays.copyOf(Qgrams, Qgrams.length+10);
        for(int i = Qgrams.length-1; i > Qgrams.length-11; i--){
            Qgrams[i] = "";
        }
    }

    public double similitud(String other){
        return 0;
    }


    public static void main(String[] args) {
        NWordsQGrams n =  new NWordsQGrams("voy a ir de compras", 3);
        System.out.println(n);
    }
}