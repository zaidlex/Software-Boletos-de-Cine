import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.sql.*;
import java.sql.SQLException;

public class InterfazCartelera {
    JFrame intCartelera;//interfaz principal 
    JPanel panelBienvenida;//panel de bienvenida
    JPanel panelCartelera;//panel donde estaran los botones de las peliculas

    private JButton botonesCartelera[];//botones para la cartelera
    private JLabel nombreEmpresa;//Nombre de la empresa
    private JLabel tiempoActual;//Nombre de la empresa
    private int width = 1000;//tamano en ancho
    private int height = 700;//tamano en alto

    public void initInterfazCartelera() {//Constructor
        intCartelera = new JFrame();
        panelCartelera = new JPanel();
        panelBienvenida = new JPanel();

        panelCartelera.setBackground(Color.LIGHT_GRAY);
        panelBienvenida.setBackground(Color.CYAN);

        startInterfazCartelera();//inicia la interfaz
        startPanels();//inicia los paneles
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

    private void startItems(){//inicializa los items
        nombreEmpresa = new JLabel("Bienvenido a Cinema de Arte");
        nombreEmpresa.setBounds(300,40,400,30);
        tiempoActual = new JLabel(getTime());
        tiempoActual.setBounds(750,160,150,30);
    }

    private void addCartelera() {//inicializa los botones (Peliculas)
        // Crea el directorio de los posters
        File directory = new File("Posters");
        if (! directory.exists())
            directory.mkdir();
        
        Connection con = null;
        Statement stmt = null;
        ResultSet resultQuery = null;

        try{
            int NumMovies = 0;
            int numMovie = 0;
            int x_Button = 20;
            //Aquí se creará los botones para cada película (lista)
            Class.forName("com.mysql.cj.jdbc.Driver"); // carga del driver
            // conexion a la base de datos
            con = DriverManager.getConnection("jdbc:mariadb://localhost/Cine?" +
                "user=UsrCine&password=c!n3#8732");
            stmt = con.createStatement();
            resultQuery = stmt.executeQuery("select * from Poster");// obtine el nombre del poster de cada pelicula
            
            if (resultQuery.last()) {
                NumMovies = resultQuery.getRow();
                resultQuery.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
            }

            botonesCartelera = new JButton[NumMovies];

            while(resultQuery.next()){
                System.out.println(resultQuery.getInt(1)+"  "+resultQuery.getString("Poster_pel"));
                // Boton con imagen
                botonesCartelera[numMovie] = new JButton( resizeImagen("Posters/"+resultQuery.getString("Poster_pel") ));
                botonesCartelera[numMovie].setBounds(x_Button, 20, 300, 400);
                numMovie++;
                x_Button += 420; // 400 de la imagen-boton y 20 de espacio entre botones
            }
        }catch(SQLException err){
            System.out.println(err.getMessage());
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }finally{
            if (resultQuery != null){
                try {
                    resultQuery.close();
                } catch (SQLException sqlEx) { } // ignore
            }
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore
            }
            if (con != null){
                try {
                    con.close();
                } catch (SQLException sqlEx) { } // ignore
            }
        }    
    }

    private void addItems(){//agrega los items a los paneles
        panelBienvenida.add(nombreEmpresa);
        panelBienvenida.add(tiempoActual);
        for(int boton = 0; boton < botonesCartelera.length; boton++){ // agrega los botones al jpanel
            panelCartelera.add(botonesCartelera[boton]);
        }
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

    private ImageIcon resizeImagen(String Path){
        ImageIcon imageIcon = new ImageIcon(Path); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(300, 400,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        return  new ImageIcon(newimg);  // transform it back
    }
}
