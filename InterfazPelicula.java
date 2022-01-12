import javax.swing.JFrame;
//import javax.swing.JButton;
//import javax.swing.JLabel;
import javax.swing.JPanel;

public class InterfazPelicula {
    JFrame intPelicula;
    JPanel panelInfoPelicula;

    private int idPelicula;
    private String Nombre;
    private String Poster;
    private String Sipnosis;
    private String Director;
    private String Actores;
    private String Distribuidora;
    private String Duracion;
    private String Clasificacion;
    private String Descripcion;

    private int width = 700;
    private int height = 400;

    public void initInterfazPelicula(int idPelicula){//constructor de la interfaz
        this.idPelicula = idPelicula;
        intPelicula = new JFrame();
        panelInfoPelicula = new JPanel();

        startInterfazCartelera();//inicia la interfaz
        startPanel();//inicia los paneles
        System.out.println(idPelicula);
    }
    
    private void startInterfazCartelera(){//Caracteristicas de la interfaz de la cartelera
        intPelicula.setSize(width, height);
        intPelicula.setTitle("Película");;
        intPelicula.setResizable(false);
        intPelicula.setVisible(true);
    }

    private void startPanel(){//Caracteristicas del panel principal
        intPelicula.getContentPane().add(panelInfoPelicula);
        panelInfoPelicula.setBounds(0, 0, width, height);
    }
}
