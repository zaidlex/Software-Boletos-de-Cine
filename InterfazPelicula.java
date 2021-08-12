import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InterfazPelicula {
    JFrame intPelicula;
    JPanel panelPelicula;

    private int width = 700;
    private int height = 400;

    public void InterfazPelicula(){//constructor de la interfaz
        intPelicula = new JFrame();
        panelPelicula = new JPanel();

        startInterfazCartelera();//inicia la interfaz
        startPanel();//inicia los paneles
    }
    
    private void startInterfazCartelera(){//Caracteristicas de la interfaz de la cartelera
        intPelicula.setSize(width, height);
        intPelicula.setLayout(null);
        intPelicula.setResizable(false);
        intPelicula.setVisible(true);
    }

    private void startPanel(){//Caracteristicas del panel principal
        intPelicula.getContentPane().add(panelCartelera);
        panelCartelera.setBounds(0, 0, width, height);
    }
}
