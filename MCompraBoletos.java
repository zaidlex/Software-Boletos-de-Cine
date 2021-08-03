package com.mg.softwarecompradeboletosdecine;

public class MCompraBoletos {
    
    public static void main(String args[]){  
        //Caracteristicas de la interfaz de la cartelera
        int width = 1000;
        int height = 700;

        InterfazCartelera ICartelera = new InterfazCartelera();
        ICartelera.setSize(width, height);
		ICartelera.setVisible(true);
    }  
}
