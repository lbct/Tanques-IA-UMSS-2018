package Model;

import Behaviour.Disparo;
import Behaviour.Movimiento;
import jade.core.Agent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bernardo
 */
public class TanquePrincipal extends Agent {
    
    public Movimiento movimiento;
    public Disparo disparo;
    public boolean disparar;
    
    public TanquePrincipal(int x, int y){
        super();
        disparar = false;
        movimiento = new Movimiento(x, y);
        disparo = new Disparo(this);
    }
    
    @Override
    protected void setup(){
        addBehaviour(movimiento);
        addBehaviour(disparo);
    }
    
    public void detener(){
        movimiento.detenerMovimiento();
    }
    
    public int getX(){
        return movimiento.getX();
    }
    
    public int getY(){
        return movimiento.getY();
    }
}
