package br.com.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class CarregarXML {
	
	
public Iterator carregar(String nome_arquivo) throws FileNotFoundException, IOException, JDOMException {  
        
	//Aqui você informa o nome do arquivo XML.  
	   File f = new File("/sdcard/"+nome_arquivo);  
	   
	   if (!f.exists()) {  
           return null;  
       }  
	  
	   //Criamos uma classe SAXBuilder que vai processar o XML4  
	   SAXBuilder sb = new SAXBuilder();  
	  
	   //Este documento agora possui toda a estrutura do arquivo.  
	   Document d = sb.build(f);  
	  
	   //Recuperamos o elemento root  
	   Element mural = d.getRootElement();  
	  
	   //Recuperamos os elementos filhos (children)  
	   List elements = mural.getChildren();  
	   
	   Iterator i = elements.iterator();  
	  
	   //Iteramos com os elementos filhos, e filhos do dos filhos  
	   /**while (i.hasNext()) {  
	      Element element = (Element) i.next();  
	      System.out.println("Códido:"+ element.getAttributeValue("codigo"));  
	      //System.out.println("Prioridade:"+ element.getAttributeValue("prioridade"));  
	      //System.out.println("Para:"+ element.getChildText("para"));  
	      //System.out.println("De:"+ element.getChildText("de"));  
	      //System.out.println("Corpo:"+ element.getChildText("corpo"));  
	   }**/
	return i;  
  
    }

}
