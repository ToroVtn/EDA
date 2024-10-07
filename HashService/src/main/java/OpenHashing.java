import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class OpenHashing<K, V> implements IndexParametricService<K, V> {
    final private int initialLookupSize= 5;
    private int totalSize = 0, arraySize = 0;

    @SuppressWarnings({"unchecked"})
    private List<Slot<K, V>>[] Lookup = new List[initialLookupSize]; // TODO make each individual List a tree

    private Function<? super K, Integer> prehash;

    public OpenHashing( Function<? super K, Integer> mappingFn) {
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

    @Override
    public void insertOrUpdate(K key, V data) {
        if (key == null || data == null) {
            String msg= String.format("inserting or updating (%s,%s). ", key, data);
            if (key==null)
                msg+= "Key cannot be null. ";

            if (data==null)
                msg+= "Data cannot be null.";

            throw new IllegalArgumentException(msg);
        }

        int index= hash(key);
        if(Lookup[index]==null){
            Lookup[index] = new ArrayList<>();
            Lookup[index].add(new Slot<>(key, data));
            arraySize++;
            totalSize++;

            if (arraySize/Lookup.length >= 0.75) resize(); // TODO figure some better load factor

            return;
        }
        Lookup[index].add(new Slot<>(key, data));
        totalSize++;
    }

    private void resize() {
        List<Slot<K, V>>[] oldLookup= Lookup;
        Lookup = new List[oldLookup.length * 2];

        for(List<Slot<K, V>> lst: oldLookup) {
            if(lst!=null){
                for(Slot<K, V> slot: lst) {
                    insertOrUpdate(slot.key, slot.value);
                }
            }
        }
    }

    @Override
    public V find(K data) {
        if (data == null) throw new IllegalArgumentException("data cannot be null");
        int index= hash(data);
        if(Lookup[index]==null){
            return null;
        }
        for (Slot<K, V> slot : Lookup[index]) {
            if(slot.key.equals(data)){
                return slot.value;
            }
        }
        return null;
    }

    @Override
    public boolean remove(K key) {
        int index= hash(key);
        if(Lookup[index]==null){
            return false;
        }
        Iterator<Slot<K, V>> iterator = Lookup[index].iterator();
        while(iterator.hasNext()){
            if(iterator.next().key.equals(key)){
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return totalSize;
    }

    @Override
    public void dump() {
        for(int rec= 0; rec < Lookup.length; rec++) {
            if (Lookup[rec] == null)
                System.out.println(String.format("slot %d is empty", rec));
            else {
                System.out.printf("slot %d contains %d entries", rec, Lookup[rec].size());
                for (Slot<K, V> slot : Lookup[rec]) {
                    System.out.print(slot + " -> ");
                }
                System.out.println();
            }
        }
    }

    static private final class Slot<K, V>	{
        private final K key;
        private V value;

        private Slot(K theKey, V theValue){
            key= theKey;
            value= theValue;
        }


        public String toString() {
            return String.format("(key=%s, value=%s)", key, value );
        }
    }
}
