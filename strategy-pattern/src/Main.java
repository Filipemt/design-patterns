import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o peso da sua encomenda: (KG)");
        double peso = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Digite o tipo do frete: (PAC/SEDEX):" );
        String frete = scanner.nextLine();

        Pedido pedido = new Pedido(peso, frete);

        switch (frete.toUpperCase()) {
            case "PAC" -> {
                pedido.setFreteStrategy(new PacStrategy());
                System.out.println("Valor do frete via PAC: R$ " + pedido.calcularFrete());
            }
            case "SEDEX" -> {
                pedido.setFreteStrategy(new SedexStrategy());
                System.out.println("Valor do frete via SEDEX: R$ " + pedido.calcularFrete());
            }
            default -> throw new IllegalArgumentException("Tipo de frete inválido: " + frete);
        }
    }
}