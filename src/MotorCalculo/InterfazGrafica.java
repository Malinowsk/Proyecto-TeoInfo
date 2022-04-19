package MotorCalculo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class InterfazGrafica {

    private static final String separador = "------------------------------------------------------------------------------------";
    private static String reporte = "";

    public static void imprimirInterfazGrafica() {
        System.out.println(" ______________________________Que desea hacer?_____________________________");
        System.out.println("|                                                                           |");
        System.out.println("|   1. Ver matrices de pasajes de BTC y ETH                                 |");
        System.out.println("|   2. Mostrar Autocorrelacion de BTC y ETH con sus coeficientes            |");
        System.out.println("|   3. Mostrar Correlacion Cruzada de BTC y ETH o viceversa                 |");
        System.out.println("|   4. Mostrar indicadores (desvio, y media)                                |");
        System.out.println("|   5. Calcular distribucion de probabilidades                              |");
        System.out.println("|   6. Codificar las cotizaciones en archivo binario BTC (HuffmanBTC.bin)   |");
        System.out.println("|   7. Codificar las cotizaciones en archivo binario ETH (HuffmanETH.bin)   |");
        System.out.println("|   8. Codificar las cotizaciones de BTC usando RLC en (RLCBTC.txt)         |");
        System.out.println("|   9. Codificar las cotizaciones de ETH usando RLC en (RLCETH.txt)         |");
        System.out.println("|  10. Codificar las cotizaciones de BTC usando RLC con Flag (RLCmBTC.txt)  |");
        System.out.println("|  11. Codificar las cotizaciones de ETH usando RLC con Flag (RLCmETH.txt)  |");
        System.out.println("|  12. Calcular entropia                                                    |");
        System.out.println("|  13. Calcular canal                                                       |");
        System.out.println("|  14. Calcular Indicadores de Canal                                        |");
        System.out.println("|  15. Salir y generar reporte en archivo reporteEjecucion.txt              |");
        System.out.println("|___________________________________________________________________________|");
        System.out.println("Ingrese una opcion: ");
    }

    public static void ejecutarInterfazGrafica(Scanner sn, Fuente BTC, Fuente ETH){
        boolean salir = false;
        int opcion;

        while(!salir) {
            InterfazGrafica.imprimirInterfazGrafica();
            opcion = sn.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Has seleccionado la opcion 1");
                    System.out.println(verMatrizPasajes(BTC));
                    System.out.println(verMatrizPasajes(ETH));
                    break;
                case 2:
                    System.out.println("Has seleccionado la opcion 2");
                    System.out.println(verAutocorrelacion(BTC, ETH));
                    break;
                case 3:
                    System.out.println("Has seleccionado la opcion 3");
                    System.out.println(verCorrelacionCruzada(BTC, ETH));
                    System.out.println(verCorrelacionCruzada(ETH, BTC));
                    break;
                case 4:
                    System.out.println("Has seleccionado la opcion 4");
                    System.out.println(verIndicadores(BTC));
                    System.out.println(verIndicadores(ETH));
                    break;
                case 5:
                    System.out.println("Has seleccionado la opcion 5");
                    System.out.println(verDistribucionDeProbabilidades(BTC));
                    System.out.println(verDistribucionDeProbabilidades(ETH));
                    break;
                case 6:
                    System.out.println("Has seleccionado la opcion 6");
                    System.out.println(codificarCotizacionesConHuffmanABinario(BTC));
                    break;
                case 7:
                    System.out.println("Has seleccionado la opcion 7");
                    System.out.println(codificarCotizacionesConHuffmanABinario(ETH));
                    break;
                case 8:
                    System.out.println("Has seleccionado la opcion 8");
                    System.out.println(codificarCotizacionesConRLC(BTC));
                    break;
                case 9:
                    System.out.println("Has seleccionado la opcion 9");
                    System.out.println(codificarCotizacionesConRLC(ETH));
                    break;
                case 10:
                    System.out.println("Has seleccionado la opcion 10");
                    System.out.println(codificarCotizacionesConRLCMejorado(BTC));
                    break;
                case 11:
                    System.out.println("Has seleccionado la opcion 11");
                    System.out.println(codificarCotizacionesConRLCMejorado(ETH));
                    break;
                case 12:
                    System.out.println("Has seleccionado la opcion 12");
                    System.out.println(compararEntropiaConLongitudHuffan(BTC));
                    System.out.println(compararEntropiaConLongitudHuffan(ETH));
                    break;
                case 13:
                    System.out.println("Has seleccionado la opcion 13");
                    System.out.println(calcularCanal(BTC, ETH));
                    break;
                case 14:
                    System.out.println("Has seleccionado la opcion 14");
                    System.out.println(calcularRuidoPerdidaDeCanal(BTC, ETH));
                    break;
                case 15:
                    salir = true;
                    guardarReporte(reporte);
                    System.out.println("Puede acceder al reporte de la ejecucion ubicado en la carpeta archivos.");
                    System.out.println("Hasta luego, que tenga un buen dia...");
                    break;
                default:
                    System.out.println("Solo números entre 1 y 15");
            }
        }
    }


    // Opcion 1
    private static String verMatrizPasajes(Fuente fuente) {
        // EJERCICIO 1a matriz de pasajes
        String retorno = "\n" + separador + "\n";
        String[] estado = {"  sube    ","mantiene  ","  baja    "};
        retorno += "Matriz de pasaje de " + fuente.getNombre() + "\n";
        retorno += "                 sube               mantiene            baja" + "\n";
        double[][] matrizPasajesBTC = MotorCalculo.calcularMatrizPasajes(fuente.obtenerScaneo());
        for(int fila = 0; fila < matrizPasajesBTC.length; fila++) {
            retorno += estado[fila];
            for (int columna = 0; columna < matrizPasajesBTC.length; columna++) {
                retorno += matrizPasajesBTC[fila][columna] + "  ";
            }
            retorno += "\n";
        }
        reporte += retorno + separador + "\n";
        return retorno + separador + "\n";
    }
    // Opcion 2
    private static String verAutocorrelacion(Fuente BTC, Fuente ETH) {
        // EJERCICIO 1b autocorrelacion de BTC y ETH
        String retorno = "\n" + separador + "\n";
        double[] autocorrelacionBTC = MotorCalculo.calcularVectorAutocorelacion(BTC.obtenerScaneo());
        double[] autocorrelacionETH = MotorCalculo.calcularVectorAutocorelacion(ETH.obtenerScaneo());

        double[] coeficienteDeAutocorrelacionBTC = MotorCalculo.calcularCoeficienteDeAutocorrelacion(BTC.obtenerScaneo());
        double[] coeficienteDeAutocorrelacionETH = MotorCalculo.calcularCoeficienteDeAutocorrelacion(ETH.obtenerScaneo());

        for (int i = 0; i < autocorrelacionBTC.length; i++) {
            retorno += "Autocorrelacion con Tau = " + i + "  BTC: " + autocorrelacionBTC[i] + "  (coefBTC: " + coeficienteDeAutocorrelacionBTC[i] + " )  " + "  ETH: " + autocorrelacionETH[i] + "  (coefETH: " + coeficienteDeAutocorrelacionETH[i] + " )  " + "\n";
        }
        reporte += retorno + separador + "\n";
        return retorno + separador + "\n";
    }
    // Opcion 3
    private static String verCorrelacionCruzada(Fuente una, Fuente otra) {
        // EJERCICIO 1c correlacion cruzada
        String retorno = "\n" + separador + "\n";
        retorno += "----Correlacion cruzada de: " + una.getNombre() + " con " + otra.getNombre() + " -----" + "\n";
        double[] correlacionCruzada = MotorCalculo.calcularCorrelacionCruzada(una.obtenerScaneo() , otra.obtenerScaneo());
        double[] coeficienteCorrelacionCruzada = MotorCalculo.calcularCoeficienteDeCorrelacionCruzada(una.obtenerScaneo() , otra.obtenerScaneo());
        // tau [0]0, [1]50, [2]100, [3]150, [4]200.
        retorno += "Correlacion cruzada con Tau = 0: " + correlacionCruzada[0] + "  (coeficiente: " + coeficienteCorrelacionCruzada[0] + " )  " + "\n";
        retorno += "Correlacion cruzada con Tau = 50: " + correlacionCruzada[1] + "  (coeficiente: " + coeficienteCorrelacionCruzada[1] + " )  " + "\n";
        retorno += "Correlacion cruzada con Tau = 100: " + correlacionCruzada[2] + "  (coeficiente: " + coeficienteCorrelacionCruzada[2] + " )  " + "\n";
        retorno += "Correlacion cruzada con Tau = 150: " + correlacionCruzada[3] + "  (coeficiente: " + coeficienteCorrelacionCruzada[3] + " )  " + "\n";
        retorno += "Correlacion cruzada con Tau = 200: " + correlacionCruzada[4] + "  (coeficiente: " + coeficienteCorrelacionCruzada[4] + " )  " + "\n";
        reporte += retorno + separador + "\n";
        return retorno + separador + "\n";
    }
    // Opcion 4
    private static String verIndicadores(Fuente fuente) {
        // Indicadores, Desvio y Media
        String retorno = "\n" + separador + "\n";
        retorno += "----Indicadores de cada fuente----" + "\n";
        retorno += "Desvio de " + fuente.getNombre() + ":  " + MotorCalculo.calcularDesvio(fuente.obtenerScaneo(), 0,fuente.obtenerScaneo().size()-1)+ "\n";
        retorno += "Media de " + fuente.getNombre() + ":  " + MotorCalculo.calcularMedia(fuente.obtenerScaneo(), 0,fuente.obtenerScaneo().size()-1)+ "\n";
        reporte += retorno + separador + "\n";
        return retorno + separador + "\n";
    }
    // Opcion 5
    private static String verDistribucionDeProbabilidades(Fuente fuente) {
        // EJERCICIO 2a distribucion de probabilidades
        String retorno = "\n" + separador + "\n";
        retorno += "----Distribucion de probabilidades de: " + fuente.getNombre() + "----" + "\n";
        double[] distribucionProbabilidades = MotorCalculo.calcularDistribucionDeProbabilidades(fuente.obtenerScaneo(), fuente.getMenorValor() ,fuente.getMayorValor());
        for (int j = 0 ; j < distribucionProbabilidades.length ;j++){
            double aux = distribucionProbabilidades[j];
            if (aux != 0.0)
                retorno += "El valor de "+ fuente.getNombre() +": " + (j+(int)fuente.getMenorValor()) + " tiene probabilidad: " + aux + "\n";
        }
        reporte += retorno + separador + "\n";
        return retorno + separador + "\n";
    }
    // Opcion 6 y 7
    private static String codificarCotizacionesConHuffmanABinario(Fuente fuente) {

        String retorno = "\n" + separador + "\n";
        retorno += "----------Huffman a cotizaciones de " + fuente.getNombre() + " --------------------------------------------" + "\n";

        // calculo el arbol de huffman y obtengo la raiz del mismo
        HuffmanNode raizArbolHuffman = MotorHuffman.generarArbol(fuente.obtenerScaneo());

        // creo un diccionario <sibolo:stringBits> y lo cargo a partir del arbol generado anteriormente
        HashMap<Double,String> diccionario = new HashMap<>();
        MotorHuffman.obtenerDiccionario(raizArbolHuffman, "", diccionario);
        char[] datosEnCharArray = ByteEncoding.listDoubleToArrayCharBits(fuente.obtenerScaneo(), diccionario);

        retorno += ByteEncoding.guardarHuffmanEnArchivoBinario(datosEnCharArray, fuente.obtenerScaneo(),"Huffman" + fuente.getNombre() + ".bin" ) + "\n";
        reporte += retorno + separador + "\n";
        return retorno + separador + "\n";
    }
    // Opcion 8 y 9
    private static String codificarCotizacionesConRLC(Fuente fuente) {
        String retorno = "\n" + separador + "\n";
        retorno += "----------------- RLC de cotizaciones de " + fuente.getNombre() + " ---------------------------------------" + "\n";
        RLC.guardarEnArchivo(fuente.obtenerScaneo(), "RLC" + fuente.getNombre() + ".bin");
        reporte += retorno + separador + "\n";
        return retorno + separador + "\n";
    }
    // Opcion 10 y 11
    private static String codificarCotizacionesConRLCMejorado(Fuente fuente) {
        String retorno = "\n" + separador + "\n";
        retorno += "----------RLC mejorado con Flag de " + fuente.getNombre() + " ---------------------------------------" + "\n";
        int longitud = ByteEncoding.longitudDeBinario(fuente.obtenerScaneo());
        retorno += "LONGITUD MAXIMA: " + longitud + "\n";

        char[] datosEnCharArray = ByteEncoding.listDoubleToArrayCharBinariosConFlag(fuente.obtenerScaneo(), longitud, 8); // 8 bits para repeticiones, max 255

        retorno += ByteEncoding.guardarRLCconFlagEnArchivoBinario(datosEnCharArray, longitud, 8,"RLCm" + fuente.getNombre() + ".bin" ) + "\n";
        reporte += retorno + separador + "\n";
        return retorno + separador + "\n";
    }
    // Opcion 12
    private static String compararEntropiaConLongitudHuffan(Fuente fuente) {
        String retorno = "\n" + separador + "\n";

        // calculo el arbol de huffman y obtengo la raiz del mismo
        HuffmanNode raizArbolHuffman = MotorHuffman.generarArbol(fuente.obtenerScaneo());
        // creo un diccionario <sibolo:stringBits> y lo cargo a partir del arbol generado anteriormente
        HashMap<Double,String> diccionario = new HashMap<>();
        MotorHuffman.obtenerDiccionario(raizArbolHuffman, "", diccionario);
        double[] distribucionProb = MotorCalculo.calcularDistribucionDeProbabilidades(fuente.obtenerScaneo(),fuente.getMenorValor(),fuente.getMayorValor());

        double longitudPromedio = 0;
        double entropia = 0.0;

        for (int i = 0 ; i < (int)(fuente.getMayorValor()-fuente.getMenorValor()+1); i++) {
            if (distribucionProb[i] != 0) {
                entropia = entropia + (distribucionProb[i] * (Math.log10(distribucionProb[i]) / Math.log10(2)));
                longitudPromedio = longitudPromedio + (distribucionProb[i] * diccionario.get(i + fuente.getMenorValor()).length());
            }
        }
        retorno += "---- Entropia de " + fuente.getNombre() + " es: " + (-entropia) + " ---" + "\n";
        retorno += "---- Longitud Promedio de Huffman: " + longitudPromedio + " ---" + "\n";

        reporte += retorno + separador + "\n";
        return retorno + separador + "\n";
    }
    // Opcion 13
    private static String calcularCanal(Fuente fuenteUno, Fuente fuenteDos) {
        String retorno = "\n" + separador + "\n";
        retorno += "-- C( Y / X )----------------------CANAL BTC --> ETH: ------------------------------" + "\n";

        double[] probabilidadEntrada = {0.0,0.0,0.0};   // sube mantiene baja
        double[] probabilidadSalida = {0.0,0.0,0.0};   // sube mantiene baja

        double[][] canal = MotorCalculo.calcularMatrizCanal(fuenteUno.obtenerScaneo(), fuenteDos.obtenerScaneo(), probabilidadEntrada, probabilidadSalida, 1); // 1 ya que la fuente 1 es entrada
        String[] estado = {"  sube    ","mantiene  ","  baja    "};
        retorno += "Matriz de pasaje de canal " + "\n";
        retorno += "                 X=sube              X=mantiene           X=baja" + "\n";
        for(int fila = 0; fila < canal.length; fila++) {
            retorno += "Y="  + estado[fila];
            for (int columna = 0; columna < canal.length; columna++) {
                retorno += canal[fila][columna] + "  ";
            }
            retorno += "\n";
        }
        for(int i = 0; i < probabilidadEntrada.length; i++) {
            retorno += "P(X = " + estado[i] + " ) = " + probabilidadEntrada[i] + "\n";
            retorno += "P(Y = " + estado[i] + " ) = " + probabilidadSalida[i] + "\n";
        }
        reporte += retorno + separador + "\n";
        return retorno + separador + "\n";
    }
    // Opcion 14
    private static String calcularRuidoPerdidaDeCanal(Fuente senialUno, Fuente senialDos) {
        String retorno = "\n" + separador + "\n";

        double[] probabilidadEntrada = {0.0,0.0,0.0};   // sube mantiene baja
        double[] probabilidadSalida = {0.0,0.0,0.0};   // sube mantiene baja

        // cYx = C(Y / X) // RUIDO
        double[][] cYx = MotorCalculo.calcularMatrizCanal(senialUno.obtenerScaneo(), senialDos.obtenerScaneo(), probabilidadEntrada, probabilidadSalida, 1); //BTC es entrada

        double ruido = MotorCalculo.calcularIndicadoresCanal(cYx, probabilidadEntrada);

        // cXy = C(X / Y) // PERDIDA
        double[][] cXy = MotorCalculo.calcularMatrizCanal(senialDos.obtenerScaneo(), senialUno.obtenerScaneo(), probabilidadEntrada, probabilidadSalida, 0);

        double perdida = MotorCalculo.calcularIndicadoresCanal(cXy, probabilidadSalida);
        retorno += "Ruido Canal: " + ruido + "\n";
        retorno += "Perdida Canal: " + perdida + "\n";

        /////////////////// Calculo de Informacion mutua de dos maneras para verificacion
        double[] dHx = {0.5415415415415415, 0.021021021021021023, 0.43743743743743746};

        double[] dHy = {0.3813813813813814, 0.30430430430430433, 0.31431431431431434};

        double entropiaX = dHx[0] * (Math.log10(dHx[0]) / Math.log10(2)) + dHx[1] * (Math.log10(dHx[1]) / Math.log10(2)) + dHx[2] * (Math.log10(dHx[2]) / Math.log10(2));

        double entropiaY = dHy[0] * (Math.log10(dHy[0]) / Math.log10(2)) + dHy[1] * (Math.log10(dHy[1]) / Math.log10(2)) + dHy[2] * (Math.log10(dHy[2]) / Math.log10(2));

        double informacionMutua1 = (-entropiaX) - perdida;
        double informacionMutua2 = (-entropiaY) - ruido;

        retorno += "Informacion Mutua1: " + informacionMutua1 + "\n";
        retorno += "Informacion Mutua2: " + informacionMutua2 + "\n";

        reporte += retorno + separador + "\n";
        return retorno + separador + "\n";
    }

    public static void guardarReporte(String cadena) {
        String time = "----------------------------- Reporte " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " --------------------------";
        String separador = "------------------------------------------------------------------------------------";
        String texto = time + "\n" + separador + "\n" + cadena + "\n" + separador + "\n" ;
        BufferedWriter bw = null;
        try {
            //Crear un objeto BufferedWriter. Si ya existe el fichero,
            //  se borra automáticamente su contenido anterior.
            //Escrbir en el fichero el texto con un salto de línea
            bw = new BufferedWriter(new FileWriter("reporteEjecucion"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss"))+".txt"));
            bw.write(texto);
        }
        catch(Exception e) {
            System.out.println("Error de escritura del fichero");
            System.out.println(e.getMessage());
        }
        finally {
            try {
                //Cerrar el buffer
                if(bw != null)
                    bw.close();
            }
            catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }
    }
}
