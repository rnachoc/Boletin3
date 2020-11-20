import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Principal {

    private final byte MAX_ESTUDIANTES = 10;
    private final int SUBIR = 1;

    public class Controll {

        public Semaphore semaphoreSubir = new Semaphore(0);
        public Semaphore semaphoreBajar = new Semaphore(0);

        public Queue<Alumno> colaSubir = new LinkedList<Alumno>();
        public Queue<Alumno> colaBajar = new LinkedList<Alumno>();

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

    public class Escalera implements Runnable {
        @Override
        public void run() {
           do {
               try {
                   Thread.sleep(400);

                   if(controll.semaphoreSubir.getQueueLength() != 0 && controll.semaphoreBajar.getQueueLength() != 0){
                       if(controll.semaphoreBajar.getQueueLength() >= MAX_ESTUDIANTES || controll.semaphoreSubir.getQueueLength() == 0){
                            byte bColaBajada = (byte) controll.semaphoreBajar.getQueueLength();
                            if(controll.semaphoreBajar.getQueueLength() > MAX_ESTUDIANTES){
                                bColaBajada = MAX_ESTUDIANTES;
                            }
                           System.out.println("Han bajado: " + bColaBajada + " estudiantes.");
                            controll.semaphoreBajar.release();

                       }else if (controll.semaphoreSubir.getQueueLength() >= MAX_ESTUDIANTES || controll.semaphoreBajar.getQueueLength() == 0){
                           byte bColaSubida = (byte) controll.semaphoreSubir.getQueueLength();
                           if (controll.semaphoreSubir.getQueueLength() > MAX_ESTUDIANTES){
                               bColaSubida = MAX_ESTUDIANTES;
                           }
                           System.out.println("Han subido: " + bColaSubida + " estudiantes.");
                            controll.semaphoreSubir.release();
                       }
                   }
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }while (true);
        }
    }

    public class Alumno implements Runnable {
        private int id;
        byte bByte;

        public Alumno(byte id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public byte getbByte() {
            return bByte;
        }

        public void setbByte(byte bByte) {
            this.bByte = bByte;
        }

        @Override
        public void run() {
            setbByte((byte) (Math.random() * 2));
            try {
            if(bByte == SUBIR){
                System.out.println("El alumno: " + getId() + " va a subir.");
                controll.colaSubir.add(this);
                controll.semaphoreSubir.acquire();

            }else{
                System.out.println("El alumno: " + getId() + " va a bajar.");
                controll.colaBajar.add(this);
                controll.semaphoreBajar.acquire();
            }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void executeMultiThreading() throws InterruptedException {
        int iContador = 0;

        new Thread(new Escalera()).start();
        while (true) {
            new Thread(new Alumno((byte) iContador)).start();
            Thread.sleep(1000);
            iContador++;
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