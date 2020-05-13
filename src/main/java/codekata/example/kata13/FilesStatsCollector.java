package codekata.example.kata13;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class FilesStatsCollector extends SimpleFileVisitor<Path> {

    private final CodeLineCounter lineCounter;
    private final Map<String, Integer> codeLinesStats;

    FilesStatsCollector(CodeLineCounter lineCounter) {
        this.lineCounter = lineCounter;
        this.codeLinesStats = new HashMap<>();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.toString().endsWith(CodeLineCounter.JAVA_SOURCE_SUFFIX)){
            codeLinesStats.put(file.toString(), lineCounter.count(file.toFile()));
        } else {
            codeLinesStats.put(file.toString(), 0);
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        long sum = codeLinesStats.entrySet().stream()
                .filter(e -> e.getKey().startsWith(dir.toString()) && e.getKey().endsWith(CodeLineCounter.JAVA_SOURCE_SUFFIX))
                .mapToInt(Map.Entry::getValue)
                .summaryStatistics()
                .getSum();

        codeLinesStats.put(dir.toString(), (int) sum);

        return FileVisitResult.CONTINUE;
    }

    public Map<String, Integer> getCodeLinesStats() {
        return Collections.unmodifiableMap(codeLinesStats);
    }
}
