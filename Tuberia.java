import javax.swing.ImageIcon;
import javax.swing.JLabel;
public class Tuberia extends JLabel{
    private String [] url = {"/Sprites/Tuberia/Tuberia_0.png", "/Sprites/Tuberia/Tuberia_1.png", "/Sprites/Tuberia/Tuberia_2.png"};
    private ImageIcon icon;
    public Tuberia(){
        //Icon
        icon = new ImageIcon(this.getClass().getResource(this.url[0]));
        setIcon(icon);
    }
}
