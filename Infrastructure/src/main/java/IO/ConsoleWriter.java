package IO;

import IO.contracts.UserWriter;

public class ConsoleWriter implements UserWriter {

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showField(String field) {
        System.out.println(field);
    }

}
