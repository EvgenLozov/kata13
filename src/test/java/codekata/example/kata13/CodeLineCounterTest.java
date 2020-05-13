package codekata.example.kata13;


import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class CodeLineCounterTest {

    CodeLineCounter lineCounter = new CodeLineCounter();

    @Test
    public void testEmptyFile() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("root/subdir1/Empty.java").getFile());
        Assert.assertEquals(0, lineCounter.count(file));
    }

    @Test
    public void testAllComments() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("root/subdir2/AllComments.java").getFile());
        Assert.assertEquals(0, lineCounter.count(file));
    }

    @Test
    public void testSingleLineComments() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("root/subdir1/SingleLineComments.java").getFile());
        Assert.assertEquals(4, lineCounter.count(file));
    }

    @Test
    public void testMultiLineComments() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("root/subdir2/MultiLineComments.java").getFile());
        Assert.assertEquals(4, lineCounter.count(file));
    }

    @Test
    public void testSingleAndMultiLineComments() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("root/SingleAndMultiLineComments.java").getFile());
        Assert.assertEquals(5, lineCounter.count(file));
    }

    @Test
    public void testNoComments() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("root/NoComments.java").getFile());
        Assert.assertEquals(4, lineCounter.count(file));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotJavSource() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("root/notjava.txt").getFile());
        lineCounter.count(file);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotFile() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("root/subdir1").getFile());
        lineCounter.count(file);
    }

}