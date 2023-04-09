import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Champiñon extends JLabel implements Runnable{
    String [] url = {"/Sprites/Champ/champ_0.png", "/Sprites/Champ/champ_1.png"};
    String url2 = "/Sprites/Champ/champ.png";
    boolean derecha = false;
    ImageIcon icon;
    int rapidez;
    public Champiñon(){
        //Icon
        icon = new ImageIcon(this.getClass().getResource(this.url[0]));
        setIcon(icon);
    }
    public void run(){
        moverChamp();
    }//end rund
    public void moverChamp(){
        while(true){
            //Control de fotogramas del champiñon
            try{
                Thread.sleep(50);
                if(this.getX() % 2 != 0){
                    icon = new ImageIcon(this.getClass().getResource(this.url[0]));
                    setIcon(icon);
                }else{
                    icon = new ImageIcon(this.getClass().getResource(this.url[1]));
                    setIcon(icon);
                }
                if(derecha){
                    setLocation(this.getX() + rapidez, this.getY());
                }else{
                    setLocation(this.getX() - rapidez, this.getY());
                }
                
            }catch(Exception ex){}
        }
    }

    public void champiñonMuerto(){
        icon = new ImageIcon(this.getClass().getResource(this.url2));
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
