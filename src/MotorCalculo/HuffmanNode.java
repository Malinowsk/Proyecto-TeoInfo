package MotorCalculo;

import java.util.Comparator;

public class HuffmanNode {
    int data;
    double c;
    HuffmanNode left;
    HuffmanNode right;
}

class MyComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y) {
        return x.data - y.data;
    }
}
