import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Principal {

    private final byte MAX_ESTUDIANTES = 10;
    private final int BAJAR = 0;
    private final int SUBIR = 1;

    public class Controll {

        public Semaphore semaphore = new Semaphore(1);

        public Queue<AlumnoSubir> colaSubir = new LinkedList<AlumnoSubir>();
        public Queue<AlumnoBajar> colaBajar = new LinkedList<AlumnoBajar>();

        public Semaphore getSemaphore() {
            return semaphore;
        }

        public void setSemaphore(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        public Queue<AlumnoSubir> getColaSubir() {
            return colaSubir;
        }

        public void setColaSubir(Queue<AlumnoSubir> colaSubir) {
            this.colaSubir = colaSubir;
        }

        public Queue<AlumnoBajar> getColaBajar() {
            return colaBajar;
        }

        public void setColaBajar(Queue<AlumnoBajar> colaBajar) {
            this.colaBajar = colaBajar;
        }
    }

    private final Controll controll = new Controll();

    public class AlumnoSubir implements Runnable {
        private byte id = 0;

        public AlumnoSubir(byte id) {
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
            try {
                controll.colaSubir.add(this);
                System.out.println("La cola para subir: " + getId() + " va a subir.");
                controll.semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    public class AlumnoBajar implements Runnable {
        private byte id = 0;

        public AlumnoBajar(byte id) {
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
            try {

                controll.colaBajar.add(this);
                System.out.println("La cola para bajar: " + getId() + " va a bajar.");
                controll.semaphore.acquire();



            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    private void executeMultiThreading() throws InterruptedException {

        while (true) {
            int iCont = 0;
            Thread.sleep(550);
            new Thread(new AlumnoSubir((byte) iCont)).start();
            iCont++;
            new Thread(new AlumnoBajar((byte) iCont)).start();
            iCont ++;
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