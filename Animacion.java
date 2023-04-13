import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.*;
import java.awt.geom.*;
public class Animacion  extends JLabel implements Runnable, KeyListener{
    boolean startGame = false, derecha = true, bandera = true, pausar = false, stop = false,
            walk = false, run = false, jump = false, finalNivel = false, colsionPM, colisionMT, 
            colisionTP, colisionCP, finJuego = false, nuevoSalto = false, reiniciar = true, reiniciarTodo = false;
    int brincoS = 0, poder = 1, cPisos = 0, cFM, vidas = 3, moverP = 0, rE = 0,
         nColision = 0, posXTu = 210, posXP = 0;
    
    
    Vidas vida;
    Mario mario;
    Fondo fondo;
    SonidoP sonido;
    JButton btnStart;
    Piso [] piso = new Piso[3];
    Tortuga [] tortuga = new Tortuga[3];
    Tuberia [] tuberia = new Tuberia[3];
    Thread [] tTortuga = new Thread[3];

    public Animacion(Mario mario, Fondo fondo, Vidas vida, JButton btnStart){
        sonido = new SonidoP("Sonido/mario1v2.wav");
        this.vida = vida;
        this.mario = mario;
        this.fondo = fondo;
        this.btnStart = btnStart;
    }

    public void run(){
        startGame = true;
        reiniciar = true;
        stop = false;
        if(reiniciarTodo){
            reiniciarTodo();
        }
        while(reiniciar){
            reaparecerPosiciones();
            limiteInferior();
            colsion();
            finN();
            if(vidas == 0){
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
                    sonido.stopAlto();
                    pararHilo();
                }catch(Exception ex){}
            }
            if(finJuego){
                sonido.pause();
                //control de tiempo de reproduccion
                try{
                    Thread.sleep(500);
                    sonido.setSong("Sonido/smb_world_clear.wav");
                    sonido.finJ();
                }catch(Exception ex){}
                try{
                    Thread.sleep(700);
                    reiniciar = false;
                    reiniciarTodo();
                    finJuego = false;
                    break;
                }catch(Exception ex){}
            }
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
                if(nuevoSalto){
                    if(jump){
                        poder = 1;
                        brincarMario();
                    }
                    if(jump && walk){
                        poder = 1;
                        brincarMario();
                    }
                    if(run && jump){
                        poder = 4;
                        brincarMario();
                    }
                }
                if(run && walk){
                    poder = 4;
                    caminarMario();
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
                        
                        reiniciar = false;
                        break;
                    }
                }// end synchronized
            }catch(Exception ex){}//end try catch*/
            
        }

    }//end run
    synchronized void pasuarHilo(){ pausar = true; }
    synchronized void reanudarHilo(){ pausar = false; notify(); }
    synchronized void pararHilo(){ 
        sonido.stopAlto();
        stop = true; 
        pausar = false; 
        reiniciar = false; 
        reiniciarTodo = true;
        btnStart.setEnabled(true);
        reiniciarTodo();
        notify(); 
    }

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
                if(!stop){
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
        if(e.getKeyCode() == KeyEvent.VK_UP){ this.jump = false; nuevoSalto = false;}
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    public void reiniciarTodo(){
        this.mario.setLocation(5, 177);
        this.fondo.setLocation(0, 0);
        for(int i = 0; i < 3; i++){
            piso[i].setBounds(posXP, 208, 130, 32);
            posXP += 210;
            tuberia[i].setBounds(posXTu, 177, 30, 31);
            posXTu += 50;
        }
        sonido.setSong("Sonido/mario1v2.wav");
        posXTu = 210;
        posXP = 0;
        vidas = 3;
        vida.setVida(vidas);
        btnStart.setEnabled(true);
        reiniciarTodo = false;
    }

    public SonidoP getSonidoP(){
        return this.sonido;
    }

    private void limiteInferior(){
        if(this.mario.getY() > 299){
            this.contadorVida();
        }
        
    }

    private void contadorVida(){
        vidas--;
        vida.setVida(vidas);
        this.mario.reiniciarPosicion(this.mario.getX());
    }

    private void colsion(){
        
        Area [] tuberiasA = new Area[tuberia.length];
        Area [] pisosA = new Area[piso.length];
        Area marioA = new Area(this.mario.getBounds());

        //creamos el area para cada una de las tuberias
        for (int i = 0; i < tuberiasA.length; i++) {
            tuberiasA[i] = new Area(tuberia[i].getBounds());
        }

        //Creamos el area para cada uno de los pisos
        for (int i = 0; i < pisosA.length; i++) {
            pisosA[i] = new Area(piso[i].getBounds());
        }
        
        //Comprobamos la colision para los pisos
        for (int i = 0; i < pisosA.length; i++) {
            if (pisosA[i].intersects(marioA.getBounds2D())) {
                nuevoSalto = true;
                this.colsionPM = true;
                break;
            } else{
                this.colsionPM = false;
            }
        }

        //Comprobamos la colision con las tuberias
        for (int i = 0; i < pisosA.length; i++) {
            if (tuberiasA[i].intersects(marioA.getBounds2D())) {
                nuevoSalto = true;
                this.colisionMT = true;
                break;
            } else{
                this.colisionMT = false;
            }
        }
    }

    public void reaparecerPosiciones(){
        for(int i = 0; i < piso.length; i++){
            if(tuberia[i].getX() < -1){
                tuberia[i].reiniciarPosicion();
            }
        }
        for(int i = 0; i < piso.length; i++){
            if(piso[i].getX() < -1){
                piso[i].reiniciarPosicion();
            }
        }
    }

    public void finN(){
        //System.out.println(this.fondo.getX());
        if(this.fondo.getX() < -2400){
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
                    for(int i = 0; i < piso.length; i++){
                        piso[i].mover_piso(poder);
                        tuberia[i].mover_tuberia(poder);
                    }
                }
            }
            
        }else{
            this.mario.marioIzquierda(cFM);
            this.mario.setLocation(this.mario.getX() - poder, this.mario.getY());
        }
    }

    private void brincarMario(){
        if(this.mario.getY() > 0){
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
                        fondo.mover_fondo(poder);
                        for(int i = 0; i < piso.length; i++){
                            piso[i].mover_piso(poder);
                            tuberia[i].mover_tuberia(poder);
                        }
                    }
                }
                
            }else{
                this.mario.marioBrincaIzquierda();
                this.mario.setLocation(this.mario.getX() - poder, this.mario.getY());
            }
        }
    }
}
