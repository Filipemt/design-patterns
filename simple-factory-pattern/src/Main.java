import notification.NotificationFactory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a forma da notificação a ser enviada:");
        String notificationType = scanner.nextLine();

        System.out.println("Digite a mensagem a ser enviada");
        String mensagem = scanner.nextLine();

        NotificationFactory notificationFactory = new NotificationFactory();
        notificationFactory.create(notificationType).send(mensagem);
    }
}