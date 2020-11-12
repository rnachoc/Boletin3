import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Principal {

    private final int IASCENSORES = 5;

    public class Controll {
        private Semaphore ascensor = new Semaphore(IASCENSORES);
        private Semaphore planta = new Semaphore(0);

        private Queue<ThreadPlanta> colaThreadPlanta = new LinkedList<ThreadPlanta>();

        public Semaphore getAscensor() {
            return ascensor;
        }
        public void setAscensor(Semaphore ascensor) {
            this.ascensor = ascensor;
        }
        public Semaphore getPlanta() {
            return planta;
        }
        public void setPlanta(Semaphore planta) {
            this.planta = planta;
        }
        public Queue<ThreadPlanta> getColaThreadComensal() {
            return colaThreadPlanta;
        }
        public void setColaThreadComensal(Queue<ThreadPlanta> colaThreadPlanta) {
            this.colaThreadPlanta = colaThreadPlanta;
        }
    }

    Controll controll = new Controll();

    public class ThreadPlanta implements Runnable {
        private byte id = 0;

        public ThreadPlanta(byte id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println("El ascensor se ha llamado a la planta: " + id);

            controll.colaThreadPlanta.add(this);
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

                int iPlantaOcupada = controll.colaThreadPlanta.poll().id;

                System.out.println("La planta: " + iPlantaOcupada + " ha cogido el ascensor: " + id);
                System.out.println("El ascensor: " + id + " esta ocupado");

                try {
                    // TIEMPO DE EJECUCION
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("El ascensor: " + id + " ha quedado libre.");

                controll.planta.release();
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
