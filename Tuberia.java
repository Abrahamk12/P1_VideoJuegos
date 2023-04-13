import javax.swing.ImageIcon;
import javax.swing.JLabel;
public class Tuberia extends JLabel{
    private String url = "/Sprites/Tuberia/Tuberia_0.png";
    private ImageIcon icon;
    public Tuberia(){
        //Icon
        icon = new ImageIcon(this.getClass().getResource(this.url));
        setIcon(icon);
    }
    public void mover_tuberia(int x){
        if(this.getX() != 110){
            icon = new ImageIcon(this.getClass().getResource(this.url));
            setIcon(icon);
            setLocation(this.getX() - x, 177);
        }
        //Movemos el fondo
        setLocation(this.getX() - x, 177);
    }
    public void reiniciarPosicion(){
        int posX = (int)(Math.random() * ((500 - 250) + 250)) + 250;
        setLocation(posX, 188);
    }
    public ImageIcon getIcon(){
        return this.icon;
    }
}
