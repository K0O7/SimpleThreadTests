class Dekker extends Thread{

    private static int iloscP = 10;
    private int mojNr;
    private volatile static int czyjaKolej = 0;
    private volatile int[] k;

    public Dekker(int nr, int[] s){
        mojNr = nr ;
        k = s;
    }


    @Override
    public void run(){
        try {
            if (mojNr == 0) {
                Thread.sleep(10+(int)(Math.random()*50));
            } else if (mojNr == 1) {
                Thread.sleep(20+(int)(Math.random()*50));
            }
                for(int i = 0; i < iloscP; i ++)
                {
                    sleep (100+(int)(Math.random()*50));
                    System.out.println("Proces "+mojNr+" chce skorzystać z sesji krytycznej");
                    k[mojNr] = 1;
                    while (k[((mojNr+1)%2)] == 1)
                        if (czyjaKolej == ((mojNr+1)%2)) {
                            k[mojNr] = 0;
                            while (czyjaKolej == ((mojNr+1)%2))
                                sleep(100+(int)(Math.random()*50));
                            k[mojNr] = 1;
                        }
                    System.out.println("Proces "+mojNr+" jest w sekcji krytycznej");
                    System.out.println();
                    sleep (100+(int)(Math.random()*50));
                    czyjaKolej = ((mojNr+1)%2);
                    k[mojNr] = 0;
                    System.out.println("Proces "+mojNr+" wyszedl z sesji krytycznej");
                }

        } catch (InterruptedException ex) {}
    }

    public static void main(String[] args) {
        int[] k = new int[2];
        k[0] = 0;
        k[1] = 0;
        Dekker d1 = new Dekker(0,k);
        Dekker d2 = new Dekker(1,k);
        d1.start();
        d2.start();
    }

}
