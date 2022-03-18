import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

class FileRead {
    private String fileName;
    private Path p;
    private InputStream is;

    FileRead() {

    }

    public FileRead path(final String path) {
        this.p = Paths.get(path);
        return this;
    }

    public FileRead file(final String fileName) {
        this.fileName = fileName;
        return this;
    }

    public FileRead file(final URL url) throws URISyntaxException {
        Path path = Paths.get(url.toURI());
        this.p = path.getParent();
        this.fileName = path.getFileName().toString();
        return this;
    }

    public long countOccurance(final String value) {
        final Path path = this.p.resolve(this.fileName);
        return this.count(path, value);
    }

    private long count(final Path path, final String val) {
        long count = 0l;
        final BiPredicate<String, String> p = (x, y) -> x.contains(y);

        try (final Stream<String> stream = Files.lines(path, Charset.forName("ISO-8859-1"))) {
            count = stream.filter(x -> p.test(x, "java")).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}

public class Test1 {

    public static void main(String[] args) throws Exception {
        //long count = new FileRead().path("C:\\Users\\OSingh\\Downloads\\").file("test1.txt").countOccurance("java");
        System.out.println(Test1.class.getResource("test1.txt"));
        long count = new FileRead().file(Test1.class.getClassLoader().getResource("test1.txt")).countOccurance("java");
        System.out.println(count);
    }
}