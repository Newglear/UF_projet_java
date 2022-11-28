package Conversation;

import Message.TCPMessage;
import Message.TCPType;
import Message.WrongConstructorException;
import NetworkManager.TcpSend;
import NetworkManager.ThreadTcpReceiveConnection;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ConversationTest {


    @Test
    public void conversationTest() throws Exception {
        ThreadTcpReceiveConnection tcpReceiveConnection = new ThreadTcpReceiveConnection();
        TcpSend.tcpConnect(InetAddress.getLocalHost());

    }
}
