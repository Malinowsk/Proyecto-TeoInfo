package MotorCalculo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ByteEncoding {
    private static int bufferLength = 8;


    private static List<Byte> encodeSequence(char[] sequence) {
        List<Byte> result = new ArrayList<Byte>();

        byte buffer = 0;
        int bufferPos = 0;

        int i = 0;
        while(i < sequence.length) {
            // la operacion de corrimiento pone un 0
            buffer = (byte) (buffer << 1);
            bufferPos++;
            if(sequence[i] == '1') {
                buffer = (byte) (buffer | 1);
            }

            if(bufferPos == bufferLength) {
                result.add(buffer);
                buffer = 0;
                bufferPos = 0;
            }

            i++;
        }

        if((bufferPos < bufferLength) && (bufferPos != 0)) {
            buffer = (byte)(buffer << (bufferLength - bufferPos));
            result.add(buffer);
        }
        return result;
    }

    public static String guardarHuffmanEnArchivoBinario(char[] datos, List<Double> scaneo, String rutaDestino) {
        String retorno = "\n" + "--------- Secuencia a guardar en binarios (sin diccionario): -----------------------" + "\n";
        char[] originalSequence = datos;

        int cantidadSimbolosALeer = scaneo.size();   // primer entero del archivo es la cantidad de datos a leer
        List<SimboloYfrecuencia> simbolosYfrecuencias = MotorHuffman.obtenerUnicosYFrecuencias(scaneo);
        retorno += cantidadSimbolosALeer + " + " + simbolosYfrecuencias.size() + " + Aca cada simbolo con su frecuencia + " + "\n";

        for (int i = 0; i < originalSequence.length; i++)
            retorno += originalSequence[i];
        retorno += "\n";

        try {
            List<Byte> encodedSequence = encodeSequence(originalSequence);
            byte[] byteArray = ConvertByteListToPrimitives(encodedSequence);

            FileOutputStream fos = new FileOutputStream(rutaDestino);
            // genero cabecera con enteros
            byte[] bytesParaEscribir = ByteBuffer.allocate(4).putInt(cantidadSimbolosALeer).array();
            fos.write(bytesParaEscribir); // guardo como primer int la cantidad de datos a leer luego

            bytesParaEscribir = ByteBuffer.allocate(4).putInt(simbolosYfrecuencias.size()).array();
            fos.write(bytesParaEscribir); // escribo la cantidad de simbolos unicos
            for (SimboloYfrecuencia dato: simbolosYfrecuencias) {   // escribo todos los simbolos y frecuencias
                // simbolo
                bytesParaEscribir = ByteBuffer.allocate(4).putInt((int)dato.simbolo).array();
                fos.write(bytesParaEscribir);
                fos.write((byte)dato.frecuencia);
            }

            // escribe binarios
            fos.write(byteArray);

            // cierra archivo
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return retorno + "\n";
    }

    public static String guardarRLCconFlagEnArchivoBinario(char[] datos, int longitudBinario, int longitudRepeticiones, String rutaDestino) {
        String retorno = "\n" + "--------- Secuencia a guardar en binarios (rlc con flag): -----------------------" + "\n";
        for (int i = 0; i < datos.length; i++)
            retorno += datos[i];
        retorno += "\n";

        try {
            List<Byte> encodedSequence = encodeSequence(datos);

            FileOutputStream fos = new FileOutputStream(rutaDestino);
            // genero cabecera
            fos.write((byte)longitudBinario);       // cantidad de bits que ocupa cada simbolo
            fos.write((byte)longitudRepeticiones);  // cantidad de bits que ocupa cada repeticion

            byte[] byteArray = ConvertByteListToPrimitives(encodedSequence);
            // escribe binarios
            fos.write(byteArray);

            // cierra archivo
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return retorno + "\n";
    }

    public static void printSequence(char[] sequence) {
        for (int i = 0; i < sequence.length; i++) {
            System.out.print(sequence[i]);
        }
    }

    private static byte[] ConvertByteListToPrimitives(List<Byte> input) {
        byte[] retorno = new byte[input.size()];
        for (int i = 0; i < retorno.length; i++) {
            retorno[i] = input.get(i);
        }
        return retorno;
    }

    public static char[] listDoubleToArrayCharBits(List<Double> lista, HashMap<Double,String> diccionario) {
        String codigoEnString = "";
        for (Double dato: lista) { //agrego todos los elementos pegaditos
            codigoEnString = codigoEnString + diccionario.get(dato);
        }
        return codigoEnString.toCharArray();
    }

    public static char[] listDoubleToArrayCharBinariosConFlag(List<Double> lista, int longitudBinario, int longitudRepeticiones) {
        String codigoEnString = "";
        for (int i = 0; i < lista.size(); i++) {
            int count = 1;
            while ( (i < lista.size() - 1) && (lista.get(i).equals(lista.get(i + 1))) ) {
                count++;
                i++;
            }

            String binario = Integer.toBinaryString(lista.get(i).intValue());
            int cantDeCerosAgregarBinario = longitudBinario - binario.length();
            if(count == 1) {
                codigoEnString = codigoEnString + "0";  // flag en 0 no usa RLC
                for(int j = 0; j < cantDeCerosAgregarBinario; j++) { // relleno con 0's
                    codigoEnString = codigoEnString + "0";
                }
                codigoEnString = codigoEnString + binario; // concateno simbolo en binario
            } else {
                codigoEnString = codigoEnString + "1"; // flag en 1 usa RLC
                for(int j = 0; j < cantDeCerosAgregarBinario; j++) { // relleno con 0's
                    codigoEnString = codigoEnString + "0";
                }
                codigoEnString = codigoEnString + binario; // concateno simbolo en binario
                String repeticiones = Integer.toBinaryString(count);
                int cantDeCerosAgregarRepeticiones = longitudRepeticiones - repeticiones.length();
                for(int j = 0; j < cantDeCerosAgregarRepeticiones; j++) {   // relleno con 0's
                    codigoEnString = codigoEnString + "0";
                }
                codigoEnString = codigoEnString + repeticiones; // agrego repeticiones
            }

        }
        return codigoEnString.toCharArray();
    }

    public static int longitudDeBinario(List<Double> lista) {
        int longitudMaxima = 0;
        for (Double dato: lista) {
            String binario = Integer.toBinaryString(dato.intValue()); //convierto en binario
            if(binario.length() > longitudMaxima) {
                longitudMaxima = binario.length();
            }
        }
        return longitudMaxima;
    }

    private static char[] decodeSequence(char[] datos, String rutaDestino) {
        char[] restoredSequence = new char[datos.length];

        try {
            byte[] inputSequence = Files.readAllBytes(new File(rutaDestino).toPath());
            int globalIndex = 0;
            byte mask = (byte) (1 << (bufferLength -1));
            int bufferPos = 0;
            int i = 0;
            while (globalIndex < datos.length) {
                byte buffer = inputSequence[i];
                while(bufferPos < bufferLength) {
                    if((buffer & mask) == mask) {
                        restoredSequence[globalIndex] = '1';
                    } else {
                        restoredSequence[globalIndex] = '0';
                    }

                    buffer = (byte) (buffer << 1);
                    bufferPos++;
                    globalIndex++;

                    if(globalIndex == datos.length) {
                        break;
                    }
                }
                i++;
                bufferPos = 0;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return restoredSequence;
    }
}
