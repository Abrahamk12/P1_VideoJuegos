import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.*;
public class Ventana extends JFrame{
    public Ventana(){
        initValues();
    }//end constructor
    public void initValues(){
        Fondo fondo = new Fondo();
        Mario mario = new Mario(fondo);
        JButton btnStart = new JButton("Start");
        //inicializamos los elementos
        fondo.setBounds(0,0,510,72);
        btnStart.setBounds(10, 80, 75, 25);

        //asignamos el focus dentro de la pantalla
        btnStart.setFocusable(false);

        //Action Listener
        ActionListener listener = new ActionListener(){
            public void actionPerformed(ActionEvent e1){
                btnStart.setEnabled(false);
                Thread thread = new Thread();
                thread.start();
            }// end actionmPerformed
        };

        //agregamos los elementos al pantalla
        add(fondo);
        add(mario);

        setTitle("Proyecto 1");
        setSize(500,305);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }//end initValues
}
