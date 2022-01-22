import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Image;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.sql.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class InterfazCartelera {
    private JFrame intCartelera;//interfaz principal 
    private JPanel panelBienvenida;//panel de bienvenida
    private JPanel panelCartelera;//panel donde estaran los botones de las peliculas
    JScrollPane panelScroll;

    private JButton botonesCartelera[];//botones para la cartelera
    private JLabel nombreEmpresa;//Nombre de la empresa
    private JLabel tiempoActual;//Nombre de la empresa
    private int width = 1000;//tamano en ancho
    private int height = 700;//tamano en alto

    public void initInterfazCartelera() {//Constructor
        intCartelera = new JFrame();
        panelCartelera = new JPanel();
        panelBienvenida = new JPanel();

        startInterfazCartelera();//inicia la interfaz
        startPanels();//inicia los paneles
        startItems();//inicia los items (labels, buttons, etc...)
        initCartelera();//agrega los botones de las peliculas al panel de la cartelera
        addItems();//agrega los items al panel bienvenida
        startPanelScroll();

        intCartelera.add(panelBienvenida);
        intCartelera.add(panelScroll);
        intCartelera.repaint();
    }

    private void startInterfazCartelera(){//Caracteristicas de la interfaz de la cartelera
        intCartelera.setSize(width, height);
        intCartelera.setTitle("Cartelera");
        intCartelera.setResizable(false);
        //al hacer click en la X cierra todo
        intCartelera.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        intCartelera.setVisible(true);
    }

    private void startPanels(){//Caracteristicas del panel bienvenida
        panelBienvenida.setBackground(Color.GRAY);
        panelBienvenida.setSize(width, 200);
        panelBienvenida.setBounds(0, 0, width, 200);//(0,0) a (1000,200)
        panelBienvenida.setLayout(null);
        
        panelCartelera.setBackground(Color.LIGHT_GRAY);
        panelCartelera.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
    }

    private void startPanelScroll(){
        panelScroll = new JScrollPane();
        panelScroll.setViewportView(panelCartelera);

        panelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelScroll.setSize(width, 470);
        panelScroll.setBounds(0, 200, width, 470); // -30 por el scroll
    }

    private void startItems(){//inicializa los items
        nombreEmpresa = new JLabel("Bienvenido a Cinema de Arte");
        nombreEmpresa.setBounds(300,40,400,30);
        tiempoActual = new JLabel(getTime());
        tiempoActual.setBounds(750,160,150,30);
    }

    private void initCartelera() {//inicializa los botones (Peliculas)
        // conexiones a la base de datos
        Connection con = null;
        Statement stmt = null;
        ResultSet resultQuery = null;
        String PosterPath="";
        //obtiene fecha actual
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String fecha_actual = formatter.format(date).toString();
        System.out.println("SELECT * FROM Cartelera WHERE '"+fecha_actual+"' BETWEEN Extreno AND FinCartelera");

        try{
            int NumMovies = 0;
            int numMovie = 0;
            //Aquí se creará los botones para cada película (lista)
            Class.forName("com.mysql.cj.jdbc.Driver"); // carga del driver
            // conexion a la base de datos
            con = DriverManager.getConnection("jdbc:mariadb://localhost/Cine?" +
                "user=UsrCine&password=c!n3#8732");
            stmt = con.createStatement();
            //"SELECT * FROM Cartelera WHERE '"+fecha_actual+"' BETWEEN Extreno AND FinCartelera"
            resultQuery = stmt.executeQuery("SELECT * FROM Cartelera WHERE '"+fecha_actual+"' BETWEEN Extreno AND FinCartelera");// obtine el nombre del poster de cada pelicula
            
            if (resultQuery.last()) {
                NumMovies = resultQuery.getRow();
                resultQuery.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
            }

            // iniciando los botones para los posters
            botonesCartelera = new JButton[NumMovies];
    
            while(resultQuery.next()){
                PosterPath = "Posters/"+resultQuery.getString("Poster_pel");
                // Crea el boton con imagen
                botonesCartelera[numMovie] = new JButton( 
                    resizeImagen( PosterPath)
                    );
                
                botonesCartelera[numMovie].addActionListener(new PosterListener(resultQuery.getInt("IDPelicula"),PosterPath, intCartelera));  
                botonesCartelera[numMovie].setBackground(Color.LIGHT_GRAY);
                botonesCartelera[numMovie].setOpaque(true);
                botonesCartelera[numMovie].setBorderPainted(false);
                numMovie++;
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

    private String getTime(){//ontiene el tiempo actual
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
        LocalDateTime now = LocalDateTime.now();  
        return dtf.format(now).toString();
    }

    private ImageIcon resizeImagen(String Path){
        ImageIcon imageIcon = new ImageIcon(Path); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(320, 400,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        return  new ImageIcon(newimg);  // transform it back
    }
}
