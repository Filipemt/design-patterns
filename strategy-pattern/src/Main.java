import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o peso da sua encomenda: " );
        double peso = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Digite o tipo do frete: (PAC/SEDEX):" );
        String frete = scanner.nextLine();

        Pedido pedido = new Pedido(peso, frete);
        double valorFrete = pedido.calcularFrete();

        System.out.println("Valor total do frete: " + valorFrete);

    }
}