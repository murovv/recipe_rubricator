import org.w3c.dom.Element;

import javax.swing.*;
import java.awt.*;

public class CreateTextPanel {
    public JPanel create(Element recipe){
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        Font font = new Font("Times New Roman",Font.PLAIN,15);

        JLabel title = new JLabel();
        title.setText(recipe.getLastChild().getTextContent());
        title.setFont(new Font("Times New Roman",Font.ITALIC,20));
        title.setHorizontalAlignment(JLabel.CENTER);

        int len = recipe.getElementsByTagName("Ingredient").getLength();
        JPanel ingsPanel = new JPanel();
        ingsPanel.setLayout(new GridLayout(len,1));
        JLabel[] ingsText = new JLabel[len];
        //итерация по ингридиентам
        for(int i = 0 ;i<len;i++){
            ingsText[i]= new JLabel();
            ingsText[i].setFont(font);
            ingsText[i].setHorizontalAlignment(JLabel.LEFT);

            String ingName = ((Element)recipe.getElementsByTagName("Ingredient").item(i)).getElementsByTagName("Name").item(0).getTextContent();
            String ingAmount = ((Element)recipe.getElementsByTagName("Ingredient").item(i)).getElementsByTagName("Amount").item(0).getTextContent();
            if (ingAmount.equals("")){
                ingsText[i].setText(ingName);
            }else
                {
                ingsText[i].setText(ingName+" - "+ingAmount);
                }
            ingsPanel.add(ingsText[i]);
        }

        //итерация по частям инструкции
        int lenInst=recipe.getElementsByTagName("Part").getLength();
        JPanel instruction = new JPanel();
        instruction.setLayout(new GridLayout(lenInst,1));
        JTextArea[] instParts = new JTextArea[lenInst];
        for (int i = 0;i<lenInst;i++){
            instParts[i]=new JTextArea();
            instParts[i].setEditable(false);
            instParts[i].setLineWrap(true);
            instParts[i].setWrapStyleWord(true);
            instParts[i].setFont(font);
            String instText =recipe.getElementsByTagName("Part").item(i).getTextContent();
            instParts[i].setText(instText);
            instruction.add(instParts[i]);
        }
        instruction.setBorder(BorderFactory.createEtchedBorder());
        JScrollPane instructionScrollPane = new JScrollPane(instruction);
        ingsPanel.setBorder(BorderFactory.createEtchedBorder());
        textPanel.add(title,BorderLayout.NORTH);
        textPanel.add(ingsPanel,BorderLayout.WEST);
        textPanel.add(instructionScrollPane,BorderLayout.CENTER);
        return  textPanel;
    }
}
