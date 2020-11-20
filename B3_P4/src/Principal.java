import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Principal {

    private final byte MAX_ESTUDIANTES = 10;
    private final int SUBIR = 1;

    public class Controll {

        public Semaphore semaphoreSubir = new Semaphore(MAX_ESTUDIANTES);
        public Semaphore semaphoreBajar = new Semaphore(MAX_ESTUDIANTES);

        public Queue<Alumno> colaSubir = new LinkedList<Alumno>();
        public Queue<Alumno> colaBajar = new LinkedList<Alumno>();

        private boolean bSubir = false;
        private boolean bBajar = false;
        private byte alumnosbajando = 0;
        private byte alumnossubiendo = 0;

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
    }

    private final Controll controll = new Controll();

    public class Escalera implements Runnable {
        private byte id = 0;

        public Esalera(byte id) {
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

    public class Alumno implements Runnable {
        private byte id;
        byte bByte;

        public Alumno(byte id) {
            this.id = id;
        }

        public byte getId() {
            return id;
        }

        public void setId(byte id) {
            this.id = id;
        }

        public byte getbByte() {
            return bByte;
        }

        public void setbByte(byte bByte) {
            this.bByte = bByte;
        }

        public InterruptedException getE() {
            return e;
        }

        public void setE(InterruptedException e) {
            this.e = e;
        }

        @Override
        public void run() {

            setbByte((byte) (Math.random() * 2));

            if(bByte == SUBIR){
                System.out.println("El alumno: " + getId() + " va a subir.");
                controll.colaSubir.add(this);
                try {
                    controll.semaphoreSubir.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("El alumno: " + getId() + " va a bajar.");
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