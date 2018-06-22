/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Behaviour;

import Model.TanquePrincipal;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;

/**
 *
 * @author Bernardo
 */
public class Disparo extends CyclicBehaviour{

    public int x, y;
    private int dirX;
    private int dirY;
    private long contadorTiempo;
    private TanquePrincipal agente;
    
    public Disparo(TanquePrincipal agente){
        super();
        this.agente = agente;
        x = agente.getX();
        y = agente.getY();
        contadorTiempo = System.currentTimeMillis();
    }
    
    @Override
    public void action() {
        if(!agente.disparar){
            x = agente.getX();
            y = agente.getY();
            dirX = agente.movimiento.dirX;
            dirY = agente.movimiento.dirY;
            contadorTiempo = System.currentTimeMillis();
        }
        else{
            if(System.currentTimeMillis() - contadorTiempo >= 10){
                contadorTiempo = System.currentTimeMillis();
                x += dirX;
                y += dirY;
                if(x < 0 || x > 640 || y < 0 || y > 480)
                    agente.disparar = false;
            }
        }
    }
    
}
