package edu.hw6.task3;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public class MagicNumberFilter implements AbstractFilter {
    // для простоты считается, что магическое число находится в начале файла(смещение 0).

    private final char[] magicNumber;

    public MagicNumberFilter(char[] magicNumber) {
        if (magicNumber == null) {
            throw new NullPointerException();
        }

        if (magicNumber.length == 0) {
            throw new IllegalArgumentException();
        }

        this.magicNumber = magicNumber;
    }

    @Override
    public boolean accept(Path path) {
        char[] readied = new char[magicNumber.length];
        try (FileReader fr = new FileReader(path.toFile())) {
            fr.read(readied);
        } catch (IOException e) {
            return false;
        }
        return Arrays.equals(magicNumber, readied);
    }
}