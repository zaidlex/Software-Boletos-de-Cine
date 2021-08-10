package com.mg.softwarecompradeboletosdecine;

import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JLabel;

public class InterfazCartelera {
    JFrame IntCartelera;
    JPanel panelCartelera;

    private JButton BotonesCartelera;//botones para la cartelera
    private JLabel NombreEmpresa;//Nombre de la empresa
    private int width = 1000;
    private int height = 700;

    public void InterfazCartelera(){//Constructor
        IntCartelera = new JFrame();
        panelCartelera = new JPanel();
        startPanel();

        startInterfazCartelera();
        startItems();
        addCartelera();
        addItems();
    }

    private void startInterfazCartelera(){
        //Caracteristicas de la interfaz de la cartelera
        IntCartelera.setSize(width, height);
        IntCartelera.setLayout(null);
        IntCartelera.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        IntCartelera.setVisible(true);
    }

    private void startPanel(){
        IntCartelera.getContentPane().add(panelCartelera);
        panelCartelera.setBounds(0, 0, width, height);
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
        panelCartelera.add(NombreEmpresa);
        panelCartelera.add(BotonesCartelera);
    }
}
