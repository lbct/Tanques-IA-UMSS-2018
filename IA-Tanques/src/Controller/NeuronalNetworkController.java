/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import NeuronalNetwork.Perceptron;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.net.URL;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Bernardo
 */
public class NeuronalNetworkController {
        
        public Perceptron perception;
        
        ArrayList<double[]> input = new ArrayList<double[]>();
        ArrayList<double[]> output = new ArrayList<double[]>();

        int inputCount = 1;
        int outputCount = 1;

        double inputMax = 5;
        double inputMin = 0;

        double outputMax = 1;
        double outputMin = 0;

        boolean loadNetwork = true;
        boolean saveNetwork = false;

        String dataPath = "Datos.csv";
        String outputPath = "DatosOutput.csv";
        String neuronPath = "DatosNetwork.bin";

        void ReadData() throws FileNotFoundException, IOException
        {
            FileInputStream file = new FileInputStream(dataPath);
            String data = readStream(file);
            file.close();

            String[] row = data.split("\n");
            for (int i = 0; i < row.length; i++)
            {
                String[] rowData = row[i].split(";");

                double[] inputs = new double[inputCount];
                double[] outputs = new double[outputCount];

                for (int j = 0; j < rowData.length; j++)
                {
                    if (j < inputCount)
                    {
                        inputs[j] = Normalize(Double.parseDouble(rowData[j]), inputMin, inputMax);
                    }
                    else
                    {
                        outputs[j - inputCount] = Normalize(Double.parseDouble(rowData[j]), outputMin, outputMax);
                    }
                }

                input.add(inputs);
                output.add(outputs);
            }

        }
        
        public String readStream(InputStream is) {
            StringBuilder sb = new StringBuilder(512);
            try {
                Reader r = new InputStreamReader(is, "UTF-8");
                int c = 0;
                while ((c = r.read()) != -1) {
                    sb.append((char) c);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return sb.toString();
        }

        double Normalize(double value, double min, double max)
        {
            return (value - min) / (max - min);
        }
        double InverseNormalize(double value, double min, double max)
        {
            return value * (max - min) + min;
        }

        void outputRequest(Perceptron p)
        {
            Scanner scanner = new Scanner(System.in);
            while (true)
            {
                double[] val = new double[inputCount];
                for (int i = 0; i < inputCount; i++)
                {
                    System.out.println("Inserte valor " + i + ": ");
                    
                    val[i] = Normalize(Double.parseDouble(scanner.nextLine()), inputMin, inputMax);
                }

                double[] sal = p.Activate(val);
                for (int i = 0; i < outputCount; i++)
                {
                    System.out.print("Respuesta " + i + ": " + InverseNormalize(sal[i], outputMin, outputMax) + " ");
                }

                System.out.println();
            }
        }
        
        public double[] getRespuesta(double[] entradas){
            for(int i=0;i<entradas.length;i++)
                entradas[i] = Normalize(entradas[i], inputMin, inputMax);
            double[] salidas = perception.Activate(entradas);
            for(int i=0;i<salidas.length;i++)
                salidas[i] = InverseNormalize(salidas[i], outputMin, outputMax);
            return salidas;
        }

        void Evaluate(Perceptron p, double from, double to, double step)
        {
            String output = "";

            for (double i = from; i < to; i += step)
            {
                double res = p.Activate(new double[] { Normalize(i, inputMin, inputMax) })[0];
                output += i + ";" + InverseNormalize(res, outputMin, outputMax) + "\n";
                System.out.println(i + ";" + res + "\n");
            }

            //System.IO.File.WriteAllText(outputPath, output);
        }
        
        public void Ejecutar() throws IOException, ClassNotFoundException{
            
            if (!loadNetwork)
            {
                ReadData();
                perception = new Perceptron(new int[] { input.get(0).length, 3, 3, output.get(0).length });

                while (!perception.Learn(input, output, 0.5, 0.03, 5000000))
                {
                    perception = new Perceptron(new int[] { input.get(0).length, 3, 3, output.get(0).length });
                }
                if (saveNetwork)
                {
                    try {
                        FileOutputStream fileOut =
                        new FileOutputStream(neuronPath);
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(perception);
                        out.close();
                        fileOut.close();
                        System.out.printf("Serialized data is saved");
                     } catch (IOException i) {
                        i.printStackTrace();
                     }
                }
            }
            else
            {
                try {
                    FileInputStream fileIn = new FileInputStream(this.neuronPath);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    perception = (Perceptron) in.readObject();
                    in.close();
                    fileIn.close();
                 } catch (IOException i) {
                    i.printStackTrace();
                    return;
                 }
            }
            //outputRequest(perception);
        }
}
