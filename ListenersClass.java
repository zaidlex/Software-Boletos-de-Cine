import java.awt.event.*;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

class PosterListener implements ActionListener {
    //Listener del boton/poster de la cartelera,
    // Datos de la película
    private int idPelicula;
    private String Poster;
    JFrame intCartelera;

    public PosterListener(int id, String poster, JFrame window){
        idPelicula = id;
        Poster = poster;
        intCartelera = window;
    }

    public void actionPerformed(ActionEvent e){ 
        //Inicia la interfaz de pelicula
        //llama a la interfaz de pelicula al hacer click al boton correspondiente
        //la interfaz principal no desaparece ni se cierra
        intCartelera.setEnabled(false);
        
        InterfazPelicula IPelicula;
        IPelicula = new InterfazPelicula();
        IPelicula.initInterfazPelicula(idPelicula, Poster, intCartelera);
    }
}

class SalaBox implements ItemListener {
    // listener del jcombobox sala
    private String sala;
    private JComboBox<String> hora;
    private HashMap<String, List<String>> Dic;

    public SalaBox(String pFuncion, JComboBox<String> hora, HashMap<String,List<String>> Dic ){
        this.hora = hora;
        this.Dic = Dic;
        sala = pFuncion;
        cambiarValores();
    }

    @Override
    public void itemStateChanged(ItemEvent item) {
        sala = item.getItem().toString();
        cambiarValores();
    }

    private void cambiarValores(){
        hora.removeAllItems();

        Dic.forEach(
            (key,value)->{ 
                if( key == sala){
                    for (String val : value) {
                        hora.addItem( val );     
                    }
                }
            });

    }

}

class HoraBox implements ItemListener {
    // listener del jcombobox hora
    private JComboBox<String> sala;
    private JButton sillas[];
    private String salaSelecionada;
    private String horaSelecionada;

    public HoraBox(JComboBox<String> sala, JButton sillas[]){
        this.sala = sala;
        this.sillas = sillas;
    }

    @Override
    public void itemStateChanged(ItemEvent item) {
        horaSelecionada = item.getItem().toString();
        salaSelecionada = sala.getSelectedItem().toString();
        System.out.println( horaSelecionada );
        System.out.println( salaSelecionada );
        limpiaSillas();
    }

    private void limpiaSillas() {
        for(int inx = 0; inx < 121; inx++)
            sillas[inx].setBackground(Color.white);
    }

}   