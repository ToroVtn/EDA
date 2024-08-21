import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.FuzzyScore;
import org.apache.commons.text.similarity.LevenshteinDetailedDistance;
import org.apache.commons.text.similarity.LevenshteinResults;

import java.util.Locale;


public class Similarity {
    private final LevenshteinDetailedDistance levenshtein = new LevenshteinDetailedDistance();
    private final Soundex soundex = new Soundex();
    private final FuzzyScore qgram = new FuzzyScore(Locale.ENGLISH);
    private final Metaphone metaphone = new Metaphone();
    private LevenshteinResults result;

    public double[] normalized(String text1, String text2) {
        int length = Math.max(text1.length(), text2.length());
        double[] distances = new double[4];
        result = levenshtein.apply(text1, text2);
        distances[0] = (double) result.getDistance() /length;
        try {
            distances[1] =1 - (double) soundex.difference(text1, text2)/length;
        } catch (EncoderException e) {
            throw new RuntimeException(e);
        }
        distances[2] = 1 - (double) qgram.fuzzyScore(text1, text2)/length;
        distances[3] = 1 - (double) metaphoneDistance(text1, text2)/length;

    return distances;
    }

    private int metaphoneDistance(String text1, String text2) {
        int dist=0;
        char[] metap1 = metaphone.encode(text1).toCharArray();
        char[] metap2 = metaphone.encode(text2).toCharArray();
        for(int i = 0; i < metap1.length && i < metap2.length; i++){
            if(metap1[i] == metap2[i]){
                dist++;
            }
        }
        return dist;
    }

    public String soundexEncode(String text1) {
        return soundex.encode(text1);
    }

    public String levenshteinOperations() {
        if(result == null) throw new IllegalStateException("must call similarity first");

        return "%d substitutions, %d deletions, %d insertions"
                .formatted( result.getSubstituteCount(),
                            result.getDeleteCount(), result.getInsertCount());
    }
}
