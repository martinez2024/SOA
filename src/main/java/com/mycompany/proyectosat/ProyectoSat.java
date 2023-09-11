/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectosat;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author abiga
 */
public class ProyectoSat {

    public static void main(String[] args) {
        System.out.println("Hola Dama!");
        Ventana vent= new Ventana();
        vent.setSize(500,500);
        Dimension ventana =Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frame =ventana.getSize();
        vent.setLocation((ventana.width /2-(frame.width /2)),ventana.height /2-(frame.height /2));
        vent.setVisible(true);
    }
}
