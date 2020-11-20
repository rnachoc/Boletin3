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

    }

    private final Controll controll = new Controll();

    public class Aurelio implements Runnable{
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (controll.sGoogleMeets.getQueueLength() > controll.sDiscord.getQueueLength()){
                    System.out.println("Aurelio ha atendido a un estudiante en GoogleMeets");
                    controll.sGoogleMeets.release();
                }else if (controll.sGoogleMeets.getQueueLength() == controll.sDiscord.getQueueLength()){
                    int iNumero = (int) (Math.random() * 2);
                    if (iNumero == DISCORD){
                        System.out.println("Aurelio ha atendido a un estudiante en DISCORD");
                        controll.sDiscord.release();
                    }else {
                        System.out.println("Aurelio ha atendido a un estudiante en GoogleMeets");
                        controll.sGoogleMeets.release();
                    }
                }else {
                    System.out.println("Aurelio ha atendido a un estudiante en DISCORD");
                    controll.sDiscord.release();
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (true);
        }
    }

    public class Alumno implements Runnable{
        private int id;
        private int app;

        public Alumno(int id){
            setId(id);
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
            setApp((int) (Math.random()*2));
            try {
            if (getApp() == DISCORD){
                System.out.println("El alumno: " + getId() + " ha entrado en Discord.");
                    controll.sDiscord.acquire();
            }else{
                System.out.println("El alumno: " + getId() + " ha entrado en GoogleMeets.");
                controll.sGoogleMeets.acquire();
            }
                System.out.println("El alumno: " + getId() + " ya ha sido atendido.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
    }

    private void executeMultiThreading() throws InterruptedException {
        int iCont = 0;
        new Thread(new Aurelio()).start();
        while (true) {
            new Thread(new Alumno(iCont)).start();
            iCont++;
            new Thread(new Alumno(iCont)).start();
            iCont++;
            Thread.sleep(4000);
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