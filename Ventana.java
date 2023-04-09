import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.*;
public class Ventana extends JFrame{
    public Ventana(){
        initValues();
    }//end constructor
    public void initValues(){
        Piso piso = new Piso();
        Vidas vida = new Vidas(3);
        Fondo fondo = new Fondo();
        Mario mario = new Mario();
        Tortuga [] tortuga = new Tortuga[3];
        Tuberia [] tuberia = new Tuberia[3];
        Champiñon [] champiñon = new Champiñon[3];
        JButton btnStart = new JButton("Start");
        Animacion animacion = new Animacion(mario, piso, fondo, tortuga, champiñon, tuberia, vida, btnStart);
        
        int posXTu = 210, posXC = 115, posXT = 501;

        //inicializamos los elementos
        
        for(int i = 0; i < 3; i++){
            tuberia[i] = new Tuberia();
            tortuga[i] = new Tortuga();
            champiñon[i] = new Champiñon();
            tortuga[i].setBounds(posXT, 186, 15, 24);
            posXT += 20;
            tuberia[i].setBounds(posXTu, 177, 30, 31);
            posXTu += 50;
            champiñon[i].setBounds(posXC, 191, 16, 16);
            posXC += 20;
        }
        vida.setBounds(410, 0, 50, 30);
        fondo.setBounds(0,0,3373,240);
        mario.setBounds(5, 177, 19,34);
        btnStart.setBounds(0,241, 75,25);
        piso.setBounds(0, 208, 3373, 32);
        //asignamos el focus dentro de la pantalla
        animacion.setFocusable(true);
        btnStart.setFocusable(false);

        //Action Listener
        ActionListener listener = new ActionListener(){
            public void actionPerformed(ActionEvent e1){
                btnStart.setEnabled(false);
                animacion.getSonidoP().play();
                Thread thread = new Thread(animacion);
                thread.start();
            }// end actionmPerformed
        };

        //Agregamos los actionListener
        animacion.addKeyListener(animacion);
        btnStart.addActionListener(listener);

        //agregamos los elementos al pantalla
        add(vida);
        add(mario);
        for(int i = 0; i < 3; i++){
            add(tortuga[i]);
            add(champiñon[i]);
            add(tuberia[i]);
        }
        add(piso);
        add(fondo);
        add(animacion);
        add(btnStart);

        setTitle("Proyecto 1");
        setSize(500,305);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }//end initValues
}
