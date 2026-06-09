package notification;

public class NotificationSms implements NotificationTypeInterface {

    @Override
    public void send(String mensagem) {
        System.out.println("Enviando SMS: " + mensagem);
    }
}
