import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.*;
public class Ventana extends JFrame{
    public Ventana(){
        initValues();
    }//end constructor
    public void initValues(){
        Vidas vida = new Vidas(3);
        Fondo fondo = new Fondo();
        Mario mario = new Mario();
        Piso [] piso = new Piso[3];
        Tuberia [] tuberia = new Tuberia[3];
        JButton btnStart = new JButton("Start");
        Animacion animacion = new Animacion(mario, fondo, vida, btnStart);
        
        int posXTu = 210, posXP = 0;

        //inicializamos los elementos
        
        for(int i = 0; i < 3; i++){
            tuberia[i] = new Tuberia();
            piso[i] = new Piso();
            piso[i].setBounds(posXP, 208, 130, 32);
            posXP += 210;
            tuberia[i].setBounds(posXTu, 177, 30, 31);
            posXTu += 50;

            animacion.piso[i] = piso[i];
            animacion.tuberia[i] = tuberia[i];
        }
        vida.setBounds(410, 0, 50, 30);
        fondo.setBounds(0,0,3373,240);
        mario.setBounds(5, 177, 19,34);
        btnStart.setBounds(0,241, 75,25);
        
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
            add(piso[i]);
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
