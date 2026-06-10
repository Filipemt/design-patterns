package pagamento;

public class PayPalGateway {

    private double valor;

    public void cobrar(double valor, String moedaPagamento) {
        System.out.println("Pagamento utilizando Pay Pal está sendo processado...");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("O processamento foi interrompido.");
        }

        System.out.println("Pagamento realizado com sucesso! Valor: $" + valor + moedaPagamento);
    }
}
