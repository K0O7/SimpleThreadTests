import java.util.concurrent.Semaphore;

class Filozof extends Thread {
    public int NrMiejsca;
    static int iloscFilozofow = 5;
    static int iloscPowtozen = 6;
    public static Semaphore[] paleczki = new Semaphore[iloscFilozofow];
    public static Semaphore odzwierny = new Semaphore(iloscFilozofow-1);

    public Filozof(int nr){
        NrMiejsca=nr;
    }

    @Override public void run() {
        for(int j=0; j<iloscPowtozen; j++){
            System.out.println(NrMiejsca + ". Medytacja");
            try {
                sleep(100+(int)(Math.random()*50));
            } catch (InterruptedException ignored) { }
            System.out.println(NrMiejsca + ". idzie do kuchni");
            idzJesc();
        }
    }

    private void idzJesc(){
        try{

            odzwierny.acquire();
            System.out.println(NrMiejsca + ". wszedl do kuchni");

            try {
                paleczki[NrMiejsca - 1].acquire();
                System.out.println(NrMiejsca + ". zlapal lewa paleczke " + (NrMiejsca - 1));
            }catch(InterruptedException ignore){}

            try {
                paleczki[NrMiejsca % 5].acquire();
                System.out.println(NrMiejsca + ". zlapal prawa paleczke " + (NrMiejsca)%5);
            }catch(InterruptedException ignore){}

            jedz();

        }catch(InterruptedException ignore){}
        finally {

            System.out.println(NrMiejsca + ". polozyl lewa paleczke " + (NrMiejsca - 1));
            paleczki[NrMiejsca-1].release();

            System.out.println(NrMiejsca + ". polozyl prawa paleczke " + (NrMiejsca)%5);
            paleczki[NrMiejsca%5].release();

            System.out.println(NrMiejsca + ". wyszedl z kuchni");
            odzwierny.release();
        }
    }

    private void jedz(){
        System.out.println(NrMiejsca + ". zaczyna jesc");
        try {
            sleep(100+(int)(Math.random()*50));
        } catch (InterruptedException ignored) { }

        System.out.println(NrMiejsca + ". skonczyl jesc");
    }

    public static void main(String[] args){
        Filozof[] filozofowie = new Filozof[iloscFilozofow];
        for(int i = 0; i<iloscFilozofow; i++) {
            paleczki[i] = new Semaphore(1);
            filozofowie[i] = new Filozof(i+1);
        }
        for(int i = 0; i<iloscFilozofow; i++)
            filozofowie[i].start();

        try{
            for(int i = 0; i<iloscFilozofow; i++)
                filozofowie[i].join();
        }
        catch (InterruptedException ignored) { }
    }

}
