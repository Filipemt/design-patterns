package notification;

public class NotificationEmail implements NotificationTypeInterface{

    @Override
    public void send(String mensagem) {
        System.out.println("Enviando EMAIL: " + mensagem);
    }
}
