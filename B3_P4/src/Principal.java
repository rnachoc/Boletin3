import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Principal {

    private final byte MAX_ESTUDIANTES = 10;
    private final int BAJAR = 0;
    private final int SUBIR = 1;

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
            controll.semaphore.acquire();



        }
    }

    private void executeMultiThreading() throws InterruptedException {
        int a = 0;

        int iNumero = (int) (Math.random() * 2);

        while (true) {
            Thread.sleep(550);
            new Thread(new Alumno((byte) iNumero)).start();
            a++;
        }
    }

    public static void main(String[] args) {

        try {
            Principal principal = new Principal();
            principal.executeMultiThreading();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }




}