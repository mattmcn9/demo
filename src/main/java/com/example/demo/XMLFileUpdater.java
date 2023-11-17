import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;

/*
 * Example Input XML:
 *  <root>
 *       <file_path_1>{{FILE_PATH_1}}</file_path_1>
 *      <file_path_2>{{FILE_PATH_2}}</file_path_2>
 *  </root>
 */

// Example Output XML:
//  <root>
//     <file_path_1>/home/user/relative/path/to/file1.txt</file_path_1>
//     <file_path_2>/home/user/another/relative/path/file2.txt</file_path_2>
// </root>

public class XMLFileUpdater {
    public static void main(String[] args) {

        try {
            // Actual file paths or variables
            String file_path_1 = "/path/to/your/first/file.txt";
            String file_path_2 = "/path/to/your/second/file.txt";

            // Load XML file
            File inputFile = new File("data.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);

            // Replace placeholders in the XML document
            replacePlaceholders(doc, file_path_1, file_path_2);

            // Write the updated content back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("updated_data.xml"));
            transformer.transform(source, result);

            System.out.println("XML file updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void replacePlaceholders(Document doc, String file_path_1, String file_path_2) {
        NodeList nodes = doc.getElementsByTagName("*");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.TEXT_NODE) {
                String content = node.getNodeValue();
                if (content.contains("{{FILE_PATH_1}}")) {
                    node.setNodeValue(content.replace("{{FILE_PATH_1}}", file_path_1));
                } else if (content.contains("{{FILE_PATH_2}}")) {
                    node.setNodeValue(content.replace("{{FILE_PATH_2}}", file_path_2));
                }
            }
        }
    }
}
