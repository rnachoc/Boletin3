import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Principal {

    private final byte MAX_ESTUDIANTES = 10;
    private final int BAJAR = 0;
    private final int SUBIR = 1;

    public class Controll {

        public Semaphore semaphoreSubir = new Semaphore(MAX_ESTUDIANTES);
        public Semaphore semaphoreBajar = new Semaphore(MAX_ESTUDIANTES);

        public Queue<AlumnoSubir> colaSubir = new LinkedList<AlumnoSubir>();
        public Queue<AlumnoBajar> colaBajar = new LinkedList<AlumnoBajar>();

        public Semaphore getSemaphoreSubir() {
            return semaphoreSubir;
        }

        public void setSemaphoreSubir(Semaphore semaphoreSubir) {
            this.semaphoreSubir = semaphoreSubir;
        }

        public Semaphore getSemaphoreBajar() {
            return semaphoreBajar;
        }

        public void setSemaphoreBajar(Semaphore semaphoreBajar) {
            this.semaphoreBajar = semaphoreBajar;
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
                //controll.colaSubir.add(this);

                if(controll.semaphoreBajar.availablePermits() == MAX_ESTUDIANTES){
                    controll.semaphoreSubir.acquire();
                    controll.semaphoreBajar.acquire();

                    System.out.println("La cola para subir: " + getId() + " va a subir.");


                    controll.semaphoreSubir.release();
                    controll.semaphoreSubir.release();

                    System.out.println("La cola para subir: " + getId() + " ha subido");
                }

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
                //controll.colaBajar.add(this);
                if(controll.semaphoreSubir.availablePermits() == MAX_ESTUDIANTES){
                    controll.semaphoreBajar.acquire();
                    controll.semaphoreSubir.acquire();

                    System.out.println("La cola para bajar: " + getId() + " va a bajar.");

                    controll.semaphoreSubir.release();
                    controll.semaphoreBajar.release();

                    System.out.println("La cola para bajar: " + getId() + " ha llegado abajo.");
                }




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