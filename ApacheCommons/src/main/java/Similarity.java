import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.FuzzyScore;
import org.apache.commons.text.similarity.LevenshteinDetailedDistance;
import org.apache.commons.text.similarity.LevenshteinResults;

import java.util.Locale;


public class Similarity {
    private LevenshteinDetailedDistance levenshtein = new LevenshteinDetailedDistance();
    private Soundex soundex = new Soundex();
    private Metaphone metaphone = new Metaphone();
    private FuzzyScore qgram = new FuzzyScore(Locale.ENGLISH);
    private LevenshteinResults result;

    public double[] similarity(String text1, String text2) {
        double[] distances = new double[3];
        result = levenshtein.apply(text1, text2);
        distances[0] = result.getDistance();
        try {
            distances[1] = soundex.difference(text1, text2);
        } catch (EncoderException e) {
            throw new RuntimeException(e);
        }
        distances[2] = qgram.fuzzyScore(text1, text2);
        return distances;
    }

    public String soundexEncode(String text1) {
        return soundex.encode(text1);
    }

    public String levenshteinOperations(String text1, String text2) {
        if(result == null) throw new IllegalStateException("must call similarity first");

        return "%d substitutions, %d deletions, %i insertions"
                .formatted( result.getSubstituteCount(),
                            result.getDeleteCount(), result.getInsertCount());
    }
}
