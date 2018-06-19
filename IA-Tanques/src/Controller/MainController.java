/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Valores;
import Model.TanquePrincipal;
import View.FramePrincipal;
import View.TanqueView;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bernardo
 */
public class MainController {
    
    private FramePrincipal frame;
    private TanquePrincipal[] tanquesJade;
    private TanqueView[] tanquesVisual;
    
    public MainController(){
        frame = new FramePrincipal();
        tanquesJade = inicializarTanqueJade();
        tanquesVisual = new TanqueView[Valores.getNumeroAgentes()];
        for(int i=0;i<Valores.getNumeroAgentes();i++)
            tanquesVisual[i] = frame.agregarNuevoTanque(i, i); //new TanqueView(10 * i, 10 * i);
        frame.setVisible(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for(int i=0;i<Valores.getNumeroAgentes();i++)
                    tanquesVisual[i].mover(tanquesJade[i].getX(), tanquesJade[i].getY());
            }
        }, 5, 5);
    }
    
    public TanquePrincipal[] inicializarTanqueJade() {
        int num = Valores.getNumeroAgentes();
        TanquePrincipal[] tanques = new TanquePrincipal[num];
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        Profile profile = new ProfileImpl();
        AgentContainer container = runtime.createMainContainer( profile );
        AgentController ac = null;
        try {
            for(int i=0;i<num;i++){
                TanquePrincipal tanque = new TanquePrincipal(i, i);
                ac = container.acceptNewAgent(Valores.nombresAgentes[i], tanque);
                ac.start();
                tanques[i] = tanque;
            }
        } catch (StaleProxyException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tanques;
    }
}
