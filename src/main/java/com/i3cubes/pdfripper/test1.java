/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.i3cubes.pdfripper;

import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

/**
 *
 * @author kumar
 */
public class test1 {
    
    public void getPageText( String filename) {
        try {
            RandomAccessFile fis = new RandomAccessFile(new File(filename),"r");
            PDFParser parser = new PDFParser(fis);
            parser.parse();
            COSDocument cosDoc=parser.getDocument();
            PDFTextStripper stripper = new PDFTextStripper ();            
            PDDocument pdfDocument = new PDDocument(cosDoc);
            
            //PDDocumentCatalog dc = pdfDocument.getDocumentCatalog();            
            int numPages = pdfDocument.getNumberOfPages();
 
            System.out.println("num of pages: " + numPages );
            stripper.setStartPage(0);
            stripper.setEndPage(pdfDocument.getNumberOfPages());
            String text=stripper.getText(pdfDocument);
            System.out.println(text);
 
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
 
    }
    
    public void getTextinArea(String filename){
        try {
            PDDocument pdDoc=PDDocument.load(new File(filename));
            PDFTextStripperByArea stripper=new PDFTextStripperByArea();
            stripper.setSortByPosition(true);
            Rectangle a1=new Rectangle(311, 87, 246, 81);
            Rectangle a2=new Rectangle(40, 181, 230, 72);
            stripper.addRegion("order_data", a1);
            stripper.addRegion("customer_data", a2);
            
            PDPage page=pdDoc.getPage(0);
            stripper.extractRegions(page);
            
            String a1_text=stripper.getTextForRegion("order_data");
            String a2_text=stripper.getTextForRegion("customer_data");
            
            System.out.println("AREA-1");
            System.out.println(a1_text);
            System.out.println("AREA-2");
            System.out.println(a2_text);
            System.out.println("-------------LINES-----------");
            String[] lines=a1_text.split("\\r?\\n",-1);
            for(String line:lines){
                System.out.println(line);
            }
            System.out.println("-----------VARS-------------");
            System.out.println("Order No::"+ExtractVariable(lines, "Order No :"));
            System.out.println("Order Date::"+ExtractVariable(lines, "Order Date:"));
            System.out.println("Date Required::"+ExtractVariable(lines, "Date Required :"));
        } catch (IOException ex) {
            Logger.getLogger(test1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void getTextinAreaLineByLine(String filename){
        try {
            PDDocument pdDoc=PDDocument.load(new File(filename));
            GetLinesFromPDF stripper=new GetLinesFromPDF();
            stripper.setSortByPosition(true);
            Rectangle a1=new Rectangle(311, 87, 246, 81);
            Rectangle a2=new Rectangle(40, 181, 230, 72);
            stripper.addRegion("order_data", a1);
            stripper.addRegion("customer_data", a2);
            
            PDPage page=pdDoc.getPage(0);
            stripper.extractRegions(page);
            System.out.println("AREA-1");
            System.out.println(stripper.getTextForRegion("order_data"));
            System.out.println("AREA-2");
            System.out.println(stripper.getTextForRegion("customer_data"));
            
            System.out.println("-----------LINES-------------");
            Writer dummy=new OutputStreamWriter(new ByteArrayOutputStream());
            stripper.writeText(pdDoc, dummy);
            for(String line:stripper.getLines()){
                System.out.println(line); 
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(test1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String ExtractVariable(String[] lines,String extract_string){
        for(String line:lines){
            if(line.indexOf(extract_string)!=-1){
                String val=line.replaceAll(extract_string, "");
                return val.trim();
            }
        }
        return null;
    }

}
