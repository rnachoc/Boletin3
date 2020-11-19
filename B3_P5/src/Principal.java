import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Principal {

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


    }

    private final Controll controll = new Controll();















}