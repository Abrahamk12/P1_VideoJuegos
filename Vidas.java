import javax.swing.JLabel;
public class Vidas extends JLabel{
    public Vidas(int vida){
        setText("Vidas: " + Integer.toString(vida));
    }
    public void setVida(int vida){
        setText("Vidas: " + Integer.toString(vida));
    }
}
