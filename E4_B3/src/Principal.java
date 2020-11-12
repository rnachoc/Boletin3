import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Principal {

    private final int ISILLAS = 11;
    private byte bPersonas = 0;

    public class Controll {
        private Semaphore dentista = new Semaphore(0);
        private Semaphore silla = new Semaphore(ISILLAS);
        private Semaphore persona = new Semaphore(0);

        private Queue<ThreadPersona> colaThreadPersona = new LinkedList<ThreadPersona>();

        public Semaphore getDentista() {
            return dentista;
        }
        public void setDentista(Semaphore dentista) {
            this.dentista = dentista;
        }
        public Semaphore getSilla() {
            return silla;
        }
        public void setSilla(Semaphore silla) {
            this.silla = silla;
        }
        public Semaphore getPersona() {
            return persona;
        }
        public void setPersona(Semaphore persona) {
            this.persona = persona;
        }
        public Queue<ThreadPersona> getColaThreadPersona() {
            return colaThreadPersona;
        }
        public void setColaThreadPersona(Queue<ThreadPersona> colaThreadPersona) {
            this.colaThreadPersona = colaThreadPersona;
        }
    }

    Controll controll = new Controll();

    public class ThreadPersona implements Runnable {
        private byte id = 0;

        public ThreadPersona(byte id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println("La persona:  " + id + "entra a la sala de espera de la consulta.");

            if(controll.colaThreadPersona.size()>= ISILLAS){
                System.out.println("NO HAY SILLAS DISPONIBLES. \nLa persona " + id + " se marcho.");
            }else{
                try {
                    controll.silla.acquire();
                    System.out.println("La persona: " + id + " se sienta en la sala de espera.");
                    controll.dentista.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                controll.colaThreadPersona.add(this);
                controll.persona.release();
            }
        }
    }

    public class ThreadDentista implements Runnable {
        @Override
        public void run() {

            System.out.println("El dentista esta disponible.");

            while (true) {
                if(controll.colaThreadPersona.size() == 0){
                    try {
                        controll.dentista.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    int bPersonaLiberada = controll.colaThreadPersona.poll().id;
                    Thread.interrupted();
                    System.out.println("La persona: " + bPersonaLiberada + " ha llamado al dentista.");
                    controll.silla.release();
                    try {
                        controll.silla.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("La persona: " + bPersonaLiberada + "esta siendo atendido.");
                    System.out.println("La persona deja su silla");

                    byte bDormir = (byte) (Math.random() * 10);
                    try {
                        Thread.sleep(bDormir * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("La persona: " + bPersonaLiberada + " se ha ido. \n El dentista está libre.");
                }
            }
        }
    }
    private void executeMultiThreading() {

        new Thread(new ThreadDentista()).start();

        while (true) {
            try {
                Thread.sleep(3000);
                new Thread(new ThreadPersona(bPersonas)).start();
                bPersonas++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        Principal principal = new Principal();

            principal.executeMultiThreading();
}}
