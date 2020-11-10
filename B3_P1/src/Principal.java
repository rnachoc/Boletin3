import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Principal {

    public class Controll {

        public int iCoches = 3;

        public Semaphore coches = new Semaphore(iCoches);
        public Semaphore pasajeros = new Semaphore(0);

        public Queue<ThreadPasajero> colaThreadPasajero = new LinkedList<ThreadPasajero>();
    }

    Controll controll = new Controll();

    public class ThreadPasajero implements Runnable {
        private byte id = 0;

        public ThreadPasajero(byte id) {
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

            controll.colaThreadPasajero.add(this);
            controll.pasajeros.release();
        }
    }

        public class ThreadCoche implements Runnable {
            private byte id = 0;

            public ThreadCoche(byte id) {
                this.id = id;
            }

            @Override
            public void run() {

                while (true) {

                    System.out.println("Coche " + id + " esta libre.");
                    try {
                        controll.coches.acquire();
                        controll.pasajeros.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int iPasajeroAcabado = controll.colaThreadPasajero.poll().id;

                    System.out.println("El pasajero: " + iPasajeroAcabado + " se ha montado en el coche: " + id);
                    System.out.println("El coche: " + id + " esta en circulacion");

                    try {
                        // TIEMPO DE EJECUCION DE LA ATRACCION
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("El coche: " + id + " ha regresado.");
                    System.out.println("El pasajero: " + iPasajeroAcabado + " se marcha.");

                    controll.coches.release();
                }
            }
        }

        private void executeMultiThreading() throws InterruptedException {
            int a = 0;
            for (int i = 0; i < controll.iCoches; i++) {
                new Thread(new ThreadCoche((byte) i)).start();
            }
            while (true) {
                Thread.sleep(500);
                new Thread(new ThreadPasajero((byte) a)).start();
                a++;
            }
        }

        public static void main(String[] args) {

            Principal principal = new Principal();
            try {
                principal.executeMultiThreading();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
}