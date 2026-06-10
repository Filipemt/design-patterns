import pagamento.PayPalAdapter;
import pagamento.ProcessadorPagamento;
import pagamento.StoneAdapter;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Map<String, Double> listaProdutos = Map.of(
                "Mouse Logitech Mx Anywhere 2s", 350.00,
                "Teclado Mecânico Keycron", 1500.0,
                "Macbook Air M1 8GB", 4000.0
        );

        System.out.println("=== PRODUTOS DISPONIVEL ===");
        listaProdutos.forEach((produto, preco) -> {
            System.out.println(produto + " - R$" + preco);
        });

        System.out.println("Digite o nome do produto a ser comprado:");
        String nomeProduto =  scanner.nextLine();

        Double precoProduto = null;

        for (String produtoLista : listaProdutos.keySet()) {
            if (produtoLista.equalsIgnoreCase(nomeProduto)) {
                precoProduto = listaProdutos.get(produtoLista);
                break;
            }
        }

        if (precoProduto == null) {
            System.out.println("Produto não encontrado!");
        }
        else  {
            System.out.println("Produto encontrado! Valor R$" + precoProduto);

            System.out.println("""
                    Qual gateway de pagamento deverá ser utilizado? 
                    (1) Stone 
                    (2) PayPal
                    """
            );

            var opcaoPagamento = scanner.nextLine();
            ProcessadorPagamento processadorPagamento = null;
            switch (opcaoPagamento) {
                case "1":
                    processadorPagamento = new StoneAdapter();
                    break;
                case "2":
                    processadorPagamento = new PayPalAdapter();
                    break;
            }

            if (processadorPagamento != null) {
                processadorPagamento.processar(precoProduto);
            }
            else {
                System.out.println("Opção de pagamento inválida.");
            }
        }

    }
}
