import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
import java.io.File;
import java.sql.*;

public class InterfazCartelera {
    JFrame intCartelera;//interfaz principal 
    JPanel panelBienvenida;//panel de bienvenida
    JPanel panelCartelera;//panel donde estaran los botones de las peliculas

    private JButton botonesCartelera;//botones para la cartelera
    private JLabel nombreEmpresa;//Nombre de la empresa
    private JLabel tiempoActual;//Nombre de la empresa
    private int width = 1000;//tamano en ancho
    private int height = 700;//tamano en alto

    public void initInterfazCartelera(){//Constructor
        intCartelera = new JFrame();
        panelCartelera = new JPanel();
        panelBienvenida = new JPanel();

        startInterfazCartelera();//inicia la interfaz
        startPanels();//inicia los paneles
        
        startDatabase();//inicia la coneccion a la base de datos
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

    private void startPanels(){//Caracteristicas del panel bienvenida
        intCartelera.getContentPane().add(panelBienvenida);
        panelBienvenida.setBounds(0, 0, width, 200);//(0,0) a (1000,200)
        intCartelera.getContentPane().add(panelCartelera);
        panelCartelera.setBounds(0, 200, width, height);//(0,200) a (1000, 700)
    }

    private void startDatabase(){//Crea la coneccion con la base de datos

    }

    private void startItems(){//inicializa los items
        nombreEmpresa = new JLabel("Bienvenido a 'Nombre de la empresa'");
        nombreEmpresa.setBounds(300,40,400,30);
        tiempoActual = new JLabel(getTime());
        tiempoActual.setBounds(750,160,150,30);
    }

    private void addCartelera(){//inicializa los botones (Peliculas)
        // Crea el directorio de los posters
        File directory = new File("Posters");
        if (! directory.exists())
            directory.mkdir();

        try{
            //Aquí se creará los botones para cada película (lista)
            Class.forName("com.mysql.cj.jdbc.Driver"); // carga del driver
            // conexion a la base de datos
            Connection con=
            DriverManager.getConnection("jdbc:mysql://brvqbhfwy3arpy8r6ohh-mysql.services.clever-cloud.com:3306/brvqbhfwy3arpy8r6ohh","uc5scfgt2fjo1slh","EYSv8RtiGMjckq8fqAN1");

            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from prueba");
            
            while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(2));

            botonesCartelera = new JButton("Poster");
            //botonesCartelera = new JButton(new ImageIcon("D:\\icon.png"));//boton con imagen
            botonesCartelera.setBounds(20, 20, 300, 400);
            con.close();
        }catch(Exception e){ System.out.println(e);}    
    }

    private void addItems(){//agrega los items a los paneles
        panelBienvenida.add(nombreEmpresa);
        panelBienvenida.add(tiempoActual);
        panelCartelera.add(botonesCartelera);
    }
/*
    private void viewMovie(int idPelicula){//Inicia la interfaz de pelicula
        //llama a la interfaz de pelicula al hacer click al boton correspondiente
        //la interfaz principal no desaparece ni se cierra
        InterfazPelicula IPelicula;
        IPelicula = new InterfazPelicula();
        IPelicula.initInterfazPelicula(idPelicula);
    }
*/
    private String getTime(){//ontiene el tiempo actual
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        return dtf.format(now).toString();
    }
}
