package fields;

import interfaces.Displayable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class Field {
    private static final String FIELD_FILE_EXTENSION = ".txt";
    private static final String FIELD_FILE_PATH = "\\resources\\gameField";

    protected Displayable[][] field;
    protected char[] bufferField;

    protected Field() {
        this.initialize();
        this.fill();
    }

    public int getRowsCount() {
        return this.bufferField.length / (this.getColumnsCount() + System.lineSeparator().length());
    }

    public int getColumnsCount() {
        return new String(this.bufferField).indexOf(System.lineSeparator());
    }

    protected abstract void fill();

    private void initialize() {
        var root = System.getProperty("user.dir");
        try {
            var file = new File(root + FIELD_FILE_PATH + FIELD_FILE_EXTENSION);
            var reader = new FileReader(file);

            this.bufferField = new char[(int)file.length()];

            for (int i = 0; i < file.length(); i++) {
                var currentChar = (char)reader.read();
                this.bufferField[i] = currentChar;
            }

        } catch (FileNotFoundException e) {
            System.out.println("The file, containing the field, could not be found.");
        } catch (IOException e) {
            System.out.println("The file, containing the field, could not be read.");
        }
    }

}
