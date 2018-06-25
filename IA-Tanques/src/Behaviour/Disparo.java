/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Behaviour;

import Controller.MainController;
import Model.TanquePrincipal;
import Model.Valores;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

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
        
        ACLMessage mensaje = agente.receive();
        
        if(mensaje != null){
            
            String idTanque = mensaje.getContent();
            
            System.out.println("El tanue "+agente.getLocalName()+" recibió un impacto de "+idTanque);
            
            
            agente.vida -= 50;
            if(agente.vida <= 0){
                System.out.println("El tanque: "+agente.getLocalName()+" está destruido.");
                agente.detener();
            }
        }
        
        if(!agente.disparar || agente.detenido()){
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
                
                for(TanquePrincipal tanque : MainController.tanquesJade){
                    if(agente != tanque && !tanque.detenido() 
                            && Math.abs(x + 10 - tanque.getCentroX()) <= 20 
                            && Math.abs(y + 10 - tanque.getCentroY()) <= 20){
                        //System.out.println(x+" "+y+" "+tanque.getX());
                        /*tanque.vida -= 50;
                        if(tanque.vida <= 0)
                            tanque.detener();*/
                        ACLMessage mensajeEnvio = new ACLMessage(ACLMessage.INFORM);
                        mensajeEnvio.setLanguage("Español");
                        AID remoteAMSf = new AID(tanque.getLocalName(), AID.ISLOCALNAME);
                        mensajeEnvio.addReceiver(remoteAMSf);
                        mensajeEnvio.setContent(agente.getLocalName());
                        agente.send(mensajeEnvio);
                        agente.disparar = false;
                    }
                }
                
                if(dirX == 0 && dirY == 0)
                    agente.disparar = false;
                if(x < 0 || x > 640 || y < 0 || y > 480)
                    agente.disparar = false;
            }
        }
    }
    
}
