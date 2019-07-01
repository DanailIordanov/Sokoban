package fields;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class BufferField {
    private static final String FIELD_FILE_EXTENSION = ".txt";
    private static final String FIELD_FILE_PATH = "/gameField";

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
        try {
            var fileStream = getClass().getResourceAsStream(FIELD_FILE_PATH + FIELD_FILE_EXTENSION);
            var reader = new BufferedReader(new InputStreamReader(fileStream));

            StringBuilder field = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                for(char i : line.toCharArray()) {
                    field.append(i);
                }
                field.append('\r');
                field.append('\n');
            }

            this.field = field.toString().toCharArray();

        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("The file, containing the field, could not be found.");
        } catch (IOException e) {
            System.out.println("The file, containing the field, could not be read.");
        }
    }

}
