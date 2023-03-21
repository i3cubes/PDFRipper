/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.i3cubes.pdfripper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;

/**
 *
 * @author kumar
 */
public class GetLinesFromPDF extends PDFTextStripperByArea{

    @Override
    protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
        super.writeString(text, textPositions); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        lines.add(text);
    }
    public static List<String> lines=new ArrayList<String>();
    
    public GetLinesFromPDF() throws IOException{
        
    }
    public List<String> getLines(){
        return lines;
    }
}
