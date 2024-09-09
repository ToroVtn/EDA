public class IdxRecord<K extends Comparable<K>, R> implements Comparable<IdxRecord<K, R>>{

    private K key;
    private R row;

    public IdxRecord(K myKey) {
        key= myKey;
    }


    public IdxRecord(K myKey, R myRow) {
        key= myKey;
        row= myRow;
    }

    public int compareTo(IdxRecord<K,R> other) {
        return key.compareTo(other.getKey());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof IdxRecord)
            return this.compareTo((IdxRecord<K, R>) obj) == 0;

        return super.equals(obj);
    }

    public String toString() {
        return String.format("%s %s", key, row);
    }

    public K getKey() {
        return key;
    }

    public R getRow() {
        return row;
    }


}
