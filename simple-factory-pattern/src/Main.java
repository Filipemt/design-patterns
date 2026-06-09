import notification.NotificationEmail;
import notification.NotificationSlack;
import notification.NotificationSms;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a forma da notificação a ser enviada:");
        String notificationType = scanner.nextLine();

        System.out.println("Digite a mensagem a ser enviada");
        String mensagem = scanner.nextLine();

        if (notificationType.equals("SMS")) {
            NotificationSms notificationSms = new NotificationSms();
            notificationSms.enviarSMS(mensagem);
        }

        if (notificationType.equals("EMAIL")) {
            NotificationEmail notificationEmail = new NotificationEmail();
            notificationEmail.enviarEmail(mensagem);
        }

        if (notificationType.equals("SLACK")) {
            NotificationSlack notificationSlack = new NotificationSlack();
            notificationSlack.enviarSlack(mensagem);
        }

    }
}