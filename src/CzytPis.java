public class CzytPis {
    public static void main(String[] args) {
        Buffer buf = new Buffer(100);
        (new Pisarze("Pisarze", buf)).start();
        (new Czytelnicy("Czytelnicy", buf)).start();
    }
}
