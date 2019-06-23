package data;

import java.io.Serializable;

public class Packet implements Serializable {

    private String output;
    private boolean clearConsole;

    public Packet(String output, boolean clearConsole) {
        this.output = output;
        this.clearConsole = clearConsole;
    }


    public String getOutput() {
        return output;
    }

    public boolean isToClearConsole() {
        return clearConsole;
    }

}
