import javax.swing.ImageIcon;
import javax.swing.JLabel;
public class Mario extends JLabel{
    Fondo fondo;
    
    String [] url_1 = {"/Sprites/Mario/MarioD_0.png", "/Sprites/Mario/MarioD_1.png", "/Sprites/Mario/MarioD_2.png", "/Sprites/Mario/MarioD_3.png"};
    String url_3 = "/Sprites/Mario/MarioD_J.png";
    String [] url2_1 = {"/Sprites/Mario/MarioI_0.png", "/Sprites/Mario/MarioI_1.png", "/Sprites/Mario/MarioI_2.png", "/Sprites/Mario/MarioI_3.png"};
    String url2_3 = "/Sprites/Mario/MarioI_J.png";
    ImageIcon icon;

    int caminarP = 0, caminarI = 1, posX, posY;

    public Mario(Fondo fondo){
        this.fondo = fondo;
        //Icon
        icon = new ImageIcon(this.getClass().getResource(this.url_1[0]));
        setIcon(icon);
    }

    public void moverMario(int posX, int posY, int poder, boolean derecha, boolean brincar){
        if(brincar){
            int up = posY -= poder;
            if(up != 147){
                try{
                    Thread.sleep(50);
                    if(derecha){
                        if(caminarP == 3){
                            caminarP = 0;
                        }else{
                            caminarP++;
                        }
                        icon = new ImageIcon(this.getClass().getResource(this.url_3));
                        if(posX != 250){
                            setIcon(icon);
                            setLocation((posX += poder), up);
                        }else{
                            setIcon(icon);
                            fondo.mover_fondo(poder);
                        }
                    }else{
                        if(caminarP == 3){
                            caminarP = 0;
                        }else{
                            caminarP++;
                        }
                        icon = new ImageIcon(this.getClass().getResource(this.url2_3));
                        setIcon(icon);
                        setLocation((posX -= poder), up);
                    }
                }catch(Exception ex){}
            }
        }else{
            if(derecha){
                if(caminarP == 3){
                    caminarP = 0;
                }else{
                    caminarP++;
                }
                icon = new ImageIcon(this.getClass().getResource(this.url_1[caminarP]));
                if(posX != 250){
                    setIcon(icon);
                    setLocation((this.posX += poder), posY);
                }else{
                    setIcon(icon);
                    setLocation((this.posX += poder), posY);
                    fondo.mover_fondo(poder);
                }
                
            }else{
                if(caminarI == 3){
                    caminarI = 0;
                }else{
                    caminarI++;
                }
                icon = new ImageIcon(this.getClass().getResource(this.url2_1[caminarI]));
                setIcon(icon);
                setLocation((posX -= poder), posY);
            }
        }
        
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
