package notification;

public class NotificationSlack implements NotificationTypeInterface{

    @Override
    public void send(String mensagem) {
        System.out.println("Enviando SLACK: " + mensagem);
    }
}
