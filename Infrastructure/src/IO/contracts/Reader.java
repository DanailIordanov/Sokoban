package IO.contracts;

import data.Packet;

public interface Reader {

    Packet read();

    void closeConnection();

}
