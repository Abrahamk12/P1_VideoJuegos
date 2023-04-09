import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Tortuga extends JLabel implements Runnable{
    String [] url = {"/Sprites/Tortuga/tortuga_0.png", "/Sprites/Tortuga/tortuga_1.png"};
    String [] url2 = {"/Sprites/Tortuga/tortugaD_0.png", "/Sprites/Tortuga/tortugaD_1.png"};
    String url3 = "/Sprites/Tortuga/tortuga.png";
    ImageIcon icon;
    int rapidez;
    boolean derecha = false;
    public Tortuga(){
        //Icon
        icon = new ImageIcon(this.getClass().getResource(this.url[0]));
        setIcon(icon);
    }
    public void run(){
        moverTortuga();
    }//end run
    public void moverTortuga(){
        while(true){
            //Control de fotogramas del champiñon
            try{
                Thread.sleep(50);
                
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
    }

    public void tortugaMuerta(){
        icon = new ImageIcon(this.getClass().getResource(this.url3));
        setIcon(icon);
    }
    
    public void reiniciarPosicion(int posX){
        setLocation(502 + posX, 188);
    }

    public void gravedad(int tiempo, int presion){
        try{
            Thread.sleep(tiempo);
            setLocation(this.getX(), (this.getY() + presion));
        }catch(Exception ex){}
    }
}
