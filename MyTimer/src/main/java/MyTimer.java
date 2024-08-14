public class MyTimer {
    private long ms;
    private boolean stopped = false;
    public MyTimer() {
        this.ms = System.nanoTime()/1000000;
    }

    public MyTimer(long ms) {
        if (ms < 0) throw new IllegalArgumentException("ms must be positive");
        this.ms = ms;
    }

    public void stop(){
        ms = System.nanoTime()/1000000 - this.ms;
        stopped = true;
    }

    public void stop(long ms){ //use with nanoTime for consistency
        if(ms < this.ms) throw new IllegalArgumentException("ms cant be before timer start");
        this.ms = ms - this.ms;
        stopped = true;
    }

    @Override
    public String toString() {
        if(!stopped) throw new RuntimeException("timer has not been stopped");
        return ms + " ms";
    }
}
