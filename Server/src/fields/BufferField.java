package fields;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BufferField {
    private static final String FIELD_FILE_EXTENSION = ".txt";
    private static final String FIELD_FILE_PATH = "\\res\\gameField";

    private char[] field;

    public BufferField() {
        this.initializeBuffer();
    }

    public char getChar(int index) {
        return this.field[index];
    }

    public int getLength() {
        return this.field.length;
    }

    @Override
    public String toString() {
        return new String(this.field);
    }

    private void initializeBuffer() {
        var root = System.getProperty("user.dir");
        try {
            var file = new File(root + FIELD_FILE_PATH + FIELD_FILE_EXTENSION);
            var reader = new FileReader(file);

            this.field = new char[(int)file.length()];

            for (int i = 0; i < file.length(); i++) {
                var currentChar = (char)reader.read();
                this.field[i] = currentChar;
            }

        } catch (FileNotFoundException e) {
            System.out.println("The file, containing the field, could not be found.");
        } catch (IOException e) {
            System.out.println("The file, containing the field, could not be read.");
        }
    }

}
