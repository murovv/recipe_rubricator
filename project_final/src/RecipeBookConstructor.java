import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class RecipeBookConstructor {
    Document recipeBook;
    Element root;
    public RecipeBookConstructor() throws ParserConfigurationException {
        DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
        DocumentBuilder db=dbf.newDocumentBuilder();
        recipeBook =db.newDocument();
        root = recipeBook.createElement("RecipeBook");
        recipeBook.appendChild(root);
    }
    public void append(Element recipe){
        if(recipe!=null) {
            root.appendChild(recipe);
        }
    }
    public void doneAppending() throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(recipeBook);
        String xmlFilePath = "myData_500.xml";
        StreamResult streamResult = new StreamResult(new File(xmlFilePath));
        transformer.transform(domSource, streamResult);
        System.out.println("Done creating XML File");
    }
}
