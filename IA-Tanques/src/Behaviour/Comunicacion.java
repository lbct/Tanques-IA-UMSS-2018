/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Behaviour;

import Model.TanquePrincipal;
import Model.Valores;
import Model.ValoresTanque;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bernardo
 */
public class Comunicacion extends CyclicBehaviour {

    private TanquePrincipal agente;
    
    public Comunicacion(TanquePrincipal agente){
        super(agente);
        this.agente = agente;
    }
    
    @Override
    public void action() {
        /*for(String nombreTanque : Valores.nombresAgentes){
            if(!nombreTanque.equals(agente.getLocalName())){
                try {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.setContentObject(new ValoresTanque(agente.getX(), agente.getY(), agente.getLocalName()));
                    msg.addReceiver(new AID(nombreTanque, AID.ISLOCALNAME));
                    agente.send(msg);
                } catch (IOException ex) {
                    Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        ACLMessage mensaje = agente.receive();
            if(mensaje != null){
            try {
                ValoresTanque  valores = (ValoresTanque)mensaje.getContentObject();
                if(Math.abs(agente.getX() - valores.getPosX()) <= 5 && Math.abs(agente.getY() - valores.getPosY()) <= 5)
                    System.out.println("El tanque "+agente.getLocalName()+" chocó contra "+valores.getNombre());
            } catch (UnreadableException ex) {
                Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }*/
    }
}
