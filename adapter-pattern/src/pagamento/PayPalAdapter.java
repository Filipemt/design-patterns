package pagamento;

public class PayPalAdapter implements ProcessadorPagamento {

    @Override
    public void processar(double valor) {
        System.out.println("Pagamento utilizando Pay Pal está sendo processado...");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("O processamento foi interrompido.");
        }

        System.out.println("Pagamento realizado com sucesso! Valor: $" + valor + " BRL");
    }
}
