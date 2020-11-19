import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Principal {

    private final int DISCORD = 1;
    private final int GOOGLEMEETS = 0;

    public class Controll {

        public Semaphore sGoogleMeets = new Semaphore(0);
        public Semaphore sDiscord = new Semaphore(0);

        public Semaphore getsGoogleMeets() {
            return sGoogleMeets;
        }

        public void setsGoogleMeets(Semaphore sGoogleMeets) {
            this.sGoogleMeets = sGoogleMeets;
        }

        public Semaphore getsDiscord() {
            return sDiscord;
        }

        public void setsDiscord(Semaphore sDiscord) {
            this.sDiscord = sDiscord;
        }

        public Queue<AlumnoSubir> colaSubir = new LinkedList<AlumnoSubir>();
        public Queue<AlumnoBajar> colaBajar = new LinkedList<AlumnoBajar>();

        public Semaphore getSemaphoreSubir() {
            return semaphoreSubir;
        }


    }

    private final Controll controll = new Controll();

    public class Aurelio implements Runnable{


        @Override
        public void run() {

            do {

            }while (true);

        }
    }

    public class Alumno implements Runnable{
        private int id;
        private int app;

        public Alumno(){

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getApp() {
            return app;
        }

        public void setApp(int app) {
            this.app = app;
        }

        @Override
        public void run() {

        }
    }




















    private void executeMultiThreading() throws InterruptedException {
        int iCont = 0;
        new Thread(new Aurelio()).start();
        while (true) {
            Thread.sleep(2000);
            new Thread(new Alumno(iCont)).start();
            iCont++;
            new Thread(new Alumno(iCont)).start();
            iCont++;
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