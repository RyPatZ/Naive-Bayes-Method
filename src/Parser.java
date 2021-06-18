import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Parser.
 */
public class Parser {

    /**
     * Read from file list.
     *
     * @param file the file
     * @return the list
     */
    public List<List<String>> readFromFile(String file){
        try {

        FileReader fileReader = new FileReader(new File(file));
        BufferedReader br = new BufferedReader(fileReader);
        String line;
        int count =0;
            List<List<String>> attributes= new ArrayList<>();
            List<String> attribute = new ArrayList<>();
            while ((line = br.readLine()) != null) {
            String[] split = line.split("\\s+");
            attribute = new ArrayList<String>(Arrays.asList(split));
            attribute.remove(0);
            attributes.add(attribute);
        }
            br.close();
            return attributes;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
