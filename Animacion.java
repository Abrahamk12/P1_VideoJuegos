import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.*;
import java.awt.geom.*;
public class Animacion  extends JLabel implements Runnable, KeyListener{
    boolean startGame = false, derecha = true, bandera = true, pausar = false, stop = false,
            walk = false, run = false, jump = false, finalNivel = false, colsionPM, colisionMT, colisionTP,
            colisionCP, finJuego = false;
    int brincoS = 0, poder = 1, cPisos = 0, cFM, vidas = 3, moverP = 0, rE = 0, nColision = 0;
    
    Piso piso;
    Vidas vida;
    Mario mario;
    Fondo fondo;
    SonidoP sonido;
    JButton btnStart;
    Tortuga [] tortuga = new Tortuga[3];
    Tuberia [] tuberia = new Tuberia[3];
    Champiñon [] champiñon = new Champiñon[3];
    Thread tChamp, tTortuga;

    public Animacion(Mario mario, Piso piso, Fondo fondo, Tortuga [] tortugas, Champiñon [] champiñon, Tuberia [] tuberias, Vidas vida, JButton btnStart){
        sonido = new SonidoP("Sonido/mario1v2.wav");
        this.piso = piso;
        this.vida = vida;
        this.mario = mario;
        this.fondo = fondo;
        this.tuberia = tuberias;
        this.tortuga = tortugas;
        this.btnStart = btnStart;
        this.champiñon = champiñon;
    }

    public void run(){
        startGame = true;
        //Movimeinto de enemigos
        for(int i = 0; i < champiñon.length; i++){
            champiñon[i].rapidez = 1;
            tChamp  = new Thread(champiñon[i]);
            tChamp.start();
            tortuga[i].rapidez = 1;
            tTortuga = new Thread(tortuga[i]);
            tTortuga.start();
        }
        while(true){
            if((vidas == 0) || finJuego){
                sonido.pause();
                //control de tiempo de reproduccion
                try{
                    Thread.sleep(500);
                    sonido.setSong("Sonido/smb_gameover.wav");
                    sonido.finJ();
                }catch(Exception ex){}
                //control para cerrar la ventana
                try{
                    Thread.sleep(3000);
                    System.exit(0);
                }catch(Exception ex){}
            }
            reaparecerPosiciones();
            colsion();
            finN();
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
                if(!jump && !colsionPM && !colisionMT){
                    this.mario.gravedad(50,8);
                }
                
            }catch(Exception ex){}
            try{
                synchronized(this){
                    while(pausar){
                        wait();
                    }
                    if(stop){
                        setLocation(5, 177);
                        break;
                    }
                }// end synchronized
            }catch(Exception ex){}//end try catch*/
            
        }

    }//end run
    synchronized void pasuarHilo(){ pausar = true; }
    synchronized void reanudarHilo(){ pausar = false; notify(); }
    synchronized void pararHilo(){ stop = true; pausar = false; notify(); btnStart.setEnabled(true);}

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
                    sonido.stopAlto();
                    sonido.setSong("Sonido/smb_pause.wav");
                    sonido.playPausa();
                    pasuarHilo();
                    bandera = false;
                }else{
                    sonido.stopAlto();
                    sonido.setSong("Sonido/mario1v2.wav");
                    sonido.play();
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

    private void colsion(){
        
        Area [] champsA = new Area[champiñon.length];
        Area [] tortugasA = new Area[tortuga.length];
        Area [] tuberiasA = new Area[tuberia.length];
        Area pisosA = new Area(piso.getBounds());
        Area marioA = new Area(this.mario.getBounds());
        for(int i = 0; i < champiñon.length; i++){
            champsA[i] = new Area(champiñon[i].getBounds());
            tuberiasA[i] = new Area(tuberia[i].getBounds());
            tortugasA[i] = new Area(tortuga[i].getBounds());
        }

        if(pisosA.intersects(marioA.getBounds2D())){
            colsionPM = true;
        }else{
            colsionPM =  false;
        }
        
        if(nColision < champiñon.length){
            if(tuberiasA[nColision].intersects(marioA.getBounds2D())){
                colisionMT = true;
            }else{
                colisionMT =  false;
            }
            //colsiiones champiñon
            if(champsA[nColision].intersects(pisosA.getBounds2D())){
                colisionCP = true;
            }else{
                this.champiñon[nColision].gravedad(50, 4);
            }
            if(champsA[nColision].intersects(marioA.getBounds2D())){
                sonido.pause();
                sonido.setSong("Sonido/smb_mariodie.wav");
                sonido.marioDie();
                this.mario.reiniciarPosicion(this.mario.getX());
                vidas--;
                vida.setVida(vidas);
                //control de tiempo de reproduccion
                try{
                    Thread.sleep(2200);
                    sonido.setSong("Sonido/mario1v2.wav");
                    sonido.play();
                }catch(Exception ex){}
            }
            if(champsA[nColision].intersects(tuberiasA[nColision].getBounds2D())){
                champiñon[nColision].derecha = true;
            }
            if(champsA[nColision].intersects(tuberiasA[nColision].getBounds2D())){
                champiñon[nColision].derecha = false;
            }
            //Colisiones tortuga
            if(tortugasA[nColision].intersects(pisosA.getBounds2D())){
                colisionTP = true;
            }else{
                this.tortuga[nColision].gravedad(50, 4);
            }
            if(tortugasA[nColision].intersects(marioA.getBounds2D())){
                sonido.pause();
                sonido.setSong("Sonido/smb_mariodie.wav");
                sonido.marioDie();
                this.mario.reiniciarPosicion(this.mario.getX());
                vidas--;
                vida.setVida(vidas);
                //control de tiempo de reproduccion
                try{
                    Thread.sleep(2200);
                    sonido.setSong("Sonido/mario1v2.wav");
                    sonido.play();
                }catch(Exception ex){}
            }
            if(tortuga[nColision].derecha == true){
                if(tortugasA[nColision].intersects(tuberiasA[nColision].getBounds2D())){
                    tortuga[nColision].derecha = false;
                }
            }else{
                if(tortugasA[nColision].intersects(tuberiasA[nColision].getBounds2D())){
                    tortuga[nColision].derecha = true;
                }
            }
            nColision++;
        }else{
            nColision = 0;
        }
    
    }

    public void reaparecerPosiciones(){
        int posxT = (int)(Math.random() * ((500 - 250) + 250)) + 250, 
            ranPos = (int)(Math.random() * ((20 - 10) + 10)) + 10;
        if(rE < champiñon.length){
            if(champiñon[rE].getX() < -1){
                champiñon[rE].reiniciarPosicion(ranPos);
            }
            if(tortuga[rE].getX() < -1){
                tortuga[rE].reiniciarPosicion(ranPos);
            }
            if(tuberia[rE].getX() < -1){
                tuberia[rE].reiniciarPosicion(posxT);
            }
            rE++;
        }else{
            rE = 0;
        }
    }

    public void finN(){
        //System.out.println(this.fondo.getX());
        if(this.fondo.getX() == -2550){
            finalNivel = true;
        }

    }
    
    private void caminarMario(){
        if(derecha){
            if(finalNivel){
                this.mario.marioDerecha(cFM);
                this.mario.setLocation(this.mario.getX() + poder, this.mario.getY());
                if(this.mario.getX() > 450){
                    finJuego = true;
                }
            }else{
                if(this.mario.getX() < 250){
                    this.mario.marioDerecha(cFM);
                    this.mario.setLocation(this.mario.getX() + poder, this.mario.getY());
                }else{
                    this.mario.marioDerecha(cFM);
                    fondo.mover_fondo(poder);
                    piso.mover_piso(poder);
                    if(moverP < tuberia.length){
                        tuberia[moverP].mover_tuberia(poder);
                        moverP++;
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
            this.mario.setLocation(this.mario.getX() - poder, this.mario.getY());
        }
    }
}
