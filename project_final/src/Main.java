import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main extends JFrame {
    private static DefaultListModel listModel = new DefaultListModel();
    private static NodeList recipes;
    private static JList list= new JList(listModel);
    static boolean noDoubleOpen =true;
    public void construct() throws ParserConfigurationException, IOException, TransformerException, InterruptedException {
        RecipeBookConstructor recipeBookConstructor = new RecipeBookConstructor();
        for(int i = 0;i<1500;i++){
            GetUrl get = new GetUrl();
            RecipeConstructor recipeConstructor = new RecipeConstructor(get.getUrl(i));
            recipeBookConstructor.append(recipeConstructor.constructRecipe(recipeBookConstructor.recipeBook));
        }
        recipeBookConstructor.doneAppending();
    }
    public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, InterruptedException {
        //new Main().construct();    //разкомментировав эту строку, при запуске программы произойдет обновление базы данных
        XMLParserAndFinder finder = new XMLParserAndFinder("myData_500.xml");
        JFrame frame = new JFrame("Введите ваши продукты");
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JTextField textField = new JTextField(15);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                recipes = finder.finder(textField.getText().toLowerCase());
                listModel.clear();
                DefaultListModel temp =new CreateList().recreateListModel(recipes);
                for(int i = 0;i<temp.size();i++){
                    listModel.addElement(temp.get(i));
                }
                System.out.println(list.toString());
                System.out.println("test");
                textField.setText("");
            }
        });
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(noDoubleOpen){
                    JFrame recipeFrame = new JFrame();
                    recipeFrame.setSize(600, 600);
                    recipeFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                    JPanel panel = new CreateTextPanel().create((Element) recipes.item(list.getSelectedIndex()));
                    recipeFrame.add(panel);
                    frame.setSize(600,600);
                    recipeFrame.setVisible(true);


                }
                noDoubleOpen =!(noDoubleOpen);
            }
        });

        JScrollPane scrollPane = new JScrollPane(list);
        frame.setLayout(new BorderLayout());
        frame.add(textField,BorderLayout.NORTH);
        frame.add(scrollPane,BorderLayout.CENTER);
        frame.setLocation(400,200);
        frame.setVisible(true);


    }
}
