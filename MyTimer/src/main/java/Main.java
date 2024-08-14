public class Main {
    public static void main(String[] args) {
        MyTimer myCrono = new MyTimer();

        myCrono.stop( System.nanoTime()/1000000 + 4000);
        System.out.println(myCrono);
    }
}
