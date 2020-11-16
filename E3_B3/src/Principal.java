import validaciones.ValidaLibrary;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Principal {


    public class Controll {

        private double n;
        private double k;
        private double x;
        private double i;

        private Semaphore S1 = new Semaphore();

        private Queue<P1> colaP1 = new LinkedList<P1>();


        public double getN() {
            return n;
        }

        public void setN(double n) {
            this.n = n;
        }

        public double getK() {
            return k;
        }

        public void setK(double k) {
            this.k = k;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getI() {
            return i;
        }

        public void setI(double i) {
            this.i = i;
        }

        public Double calculoFactorial(Double dNum){
            if(dNum == 0 || dNum == 1){
                return 1d;
            }else{

                return dNum * calculoFactorial(dNum -1);
            }
        }
        }

    Controll controll = new Controll();

    public class P1 implements Runnable {

        @Override
        public void run() {

            controll.setX(controll.calculoFactorial(controll.getN()));
            controll.S1.release();
        }
    }

    public class P2 implements Runnable {
        private Double dk;

        public Double getdk() {
            return dk;
        }

        public void setdk(Double dk) {
            this.dk = dk;
        }

        @Override
        public void run() {
            try {
                controll.S1.acquire();
                controll.setI(controll.calculoFactorial(controll.getK()) * controll.calculoFactorial(controll.getN() - controll.getK()));
                Double dCalculo = (controll.getX() / controll.getI());
                System.out.println("Resultado: " + dCalculo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void executeMultiThreading() throws InterruptedException {
        Double K;
        Double N;

        do {
            N = (Double) ValidaLibrary.valida("Introduzca un numero: ", 0, -1, 2);
            K = (Double) ValidaLibrary.valida("Introduzca un numero: ", 0, N, 2);

        }while (N < K);

        controll.setN(N);


        new Thread(new P1()).start();




        new Thread(new P2()).start();
        new Thread(new P2()).join();
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