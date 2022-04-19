import MotorCalculo.Fuente;
import MotorCalculo.InterfazGrafica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        File fileBTC = new File("./archivos/BTC.txt");
        File fileETH = new File("./archivos/ETH.txt");

        Fuente BTC;
        Fuente ETH;

        try {

            // BTC
            Scanner scannBTC = new Scanner(fileBTC);
            BTC = new Fuente(scannBTC, "BTC");

            // ETH
            Scanner scannETH = new Scanner(fileETH);
            ETH = new Fuente(scannETH, "ETH");


            // MENU
            Scanner sn = new Scanner(System.in);
            InterfazGrafica.ejecutarInterfazGrafica(sn, BTC, ETH);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
