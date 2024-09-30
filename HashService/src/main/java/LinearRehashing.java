import java.io.*;
import java.util.Scanner;
import java.util.function.Function;

public class LinearRehashing<K, V> implements IndexParametricService<K, V> {
    final private int initialLookupSize= 10;
    private int size = 0;
    // estática. No crece. Espacio suficiente...
    @SuppressWarnings({"unchecked"})
    private Slot<K,V>[] Lookup= (Slot<K,V>[]) new Slot[initialLookupSize];

    private Function<? super K, Integer> prehash;

    public LinearRehashing( Function<? super K, Integer> mappingFn) {
        if (mappingFn == null)
            throw new RuntimeException("fn not provided");

        prehash= mappingFn;
    }

    // ajuste al tamaño de la tabla
    private int hash(K key) {
        if (key == null)
            throw new IllegalArgumentException("key cannot be null");

        return prehash.apply(key) % Lookup.length;
    }



    public void insertOrUpdate(K key, V data) {
        if (key == null || data == null) {
            String msg = String.format("inserting or updating (%s,%s). ", key, data);
            if (key == null)
                msg += "Key cannot be null. ";

            if (data == null)
                msg += "Data cannot be null.";

            throw new IllegalArgumentException(msg);
        }

        if((double) size / Lookup.length > 0.75) resize();

        int auxKey = hash(key);
        int index = Lookup.length;
        while (Lookup[auxKey] != null) {
            if (Lookup[auxKey].key.equals(key)) return;

            if (Lookup[auxKey].deleted) index = auxKey;

            auxKey++;
            if (auxKey == Lookup.length) auxKey = 0;
        }

        if (index != Lookup.length) {
            Lookup[index] = new Slot<>(key, data);
            size++;
            return;
        }

        Lookup[auxKey] = new Slot<>(key, data);
        size++;
    }

    private void resize() {
        Slot<K,V>[] oldLookup= Lookup;
        Lookup= (Slot<K,V>[]) new Slot[Lookup.length*2];
        for (Slot<K, V> slot : oldLookup) {
            if (slot != null) {
                insertOrUpdate(slot.key, slot.value);
            }
        }
    }


    // find or get
    public V find(K key) {
        if (key == null)
            return null;
        int index = hash(key);
        while(Lookup[index]!=null){
            if(Lookup[index].key.equals(key)) return Lookup[hash(key)].value;
            index++;
        }
        return null;
    }

    public boolean remove(K key) {
        if (key == null)
            return false;

        // lo encontre?
        int i = hash(key);
        while(Lookup[i]!=null && !Lookup[i].key.equals(key)){
            i++;
        }
        if(Lookup[i]==null) return false;

        if(Lookup[(i+1) % Lookup.length]==null) {
            Lookup[i]=null;
            return true;
        }

        Lookup[i].deleted=true;
        size--;
        return true;
    }


    public void dump()  {
        for(int rec= 0; rec < Lookup.length; rec++) {
            if (Lookup[rec] == null)
                System.out.println(String.format("slot %d is empty", rec));
            else
                System.out.println(String.format("slot %d contains %s",rec, Lookup[rec]));
        }
    }


    public int size() {
        return size;
    }



    static private final class Slot<K, V>	{
        private final K key;
        private final V value;
        private boolean deleted = false;

        private Slot(K theKey, V theValue){
            key= theKey;
            value= theValue;
        }


        public String toString() {
            return String.format("(key=%s, value=%s, deleted=%s)", key, value, deleted);
        }
    }


//    public static void main(String[] args) throws FileNotFoundException {
//        File file = new File("E:\\facu\\EDA\\EDA\\HashService\\src\\main\\resources\\amazon-categories30.txt");
//        Scanner input = new Scanner(file).useDelimiter("#");
//
//        LinearRehashing<String, String> hash = new LinearRehashing<>(s -> {
//            int sum = 0;
//            for(int i =0; i<s.length(); i++){
//                sum = Math.abs(31 * sum + s.codePointAt(i));
//            }
//            return sum;
//        });
//
//        while (input.hasNext()){
//            String title = input.next();
//            hash.insertOrUpdate(title, title);
//            input.nextLine();
//        }
//        hash.dump();
//        System.out.println();
//        hash.remove("The Casebook of Sherlock Holmes, Volume 2 (Casebook of Sherlock Holmes)");
//        hash.dump();
//        System.out.println();
//        System.out.println(hash.find("Batik"));
//    }


	public static void main(String[] args) {
		LinearRehashing<Integer, String> myHash= new LinearRehashing<>(f->f);
        myHash.insertOrUpdate(3, "Dick");
        myHash.dump();
        System.out.println();

        myHash.insertOrUpdate(23, "Joe");
        myHash.dump();
        System.out.println();

        myHash.insertOrUpdate(4, "Sue");
        myHash.dump();
        System.out.println();

        myHash.insertOrUpdate(15, "Meg");
        myHash.dump();
        System.out.println();

        myHash.remove(23); //Joe
        myHash.dump();
        System.out.println();

        myHash.remove(15);//Meg
        myHash.dump();
        System.out.println();

        myHash.insertOrUpdate(4, "Sue");
        myHash.dump();
        System.out.println();

        myHash.insertOrUpdate(43, "Paul");
        myHash.dump();
        System.out.println();

        myHash.insertOrUpdate(60, "60");
        myHash.dump();
        System.out.println();

        myHash.insertOrUpdate(5, "5");
        myHash.dump();
        System.out.println();
    }
}
