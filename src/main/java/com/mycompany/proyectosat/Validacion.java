/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectosat;

import conex.sat.Acuse;
import conex.sat.ConsultaCFDIService;
import conex.sat.IConsultaCFDIService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;



/**
 *
 * @author abiga
 */
public class Validacion {
    String rutaarchivo;
    private Ventana ventana;
    
    public Validacion(String rutaarchivo, Ventana ventana){
        this.rutaarchivo=rutaarchivo;
        this.ventana=ventana;
    }

    private Validacion() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public String Estatus(String rfcEmisor, String rfcReceptor,String total, String uuid){
        return null;
    }
    public Acuse consulta(java.lang.String expresionImpresa){
        try{
            ConsultaCFDIService service =new ConsultaCFDIService();
            IConsultaCFDIService port=service.getBasicHttpBindingIConsultaCFDIService();
            return port.consulta(expresionImpresa);
          
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public File archivoCFDI(String ubicacionarchivo){
        try{
        File archivo =new File(ubicacionarchivo);
        if(!archivo.exists()){
        System.err.println("El archivo no se encuentra en esta ubicacion:"+ubicacionarchivo);
        return null;
    }
        FileReader fileReader = new FileReader(archivo);
        BufferedReader bufferedReader=new BufferedReader(fileReader);
        String linea;
        while ((linea = bufferedReader.readLine()) != null)
         System.out.println(linea);
        bufferedReader.close();
        fileReader.close();
        return archivo;
        
        
    }catch(Exception e){
        e.printStackTrace();
        return null;
    }
        
    }
    public String datosCFDI(String rfcEmisor, String rfcReceptor,String total, String uuid){
        try{
            String cadenaConsulta="?re=" + rfcEmisor + "&rr=" + rfcReceptor + "&tt=" + total + "&id=" + uuid;
            Acuse acusesat=consulta(cadenaConsulta);
            
            if(acusesat!=null){
                String codigoEstatus = (String)acusesat.getCodigoEstatus().getValue();
                return "codigo de estatus CFDI: "+codigoEstatus;
            }else{
                return "No se obtuvo informacion CFDI";
            }
         
        }catch (Exception e){
            e.printStackTrace();
            return "Error CFDI";
        }
    }
    public String validarExcel(String rutaexcel){
        String resultado="Correcto";
        FileInputStream inputStream = null;
        FileOutputStream outputStream=null;
        Workbook workbook=null;
        try{
            String excelFilePath =rutaexcel;
            inputStream = new FileInputStream(excelFilePath);
            workbook = new XSSFWorkbook(inputStream);
            Sheet sheet =workbook.getSheetAt(0);
            
            int contarRenglon=0;
            for (Row row : sheet) {
                contarRenglon++;
                
                if(contarRenglon==1){
                    continue;
                }
                String rfcEmisor="";
                String rfcReceptor="";
                String total="";
                String uuid="";
                HashMap mapDatossat=new HashMap<>();
                int contarcelda=0;
                
                for(Cell cell:row){
                    contarcelda++;
                    String cellValue=cell.toString();
                    switch(contarcelda){
                        case 1:
                            rfcEmisor=cellValue;
                            break;
                        case 2:
                            rfcReceptor=cellValue;
                        case 3:
                            total=cellValue;
                        case 4:
                            uuid=cellValue;
                        case 5:
                            mapDatossat=Estatus1(rfcEmisor,rfcReceptor,total,uuid);
                            this.ventana.Consola((contarRenglon-1)+" - RFC EMISOR = " 
                                    + rfcEmisor + ", RFC RECEPTOR = " 
                                    + rfcReceptor + ", TOTAL = " 
                                    + total + ", UUID = "
                                    + uuid + ", ESTATUS PROCESO = " 
                                    + mapDatossat.get("RESULTADO").toString()
                            );
                            cell.setCellValue(mapDatossat.get("ESTATUSPETICION").toString());
                            break;
                            
                    }
                    if(mapDatossat.get("Resultado").toString().toUpperCase().equals("S - COMPROBANTE OBTENIDO SATISFACTORIAMENTE."))
                        switch(contarcelda){
                        
                           case 6:
                                cell.setCellValue(mapDatossat.get("ESTATUSCFDI").toString());
                                break;
                            case 7: 
                                cell.setCellValue(mapDatossat.get("CANCELABLE").toString());
                                break;
                            case 8:
                                cell.setCellValue(mapDatossat.get("ESTATUSCANCELACION").toString());
                            case 9:
                                 cell.setCellValue(mapDatossat.get("VALIDAREFOS").toString());
                                 break;
                        }
                }
            }
        
        inputStream.close();
        outputStream=new FileOutputStream(rutaexcel);
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
        
        this.ventana.Consola("Registros procesados = " + (contarRenglon-1));
        System.out.println("Archivo creado");
        
        
        
    }catch (Exception e){
        e.printStackTrace();
        resultado="Error: "+ e.getMessage();
    } finally{
            try{
                if(inputStream !=null){
                    inputStream.close();
                    
                }
                if(outputStream !=null){
                    outputStream.close();
                }
                if(workbook !=null){
                    workbook.close();
                }
                
            }catch(IOException e){
                e.printStackTrace();
       
            }
            return resultado;
        }
        
    }
    public String XML(File[] fichXML){
    List <HashMap> mapDatosCFDIXML = new ArrayList();
    for (File fichero : fichXML) {
        System.out.println("Directorio XML: " + fichero.getAbsolutePath());
        CFDI parseCFDI=new CFDI();
        HashMap mapaCFDI= parseCFDI.datosXML(fichero.getAbsolutePath());
        mapDatosCFDIXML.add(mapaCFDI);
            }
    for(HashMap mapacfdiXML: mapDatosCFDIXML){
        HashMap mapaRespSAT=Estatus(mapacfdiXML.get("RFCemisor").toString(),
                mapacfdiXML.get("RFCreceptor").toString(),
                mapacfdiXML.get("total").toString(),
                mapacfdiXML.get("fecha").toString(),
                mapacfdiXML.get("uuid").toString());
        //juntar los dos mapas 
        mapacfdiXML.putAll(mapaRespSAT);
    }
     return ("");   
   }
public void generarExcelI(List <HashMap> MapaDatosCFDIXML){
    try{
            //crear un nuevo Excel 
              Workbook workbook = new XSSFWorkbook();
            //crear una nueva hoja 
              Sheet sheet =workbook.createSheet(" Listado CFDI");
            //crear encabezados 
            Row headerRow sheet.createRow(0);
            headerRow.createCell(0).setCellValue("rfcEmisor"); 
            headerRow.createCell(1).setCellValue ("rfcReceptor");
            headerRow.createCell(2).setCellValue ("total");
            headerRow.createCell(3).setCellValue ("uuid");
            headerRow.createCell(4).setCellValue ("fecha");
            headerRow.createCell(5).setCellValue ("serie");
            headerRow.createCell(6).setCellValue ("folio");
        
          for (HashMap mapaDatosCFDIXML();
          Row row = sheet.createRow (sheet.getLastRowNum()+1);
          row.createCell(0)setCellValue(mapaDatosCFDIXML.get(RFCemisor).toString());

           FileOutputStream outputStream = new FileOutputStream( "ruta");
           Workbook.write(OutputStream);
           outputStream.close();

        }catch(Exception e){
        e.printStackTrace();
    }
}
    
    
    public HashMap Estatus1(String rfcEmisor,String rfcReceptor, String total,String uuid){
        HashMap<Object,Object> mapDatossat=new HashMap<>();
        rfcEmisor=rfcEmisor.replaceAll("&","&amp;");
        rfcReceptor = rfcReceptor.replaceAll("&", "&amp;");
        String respuestaPeticion = "SIN RESPUESTA DEL SAT";
        String cadenaPeticion = "?re=" + rfcEmisor + "&rr=" + rfcReceptor + "&tt=" + total + "&id=" + uuid;
        Validacion operacionesWSValidacion = new Validacion();
        Acuse acusesat=operacionesWSValidacion.consulta(cadenaPeticion);
        
        if(acusesat !=null){
            if(acusesat.getCodigoEstatus() !=null){
              String codigoEstatus=(String)acusesat.getCodigoEstatus().getValue();
               if (codigoEstatus != null && !codigoEstatus.equals("")) {
                   mapDatossat.put("ESTATUSPETICION", codigoEstatus);
                   if(codigoEstatus.toUpperCase().equals("N - 601: La expresion impresa no es valida".toUpperCase())){
                        respuestaPeticion = "N - 601: La expresion impresa  no es valida";
                        
                   }else if (codigoEstatus.toUpperCase().equals("N - 601: La expresion impresa fue encontrada.".toUpperCase())) {
                       respuestaPeticion = "N - 602: Comprobante no encontrado";
                       
                   }else if (codigoEstatus.toUpperCase().equals("S - Comprobante obtenido.".toUpperCase())) {
                       if (acusesat.getEstado() != null) {
                           mapDatossat.put("ESTATUSCFDI", acusesat.getEstado().getValue());
                           mapDatossat.put("CANCELABLE", acusesat.getEsCancelable().getValue());
                           mapDatossat.put("ESTATUSCANCELACION", acusesat.getEstatusCancelacion().getValue());
                           mapDatossat.put("VALIDAREFOS", acusesat.getValidacionEFOS().getValue());
                           respuestaPeticion = "S - Comprobante obtenido satisfactoriamente."; 
                       }else{
                           respuestaPeticion="No existe";
                       }
                   }
              
                            
               }else{
                   respuestaPeticion="Sin respuesta del SAT";
               }
             
            }else{
                respuestaPeticion="Sin respuesta del SAT";
            }
        }else{
            respuestaPeticion="Sin respuesta SAT";
        }
        mapDatossat.put("resultado",respuestaPeticion);
        return mapDatossat;
        
    }
     private HashMap Estatus2(String rfcEmisor,String rfcReceptor, String total,String uuid){
        throw new UnsupportedOperationException("No es compatible.");
    }
     String Estatus(String crF090521AP6, String string) {
    throw new UnsupportedOperationException("No es compatible.");
  }
    
    
}
