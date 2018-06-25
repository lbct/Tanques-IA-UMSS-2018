/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NeuronalNetwork;

import java.util.ArrayList;
import java.util.Random;


/**
 *
 * @author Bernardo
 */
public class Layer implements java.io.Serializable{
        public ArrayList<Neuron> neurons;
        public int numberOfNeurons;
        public double[] output;

        public Layer(int _numberOfNeurons, int numberOfInputs, Random r)
        {
            numberOfNeurons = _numberOfNeurons;
            neurons = new ArrayList<Neuron>();
            for (int i = 0; i < numberOfNeurons; i++)
            {
                neurons.add(new Neuron(numberOfInputs, r));
            }
        }

        public double[] Activate(double[] inputs)
        {
            double[] lista = new double[numberOfNeurons];
            for (int i = 0; i < numberOfNeurons; i++)
                lista[i] = neurons.get(i).Activate(inputs);
            return lista;
        }
}
