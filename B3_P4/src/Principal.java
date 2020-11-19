import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Principal {
    public class Controll {
        public Semaphore semaphore = new Semaphore(1);

        public Queue<subir> colaThreadComensal = new LinkedList<subir>();
        public Queue<bajar> colaThreadComensal = new LinkedList<bajar>();

    }

    Controll controll = new Controll();

    public class subir implements Runnable {
        private byte id = 0;

        public subir(byte id) {
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

    public class bajar implements Runnable {
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





}