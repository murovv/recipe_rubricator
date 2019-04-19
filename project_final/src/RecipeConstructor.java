import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class RecipeConstructor {

    String url;
    public RecipeConstructor(String url){
        this.url=url;
    }

    public org.w3c.dom.Element constructRecipe(org.w3c.dom.Document doc1) throws IOException, ParserConfigurationException, InterruptedException {
        Document doc = null;
        boolean wasntExeption=true;
        //здесь все выводы системные, тк пользователь и не должен видеть работы этой части программы
        try {
            doc = Jsoup.connect(url).get();
            System.out.println("connected to "+ url);

        } catch (org.jsoup.HttpStatusException e){
            wasntExeption=false;
            System.out.println("no such page");
        }catch (java.net.SocketTimeoutException e){
            wasntExeption=false;
            System.out.println("timeout exeption");
        }  finally{
            Random random = new Random();
            Thread.sleep(3000+ random.nextInt(1000));
            /*на сайте есть простейшая система защиты от парсинга сайтов, которая блокирует доступ к сайту при слишком частых запросах
            или если запросы выполняются с точным интервалом
            поэтому нужна задержка между запросами
             */
        }
        if(wasntExeption) {
            org.w3c.dom.Element recipe = doc1.createElement("Recipe");
            Elements ings = doc.getElementsByAttributeValue("itemprop", "ingredient");
            org.w3c.dom.Element ingsNode = doc1.createElement("Ingredients");
            //Получаем ингредиенты
            for (Element ing : ings) {
                Node ingNode = doc1.createElement("Ingredient");
                Node name = doc1.createElement("Name");
                name.setTextContent(ing.child(0).child(0).text());
                Node amount = doc1.createElement("Amount");
                amount.setTextContent(ing.getElementsByAttributeValue("itemprop", "amount").text());
                ingNode.appendChild(name);
                ingNode.appendChild(amount);
                ingsNode.appendChild(ingNode);
            }
            recipe.appendChild(ingsNode);

            //ПОЛУЧАЕМ РЕЦЕПТ ПРИГОТОВЛЕНИЯ
            String[] badString={".","...",""," "};
            /*на сайте некоторые инструкции представлены картинками, и комментарии к этим катрикам являются точки, троеточия и тому подобное
            тк данная программа эти картинки не выводит и таких рецептов не так много, эти части будут вырезаться, а рецепты с пустой инструкцией удаляться
             */
            Elements instructionPart = doc.getElementsByAttributeValue("itemprop", "recipeInstructions");
            Node instructionNode = doc1.createElement("Instruction");
            for (Element part : instructionPart) {
                boolean isNotBadString=true;
                String textContent=part.getElementsByTag("p").text();
                for(int j=0;j<badString.length;j++){
                    if(textContent.equals(badString[j])){
                        isNotBadString=false;
                    }
                }
                if(isNotBadString) {
                    Node partNode = doc1.createElement("Part");
                    partNode.setTextContent(textContent);
                    instructionNode.appendChild(partNode);
                }
            }
            if (instructionNode.getTextContent().equals("")){
                /* некоторые рецепты на сайте обладают отличной от остальных структурой инструкции,
                поэтому вполне может быть не найдена ни одна часть инструкции
                 */
                return null;
            }
            recipe.appendChild(instructionNode);
            System.out.println(recipe.getTextContent());
            //ДОБАВИТЬ НАЗВАНИЕ РЕЦЕПТА
            String recipeName = doc.getElementsByTag("h1").text();
            org.w3c.dom.Element recipeNameElement = doc1.createElement("RecipeName");
            recipeNameElement.setTextContent(recipeName);
            recipe.appendChild(recipeNameElement);
            return recipe;
        }
        wasntExeption=true;
        System.out.println("NULL");
        return  null;
    }
}
