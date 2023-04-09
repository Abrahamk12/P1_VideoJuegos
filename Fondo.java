import javax.swing.ImageIcon;
import javax.swing.JLabel;
public class Fondo extends JLabel{
    private String url = "/Sprites/bg.png";
    private ImageIcon icon;
    public Fondo(){
        //Icon
        icon = new ImageIcon(this.getClass().getResource(this.url));
        setIcon(icon);
    }
    //Movimiento de la imagen
    public void mover_fondo(int x){
        if(this.getX() != 110){
            icon = new ImageIcon(this.getClass().getResource(this.url));
            setIcon(icon);
            setLocation(this.getX() - x, 0);
        }
        //Movemos el fondo
        setLocation(this.getX() - x, 0);
    }
    public ImageIcon getIcon(){
        return this.icon;
    }
}