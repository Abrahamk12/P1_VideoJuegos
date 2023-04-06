import javax.swing.JLabel;
import java.awt.event.*;
import java.awt.geom.*;
public class Animacion  extends JLabel implements Runnable, KeyListener{
    boolean startGame = false, derecha = true, bandera = true, pausar = false, stop = false,
            walk = false, run = false, jump = false, finalNivel = false, colsionPM, colisionTP,
            colisionCP, colisionTT = false, colisionCT = false;
    int brincoS = 0, poder = 1, cPisos = 0;
    Fondo fondo;
    SonidoP sonido;
    private Mario mario;
    private Piso [] pisos = new Piso[3];
    Tortuga [] tortuga = new Tortuga[3];
    Tuberia [] tuberia = new Tuberia[3];
    Champiñon [] champiñon = new Champiñon[3];
    Thread tChamp, tTortuga;

    int cFM, vidas = 3;

    public Animacion(Mario mario, Piso [] pisos, Fondo fondo, Tortuga [] tortugas, Champiñon [] champiñon, Tuberia [] tuberias){
        sonido = new SonidoP("Sonido/mario1v2.wav");
        this.mario = mario;
        this.pisos = pisos;
        this.fondo = fondo;
        this.tuberia = tuberias;
        this.tortuga = tortugas;
        this.champiñon = champiñon;
    }

    public void run(){
        startGame = true;
        //Movimeinto de enemigos
        for(int i = 0; i < champiñon.length; i++){
            champiñon[i].rapidez = 1;
            champiñon[i].derecha = colisionCT;
            tChamp  = new Thread(champiñon[i]);
            tChamp.start();
            tortuga[i].rapidez = 1;
            tTortuga = new Thread(tortuga[i]);
            tTortuga.start();
        }
        while(true){
            if(vidas == 0){
                break;
            }
            limiteInferior();
            colsion();
            try{
                Thread.sleep(50);
                if(walk){
                    if(cFM < 3){
                        cFM++;
                    }else{
                        cFM = 0;
                    }
                    poder = 1;
                    caminarMario();
                }
                if(jump){
                    poder = 1;
                    brincarMario();
                }
                if(jump && walk){
                    poder = 1;
                    brincarMario();
                }
                if(run && walk){
                    poder = 4;
                    caminarMario();
                }
                if(run && jump){
                    poder = 4;
                    brincarMario();
                }
                if(!jump && !colsionPM){
                    this.mario.gravedad(50,2);
                }
                
            }catch(Exception ex){}
        }

    }//end run
    synchronized void pasuarHilo(){ pausar = true; }
    synchronized void reanudarHilo(){ pausar = false; notify(); }
    synchronized void pararHilo(){ stop = true; pausar = false; notify(); }

    @Override
    public void keyPressed(KeyEvent e) {
        if(startGame){
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){ 
                this.walk = true;
                this.derecha = true;
            }
            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                this.walk = true;
                this.derecha = false; 
            }
            if(e.getKeyCode() == KeyEvent.VK_CONTROL){ this.run = true; }
            if(e.getKeyCode() == KeyEvent.VK_UP){ this.jump = true; }
            if(e.getKeyCode() == KeyEvent.VK_P){ 
                if(bandera){
                    sonido.resume();
                    pasuarHilo();
                    bandera = false;
                }else{
                    sonido.pause();
                    reanudarHilo();
                    bandera = true;
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){ 
                pararHilo();
                sonido.stopAlto();
                bandera = true;
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            this.walk = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            this.walk = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_CONTROL){ this.run = false; }
        if(e.getKeyCode() == KeyEvent.VK_UP){ this.jump = false; }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    public SonidoP getSonidoP(){
        return this.sonido;
    }

    public void limiteInferior(){
        if(this.mario.getY() > 300){
            this.mario.reiniciarPosicion(this.mario.getX()-5);
        }

    }

    private void colsion(){
        Area [] pisosA = new Area[pisos.length];
        Area [] champsA = new Area[champiñon.length];
        Area [] tortugasA = new Area[tortuga.length];
        Area [] tuberiasA = new Area[tuberia.length];
        Area marioA;
        marioA = new Area(this.mario.getBounds());
        for(int i = 0; i < pisos.length; i++){
            pisosA[i] = new Area(pisos[i].getBounds());
            champsA[i] = new Area(champiñon[i].getBounds());
            tuberiasA[i] = new Area(tuberia[i].getBounds());
            tortugasA[i] = new Area(tortuga[i].getBounds());
        }
        int i = 0;
        if(i < pisos.length){
            if(pisosA[i].intersects(marioA.getBounds2D())){
                colsionPM = true;
            }else{
                colsionPM =  false;
            }
            //colsiiones champiñon
            if(champsA[i].intersects(pisosA[i].getBounds2D())){
                colisionCP = true;
            }else{
                this.champiñon[i].gravedad(50, 4);
            }
            if(champsA[i].intersects(marioA.getBounds2D())){
                if(this.mario.getY() > this.champiñon[i].getAlignmentY()){
                    this.champiñon[i].champiñonMuerto();
                    this.champiñon[i].reiniciarPosicion();
                }else{
                    this.mario.reiniciarPosicion(this.mario.getX() - 2);
                    vidas--;
                }
            }
            if(champsA[i].intersects(tuberiasA[i].getBounds2D())){
                colisionCT = true;
            }
            //Colisiones tortuga
            if(tortugasA[i].intersects(pisosA[i].getBounds2D())){
                colisionTP = true;
            }else{
                this.tortuga[i].gravedad(50, 4);
            }
            if(tortugasA[i].intersects(marioA.getBounds2D())){
                if(this.mario.getY() > this.tortuga[i].getAlignmentY()){
                    this.tortuga[i].tortugaMuerta();
                    this.tortuga[i].reiniciarPosicion();
                }else{
                    this.mario.reiniciarPosicion(this.mario.getX() - 2);
                    vidas--;
                }
            }
            if(tortugasA[i].intersects(tuberiasA[i].getBounds2D())){
                colisionTT = true;
            }
            i++;
        }else{
            i = 0;
        }
    }
    
    private void caminarMario(){
        int moverP = 0;
        if(derecha){
            if(finalNivel){
                this.mario.marioDerecha(cFM);
                this.mario.setLocation(this.mario.getX() + poder, this.mario.getY());
            }else{
                if(this.mario.getX() < 250){
                    this.mario.marioDerecha(cFM);
                    this.mario.setLocation(this.mario.getX() + poder, this.mario.getY());
                }else{
                    this.mario.marioDerecha(cFM);
                    fondo.mover_fondo(poder);
                    if(moverP < pisos.length){
                        moverP++;
                        pisos[moverP].mover_piso(poder);
                    }else{
                        moverP = 0;
                    }
                }
            }
            
        }else{
            this.mario.marioIzquierda(cFM);
            this.mario.setLocation(this.mario.getX() - poder, this.mario.getY());
        }
    }

    private void brincarMario(){
        if(derecha){
            if(finalNivel){
                this.mario.marioBrincaDerecha();
                this.mario.setLocation(this.mario.getX() + brincoS, this.mario.getY() - poder);
            }else{
                if(this.mario.getX() < 250){
                    this.mario.marioBrincaDerecha();
                    this.mario.setLocation(this.mario.getX() + brincoS, this.mario.getY() - poder);
                }else{
                    this.mario.setLocation(this.mario.getX(), this.mario.getY() - poder);
                }
            }
            
        }else{
            this.mario.marioBrincaIzquierda();
            this.mario.setLocation(this.mario.getX() + poder, this.mario.getY());
        }
    }
}
