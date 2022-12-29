/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Tela.java
 *
 * Created on 03/03/2011, 18:28:20
 */
package Controler;

import Modelo.Personagem;
import Modelo.Flappy;
import Modelo.Chegada;
import Modelo.Bola;
import Auxiliar.Consts;
import Auxiliar.Desenho;
import Modelo.Brick;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author franc
 */
public class Tela extends javax.swing.JFrame implements MouseListener, KeyListener {

    private Flappy lFlappy;
    private Chegada lChegada;
    private ArrayList<Personagem> e;
    private ControleDeJogo cj = new ControleDeJogo();
    private Graphics g2;
    
    protected int fase;
    protected boolean restart;
    private Personagem pTemp;
    /**
     * Creates new form tabuleiro
     */
    public Tela() {
        Desenho.setCenario(this);
        initComponents();
        this.addMouseListener(this); /*mouse*/

        this.addKeyListener(this);   /*teclado*/
        /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(Consts.RES * Consts.CELL_SIDE + getInsets().left + getInsets().right,
                Consts.RES * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);

        e = new ArrayList<>(100);
        fase = 0;
        restart = false;
        
        /*Cria e adiciona personagens*/
        lFlappy = new Flappy("flappy.png");
        lFlappy.setPosicao(1, 1);
        this.addPersonagem(lFlappy);
        
        lChegada = new Chegada("chegada.png");
        lChegada.setPosicao(8, 2);
        this.addPersonagem(lChegada);

        Bola bBola1 = new Bola ("ball.png", false);
        bBola1.setPosicao(7, 7);
        this.addPersonagem(bBola1);    
        
        for (int i = 0; i < 5; i ++) {
            Brick bBrick1 = new Brick ("brick.png");
            bBrick1.setPosicao(2, i);
            this.addPersonagem(bBrick1);
        }
        
        for (int i = 0; i < 9; i ++) {
            if (i != 5 && i != 2) {
                Brick bBrick2 = new Brick ("brick.png");
                bBrick2.setPosicao(8, i);
                this.addPersonagem(bBrick2);
            }
        }
        
        Bola bBola2 = new Bola ("sand.png", true);
        bBola2.setPosicao(1, 3);
        this.addPersonagem(bBola2);   
        
        Bola bBola3 = new Bola ("sand.png", true);
        bBola3.setPosicao(1, 4);
        this.addPersonagem(bBola3);   
        
        
        for (int i = 0; i < Consts.RES; i ++) {
            if (i == 0 || i == Consts.RES - 1) {
                for (int j = 0; j < Consts.RES; j++) {
                    Brick bBrickDefault = new Brick ("brick.png");
                    bBrickDefault.setPosicao(i, j);
                    this.addPersonagem(bBrickDefault);
                }
            } else {
                Brick bBrickDefault1 = new Brick ("brick.png");
                bBrickDefault1.setPosicao(i, 0);
                this.addPersonagem(bBrickDefault1);
                Brick bBrickDefault2 = new Brick ("brick.png");
                bBrickDefault2.setPosicao(i, Consts.RES - 1);
                this.addPersonagem(bBrickDefault2);
            }
        }
    }


    public void addPersonagem(Personagem umPersonagem) {
        e.add(umPersonagem);
    }

    public void removePersonagem(Personagem umPersonagem) {
        e.remove(umPersonagem);
    }

    public Graphics getGraphicsBuffer(){
        return g2;
    }

    public void paint(Graphics gOld) {
        Graphics g = this.getBufferStrategy().getDrawGraphics();
        /*Criamos um contexto gráfico*/
        g2 = g.create(getInsets().left, getInsets().top, getWidth() - getInsets().right, getHeight() - getInsets().top);
        /*************Desenha cenário de fundo**************/
        for (int i = 0; i < Consts.RES; i++) {
            for (int j = 0; j < Consts.RES; j++) {
                try {
                    Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "null.png");
                    g2.drawImage(newImage,
                            j * Consts.CELL_SIDE, i * Consts.CELL_SIDE, Consts.CELL_SIDE, Consts.CELL_SIDE, null);

                } catch (IOException ex) {
                    Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (!this.e.isEmpty()) {
            this.cj.desenhaTudo(e);
            this.cj.processaTudo(e);
            if (this.cj.verificaVitoria(e) || restart) {
                if (restart == false){
                    fase++;
                }
                System.out.println(e.size());
                for (int i = 0; i < Consts.RES; i++){
                    for (int j = 0; j < e.size(); j++) {
                        removePersonagem(e.get(j));
                    }
                }
                System.out.println(e);
                restart = false;
                switch (fase) {
                    case 0 -> {
                    lFlappy = new Flappy("flappy.png");
                    lFlappy.setPosicao(1, 1);
                    this.addPersonagem(lFlappy);

                    lChegada = new Chegada("chegada.png");
                    lChegada.setPosicao(8, 2);
                    this.addPersonagem(lChegada);

                    Bola bBola1 = new Bola ("ball.png", false);
                    bBola1.setPosicao(7, 7);
                    this.addPersonagem(bBola1);    

                    for (int i = 0; i < 5; i ++) {
                        Brick bBrick1 = new Brick ("brick.png");
                        bBrick1.setPosicao(2, i);
                        this.addPersonagem(bBrick1);
                    }

                    for (int i = 0; i < 9; i ++) {
                        if (i != 5 && i != 2) {
                            Brick bBrick2 = new Brick ("brick.png");
                            bBrick2.setPosicao(8, i);
                            this.addPersonagem(bBrick2);
                        }
                    }

                    Bola bBola2 = new Bola ("sand.png", true);
                    bBola2.setPosicao(1, 3);
                    this.addPersonagem(bBola2);   

                    Bola bBola3 = new Bola ("sand.png", true);
                    bBola3.setPosicao(1, 4);
                    this.addPersonagem(bBola3);   


                    for (int i = 0; i < Consts.RES; i ++) {
                        if (i == 0 || i == Consts.RES - 1) {
                            for (int j = 0; j < Consts.RES; j++) {
                                Brick bBrickDefault = new Brick ("brick.png");
                                bBrickDefault.setPosicao(i, j);
                                this.addPersonagem(bBrickDefault);
                            }
                        } else {
                            Brick bBrickDefault1 = new Brick ("brick.png");
                            bBrickDefault1.setPosicao(i, 0);
                            this.addPersonagem(bBrickDefault1);
                            Brick bBrickDefault2 = new Brick ("brick.png");
                            bBrickDefault2.setPosicao(i, Consts.RES - 1);
                            this.addPersonagem(bBrickDefault2);
                        }
                    }
                    }
                    case 1 -> {
                        lFlappy = new Flappy("flappy.png");
                        lFlappy.setPosicao(1, 1);
                        this.addPersonagem(lFlappy);

                        lChegada = new Chegada("chegada.png");
                        lChegada.setPosicao(8, 8);
                        this.addPersonagem(lChegada);

                        Bola bBola1 = new Bola ("ball.png", false);
                        bBola1.setPosicao(1, 5);
                        this.addPersonagem(bBola1);    


                        for (int i = 0; i < Consts.RES; i ++) {
                            if (i == 0 || i == Consts.RES - 1) {
                                for (int j = 0; j < Consts.RES; j++) {
                                    Brick bBrickDefault = new Brick ("brick.png");
                                    bBrickDefault.setPosicao(i, j);
                                    this.addPersonagem(bBrickDefault);
                                }
                            } else {
                                Brick bBrickDefault1 = new Brick ("brick.png");
                                bBrickDefault1.setPosicao(i, 0);
                                this.addPersonagem(bBrickDefault1);
                                Brick bBrickDefault2 = new Brick ("brick.png");
                                bBrickDefault2.setPosicao(i, Consts.RES - 1);
                                this.addPersonagem(bBrickDefault2);
                            }
                        }
                        for (int i = 3; i < 6; i ++){
                            Brick bBrickLine1 = new Brick ("brick.png");
                            bBrickLine1.setPosicao(2, i);
                            this.addPersonagem(bBrickLine1);
                        }
                        
                        for (int i = 3; i < 7; i ++){
                            Brick bBrickLine2 = new Brick ("brick.png");
                            bBrickLine2.setPosicao(4, i);
                            this.addPersonagem(bBrickLine2);
                        }
                        
                        for (int i = 5; i < 9; i ++){
                            Brick bBrickLine3 = new Brick ("brick.png");
                            bBrickLine3.setPosicao(6, i);
                            this.addPersonagem(bBrickLine3);
                        }
                        
                        for (int i = 1; i < 7; i ++){
                            if (i != 2 && i != 4){
                                Brick bBrickLine3 = new Brick ("brick.png");
                                bBrickLine3.setPosicao(8, i);
                                this.addPersonagem(bBrickLine3);
                            }
                        }
                        
                        
                        
                        Bola bBola2 = new Bola ("sand.png", true);
                        bBola2.setPosicao(3, 6);
                        this.addPersonagem(bBola2);
                        
                        Bola bBola3 = new Bola ("sand.png", true);
                        bBola3.setPosicao(5, 5);
                        this.addPersonagem(bBola3);
                    }
                    
                    case 2 -> {
                        lFlappy = new Flappy("flappy.png");
                        lFlappy.setPosicao(1, 1);
                        this.addPersonagem(lFlappy);

                        lChegada = new Chegada("chegada.png");
                        lChegada.setPosicao(8, 8);
                        this.addPersonagem(lChegada);

                        Bola bBola1 = new Bola ("ball.png", false);
                        bBola1.setPosicao(2, 5);
                        this.addPersonagem(bBola1);   
                        
                        for (int i = 0; i < Consts.RES; i ++) {
                            if (i == 0 || i == Consts.RES - 1) {
                                for (int j = 0; j < Consts.RES; j++) {
                                    Brick bBrickDefault = new Brick ("brick.png");
                                    bBrickDefault.setPosicao(i, j);
                                    this.addPersonagem(bBrickDefault);
                                }
                            } else {
                                Brick bBrickDefault1 = new Brick ("brick.png");
                                bBrickDefault1.setPosicao(i, 0);
                                this.addPersonagem(bBrickDefault1);
                                Brick bBrickDefault2 = new Brick ("brick.png");
                                bBrickDefault2.setPosicao(i, Consts.RES - 1);
                                this.addPersonagem(bBrickDefault2);
                            }
                        }
                        
                        for (int i = 0; i < 2; i ++) {
                            Brick bBrickLine1 = new Brick ("brick.png");
                            bBrickLine1.setPosicao(2, i);
                            this.addPersonagem(bBrickLine1);
                        }
                        
                        for (int i = 3; i < 6; i ++) {
                            Brick bBrickLine2 = new Brick ("brick.png");
                            bBrickLine2.setPosicao(i, 3);
                            this.addPersonagem(bBrickLine2);
                        }
                                                
                        for (int i = 3; i < 6; i ++) {
                            if (i == 3) {
                                Brick bBrickLine3 = new Brick ("brick.png");
                                bBrickLine3.setPosicao(4, 4);
                                this.addPersonagem(bBrickLine3);
                            } else {
                                Brick bBrickLine3 = new Brick ("brick.png");
                                bBrickLine3.setPosicao(i, 5);
                                this.addPersonagem(bBrickLine3);                               
                            }

                        }
                                                                        
                        for (int i = 4; i < 9; i ++) {
                            if (i != 6){
                                Brick bBrickLine4 = new Brick ("brick.png");
                                bBrickLine4.setPosicao(i, 7);
                                this.addPersonagem(bBrickLine4);                               
                            }

                        }
                        
                        Bola bBola2 = new Bola ("sand.png", true);
                        bBola2.setPosicao(3, 5);
                        bBola2.setbTransponivel(false);
                        this.addPersonagem(bBola2);
                        
                        Bola bBola3 = new Bola ("sand.png", true);
                        bBola3.setPosicao(3, 4);
                        this.addPersonagem(bBola3);
                        
                        Bola bBola4 = new Bola ("sand.png", true);
                        bBola4.setPosicao(6, 7);
                        this.addPersonagem(bBola4);
                    }
                }
            } 
        }

        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }

    public void go() {
        TimerTask task = new TimerTask() {
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.PERIOD);
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R -> {
                restart = true;
                this.paint(this.g2);
            }
            case KeyEvent.VK_C -> this.e.clear();
            case KeyEvent.VK_L -> {
                try {
                    File tanque = new File("c:\\temp\\POO.zip");
                    FileInputStream canoOut = new FileInputStream(tanque);
                    GZIPInputStream compactador = new GZIPInputStream(canoOut);
                    ObjectInputStream serializador = new ObjectInputStream(compactador);
                    this.e = (ArrayList<Personagem>)serializador.readObject();
                    this.lFlappy = (Flappy)this.e.get(0);
                    serializador.close();
                } catch (Exception ex) {
                    Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case KeyEvent.VK_S -> {
                try {
                    File tanque = new File("c:\\temp\\POO.zip");
                    tanque.createNewFile();
                    FileOutputStream canoOut = new FileOutputStream(tanque);
                    GZIPOutputStream compactador = new GZIPOutputStream(canoOut);
                    ObjectOutputStream serializador = new ObjectOutputStream(compactador);
                    serializador.writeObject(this.e);
                    serializador.flush();
                    serializador.close();
                } catch (IOException ex) {
                    Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case KeyEvent.VK_UP -> lFlappy.moveUp();
            case KeyEvent.VK_DOWN -> lFlappy.moveDown();
            case KeyEvent.VK_LEFT -> lFlappy.moveLeft();
            case KeyEvent.VK_RIGHT -> lFlappy.moveRight();
            default -> {
            }
        }
        if (cj.ehEmpurravel(this.e, lFlappy.getPosicao()) != null) {
            pTemp = cj.ehEmpurravel(this.e, lFlappy.getPosicao());
            pTemp.setbTransponivel(true);
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> pTemp.moveUp();
                case KeyEvent.VK_DOWN -> pTemp.moveDown();
                case KeyEvent.VK_LEFT -> pTemp.moveLeft();
                case KeyEvent.VK_RIGHT -> pTemp.moveRight();
                default -> {
                }
            }
            if (!cj.ehPosicaoValida(this.e, pTemp.getPosicao(), 0)) {
                if (pTemp.isbDestruivel())
                    this.removePersonagem(pTemp);
                else {
                    lFlappy.voltaAUltimaPosicao();
                    pTemp.voltaAUltimaPosicao();
                }
            }
        } else if (!cj.ehPosicaoValida(this.e, lFlappy.getPosicao(), 0)) {
            lFlappy.voltaAUltimaPosicao();
        }

        this.setTitle("-> Cell: " + (lFlappy.getPosicao().getColuna()) + ", "
                + (lFlappy.getPosicao().getLinha()));

    }



    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("POO2015-1 - Adventures of lolo");
        setAutoRequestFocus(false);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
