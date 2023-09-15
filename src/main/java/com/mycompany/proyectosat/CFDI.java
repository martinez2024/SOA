/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectosat;

import java.io.File;
import org.w3c.dom.Element;

/**
 *
 * @author abiga
 */
public class CFDI {
    
    public static void main(String[] args) throws Exception{
        DocumentBuilerFactory factory=DocumentBuilerFactory.newInstance();
        DocumentBuiler builder =factory.new DocumentBuiler();
        File archivo = new File("C:\Users\abiga\Documents\SOA\Unidad3\JES900109Q90_J6_3502.xml");
        
        Document doc=builder.parse(archivo);
        Element root= doc.getDocumentElement();
        
        String RFCemisor=root.getElementsByTagName("cfdi:Emisor").item(0).getAttributes().getNamedItem("rfc").getTextContent();
        String RFCreceptor=root.getElementsByTagName("cfdi:receptor").item(0).getAttributes().getNamedItem("rfc").getTextContent();
        String total=root.getAttribute("Total");
        String fecha=root.getAttribute("fecha");
        String uuid=root.getElementsByTagName("tfdi: Timbre fiscal digital").item(0).getAttributes().getNamedItem("UUID").getTextContent();
        String serie=root.getAttribute("Serie");
        String folio=root.getAttribute("folio");
        
        System.out.println("RFC Emisor: " + RFCemisor);
        System.out.println("RFC Receptor: " + RFCreceptor);
        System.out.println("Total: " + total);
        System.out.println("UUID: " + uuid);
        System.out.println("Fecha: " + fecha);
        System.out.println("Serie: " + serie);
        System.out.println("folio: " + folio);
      
    }
   
    
}
