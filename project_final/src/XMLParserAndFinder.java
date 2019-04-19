import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLParserAndFinder {
    String matchString;
    String adress;
    Document doc;
    NodeList nList;
    Element newNList;
    public XMLParserAndFinder(String adress){
        try{
            this.adress= "myData.xml";
            File fXmlFile = new File(adress);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            this.nList = doc.getElementsByTagName("Recipe");
            this.newNList=doc.createElement("RecipeBook");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public NodeList finder(String matchString){
        //итерация по рецептам
        System.out.println(matchString);
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Element eElement = (Element) nNode;
            NodeList ings =  eElement.getElementsByTagName("Ingredient");
                for (int i=0;i<ings.getLength();++i){
                    Element ing = (Element) ings.item(i);
                    if (matchString.equals(ing.getElementsByTagName("Name").item(0).getTextContent().toLowerCase())){
                        newNList.appendChild(nNode);
                    }
                }

        }
        nList=(NodeList)newNList;
        newNList=doc.createElement("RecipeBook");
        return nList;
    }
}
