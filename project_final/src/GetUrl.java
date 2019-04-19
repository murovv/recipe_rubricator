import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class GetUrl {
    public String getUrl(int i) throws IOException, ParserConfigurationException {
        i+=10;
        return "https://www.povarenok.ru/recipes/show/"+i;
    }
}