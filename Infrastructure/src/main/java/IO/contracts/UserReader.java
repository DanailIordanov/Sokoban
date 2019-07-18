package IO.contracts;

import infrastrucutre.GameMode;

import java.net.InetAddress;
import java.net.UnknownHostException;

public interface UserReader {

    GameMode getGameMode();

    InetAddress getIP() throws UnknownHostException;

    int getPort();

    String getValidCommand();

    void closeConnection();

}
