import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Principal {

    public class Controll{

        public int iCoches = 3;
        public Semaphore coches = new Semaphore(iCoches);
        public Semaphore pasajeros = new Semaphore(0);

        public Queue<ThreadPasajero> colaThreadPasajero = new LinkedList<ThreadPasajero>();

    }

    Controll controll = new Controll();

    public class ThreadPasajero implements Runnable{
        byte id = 0;

        public ThreadPasajero(byte id){
            this.id = id;
        }
        @Override
        public void run() {
            byte iTiempoRandom = (byte) (Math.random() * 10);
            System.out.println("El pasajero: " + id + " llega a la cola en " + iTiempoRandom + " segundos.");

            try {
                Thread.sleep(iTiempoRandom * 1000);
            } catch (InterruptedException e) {
                System.out.println("ERROR! ");
                e.printStackTrace();
            }
            controll.setIdPasajero(id);
            System.out.println("Pasajero " + id + " ha llegado a la atraccion en: " + (iTiempoRandom + 1) + " segundos");
            controll.colaThreadPasajero.add(this);

            try {
                controll.cocheLibre.acquire();
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            controll.cocheUsandose.release();


            try {
                controll.cochesRestantes.acquire();
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            try {
                controll.pasajerosRestantes.acquire();
            } catch (InterruptedException e) {
                System.out.println();
                e.printStackTrace();
            }
            System.out.println(id+" Se ha bajado de la atraccion.");

        }
    }

    public class ThreadCoche implements Runnable{
        byte id = 0;

        public ThreadCoche(byte id) {
            this.id = id;
        }

        @Override
        public void run() {

            while (true){

                System.out.println("Coche " + id + " esta libre.");
                controll.cocheLibre.release();

                try {
                    controll.cocheUsandose.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("El pasajero: " + controll.colaThreadPasajero.poll().id + " se ha montado en el coche: " + id);

                System.out.println("El coche: " + id + " .esta en circulacion");

                controll.cochesRestantes.release();

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("El coche: " + id + " ha regresado.");
                controll.pasajerosRestantes.release();
            }

        }
    }

    private void executeMultiThreading() throws InterruptedException{
        int a = 0;
        for(int i = 0; i < controll.iCoches; i++) {
            new Thread(new ThreadCoche((byte) i)).start();
        }
        while (true){
            new Thread(new ThreadPasajero((byte) a)).start();
            a++;
            Thread.sleep(500);

        }

    }

    public static void main(String[] args) throws InterruptedException {

        Principal principal = new Principal();
        principal.executeMultiThreading();


    }

}
