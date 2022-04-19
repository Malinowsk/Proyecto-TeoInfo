package MotorCalculo;

import java.util.List;

public class MotorCalculo {

    public static double[][] calcularMatrizPasajes(List<Double> scaneo) {

        int cantidadSimbolos = 0;
        int ultimoEstado = 0; // sube, mantiene, baja    // estado inicial sube
        double[][] probabilidadActual = {{0,0,0},{0,0,0},{0,0,0}};// inicializo en 0's
        double[][] exitos = {{0,0,0},{0,0,0},{0,0,0}};
        double[] sumaColumnas = {0,0,0};  // sube, mantiene, baja

        double s1 = scaneo.get(cantidadSimbolos);
        cantidadSimbolos++;
        double s2 = scaneo.get(cantidadSimbolos);

        while (cantidadSimbolos < scaneo.size()) {

            if (s1 < s2) {  // sube
                exitos[0][ultimoEstado]++;
                sumaColumnas[0]++;
                ultimoEstado = 0;
            }

            if (s1 == s2) {  // mantiene
                exitos[1][ultimoEstado]++;
                sumaColumnas[1]++;
                ultimoEstado = 1;
            }

            if (s1 > s2) {  // baja
                exitos[2][ultimoEstado]++;
                sumaColumnas[2]++;
                ultimoEstado = 2;
            }

            cantidadSimbolos++;

            s1 = s2;

            // lee el siguiente simbolo
            if (cantidadSimbolos < scaneo.size()) {
                s2 = scaneo.get(cantidadSimbolos);
            }

            // divide cada elemento de la columna por la suma de la columna
            for (int fila = 0; fila < probabilidadActual.length; fila++) {
                for (int columna = 0; columna < probabilidadActual.length; columna++) {
                    probabilidadActual[fila][columna] = exitos[fila][columna] / sumaColumnas[columna];
                }
            }
        }
        return probabilidadActual;
    }

    public static double[] calcularVectorAutocorelacion(List<Double> scaneo) {

        double[] autocorrelacion = new double[51];  // tau de 0 a 50

        double suma;
        double contador;

        for(int tau = 0; tau < autocorrelacion.length; tau++) {
            suma = 0;
            contador = 0;
            for(int j = 0; j < scaneo.size() - tau; j++) {
                suma = suma + (scaneo.get(j) * scaneo.get(j + tau));
                contador++;
            }
            autocorrelacion[tau] = suma/contador;
        }
        return autocorrelacion;
    }

    public static double[] calcularCorrelacionCruzada(List<Double> scaneo , List<Double> otroScaneo) {

        double[] correlacionCruzada = new double[5];  // tau [0]0, [1]50, [2]100, [3]150, [4]200.
        int[] taus = {0, 50, 100, 150, 200};

        double suma;
        double contador;

        for(int tau = 0; tau < correlacionCruzada.length; tau++) {
            suma = 0;
            contador = 0;
            for(int j = 0; j < scaneo.size() - taus[tau]; j++) {
                suma = suma + (scaneo.get(j) * otroScaneo.get(j + taus[tau]));
                contador++;
            }
            correlacionCruzada[tau] = suma/contador;
        }
        return correlacionCruzada;
    }

    // funcion que devulve un arreglo con la distribución de probabilidades de cada cotizacion
    public static double[] calcularDistribucionDeProbabilidades(List<Double> scaneo, double menorValor , double mayorValor){
        int rangoA = (int)menorValor;
        int rangoB = (int)mayorValor;
        double[] probabilidad = new double [rangoB-rangoA+1];
        int pruebas = 0;
        double s ;

        while (pruebas < scaneo.size()){
            s = scaneo.get(pruebas);    // extra nuevo simbolo de la fuente
            probabilidad[(int)s - rangoA]++;
            pruebas++;
        }
        // actualiza ambos arreglos
        for(int j=0 ; j < probabilidad.length; j++) {
            probabilidad[j] = (probabilidad[j] / scaneo.size());
        }
        return probabilidad;
    }


    public static double calcularMedia(List<Double> scaneo, int desde, int hasta) {    // incluye limites
        double suma = 0;
        for (int i = desde; i <= hasta; i++) {
            suma = suma + scaneo.get(i);
        }
        return (suma / (double)(hasta - desde + 1));
    }

    public static double calcularDesvio(List<Double> scaneo, int desde, int hasta) {
        double media = MotorCalculo.calcularMedia(scaneo, desde, hasta);
        double suma = 0;
        for (int i = desde; i <= hasta; i++) {
            suma = suma + Math.pow((scaneo.get(i) - media),2);
        }
        return Math.sqrt(suma/((double)(hasta-desde+1)));
    }

    public static double[] calcularCoeficienteDeCorrelacionCruzada(List<Double> scaneo , List<Double> otroScaneo) {
        double[] correlacionCruzada = MotorCalculo.calcularCorrelacionCruzada(scaneo,otroScaneo);
        double[] coeficienteDeCorrelacionCruzada = new double[correlacionCruzada.length];
        double productoDeLasMedias;
        double productoDeLosDesvios;
        int[] taus = {0, 50, 100, 150, 200};

        for (int tau = 0; tau < correlacionCruzada.length; tau++) {
            productoDeLasMedias =  ((MotorCalculo.calcularMedia(scaneo,0, scaneo.size()-taus[tau]-1)) * (MotorCalculo.calcularMedia(otroScaneo,taus[tau], otroScaneo.size()-1)));
            productoDeLosDesvios = ((MotorCalculo.calcularDesvio(scaneo,0,scaneo.size()-taus[tau]-1)) * (MotorCalculo.calcularDesvio(otroScaneo,taus[tau],otroScaneo.size()-1)));
            coeficienteDeCorrelacionCruzada[tau] = (correlacionCruzada[tau] - productoDeLasMedias) / productoDeLosDesvios;
        }
        return coeficienteDeCorrelacionCruzada;
    }

    public static double[] calcularCoeficienteDeAutocorrelacion(List<Double> scaneo) {
        double[] autocorrelacion = MotorCalculo.calcularVectorAutocorelacion(scaneo);

        double[] coeficienteDeAutocorrelacion = new double[autocorrelacion.length];   // 51 de 0 a 50
        double productoDeLasMedias;
        double productoDeLosDesvios;

        for (int tau = 0; tau < autocorrelacion.length; tau++) {
            productoDeLasMedias =  ((MotorCalculo.calcularMedia(scaneo,0, scaneo.size()-tau-1)) * (MotorCalculo.calcularMedia(scaneo,tau, scaneo.size()-1)));
            productoDeLosDesvios = ((MotorCalculo.calcularDesvio(scaneo,0,scaneo.size()-tau-1)) * (MotorCalculo.calcularDesvio(scaneo,tau,scaneo.size()-1)));
            coeficienteDeAutocorrelacion[tau] = (autocorrelacion[tau] - productoDeLasMedias) / productoDeLosDesvios;
        }
        return coeficienteDeAutocorrelacion;
    }

    public static double[][] calcularMatrizCanal(List<Double> senialUno, List<Double> senialDos, double[] probabilidadEntrada, double[] probabilidadSalida, int primeroEsEntrada) {
    // si entrada = 1 senialUno es entrada, caso contrario es salida
        double[] auxUno = {0.0,0.0,0.0};
        double[] auxDos = {0.0,0.0,0.0};

        double[][] canal = {{0, 0, 0},{0, 0, 0},{0, 0, 0}}; // sube, mantiene, baja
        // miro el dato de la fuente 1 y y comparo con lo que sucedio en la funete 2

        int cantidadSimbolosSenialuno = 0;
        int cantidadSimbolosSenialDos = 0;
        double[] sumaColumnas = {0,0,0};  // sube, mantiene, baja

        double s1 = senialUno.get(cantidadSimbolosSenialuno);
        cantidadSimbolosSenialuno++;
        double s2 = senialUno.get(cantidadSimbolosSenialuno);

        double s3 = senialDos.get(cantidadSimbolosSenialDos);
        cantidadSimbolosSenialDos++;
        double s4 = senialDos.get(cantidadSimbolosSenialDos);

        while ((cantidadSimbolosSenialuno < senialUno.size()) && (cantidadSimbolosSenialDos < senialDos.size())) {

            if (s1 < s2) {  // sube
                if (s3 < s4) {  // sube
                    canal[0][0]++;
                    auxDos[0]++;
                }

                if (s3 == s4) {  // mantiene
                    canal[1][0]++;
                    auxDos[1]++;
                }

                if (s3 > s4) {  // baja
                    canal[2][0]++;
                    auxDos[2]++;
                }
                sumaColumnas[0]++;
                auxUno[0]++;
            }

            if (s1 == s2) {  // mantiene
                if (s3 < s4) {  // sube
                    canal[0][1]++;
                    auxDos[0]++;
                }

                if (s3 == s4) {  // mantiene
                    canal[1][1]++;
                    auxDos[1]++;
                }

                if (s3 > s4) {  // baja
                    canal[2][1]++;
                    auxDos[2]++;
                }
                sumaColumnas[1]++;
                auxUno[1]++;
            }

            if (s1 > s2) {  // baja
                if (s3 < s4) {  // sube
                    canal[0][2]++;
                    auxDos[0]++;
                }

                if (s3 == s4) {  // mantiene
                    canal[1][2]++;
                    auxDos[1]++;
                }

                if (s3 > s4) {  // baja
                    canal[2][2]++;
                    auxDos[2]++;
                }
                sumaColumnas[2]++;
                auxUno[2]++;
            }

            if(primeroEsEntrada == 1) {
                for(int i = 0; i<probabilidadEntrada.length; i++) {
                    probabilidadEntrada[i] = auxUno[i] / (senialUno.size()-1);
                    probabilidadSalida[i] = auxDos[i] / (senialUno.size()-1);
                }
            } else {
                for(int i = 0; i<probabilidadEntrada.length; i++) {
                    probabilidadEntrada[i] = auxDos[i] / (senialUno.size()-1);
                    probabilidadSalida[i] = auxUno[i] / (senialUno.size()-1);
                }
            }

            cantidadSimbolosSenialuno++;
            cantidadSimbolosSenialDos++;

            s1 = s2;
            s3 = s4;

            // lee el siguiente simbolo
            if (cantidadSimbolosSenialuno < senialUno.size()) {
                s2 = senialUno.get(cantidadSimbolosSenialuno);
            }
            // lee el siguiente simbolo
            if (cantidadSimbolosSenialDos < senialDos.size()) {
                s4 = senialDos.get(cantidadSimbolosSenialDos);
            }
        }
        // divide cada elemento por la suma de la columna
        for (int fila = 0; fila < canal.length; fila++) {
            for (int columna = 0; columna < canal.length; columna++) {
                canal[fila][columna] = canal[fila][columna] / sumaColumnas[columna];
            }
        }
        return canal;
    }

    public static double calcularIndicadoresCanal(double[][] matriz, double[] probabilidades) {
        // para el ruido recibe matriz c(Y / x) y probabilidades de entrada X
        // para la perdida recibe matiz c(X / y) y probabildades de salida Y
        double retorno = 0.0;
        for(int columna =0; columna < probabilidades.length; columna++) {
            double sumatoriaInterna = 0.0;
            for(int fila = 0; fila < matriz.length; fila++) {
                sumatoriaInterna = sumatoriaInterna + (matriz[fila][columna] * (Math.log10(matriz[fila][columna])/Math.log10(2)));
            }
            retorno = retorno + probabilidades[columna] * (-sumatoriaInterna);
        }
        return retorno;
    }

}
