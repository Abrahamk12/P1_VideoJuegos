import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.geom.*;
public class Tortuga extends JLabel implements Runnable{
    String [] url = {"/Sprites/Tortuga/tortuga_0.png", "/Sprites/Tortuga/tortuga_1.png"};
    String [] url2 = {"/Sprites/Tortuga/tortugaD_0.png", "/Sprites/Tortuga/tortugaD_1.png"};
    String url3 = "/Sprites/Tortuga/tortuga.png";
    ImageIcon icon;
    int rapidez;
    boolean derecha = false;
    Piso [] piso = new Piso[3];
    public Tortuga(){
        //Icon
        icon = new ImageIcon(this.getClass().getResource(this.url[0]));
        setIcon(icon);
    }
    public void run(){
        while(true){
            //Control de fotogramas de l a tortuga
            try{
                Thread.sleep(50);
                colisionPiso();
                posicion();
                
                if(derecha){
                    if(this.getX() % 2 != 0){
                        icon = new ImageIcon(this.getClass().getResource(this.url2[0]));
                        setIcon(icon);
                    }else{
                        icon = new ImageIcon(this.getClass().getResource(this.url2[1]));
                        setIcon(icon);
                    }
                    setLocation(this.getX() + rapidez, this.getY());
                }else{
                    if(this.getX() % 2 != 0){
                        icon = new ImageIcon(this.getClass().getResource(this.url[0]));
                        setIcon(icon);
                    }else{
                        icon = new ImageIcon(this.getClass().getResource(this.url[1]));
                        setIcon(icon);
                    }
                    setLocation(this.getX() - rapidez, this.getY());
                }
                
            }catch(Exception ex){}
        }
    }//end run
    

    private void colisionPiso(){

        Area [] pisosA = new Area[piso.length];
        Area tortugaA = new Area(this.getBounds());
        //Creamos el area para cada uno de los pisos
        for (int i = 0; i < pisosA.length; i++) {
            pisosA[i] = new Area(piso[i].getBounds());
        }

        //Comprobamos la colision para los pisos
        for (int i = 0; i < pisosA.length; i++) {
            if (!pisosA[i].intersects(tortugaA.getBounds2D())) {
                gravedad(10, 4);
                break;
            } 
        }
    }
    public void tortugaMuerta(){
        icon = new ImageIcon(this.getClass().getResource(this.url3));
        setIcon(icon);
    }
    private void posicion(){
        if(this.getX() < -1){
            this.reiniciarPosicion();
        }
        if(this.getY() > 299){
            this.reiniciarPosicion();
        }
    }
    public void reiniciarPosicion(){
        int posX = (int)(Math.random() * ((500 - 250) + 250)) + 250;
        setLocation(posX, 177);
    }

    public void gravedad(int tiempo, int presion){
        try{
            Thread.sleep(tiempo);
            setLocation(this.getX(), (this.getY() + presion));
        }catch(Exception ex){}
    }
}
