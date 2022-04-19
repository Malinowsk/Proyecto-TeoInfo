package MotorCalculo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Fuente {

    Scanner scann;
    private final List<Double> scaneo = new ArrayList<>();
    private double menorValor;
    private double mayorValor;
    private String nombre;

    public Fuente(Scanner scann, String nombre) {
        this.scann = scann;
        this.pasarArchivoEnArreglo();
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    private void pasarArchivoEnArreglo() {
        double menorValor = Double.MAX_VALUE;
        double mayorValor = 0;
        for(int i = 0; this.scann.hasNext(); i++) {
            this.scaneo.add(i, scann.nextDouble());
            if (scaneo.get(i) < menorValor){
                menorValor = scaneo.get(i);
            }
            if (scaneo.get(i) > mayorValor){
                mayorValor = scaneo.get(i);
            }
        }
        this.menorValor= menorValor;
        this.mayorValor= mayorValor;

    }

    public double getMenorValor() {
        return menorValor;
    }

    public double getMayorValor() {
        return mayorValor;
    }

    public List<Double> obtenerScaneo() {
        return Collections.unmodifiableList(scaneo);
    }
}
