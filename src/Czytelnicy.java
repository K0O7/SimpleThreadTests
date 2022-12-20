public class Czytelnicy extends Thread{
    final private Czyta buf;

    public Czytelnicy(String name, Czyta buf) {
        super(name); this.buf = buf;
    }

    @Override public void run() {
        for (int i = 1; i <= 100; i++) {
            System.out.println(getName()+" Przeczytal "+buf.wez());
        }
    }
}
