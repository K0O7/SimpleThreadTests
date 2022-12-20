public class Buffer implements Pisze, Czyta {

    final private int RozmiarBuff;
    private int odlozone = 0, wyciagniete = 0, ileNapisane = 0;
    private int[] elems;

    public Buffer(int roz) {
        RozmiarBuff = roz; elems = new int[RozmiarBuff];
    }

    public synchronized void poloz(int nrKsiazki) {
        while (ileNapisane >= RozmiarBuff)
            try {
                System.out.println(Thread.currentThread().getName()+" czeka z " + nrKsiazki);
                wait();
            } catch (InterruptedException e) {System.out.println(e);}

        elems[odlozone] = nrKsiazki;
        odlozone = (odlozone + 1) % RozmiarBuff;
        ileNapisane += 1;

        System.out.println(Thread.currentThread().getName()+" Napisane: " + nrKsiazki);

        if (nrKsiazki == 1) notifyAll();
    }

    public synchronized int wez() {
        while (ileNapisane == 0)
            try {
                System.out.println(Thread.currentThread().getName()+" czeka");
                wait();
            }
        catch (InterruptedException e) {
                System.out.println(e);
            }

        int nrKsiazki = elems[wyciagniete];
        wyciagniete = (wyciagniete + 1) % RozmiarBuff;
        ileNapisane -= 1;

        System.out.println( Thread.currentThread().getName() + " czyta: " + nrKsiazki);

        if (ileNapisane == RozmiarBuff-1)
            notifyAll();
        return nrKsiazki;
    }
}