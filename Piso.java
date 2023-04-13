import javax.swing.ImageIcon;
import javax.swing.JLabel;
public class Piso extends JLabel{
   // String url = "/Sprites/piso.png";
    String url = "/Sprites/blque.png";
    ImageIcon icon;
    public Piso(){
        //Icon
        icon = new ImageIcon(this.getClass().getResource(this.url));
        setIcon(icon);
    }
    public void mover_piso(int x){
        if(this.getX() != 250){
            icon = new ImageIcon(this.getClass().getResource(this.url));
            setIcon(icon);
            setLocation(this.getX() - x, 208);
        }
        //Movemos el fondo
        setLocation(this.getX() - x, 208);
    }
    public void reiniciarPosicion(){
        int posX = (int)(Math.random() * ((500 - 250) + 250)) + 250;
        setLocation(posX, 208);
    }
}
