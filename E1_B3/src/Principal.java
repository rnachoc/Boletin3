import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Principal {

    public class Controll {

        public int iPlatos = 5;

        public Semaphore platos = new Semaphore(iPlatos);
        public Semaphore comensales = new Semaphore(0);

        public Queue<ThreadComensales> colaThreadComensal = new LinkedList<ThreadComensales>();
    }

    Controll controll = new Controll();

    public class ThreadComensales implements Runnable {
        private byte id = 0;

        public ThreadComensales(byte id) {
            this.id = id;
        }

        @Override
        public void run() {
            byte iTiempoRandom = (byte) (Math.random() * 10);
            System.out.println("El comensal: " + id + " llega a la cola en " + iTiempoRandom + " segundos.");

            try {
                Thread.sleep(iTiempoRandom * 1000);

            } catch (InterruptedException e) {
                System.out.println("ERROR! ");
                e.printStackTrace();
            }

            controll.colaThreadComensal.add(this);
            controll.comensales.release();
        }
    }

    public class ThreadPlatos implements Runnable {
        private byte id = 0;

        public ThreadPlatos(byte id) {
            this.id = id;
        }

        @Override
        public void run() {

            while (true) {

                System.out.println("Plato " + id + " esta disponible.");
                try {
                    controll.platos.acquire();
                    controll.comensales.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int iComensalAcabado = controll.colaThreadComensal.poll().id;

                System.out.println("El comensal: " + iComensalAcabado + " ha cogido el plato: " + id);
                System.out.println("El plato: " + id + " esta siendo comido");

                try {
                    // TIEMPO DE EJECUCION
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("El plato: " + id + " ha regresado limpito.");
                System.out.println("El comensal: " + iComensalAcabado + " se marcha.");

                controll.comensales.release();
            }
        }
    }

    private void executeMultiThreading() throws InterruptedException {
        int a = 0;
        for (int i = 0; i < controll.iPlatos; i++) {
            new Thread(new ThreadPlatos((byte) i)).start();
        }
        while (true) {
            Thread.sleep(500);
            new Thread(new ThreadComensales((byte) a)).start();
            a++;
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