package IO.contracts;

import java.net.InetAddress;
import java.net.UnknownHostException;

public interface UserReader {

    InetAddress getIP() throws UnknownHostException;

    int getPort();

    String getValidCommand();

    void closeConnection();

}
