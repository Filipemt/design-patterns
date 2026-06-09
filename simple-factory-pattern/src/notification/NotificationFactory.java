package notification;

public class NotificationFactory {

    public NotificationTypeInterface create(String notificationType) {

        return switch (notificationType) {
            case "SMS" -> new NotificationSms();
            case "EMAIL" -> new NotificationEmail();
            case "SLACK" -> new NotificationSlack();
            default -> throw new IllegalArgumentException("Tipo de notificação inválida: " + notificationType);
        };

    }
}
