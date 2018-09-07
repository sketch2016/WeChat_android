package service;

public class MessageService {



    public interface CallBack {
        void storeMessage();
        void sendMessage();
        void receiveMessage();
    }
}
