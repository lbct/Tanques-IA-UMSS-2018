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
public class Perceptron implements java.io.Serializable{
    ArrayList<Layer> layers;

        public Perceptron(int[] neuronsPerlayer)
        {
            layers = new ArrayList<Layer>();
            Random r = new Random();

            for (int i = 0; i < neuronsPerlayer.length; i++)
            {
                layers.add(new Layer(neuronsPerlayer[i], i == 0 ? neuronsPerlayer[i] : neuronsPerlayer[i - 1], r));
            }
        }
        public double[] Activate(double[] inputs)
        {
            double[] outputs = new double[0];
            for (int i = 1; i < layers.size(); i++)
            {
                outputs = layers.get(i).Activate(inputs);
                inputs = outputs;
            }
            return outputs;
        }
        double IndividualError(double[] realOutput, double[] desiredOutput)
        {
            double err = 0;
            for (int i = 0; i < realOutput.length; i++)
            {
                err += Math.pow(realOutput[i] - desiredOutput[i], 2);
            }
            return err;
        }
        double GeneralError(ArrayList<double[]> input, ArrayList<double[]> desiredOutput)
        {
            double err = 0;
            for (int i = 0; i < input.size(); i++)
            {
                err += IndividualError(Activate(input.get(i)), desiredOutput.get(i));
            }
            return err;
        }
        ArrayList<String> log;
        public boolean Learn(ArrayList<double[]> input, ArrayList<double[]> desiredOutput, double alpha, double maxError, int maxIterations)
        {
            double err = 99999;
            log = new ArrayList<String>();
            while (err > maxError)
            {
                maxIterations--;
                if (maxIterations <= 0)
                {
                    System.out.println("---------------------Minimo local-------------------------");
                    return true;
                }

                ApplyBackPropagation(input, desiredOutput, alpha);
                err = GeneralError(input, desiredOutput);
                log.add(err+"");
                System.out.println(err);
            }
            //System.IO.File.WriteAllLines(@"C:\Users\ASUS\LogTail.txt", log.ToArray());
            return true;
        }

        ArrayList<double[]> sigmas;
        ArrayList<double[][]> deltas;

        void SetSigmas(double[] desiredOutput)
        {
            sigmas = new ArrayList<double[]>();
            for (int i = 0; i < layers.size(); i++)
            {
                sigmas.add(new double[layers.get(i).numberOfNeurons]);
            }
            for (int i = layers.size() - 1; i >= 0; i--)
            {
                for (int j = 0; j < layers.get(i).numberOfNeurons; j++)
                {
                    if (i == layers.size() - 1)
                    {
                        double y = layers.get(i).neurons.get(j).lastActivation;
                        sigmas.get(i)[j] = (Neuron.Sigmoid(y) - desiredOutput[j]) * Neuron.SigmoidDerivated(y);
                    }
                    else
                    {
                        double sum = 0;
                        for (int k = 0; k < layers.get(i + 1).numberOfNeurons; k++)
                        {
                            sum += layers.get(i + 1).neurons.get(k).weights[j] * sigmas.get(i + 1)[k];
                        }
                        sigmas.get(i)[j] = Neuron.SigmoidDerivated(layers.get(i).neurons.get(j).lastActivation) * sum;
                    }
                }
            }
        }
        void SetDeltas()
        {
            deltas = new ArrayList<double[][]>();
            for (int i = 0; i < layers.size(); i++)
            {
                deltas.add(new double[layers.get(i).numberOfNeurons][layers.get(i).neurons.get(0).weights.length]);
            }
        }
        void AddDelta()
        {
            for (int i = 1; i < layers.size(); i++)
            {
                for (int j = 0; j < layers.get(i).numberOfNeurons; j++)
                {
                    for (int k = 0; k < layers.get(i).neurons.get(j).weights.length; k++)
                    {
                        deltas.get(i)[j][k] += sigmas.get(i)[j] * Neuron.Sigmoid(layers.get(i - 1).neurons.get(k).lastActivation);
                    }
                }
            }
        }
        void UpdateBias(double alpha)
        {
            for (int i = 0; i < layers.size(); i++)
            {
                for (int j = 0; j < layers.get(i).numberOfNeurons; j++)
                {
                    layers.get(i).neurons.get(j).bias -= alpha * sigmas.get(i)[j];
                }
            }
        }
        void UpdateWeights(double alpha)
        {
            for (int i = 0; i < layers.size(); i++)
            {
                for (int j = 0; j < layers.get(i).numberOfNeurons; j++)
                {
                    for (int k = 0; k < layers.get(i).neurons.get(j).weights.length; k++)
                    {
                        layers.get(i).neurons.get(j).weights[k] -= alpha * deltas.get(i)[j][k];
                    }
                }
            }
        }
        void ApplyBackPropagation(ArrayList<double[]> input, ArrayList<double[]> desiredOutput, double alpha)
        {
            SetDeltas();
            for (int i = 0; i < input.size(); i++)
            {
                Activate(input.get(i));
                SetSigmas(desiredOutput.get(i));
                UpdateBias(alpha);
                AddDelta();
            }
            UpdateWeights(alpha);

        }
}
