import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class XMLFileUpdater2 {
    public static void main(String[] args) {
        try {
            // Get the user's home directory
            String userHome = System.getProperty("user.home");

            // Path to the XML file
            String xmlFilePath = userHome + File.separator + "data.xml";

            // Actual file paths or variables
            String file_path_1 = "relative/path/to/file1.txt";
            String file_path_2 = "another/relative/path/file2.txt";

            // Load XML file from the user's home directory
            File inputFile = new File(xmlFilePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);

            // Resolve relative paths using the user's home directory
            file_path_1 = userHome + File.separator + file_path_1;
            file_path_2 = userHome + File.separator + file_path_2;

            // Replace relative paths in the XML document
            replaceRelativePaths(doc, file_path_1, file_path_2);

            // Write the updated content back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(inputFile);
            transformer.transform(source, result);

            System.out.println("XML file updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void replaceRelativePaths(Document doc, String file_path_1, String file_path_2) {
        NodeList nodes = doc.getElementsByTagName("*");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.TEXT_NODE) {
                String content = node.getNodeValue();
                if (content.equals(file_path_1)) {
                    node.setNodeValue(file_path_1);
                } else if (content.equals(file_path_2)) {
                    node.setNodeValue(file_path_2);
                }
            }
        }
    }
}
