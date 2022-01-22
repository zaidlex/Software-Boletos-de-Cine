import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class panelSala extends JPanel{
    private int idPelicula;

    String[] sLetra = {"A","B","C","D","E","F","G","H","I","J","K"}, 
        sNumero={"1","2","3","4","5","6","7","8", "9","10","11"};
    HashMap<String,List<String>> DicSalaHora;
    private JButton sillas[], comprar; // sillas y comprar
    private JLabel  Pantalla, lLetras[], lNumeros[]; // letras y numeros de los asientos
    private JLabel lSala, lHorario; // etiquetas para los selectores
    private JComboBox<String> sala, horario; // seleccion de sala y horario

    public panelSala(int id){
        idPelicula = id;

        startPanel();
        startItemsSala();
        startSillasBotones();
        addItems();
    }   

    private void startPanel(){
        this.setSize(1000, 360);
        this.setBounds(0, 340, 1000, 360);
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);
    }

    private void startItemsSala() {
        int xPain = 30, yPain =450;
        String AuxSala = "";
        String primeraFuncion = "";
        // Constructor del diccionario
        DicSalaHora = new HashMap<String,List<String>>();
        List<String> values = new ArrayList<String>();

        lHorario = new JLabel("Horarios disponibles");
        lHorario.setBounds(xPain, yPain+60, 150, 20);
        horario = new JComboBox<String>();
        horario.setBounds(xPain, yPain+90, 150, 20);

        //Salas y horarios
        lSala = new JLabel("Salas disponibles");
        lSala.setBounds(xPain, yPain, 150, 20);
        sala = new JComboBox<String>();
        sala.setBounds(xPain, yPain+30, 150, 20);

        // conexiones a la base de datos
        Connection con = null;
        Statement stmt = null;
        ResultSet resultQuery = null;
        try{
            //Aquí se creará los botones para cada película (lista)
            Class.forName("com.mysql.cj.jdbc.Driver"); // carga del driver
            // conexion a la base de datos
            con = DriverManager.getConnection("jdbc:mariadb://localhost/Cine?" +
                "user=UsrCine&password=c!n3#8732");
            stmt = con.createStatement();
            resultQuery = stmt.executeQuery("SELECT * FROM PeliculaSH WHERE IDPelicula = "+String.valueOf(this.idPelicula) );// obtine el nombre del poster de cada pelicula  
            
            while(resultQuery.next()){
                if( AuxSala.compareTo("") == 0 ){
                    AuxSala = resultQuery.getString("Nombre_Sala");
                    values.add( resultQuery.getString("hora") );
                    primeraFuncion = AuxSala;
                }else if( AuxSala.compareTo( resultQuery.getString("Nombre_Sala") ) == 0 ){
                    values.add( resultQuery.getString("hora") );
                }else{
                    DicSalaHora.put(AuxSala, values);
                    values = new ArrayList<String>();

                    AuxSala = resultQuery.getString("Nombre_Sala");
                    values.add( resultQuery.getString("hora") );
                }
            }

            if( ! AuxSala.equals(""))
                DicSalaHora.put(AuxSala, values);

            DicSalaHora.forEach((key,value)->sala.addItem(key));

            sala.addItemListener ( new SalaBox( primeraFuncion, horario, DicSalaHora) );

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

    private void startSillasBotones() {
        int width_sillas = 40, height_sillas=25;
        int corX = 350, corY=345;

        // pantalla
        Pantalla = new JLabel("Pantalla", SwingConstants.CENTER);
        Pantalla.setOpaque(true);
        Pantalla.setBackground(Color.black);
        Pantalla.setForeground(Color.white);
        Pantalla.setBounds(corX, corY, 500, 15);
        corY+=15; // height pantalla
        lLetras = new JLabel[11];
        lNumeros = new JLabel[11];
        
        for(int inx = 0; inx < 11; inx++){
            lLetras[inx] = new JLabel( sLetra[inx], SwingConstants.CENTER );
            lLetras[inx].setBounds(corX+width_sillas+(width_sillas*inx), corY, width_sillas, height_sillas );
            lNumeros[inx] = new JLabel( sNumero[inx], SwingConstants.CENTER );
            lNumeros[inx].setBounds(corX, corY+height_sillas+(height_sillas*inx), width_sillas, height_sillas);
        }

        //etiquetas y botones de las sillas
        sillas = new JButton[121]; // 11 numeros * 11 letras = 121 sillas
        for(int inx = 0; inx < 121; inx++){
            sillas[inx] = new JButton();
            sillas[inx].setBorder(null);
            sillas[inx].setBorder(new LineBorder(Color.BLACK));
            sillas[inx].setBackground(Color.white);
            sillas[inx].setOpaque(true);
        }

        for(int inx_y = 0, inx = 0; inx_y < 11; inx_y++){
            for(int inx_x = 0; inx_x < 11; inx_x ++,inx++){
                sillas[inx].setText(sLetra[inx_y]+sNumero[inx_x]);
                sillas[inx].setBounds(
                    corX+width_sillas+(width_sillas*inx_x), // x 
                    corY+height_sillas+(height_sillas*inx_y), // y
                    width_sillas, // width
                    height_sillas); // height
            }
        }
        // agrega el listener al combobox de hora
        horario.addItemListener( new HoraBox(sala, sillas) );

        comprar = new JButton("Comprar");
        comprar.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        comprar.setBackground(Color.GREEN);
        comprar.setBounds(860, 470, 120, 40);
    }

    private void addItems(){
        this.add(Pantalla); // etiqueta pantalla

        for(int inx = 0; inx < 11; inx++){ // etiquetass numeros y letras
            this.add( lLetras[inx] );
            this.add( lNumeros[inx] );
        }
        for(int inx = 0; inx < 121; inx++) // botones sala
            this.add( sillas[inx] );
        
        this.add( comprar ); // botón comprar
        this.add( lSala );
        this.add( sala );
        this.add( lHorario );
        this.add( horario);
    }
}
