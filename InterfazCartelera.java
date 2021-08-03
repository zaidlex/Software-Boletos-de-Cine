package com.mg.softwarecompradeboletosdecine;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JLabel;

public class InterfazCartelera extends JFrame {
    
    private JButton BotonesCartelera;//botones para la cartelera
    private JLabel NombreEmpresa;//Nombre de la empresa

    public void InterfazCartelera(){//Constructor
        setLayout(null);
        startItems();
        addCartelera();
        addItems();
    }

    private void startItems(){
        NombreEmpresa = new JLabel("Bienvenido a 'Nombre de la empresa'");
        NombreEmpresa.setBounds(40,40,200,30);
    }

    public void addCartelera(){
        //Aquí se creará los botones para cada película
        BotonesCartelera = null;
    }

    public void addItems(){
        add(NombreEmpresa);
        //this.add(BotonesCartelera);
    }
}
