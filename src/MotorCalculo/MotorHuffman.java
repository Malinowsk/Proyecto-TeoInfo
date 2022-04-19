package MotorCalculo;
import java.util.*;


public class MotorHuffman {
    /*
    public List<Double> binaryToDoubleList(HuffmanNode root, char[] binarios) {
        List<Double> retorno = new ArrayList<>();
        HuffmanNode raiz = new HuffmanNode();   // no se que tan necesario es esto
        raiz.right = root.right;
        raiz.c = root.c;
        raiz.left = root.left;
        raiz.data = root.data;
        HuffmanNode aux;
        int i = 0;
        while (i < binarios.length) {
            aux = raiz;
            while (aux.left != null && aux.right != null) { // mientras no sea hoja
                // left = 0;   right = 1;
                if(binarios[i] == '0')
                    aux = aux.left;
                else
                    aux = aux.right;
                i++;
            }
            retorno.add(aux.c);
        }
        return retorno;
    }*/

    public static void obtenerDiccionario(HuffmanNode root, String s, HashMap<Double,String> diccionario) {
        if (root.left == null && root.right == null) {
            //System.out.println("Diccionario de:  " + root.c + ":" + s);
            diccionario.put(root.c, s);
            return;
        }
        obtenerDiccionario(root.left, s + "0", diccionario);
        obtenerDiccionario(root.right,s + "1", diccionario);
    }

    public static List<SimboloYfrecuencia> obtenerUnicosYFrecuencias(List<Double> simbolos) {
        List<SimboloYfrecuencia> unicos = new ArrayList<SimboloYfrecuencia>();

        SimboloYfrecuencia aux = new SimboloYfrecuencia();
        for (int i = 0; i < simbolos.size(); i++) { // por cada simbolo
            aux.simbolo= simbolos.get(i);
            if (!unicos.contains(aux)) { // Si no esta lo agrega
                SimboloYfrecuencia nodo = new SimboloYfrecuencia();
                nodo.simbolo = simbolos.get(i);
                nodo.frecuencia = 1;
                unicos.add(nodo);
            } else {        // miro el valor que tiene y le sumo 1
                int nuevaFrecuencia = unicos.get(unicos.indexOf(aux)).frecuencia + 1;
                unicos.remove(unicos.indexOf(aux));				// lo remuevo
                SimboloYfrecuencia nodo = new SimboloYfrecuencia();
                nodo.simbolo = simbolos.get(i);
                nodo.frecuencia = nuevaFrecuencia;
                unicos.add(nodo);		            // lo agrego con su nuevo valor
            }
        }
        return unicos;
    }

    public static HuffmanNode generarArbol(List<Double> simbolosArchivo) {
        HuffmanNode raiz = null;    // nodo raiz

        List<SimboloYfrecuencia> simbolosYfrecuencias = obtenerUnicosYFrecuencias(simbolosArchivo);
        int cantidadSimbolos = simbolosYfrecuencias.size();

        PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(cantidadSimbolos, new MyComparator());

        for (int i = 0; i < cantidadSimbolos; i++) {

            HuffmanNode hn = new HuffmanNode();

            hn.c = simbolosYfrecuencias.get(i).simbolo;
            hn.data = simbolosYfrecuencias.get(i).frecuencia;

            hn.left = null;
            hn.right = null;

            q.add(hn);
        }

        while (q.size() > 1) {
            HuffmanNode x = q.peek();
            q.poll();
            HuffmanNode y = q.peek();
            q.poll();

            HuffmanNode f = new HuffmanNode();
            f.data = x.data + y.data;
            f.c = '-';
            f.left = x;
            f.right = y;
            raiz = f;
            q.add(f);
        }
        return raiz;
    }

}