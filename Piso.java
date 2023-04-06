import javax.swing.ImageIcon;
import javax.swing.JLabel;
public class Piso extends JLabel{
    String url = "/Sprites/piso.png";
    ImageIcon icon;
    public Piso(){
        //Icon
        icon = new ImageIcon(this.getClass().getResource(this.url));
        setIcon(icon);
    }
    public void mover_piso(int x){
        if(this.getX() != 110){
            icon = new ImageIcon(this.getClass().getResource(this.url));
            setIcon(icon);
            setBounds(this.getX() - x, 208, 240, 32);
        }
        //Movemos el fondo
        setBounds(this.getX() - x, 208, 240, 32);
    }
}
