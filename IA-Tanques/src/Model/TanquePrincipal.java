package Model;


import Behaviour.Comunicacion;
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
    
    private Movimiento movimiento;
    private Comunicacion comunicacion;
    
    public TanquePrincipal(int x, int y){
        super();
        movimiento = new Movimiento(x, y);
        comunicacion = new Comunicacion(this);
    }
    
    @Override
    protected void setup(){
        addBehaviour(movimiento);
        addBehaviour(comunicacion);
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
