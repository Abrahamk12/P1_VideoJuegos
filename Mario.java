import javax.swing.ImageIcon;
import javax.swing.JLabel;
public class Mario extends JLabel{
    
    String [] url_1 = {"/Sprites/Mario/MarioD_0.png", "/Sprites/Mario/MarioD_1.png", "/Sprites/Mario/MarioD_2.png", "/Sprites/Mario/MarioD_3.png"};
    String url_3 = "/Sprites/Mario/MarioD_J.png";
    String [] url2_1 = {"/Sprites/Mario/MarioI_0.png", "/Sprites/Mario/MarioI_1.png", "/Sprites/Mario/MarioI_2.png", "/Sprites/Mario/MarioI_3.png"};
    String url2_3 = "/Sprites/Mario/MarioI_J.png";
    ImageIcon icon;

    public Mario(){
        //Icon
        icon = new ImageIcon(this.getClass().getResource(this.url_1[0]));
        setIcon(icon);
    }

    public void marioDerecha(int i){
        icon = new ImageIcon(this.getClass().getResource(this.url_1[i]));
        setIcon(icon);
    }
    public void marioIzquierda(int i){
        icon = new ImageIcon(this.getClass().getResource(this.url2_1[i]));
        setIcon(icon);
    }
    
    public void marioBrincaDerecha(){
        icon = new ImageIcon(this.getClass().getResource(this.url_3));
        setIcon(icon);
    }
    
    public void marioBrincaIzquierda(){
        icon = new ImageIcon(this.getClass().getResource(this.url2_3));
        setIcon(icon);
    }

    public void gravedad(int tiempo, int presion){
        try{
            Thread.sleep(tiempo);
            setLocation(this.getX(), (this.getY() + presion));
        }catch(Exception ex){}
    }

    public void reiniciarPosicion(int posX){
        if(this.getY() > 210){
            setLocation(posX, 177);
        }else if(this.getX() == 0){
            setLocation(posX, 177);
        }
    }

}
