/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NeuronalNetwork;

import java.util.Random;

/**
 *
 * @author Bernardo
 */
public class Neuron implements java.io.Serializable{
    public double[] weights; //Pesos
        public double lastActivation; //La ultima salida
        public double bias; //Umbral

        public Neuron(int numberOfInputs, Random r)
        {
            bias = 10 * r.nextDouble() - r.nextDouble();
            weights = new double[numberOfInputs];
            for (int i = 0; i < numberOfInputs; i++)
            {
                weights[i] = 10 * r.nextDouble() - r.nextDouble();
            }
        }
        
        //Funcion que te calcula la salida
        public double Activate(double[] inputs)
        {
            double activation = bias;

            for (int i = 0; i < weights.length; i++)
            {
                activation += weights[i] * inputs[i];
            }

            lastActivation = activation;
            return Sigmoid(activation);
        }
        public static double Sigmoid(double input)
        {
            return 1 / (1 + Math.exp(-input));
        }
        public static double SigmoidDerivated(double input)
        {
            double y = Sigmoid(input);
            return y * (1 - y);
        }
}
