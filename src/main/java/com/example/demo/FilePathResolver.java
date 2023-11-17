import java.nio.file.*;

public class FilePathResolver {
    public static void main(String[] args) {
        // User's home directory
        String userHome = System.getProperty("user.home");

        // Given relative paths from the XML file
        String relativePath1 = "relative/path/to/file1.txt";
        String relativePath2 = "another/relative/path/file2.txt";

        // Resolve the absolute paths
        String absolutePath1 = resolvePath(userHome, relativePath1);
        String absolutePath2 = resolvePath(userHome, relativePath2);

        // Display the resolved absolute paths
        System.out.println("Absolute Path 1: " + absolutePath1);
        System.out.println("Absolute Path 2: " + absolutePath2);
    }

    private static String resolvePath(String baseDir, String relativePath) {
        // Use Paths.get() to resolve the paths
        Path absolute = Paths.get(baseDir).resolve(relativePath).normalize();
        return absolute.toString();
    }
}
