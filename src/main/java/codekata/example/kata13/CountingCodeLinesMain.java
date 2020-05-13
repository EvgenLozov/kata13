package codekata.example.kata13;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class CountingCodeLinesMain {

    public static final String INDENT_SYMBOL = " ";

    public static void main(String[] args) throws IOException {

        String root = getFilePath();

        System.out.println("Counting the number of lines of actual code in Java source files ...");

        FilesStatsCollector statsCollector = new FilesStatsCollector(new CodeLineCounter());
        Files.walkFileTree(Paths.get(root), statsCollector);

        System.out.println("Counting results: ");
        printDir(new File(root), 1, statsCollector.getCodeLinesStats());
    }

    private static String getFilePath() {
        String root = "";
        boolean correctInput = false;
        Scanner scanner = new Scanner(System.in);

        while (!correctInput){
            System.out.println("Enter a valid directory/file absolute path: ");
            root = scanner.nextLine();
            correctInput = new File(root).exists();
        }

        return root;
    }

    private static void printDir(File dir, int indent, Map<String, Integer> codeLinesStats) {

        System.out.println(repeat(INDENT_SYMBOL, indent) + dir.getName() + " : " + codeLinesStats.get(dir.getAbsolutePath()));

        File[] listFiles = dir.listFiles();

        if (listFiles == null) {
            return;
        }

        Stream.of(listFiles)
                .filter(File::isDirectory)
                .forEach(d -> printDir(d, indent + 2, codeLinesStats));

        Stream.of(listFiles)
                .filter(File::isFile)
                .forEach(f -> System.out.println(repeat(INDENT_SYMBOL, indent + 1)
                                                + f.getName()
                                                + " : "
                                                + codeLinesStats.getOrDefault(f.getAbsolutePath(), 0)));
    }

    private static String repeat(String s, int n) {
        return String.join("", Collections.nCopies(n, s));
    }
}
