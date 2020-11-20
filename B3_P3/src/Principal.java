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
                            bNum1 = (byte) (Math.random() * 3);
                            bNum2 = (byte) (Math.random() * 3);
                        }while (bNum1 == bNum2);

                        if (bNum1 == controll.TABACO && bNum2 == controll.CERILLA){

                            sResultado = "TABACO Y CERILLA";
                            controll.Tabaco.release();
                            controll.Cerilla.release();
                        }else if (bNum1 == controll.TABACO && bNum2 == controll.PAPEL){

                            sResultado = "TABACO Y PAPEL";
                            controll.Tabaco.release();
                            controll.Papel.release();
                        }else if (bNum1 == controll.CERILLA && bNum2 == controll.PAPEL){

                            sResultado = "CERILLA Y PAPEL";
                            controll.Papel.release();
                            controll.Cerilla.release();
                        }else if (bNum1 == controll.CERILLA && bNum2 == controll.TABACO){

                            sResultado = "CERILLA Y TABACO";
                            controll.Tabaco.release();
                            controll.Cerilla.release();
                        }else if (bNum1 == controll.PAPEL && bNum2 == controll.TABACO){

                            sResultado = "PAPEL Y TABACO";
                            controll.Papel.release();
                            controll.Tabaco.release();
                        }else if (bNum1 ==controll.PAPEL && bNum2 == controll.CERILLA){

                            sResultado = "PAPEL Y CERILLA";
                            controll.Papel.release();
                            controll.Cerilla.release();
                        }

                        System.out.println("La mesa se ha rellenado con: " + sResultado);



                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }





            }while (true);






        }
    }















}