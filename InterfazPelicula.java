import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import javax.swing.JPanel;
import java.sql.*;
import java.sql.SQLException;

public class InterfazPelicula {
    JFrame intPelicula;
    JFrame intCartelera;
    JPanel panelInfoPelicula;
    panelSala panelSalaPelicula;

    JLabel lNombre, lPoster, lSipnosis, lDirector, lActores, lDistribuidoras, lDuracion, lClasificacion, lGeneros[];

    private int idPelicula;
    private String Nombre, Poster, Sipnosis, Director, Actores, Distribuidora, Duracion, Clasificacion, Descripcion;
    private String[] Generos, GenerosDesc;

    private int width = 1000;
    private int height = 700;

    public void initInterfazPelicula(int idPelicula, String Poster, JFrame intCartelera){//constructor de la interfaz
        this.idPelicula = idPelicula;
        this.Poster = Poster;
        this.intCartelera = intCartelera;
        getDatos();

        intPelicula = new JFrame();
        panelInfoPelicula = new JPanel();

        startInterfazCartelera();//inicia la interfaz
        startPanels();//inicia los paneles
        startItemsInfoPelicula();
        addItems();

        intPelicula.add( panelInfoPelicula );
        intPelicula.add( panelSalaPelicula );
    }
    
    private void startInterfazCartelera(){//Caracteristicas de la interfaz de la cartelera
        intPelicula.setSize(width, height);
        intPelicula.setTitle("Película");;
        intPelicula.setResizable(false);
        intPelicula.setVisible(true);
        intPelicula.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        intPelicula.addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(intPelicula, 
                    "¿Quieres salir?", "¿Cerrar ventana?", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                        intCartelera.setEnabled(true);
                        intPelicula.dispose();
                }
            }

            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
    }

    private void startPanels(){//Caracteristicas del panel principal
        int heigthPanelInfo = 340;
        panelInfoPelicula.setSize(width, heigthPanelInfo);
        panelInfoPelicula.setBounds(0, 0, width, heigthPanelInfo);
        panelInfoPelicula.setBackground(Color.GRAY);
        panelInfoPelicula.setLayout(null);
        panelSalaPelicula = new panelSala( idPelicula );
    }

    private void startItemsInfoPelicula(){// size panel 1000 x 450
        lNombre = new JLabel( Nombre ); // nombre de la pelicula
        lNombre.setBounds(350, 20, 600, 40);
        lNombre.setFont(new Font( "Serif", Font.BOLD, 20 ));
 
        lPoster = new JLabel( resizeImagen( Poster ) ); // imagen del poster de tamaño 240, 300
        lPoster.setBounds(25, 20, 240, 300);

        lClasificacion = new JLabel( Clasificacion );
        if(Clasificacion.compareTo("B15")  == 0){
            lClasificacion.setBounds(350, 70, 28, 20 );
        }else{ 
            lClasificacion.setBounds(350, 70, 10, 20 ); 
        }
        lClasificacion.setBackground(Color.decode("#33A8FF"));
        lClasificacion.setOpaque(true);
        lClasificacion.setToolTipText( Descripcion ); // descripción de la clasificación

        lDuracion = new JLabel( Duracion ); // duracion de la pelicula
        lDuracion.setBounds(380, 70, 70, 20);

        // crea las etiquetas de las categorias
        lGeneros = new JLabel[Generos.length];
        for(int inx = 0; inx < Generos.length; inx++){
            lGeneros[inx] = new JLabel(Generos[inx]);
            lGeneros[inx].setBounds(500 +(70*inx), 70, 80, 20);
            lGeneros[inx].setToolTipText(GenerosDesc[inx]);
        }

        lSipnosis = new JLabel();
        lSipnosis.setText("<html><strong>Sipnosis: </strong>"+ Sipnosis +"</html>");
        lSipnosis.setBounds(350, 100, 500, 80);

        lActores = new JLabel(  );
        lActores.setText("<html><strong>Actores: </strong>"+ Actores +"</html>");
        lActores.setBounds(350, 200, 500, 40);

        lDirector = new JLabel( Director );
        lDirector.setText("<html><strong>Director: </strong>"+ Director +"</html>");
        lDirector.setBounds(350, 260, 200, 60);

        lDistribuidoras = new JLabel( Distribuidora ); // nombre de la distribuidora
        lDistribuidoras.setText("<html><strong>Distribuidora: </strong>"+ Distribuidora +"</html>");
        lDistribuidoras.setBounds(560, 260, 290, 60);
    }

    private void addItems(){
        panelInfoPelicula.add( lNombre );
        panelInfoPelicula.add( lPoster );
        panelInfoPelicula.add( lSipnosis );
        panelInfoPelicula.add( lDirector );
        panelInfoPelicula.add( lActores );
        panelInfoPelicula.add( lDistribuidoras );
        panelInfoPelicula.add( lDuracion );
        panelInfoPelicula.add( lClasificacion );
        for (int inx = 0; inx < lGeneros.length; inx++)
            panelInfoPelicula.add(lGeneros[inx]);
    }

    private void getDatos(){
        Connection con = null;
        Statement stmt = null;
        ResultSet resultQuery = null;
        ResultSet resultQueryGeneros = null;
        
        try{
            //Aquí se creará los botones para cada película (lista)
            Class.forName("com.mysql.cj.jdbc.Driver"); // carga del driver
            // conexion a la base de datos
            con = DriverManager.getConnection("jdbc:mariadb://localhost/Cine?" +
                "user=UsrCine&password=c!n3#8732");
            stmt = con.createStatement();
            resultQuery = stmt.executeQuery(// obtine el nombre del poster de cada pelicula
                "SELECT * FROM `PeliculaINFO` WHERE IDPelicula = "+String.valueOf(this.idPelicula)
                );
    
            if(resultQuery.next()){ // informacion de la pelicula
                this.Nombre = resultQuery.getString("Nombre_Pelicula");
                this.Sipnosis = resultQuery.getString("Sipnosis");
                this.Director = resultQuery.getString("Director");
                this.Actores = resultQuery.getString("Actores");
                this.Distribuidora = resultQuery.getString("Distribuidora");
                this.Duracion = resultQuery.getString("Duracion");
                this.Clasificacion = resultQuery.getString("Clasificacion");
                this.Descripcion = resultQuery.getString("Descripcion");
            }

            // Obtiene las categorias de la pelicula 
            resultQueryGeneros = stmt.executeQuery("SELECT * FROM PeliculaGENERO WHERE IDPelicula = "+String.valueOf(this.idPelicula) );
            if (resultQueryGeneros.last()) {
                Generos = new String[resultQueryGeneros.getRow()];
                GenerosDesc = new String[resultQueryGeneros.getRow()];
                resultQueryGeneros.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
            }
            for (int inx = 0; resultQueryGeneros.next(); inx++){
                Generos[inx] = resultQueryGeneros.getString("genero_pel");
                GenerosDesc[inx] = resultQueryGeneros.getString("Descripcion");
            }
            
        }catch(SQLException err){
            System.out.println(err.getMessage());
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }finally{
            if (resultQuery != null){
                try {
                    resultQuery.close();
                } catch (SQLException sqlEx) {System.out.println("Error: "+sqlEx.getMessage()); } // ignore
            }
            if (resultQueryGeneros != null){
                try {
                    resultQueryGeneros.close();
                } catch (SQLException sqlEx) {System.out.println("Error: "+sqlEx.getMessage());}
            }
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {System.out.println("Error: "+sqlEx.getMessage()); } // ignore
            }
            if (con != null){
                try {
                    con.close();
                } catch (SQLException sqlEx) {System.out.println("Error: "+sqlEx.getMessage()); } // ignore
            }
        }        
    }

    private ImageIcon resizeImagen(String Path){
        ImageIcon imageIcon = new ImageIcon(Path); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(240, 300,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        return  new ImageIcon(newimg);  // transform it back
    }
}
