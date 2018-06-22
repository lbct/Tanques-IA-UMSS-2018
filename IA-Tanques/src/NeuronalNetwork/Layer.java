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
            ArrayList<Double> outputs = new ArrayList<Double>();
            for (int i = 0; i < numberOfNeurons; i++)
            {
                outputs.add(neurons.get(i).Activate(inputs));
            }
            double[] lista = new double[outputs.size()];
            for(int i=0;i<outputs.size();i++)
                lista[i] = outputs.get(i);
            return lista;
        }
}
