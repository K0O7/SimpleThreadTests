public class Pisarze extends Thread{
    final private Pisze buf;

    public Pisarze(String name, Pisze buf) {
        super(name); this.buf = buf;
    }

    @Override public void run() {
        for (int i = 1; i <= 100; i++) {
            System.out.println(getName()+" Napisal "+i); buf.poloz(i);
        }
    }
}
