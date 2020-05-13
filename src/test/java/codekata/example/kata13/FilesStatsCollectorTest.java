package codekata.example.kata13;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class FilesStatsCollectorTest {

    FilesStatsCollector statsCollector = new FilesStatsCollector(new CodeLineCounter());

    @Test
    public void testSingleFileJavaSource() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("root/subdir1/SingleLineComments.java").getFile());
        statsCollector.visitFile(file.toPath(), null);
        assertEquals(4, (int) statsCollector.getCodeLinesStats().get(file.getAbsolutePath()));
    }

    @Test
    public void testSingleFileNonJavaSource() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("root/notjava.txt").getFile());
        statsCollector.visitFile(file.toPath(), null);
        assertEquals(0, (int) statsCollector.getCodeLinesStats().get(file.getAbsolutePath()));
    }

    @Test
    public void testDirectory() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("root/subdir2").getFile());
        Files.walkFileTree(Paths.get(file.getAbsolutePath()), statsCollector);
        assertEquals(4, (int) statsCollector.getCodeLinesStats().get(file.getAbsolutePath()));
    }

    @Test
    public void testDirectoryWithSubdirs() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("root").getFile());
        Files.walkFileTree(Paths.get(file.getAbsolutePath()), statsCollector);
        assertEquals(17, (int) statsCollector.getCodeLinesStats().get(file.getAbsolutePath()));
    }
}