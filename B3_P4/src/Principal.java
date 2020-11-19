import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Principal {
    public class Controll {

        public Semaphore semaphore = new Semaphore(1);

        public Queue<Alumno> colaSubir = new LinkedList<Alumno>();
        public Queue<Alumno> colaBajar = new LinkedList<Alumno>();

        public Semaphore getSemaphore() {
            return semaphore;
        }

        public void setSemaphore(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        public Queue<Alumno> getColaSubir() {
            return colaSubir;
        }

        public void setColaSubir(Queue<Alumno> colaSubir) {
            this.colaSubir = colaSubir;
        }

        public Queue<Alumno> getColaBajar() {
            return colaBajar;
        }

        public void setColaBajar(Queue<Alumno> colaBajar) {
            this.colaBajar = colaBajar;
        }
    }

    private final Controll controll = new Controll();

    public class Alumno implements Runnable {
        private byte id = 0;

        public Alumno(byte id) {
            this.id = id;
        }

        public byte getId() {
            return id;
        }

        public void setId(byte id) {
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

            controll.colaSubir.add(this);
            controll.semaphore.release();
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




}