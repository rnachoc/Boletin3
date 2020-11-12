import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Principal {

    private final int IDENTISTA = 1;

    public class Controll {
        private Semaphore dentista = new Semaphore(IDENTISTA);
        private Semaphore silla = new Semaphore(0);

        private Queue<ThreadSilla> colaThreadSilla = new LinkedList<ThreadSilla>();

        public Semaphore getDentista() {
            return dentista;
        }
        public void setDentista(Semaphore dentista) {
            this.dentista = dentista;
        }
        public Semaphore getSilla() {
            return silla;
        }
        public void setSilla(Semaphore silla) {
            this.silla = silla;
        }
        public Queue<ThreadSilla> getColaThreadComensal() {
            return colaThreadSilla;
        }
        public void setColaThreadSilla(Queue<ThreadSilla> colaThreadSilla) {
            this.colaThreadSilla = colaThreadSilla;
        }
    }

    Controll controll = new Controll();

    public class ThreadSilla implements Runnable {
        private byte id = 0;

        public ThreadSilla(byte id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println("El ascensor se ha llamado a la planta: " + id);

            controll.colaThreadSilla.add(this);
            controll.planta.release();
        }
    }

    public class ThreadAscensor implements Runnable {
        private byte id = 0;

        public ThreadAscensor(byte id) {
            this.id = id;
        }

        @Override
        public void run() {

            while (true) {

                System.out.println("Ascensor " + id + " esta disponible.");
                try {
                    controll.ascensor.acquire();
                    controll.planta.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int iPlantaOcupada = controll.colaThreadSilla.poll().id;

                System.out.println("La planta: " + iPlantaOcupada + " ha cogido el ascensor: " + id);
                System.out.println("El ascensor: " + id + " esta ocupado");

                try {
                    // TIEMPO DE EJECUCION
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("El ascensor: " + id + " ha quedado libre.");

                controll.ascensor.release();
            }
        }
    }
    private void executeMultiThreading() throws InterruptedException {
        for (int i = 0; i < IASCENSORES; i++) {
            new Thread(new ThreadAscensor((byte) i)).start();
        }
        while (true) {
            int a = (int) (Math.random()*15);
            Thread.sleep(500);
            new Thread(new ThreadPlanta((byte) a)).start();
        }
    }
    public static void main(String[] args) {

        Principal principal = new Principal();
        try {
            principal.executeMultiThreading();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
