package MotorCalculo;

public class SimboloYfrecuencia {
    public double simbolo;
    public int frecuencia;

    @Override
    public boolean equals(Object obj) {
        SimboloYfrecuencia other = (SimboloYfrecuencia) obj;
        if (this.simbolo == other.simbolo) // compara por simbolo
            return true;
        return false;
    }
}