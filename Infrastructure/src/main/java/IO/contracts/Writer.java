package IO.contracts;

import data.Packet;

public interface Writer {

    void write(Packet data);

    void closeConnection();

}
