package handlers;

import IO.StreamReader;
import IO.StreamWriter;
import IO.contracts.Reader;
import IO.contracts.UserReader;
import IO.contracts.Writer;
import data.Packet;
import infrastrucutre.Constants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class ConnectionHandler {

    private Reader inputStream;
    private Writer outputStream;
    private UserReader reader;

    public ConnectionHandler(UserReader reader) {
        this.reader = reader;
    }

    public Reader getInputStream() {
        return inputStream;
    }

    public void setInputStream(Reader inputStream) {
        this.inputStream = inputStream;
    }

    public Writer getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(Writer outputStream) {
        this.outputStream = outputStream;
    }

    public InetAddress detectAddress() {
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println(Constants.UNABLE_TO_DETECT_ADDRESS);
            var validHost = false;
            while (!validHost) {
                try {
                    localHost = reader.getIP();
                    validHost = true;
                } catch (UnknownHostException ex) {
                    System.out.println(Constants.INVALID_ADDRESS);
                }
            }
        }
        return localHost;
    }

    public void establish(ServerSocket server) {
        try {
            var client = server.accept();
            System.out.println(Constants.CONNECTED_TO_SERVER);

            this.setOutputStream(new StreamWriter(new ObjectOutputStream(client.getOutputStream())));
            this.setInputStream(new StreamReader(new ObjectInputStream(client.getInputStream())));
        } catch (IOException e) {
            System.out.println(Constants.UNABLE_TO_CONNECT);
        }
    }

    public void send(Packet data) {
        getOutputStream().write(data);
    }

    public void close(ServerSocket server) {
        try {
            this.getInputStream().closeConnection();
            this.getOutputStream().closeConnection();
            server.close();
        } catch (IOException e) {
            System.out.println();
        }
    }

}
