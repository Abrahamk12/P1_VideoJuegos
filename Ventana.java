import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.*;
public class Ventana extends JFrame{
    public Ventana(){
        initValues();
    }//end constructor
    public void initValues(){
        Fondo fondo = new Fondo();
        Mario mario = new Mario();
        Piso [] pisos = new Piso[3];
        Tortuga [] tortuga = new Tortuga[3];
        Tuberia [] tuberia = new Tuberia[3];
        Champiñon [] champiñon = new Champiñon[3];
        Animacion animacion = new Animacion(mario, pisos, fondo, tortuga, champiñon, tuberia);
        JButton btnStart = new JButton("Start");

        int posXP = 0, posXTu = 210, posXC = 115, posXT = 501, posXB = 35;

        //inicializamos los elementos
        fondo.setBounds(0,0,3373,240);
        mario.setBounds(5, 177, 19,34);
        btnStart.setBounds(0,241, 75,25);
        for(int i = 0; i < 10; i++){
            
        }
        for(int i = 0; i < 3; i++){
            pisos[i] = new Piso();
            pisos[i].setBounds(posXP, 208, 240, 32);
            posXP += 290;
            tuberia[i] = new Tuberia();
            tuberia[i].setBounds(posXTu, 177, 30, 31);
            posXTu += 80;
            tortuga[i] = new Tortuga();
            tortuga[i].setBounds(posXT, 186, 15, 24);
            posXT += 20;
            champiñon[i] = new Champiñon();
            champiñon[i].setBounds(posXC, 191, 16, 16);
            posXC += 20;
        }

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
        add(mario);
        for(int i = 0; i < 3; i++){
            add(pisos[i]);
            add(tortuga[i]);
            add(champiñon[i]);
            add(tuberia[i]);
        }
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
