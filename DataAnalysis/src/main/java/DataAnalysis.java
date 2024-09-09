import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class DataAnalysis {
    public static void main(String[] args) throws IOException {

        // leemos el archivo

        // opcion 5
        String fileName= "co_1980_alabama.csv";
        InputStream is = DataAnalysis.class.getClassLoader().getResourceAsStream(fileName);
        Reader in = new InputStreamReader(is);


        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(in);

        HashMap<Long, CSVRecord> datos = new HashMap<>();

        // indice sobre polucion o los que deseemos
        IndexService<IdxRecord<Double, Long>> indexPolucion = new IndexGeneric<>(IdxRecord.class);

        // insertamos en la colección y en indice
        for (CSVRecord record : records) {
            long rowid = record.getRecordNumber();
            datos.putIfAbsent(rowid, record);
            String value = record.get("daily_max_8_hour_co_concentration");
            indexPolucion.insert(new IdxRecord<>(Double.valueOf(value), rowid));
        }

        in.close();

        System.out.println(indexPolucion.getMin().getKey());
        System.out.println(datos.get(indexPolucion.getMin().getRow()));
        indexPolucion.sortedPrint();
    }
}

//
//       	// opcion 1:  probar con  / o sin barra
//	    URL resource = DataAnalysis.class.getClassLoader().getResource("co_1980_alabama.csv");
//	    Reader in = new FileReader(resource.getFile());
//
//
//
//
//    	// opcion 2:  probar con  / o sin barra
//        URL resource= DataAnalysis.class.getResource("/co_1980_alabama.csv");
//   	    Reader in = new FileReader(resource.getFile());
//
//
//
//    	// opcion 3: probar con / o sin barra
//    	String fileName= "/co_1980_alabama.csv";
//    	InputStream is = DataAnalysis.class.getClass().getResourceAsStream(fileName);
//    	Reader in = new InputStreamReader(is);
//
//
//  		// opcion 4
// 		String fileName= "/co_1980_alabama.csv";
// 		InputStream is = DataAnalysis.class.getResourceAsStream(fileName );
// 		Reader in = new InputStreamReader(is);
