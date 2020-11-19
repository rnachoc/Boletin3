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

        private boolean bSubir = false;
        private boolean bBajar = false;
        private byte alumnosbajando = 0;
        private byte alumnossubiendo = 0;

        public Semaphore getSemaphoreSubir() {
            return semaphoreSubir;
        }

        public boolean isbSubir() {
            return bSubir;
        }

        public void setbSubir(boolean bSubir) {
            this.bSubir = bSubir;
        }

        public boolean isbBajar() {
            return bBajar;
        }

        public void setbBajar(boolean bBajar) {
            this.bBajar = bBajar;
        }

        public byte getAlumnosbajando() {
            return alumnosbajando;
        }

        public void setAlumnosbajando(byte alumnosbajando) {
            this.alumnosbajando = alumnosbajando;
        }

        public byte getAlumnossubiendo() {
            return alumnossubiendo;
        }

        public void setAlumnossubiendo(byte alumnossubiendo) {
            this.alumnossubiendo = alumnossubiendo;
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
                controll.semaphoreBajar.acquire();
                controll.colaSubir.add(this);
                controll.bSubir = true;
                if(controll.colaBajar.isEmpty() && controll.alumnossubiendo != 10 || controll.bSubir){
                    controll.bBajar = false;

                    System.out.println("La cola para subir: " + getId() + " va a subir.");
                    controll.alumnosbajando++;
                    Thread.sleep(2000);

                    System.out.println("El alumno para subir: " + getId() + " ha subido");
                    controll.semaphoreBajar.release();
                }

                if (controll.alumnossubiendo == 10){
                    controll.bSubir = false;
                    System.out.println("Han subido 10 alumnos.");
                    for (int i = 0; i < MAX_ESTUDIANTES ; i++){
                        controll.colaBajar.poll();


                    }
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
                controll.semaphoreSubir.acquire();
                controll.colaBajar.add(this);

                if(controll.colaSubir.isEmpty() && controll.alumnosbajando != MAX_ESTUDIANTES || controll.bBajar){
                    controll.bBajar = true;
                    controll.bSubir = false;
                    System.out.println("La cola para bajar: " + getId() + " va a bajar.");

                    controll.alumnosbajando++;
                    Thread.sleep(2000);

                    System.out.println("El alumno para bajar: " + getId() + " ha llegado abajo.");
                    controll.semaphoreSubir.release();
                }

                if(controll.alumnosbajando == 10){
                    System.out.println("Han bajado 10 alumnos.");
                    controll.alumnosbajando = 0;

                    for (int i = 0; i < MAX_ESTUDIANTES ; i++){
                        controll.colaBajar.poll();
                        Thread.sleep(500);
                    }
                }



            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    private void executeMultiThreading() throws InterruptedException {

        while (true) {
            int iCont = 0;
            Thread.sleep(200);
            new Thread(new AlumnoSubir((byte) iCont)).start();
            iCont++;
            Thread.sleep(200);
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