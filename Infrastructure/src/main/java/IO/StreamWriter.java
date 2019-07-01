package IO;

import IO.contracts.Writer;
import data.Packet;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class StreamWriter implements Writer {

    private ObjectOutputStream outputStream;

    public StreamWriter(ObjectOutputStream outputStream) {
        this.setOutputStream(outputStream);
    }

    private void setOutputStream(ObjectOutputStream value) {
        try {
            value.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.outputStream = value;
    }

    @Override
    public void write(Packet data) {
        try {
            outputStream.writeObject(data);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() {
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
