import github.kyxap.com.utils.StringParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringParserTest {

    @Test
    public void parseHtml() throws IOException {
        // Path relative to src/test/resources
        final String filePath = "rtx40xx.html";

        // Resolve the path to the file
        final Path path = Paths.get("src", "test", "resources", filePath);

        // Read the content of the file
        final String content = Files.readString(path);

        final String parsedVer = StringParser.extractVersionFromHtlmString(content);

        // Perform assertions or further processing
        assertEquals("546.29", parsedVer);
    }
}
