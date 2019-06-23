package IO;

import IO.contracts.Reader;
import data.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;

public class StreamReader implements Reader {

    private ObjectInputStream inputStream;

    public StreamReader(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Packet read() {
        try {
            return (Packet) this.inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void closeConnection() {
        try {
            this.inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
