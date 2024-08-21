import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.FuzzyScore;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.Locale;


public class Similarity {
    private LevenshteinDistance levenshtein = new LevenshteinDistance();
    private Soundex soundex = new Soundex();
    private Metaphone metaphone = new Metaphone();
    private FuzzyScore qgram = new FuzzyScore(Locale.ENGLISH);

    public double[] similarity(String text1, String text2) {
        double[] distances = new double[3];
        distances[0] = levenshtein.apply(text1, text2);
        try {
            distances[1] = soundex.difference(text1, text2);
        } catch (EncoderException e) {
            throw new RuntimeException(e);
        }
        distances[2] = qgram.fuzzyScore(text1, text2);
        return distances;
    }

}
