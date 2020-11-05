import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Principal {

    public class Controll{

        public int iCoches = 3;
        public byte bTiempoPasajeros = 5;
        public byte getbTiempoAtraccion = 2;
        public Semaphore cocheLibre = new Semaphore(0);
        public Semaphore cocheUsandose = new Semaphore(0);
        public Semaphore cochesRestantes = new Semaphore(0);
        public Semaphore pasajerosRestantes = new Semaphore(0);

        public Random random = new Random();

        public Queue<ThreadPasajero> colaThreadPasajero = new LinkedList<ThreadPasajero>();

        public volatile byte idCoche = 0;
        public volatile byte idPasajero = 0;

        public byte getIdCoche() {
            return idCoche;
        }

        public void setIdCoche(byte idCoche) {
            this.idCoche = idCoche;
        }

        public byte getIdPasajero() {
            return idPasajero;
        }

        public void setIdPasajero(byte idPasajero) {
            this.idPasajero = idPasajero;
        }
    }

    Controll controll = new Controll();

    public class ThreadPasajero implements Runnable{
        byte id = 0;

        public ThreadPasajero(byte id){
            this.id = id;
        }
        @Override
        public void run() {
            byte iTiempoRandom = (byte) (1 + (byte) controll.random.nextInt(1000 * controll.bTiempoPasajeros));

            try {
                Thread.sleep(iTiempoRandom);
            } catch (InterruptedException e) {
                System.out.println("ERROR! ");
                e.printStackTrace();
            }
            controll.setIdPasajero(id);
            System.out.println("Pasajero " + id + "ha llegado a la atraccion en: " + iTiempoRandom / 1000 + "segundos");
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

        @Override
        public void run() {

        }
    }

    private void executeMultiThreading() throws InterruptedException{

    }

    public static void main(String[] args) {


    }

}
