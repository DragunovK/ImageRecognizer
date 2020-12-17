/*
 * Created by Kirill Dragunov on 16 dec. 2020 16:21:51
 */

package com.recognizer;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

public class NeuralNetwork {
    private SimpleMatrix W; //1st layer wts
    private SimpleMatrix V; //2nd layer wts

    private double T; //threshold
    private double maxAllowedError;

    /**
     * Constructor
     * @param referenceImageMatrix aka X -- M x N - dimensional
     * @param epsilon -- in range (0, 1/M)
     */
    public NeuralNetwork(SimpleMatrix referenceImageMatrix, double maxAllowedError, double epsilon) {
        int M = referenceImageMatrix.numRows();
        int N = referenceImageMatrix.numCols();

        W = referenceImageMatrix.transpose().scale(0.5);

        V = new SimpleMatrix(M, M);
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                if (i == j) V.set(i, j, 1);
                else V.set(i, j, -epsilon);
            }
        }

        T = N / 2.;
        this.maxAllowedError = maxAllowedError;

        System.out.println("e = " + epsilon);
    }

    public ArrayList<Integer> recognize(SimpleMatrix X) {
        SimpleMatrix Z = X.mult(W).plus(T);
        SimpleMatrix Z_next = Z;

        int iteration = 1;
        while (true) {
            Z = Z_next;
            Z_next = activate(Z.mult(V));

            double mse = (Z_next.minus(Z)).elementPower(2).elementSum();
            if (mse <= maxAllowedError)
                break;
            iteration++;
        }

        System.out.println("Iterations: " + iteration);
        System.out.println("Y: " + Z_next);

        return findWinners(Z_next);
    }

    public ArrayList<Integer> findWinners(SimpleMatrix matrix) {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < matrix.numCols(); i++) {
            if (matrix.get(i) > 0) result.add(i);
        }

        return result;
    }

    public SimpleMatrix activate(SimpleMatrix toActivate) {
        for (int i = 0; i < toActivate.numRows(); i++) {
            for (int j = 0; j < toActivate.numCols(); j++) {
                double value = toActivate.get(i, j);

                toActivate.set(i, j, value >= 0 ? value : 0);
            }
        }
        return toActivate;
    }
}
