package codekata.example.kata13;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class CodeLineCounter {

    static final String JAVA_SOURCE_SUFFIX = ".java";

    int count(File file) throws IOException {

        if (file.isDirectory()){
            throw new IllegalArgumentException("Directory is not supported");
        }

        if (!file.getName().endsWith(JAVA_SOURCE_SUFFIX)){
            throw new IllegalArgumentException("Java source files are supported only");
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        int linesCount = 0;
        int commentsCount = 0;

        boolean multiLineComment = false;

        while ((line = reader.readLine()) != null) {
            linesCount++;

            String trimLine = line.trim();

            if (trimLine.startsWith("/*")){
                if (!trimLine.endsWith("*/")){
                    multiLineComment = true;
                }
                commentsCount++;
                continue;
            }

            if (trimLine.endsWith("*/") && multiLineComment) {
                commentsCount++;
                multiLineComment = false;
                continue;
            }

            if(trimLine.startsWith("//")) {
                commentsCount++;
                continue;
            }

            if(multiLineComment){
                commentsCount++;
            }
        }

        reader.close();

        return linesCount - commentsCount;
    }
}
