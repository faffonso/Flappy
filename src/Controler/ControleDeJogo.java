/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controler;

import Auxiliar.Consts;
import Modelo.Personagem;
import Modelo.Flappy;
import Modelo.Chegada;
import Modelo.Bola;
import auxiliar.Posicao;
import java.util.ArrayList;

/**
 *
 * @author franc
 */
public class ControleDeJogo {    
    private Personagem pTemp; 
    private int aux;
    
    public void desenhaTudo(ArrayList<Personagem> e){
        for(int i = 0; i < e.size(); i++){ 
            if (e.get(i).isbGravidade()) {
                e.get(i).setbTransponivel(true);
                e.get(i).moveDown();
            }
            if (e.get(i).isbEmpurravel()) {
                if (!ehPosicaoValida(e, e.get(i).getPosicao(), i)) {
                    e.get(i).voltaAUltimaPosicao();
                    e.get(i).setbTransponivel(false);
                }
            }
            e.get(i).autoDesenho();
        }
    }
    public void processaTudo(ArrayList<Personagem> e){
        Flappy eFlappy = (Flappy)e.get(0);
        for(int i = 1; i < e.size(); i++){
            pTemp = e.get(i);
            if(eFlappy.getPosicao().igual(pTemp.getPosicao()))
                if(pTemp.isbTransponivel())
                    eFlappy.voltaAUltimaPosicao();
        }        
    }
    
    public boolean verificaVitoria(ArrayList<Personagem> e) {
        Chegada eChegada = (Chegada)e.get(1);
        Bola eBola = (Bola)e.get(2);
        
        aux = eBola.getPosicao().getLinha() + 1;
        if (aux < Consts.RES) {
            eBola.moveDown();
            if (eChegada.getPosicao().igual(eBola.getPosicao())){
                eBola.voltaAUltimaPosicao();
                return true;
            }
            eBola.voltaAUltimaPosicao();
        }
        return false;
        
    }
    
    /*Retorna true se a posicao p é válida para Lolo com relacao a todos os personagens no array*/
    public boolean ehPosicaoValida(ArrayList<Personagem> e, Posicao p, int index){
        for(int i = 0; i < e.size(); i++){
            pTemp = e.get(i);            
            if(!pTemp.isbTransponivel())
                if(pTemp.getPosicao().igual(p)){
                    return false;
                }
        }        
        return true;
    }
    
    public Personagem ehEmpurravel(ArrayList<Personagem> e, Posicao p) {
        Personagem pTemp;
        for(int i = 1; i < e.size(); i++){
            pTemp = e.get(i);            
            if(pTemp.isbEmpurravel())
                if(pTemp.getPosicao().igual(p))
                    return pTemp;
        }        
        return null;
    }
    
    public boolean ehCaivel(ArrayList<Personagem> e, Posicao p) {
        for(int i = 1; i < e.size(); i++){
            pTemp = e.get(i);    
                if(pTemp.getPosicao().igual(p)){
                    return false;
                }
        }        
        return true;
    }
}
