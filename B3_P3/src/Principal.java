import java.util.concurrent.Semaphore;

public class Principal {

    public class Controll{
        private final byte TABACO = 1;
        private final byte PAPEL = 2;
        private final byte CERILLA = 3;


        private Semaphore Tabaco = new Semaphore(0);
        private Semaphore Papel = new Semaphore(0);
        private Semaphore Cerilla = new Semaphore(0);



    }

    public Controll controll = new Controll();

    public class Malboro implements Runnable{
        @Override
        public synchronized void run() {
            String sResultado = "";
            do{
                if (controll.Tabaco.availablePermits() == 0 && controll.Papel.availablePermits() == 0 && controll.Cerilla.availablePermits() == 0){
                    try {
                        Thread.sleep(150);
                        byte bNum1;
                        byte bNum2;
                        do {
                            bNum1 = (byte) (1 + Math.random() * 3);
                            bNum2 = (byte) (1 + Math.random() * 3);
                        }while (bNum1 == bNum2);

                        if (bNum1 == controll.TABACO && bNum2 == controll.CERILLA){

                            sResultado = "TABACO Y CERILLA";
                            controll.Tabaco.release();
                            controll.Cerilla.release();
                        }
                        if (bNum1 == controll.TABACO && bNum2 == controll.PAPEL){

                            sResultado = "TABACO Y PAPEL";
                            controll.Tabaco.release();
                            controll.Papel.release();
                        }
                        if (bNum1 == controll.CERILLA && bNum2 == controll.PAPEL){

                            sResultado = "CERILLA Y PAPEL";
                            controll.Papel.release();
                            controll.Cerilla.release();
                        }
                        if (bNum1 == controll.CERILLA && bNum2 == controll.TABACO){

                            sResultado = "CERILLA Y TABACO";
                            controll.Tabaco.release();
                            controll.Cerilla.release();
                        }
                        if (bNum1 == controll.PAPEL && bNum2 == controll.TABACO){

                            sResultado = "PAPEL Y TABACO";
                            controll.Papel.release();
                            controll.Tabaco.release();
                        }
                        if (bNum1 ==controll.PAPEL && bNum2 == controll.CERILLA){

                            sResultado = "PAPEL Y CERILLA";
                            controll.Papel.release();
                            controll.Cerilla.release();
                        }

                        System.out.println("La mesa se ha rellenado con: " + sResultado + ".");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }while (true);
        }
    }

    public class Alumno implements Runnable{
        int iID;

        public Alumno(int iID){
            this.iID = iID;
        }

        public int getiID() {
            return iID;
        }

        public void setiID(int iID) {
            this.iID = iID;
        }

        @Override
        public void run() {
            byte bCigarrosFumados = 0;

            int iPapelAlumno = (int) (Math.random() * 7);
            int iCerillaAlumno = (int) (Math.random() * 7);
            int iTabacoAlumno = (int) (Math.random() * 7);

            do {
                if (iPapelAlumno == 0){
                    if (controll.Papel.availablePermits() == 0){
                        try {
                            System.out.println("A el alumno: " + getiID() + " le faltan papeles");
                            controll.Papel.acquire();
                            iPapelAlumno ++;
                            System.out.println("El alumno: " + getiID() + " ha cogido un papelillo.");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (iCerillaAlumno == 0){
                    if (controll.Cerilla.availablePermits() == 0){
                        try {
                            System.out.println("A el alumno: " + getiID() + " le faltan cerillas");
                            controll.Cerilla.acquire();
                            System.out.println("El alumno: " + getiID() + " ha cogido una cerilla.");
                            iCerillaAlumno ++;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (iTabacoAlumno == 0){
                    if (controll.Tabaco.availablePermits() == 0){
                        try {
                            System.out.println("A el alumno: " + getiID() + " le falta tabaco.");

                            controll.Tabaco.acquire();
                            System.out.println("El alumno: " + getiID() + " ha cogido tabaco.");
                            iTabacoAlumno ++;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (iPapelAlumno > 0 && iCerillaAlumno > 0 && iTabacoAlumno > 0){
                    iPapelAlumno --;
                    iCerillaAlumno--;
                    iTabacoAlumno--;
                    bCigarrosFumados++;
                    System.out.println("El alumno lleva : " + bCigarrosFumados + " cigarros fumados.");
                }
            }while (bCigarrosFumados < 10);
        }
    }

    private void executeMultiThreading() {

        new Thread(new Malboro()).start();
        int iCont = 0;

        while (true) {
            try {
                Thread.sleep(3000);
                new Thread(new Alumno(iCont)).start();
                iCont++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        Principal principal = new Principal();

        principal.executeMultiThreading();
    }
}