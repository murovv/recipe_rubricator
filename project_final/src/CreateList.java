import org.w3c.dom.NodeList;

import javax.swing.*;

public class CreateList {
    public JList create(NodeList recipes){
        int listLen = recipes.getLength();
        Object[] elements = new Object[listLen];
        for(int i = 0; i<listLen;i++){
            elements[i]=recipes.item(i).getLastChild().getTextContent();

        }
        JList list = new JList(elements);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        System.out.println("created");
        return list;
    }
    public DefaultListModel recreateListModel(NodeList recipes){
       DefaultListModel listModel = new DefaultListModel();
       for(int i = 0;i<recipes.getLength();i++){
           listModel.addElement(recipes.item(i).getLastChild().getTextContent());
       }
       return  listModel;
    }
}
