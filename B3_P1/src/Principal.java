import java.util.LinkedList;
import java.util.Queue;
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

        public Queue<ThreadPasajero> colaThreadPasajero = new LinkedList<ThreadPasajero>();

        public volatile byte idCoche = 0;
        public volatile byte idPasajero = 0;
    }

    public class ThreadPasajero implements Runnable{

        @Override
        public void run() {

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
