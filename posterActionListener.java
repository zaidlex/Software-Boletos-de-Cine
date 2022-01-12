import java.awt.event.*;

public class posterActionListener implements ActionListener {
    // Datos de la película
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

    public posterActionListener(int id){
        idPelicula = id;
    }

    public void actionPerformed(ActionEvent e){ 
        //Inicia la interfaz de pelicula
        //llama a la interfaz de pelicula al hacer click al boton correspondiente
        //la interfaz principal no desaparece ni se cierra
        InterfazPelicula IPelicula;
        IPelicula = new InterfazPelicula();
        IPelicula.initInterfazPelicula(idPelicula);
    }
}
