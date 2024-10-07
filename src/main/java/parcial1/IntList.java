package parcial1;

public class IntList {

    private Item first;
    private Item last;

    public IntList (){
        first = null;
        last = null;
    }

    public IntList ( int [] init ){
        for( int i = init.length - 1; i >= 0; --i ){
            Item it = new Item( init[i] );
            it.next = first;
            first = it;
            if ( last == null )
                last = it;
        }
    }

    public void clear(){
        first = null;
        last = null;
    }

    public String toString(){
        String result = "";
        Item it = first;
        while ( it != null )
        {
            result += it.toString();
            it = it.next;
            if ( it != null )
                result += " -> ";
        }

        return result;
    }

    private final class Item {
        private final Integer numero;
        private Item next = null;
        public Item(Integer numero) {
            this.numero = numero;
        }
        public String toString(){
            return numero.toString();
        }
    }


    public int SplitAndErase( int umbral, IntList result ){
        Item it = first;
        Item prev = null;
        Item it2 = result.first == null ? null : result.last;
        int rep = 0;
        while(it != null){
            if(it.numero < umbral){
                prev = it;
                it=it.next;
            }

            else if (it.numero == umbral) {
                rep++;
                if(prev == null)
                    first = first.next;
                else prev.next = it.next;
                it = it.next;
            }

            else {
                if(it2 == null)
                    it2 = result.first = result.last = it;
                else {
                    it2.next = it;
                    it2 = it2.next;
                }

                if(prev == null)
                    first = first.next;
                else prev.next = it.next;
                it = it.next;
            }
        }
        return rep;
    }


    public static void main1(String[] args) {
        IntList iL = new IntList( new int[] { 100, 50, 30, 50, 80, 100, 100, 30, 120, 120} );
        System.out.println( iL );

        IntList iLRes = new IntList();
        int res = iL.SplitAndErase(80, iLRes );

        System.out.println( "Original" );
        System.out.println( iL );

        System.out.println( "ResList" );
        System.out.println( iLRes );

        System.out.println( "Borrados" );
        System.out.println( res );

    }

    public static void main2(String[] args) {
        IntList iL = new IntList( new int[] { 100, 50, 30, 50, 80, 100, 100, 30, 120, 120} );
        System.out.println( iL );

        IntList iLRes = new IntList(new int[] { 10, 10, 10} );
        int res = iL.SplitAndErase(50, iLRes );

        System.out.println( "Original" );
        System.out.println( iL );

        System.out.println( "ResList" );
        System.out.println( iLRes );

        System.out.println( "Borrados" );
        System.out.println( res );

    }

    public static void main(String[] args) {
        IntList iL = new IntList( new int[] { 100, 50, 30, 50, 80, 100, 100, 30, 120, 120} );
        System.out.println( iL );

        IntList iLRes = new IntList(new int[] { 10, 10, 10} );
        int res = iL.SplitAndErase(10, iLRes );

        System.out.println( "Original" );
        System.out.println( iL );

        System.out.println( "ResList" );
        System.out.println( iLRes );

        System.out.println( "Borrados" );
        System.out.println( res );

    }
}