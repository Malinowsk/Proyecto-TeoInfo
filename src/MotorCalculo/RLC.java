package MotorCalculo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

public class RLC {
    // funcion aplica algoritmo RLC a listaSimbolos y lo guarda en rutaDestino
    public static void guardarEnArchivo(List<Double> listaSimbolos, String rutaDestino) {
        try {
            FileOutputStream fos = new FileOutputStream(rutaDestino);
            int n = listaSimbolos.size();
            for (int i = 0; i < n; i++) {
                int count = 1;
                while ( (i < n - 1) && (listaSimbolos.get(i).equals(listaSimbolos.get(i + 1))) ) {
                    count++;
                    i++;
                }
                byte[] bytesParaEscribir = ByteBuffer.allocate(4).putInt(listaSimbolos.get(i).intValue()).array();
                fos.write(bytesParaEscribir); // escribo int con simbolo
                fos.write((byte)count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
