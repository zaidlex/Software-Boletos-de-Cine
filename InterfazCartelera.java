import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

public class InterfazCartelera {
    JFrame intCartelera;//interfaz principal 
    JPanel panelBienvenida;//panel de bienvenida
    JPanel panelCartelera;//panel donde estaran los botones de las peliculas

    private JButton botonesCartelera;//botones para la cartelera
    private JLabel nombreEmpresa;//Nombre de la empresa
    private int width = 1000;//tamano en ancho
    private int height = 700;//tamano en alto

    public void InterfazCartelera(){//Constructor
        intCartelera = new JFrame();
        panelCartelera = new JPanel();
        panelBienvenida = new JPanel();

        startInterfazCartelera();//inicia la interfaz
        startPanel();//inicia los paneles
        
        startItems();//inicia los items (labels, buttons, etc...)
        addCartelera();//agrega los botones de las peliculas al panel de la cartelera
        addItems();//agrega los items al panel bienvenida
    }

    private void startInterfazCartelera(){//Caracteristicas de la interfaz de la cartelera
        intCartelera.setSize(width, height);
        intCartelera.setLayout(null);
        intCartelera.setResizable(false);
        //al hacer click en la X cierra todo
        intCartelera.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        intCartelera.setVisible(true);
    }

    private void startPanel(){//Caracteristicas del panel bienvenida
        intCartelera.getContentPane().add(panelBienvenida);
        panelBienvenida.setBounds(0, 0, width, 200);
    }

    private void startItems(){//inicializa los items
        nombreEmpresa = new JLabel("Bienvenido a 'Nombre de la empresa'");
        nombreEmpresa.setBounds(40,40,200,30);
    }

    public void addCartelera(){//inicializa los botones (Peliculas)
        //Aquí se creará los botones para cada película (lista)
        intCartelera.getContentPane().add(panelCartelera);
        panelCartelera.setBounds(0, 200, width, height);
        botonesCartelera = null;
    }

    public void addItems(){//agrega los items al panel bienvenida
        panelBienvenida.add(nombreEmpresa);
        panelBienvenida.add(botonesCartelera);
    }

    private void viewMovie(){//Inicia la interfaz de pelicula
        //llama a la interfaz de pelicula al hacer click al boton correspondiente
        //la interfaz principal no desaparece ni se cierra
        InterfazPelicula IPelicula;
        IPelicula = new InterfazPelicula();
    }
}
