package pagamento;

public class StoneGateway {

    private double valor;

    public void cobrar(double valor) {
        System.out.println("Pagamento utilizando STONE está sendo processado...");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("O processamento foi interrompido.");
        }

        System.out.println("Pagamento realizado com sucesso! Valor: R$ " + valor);
    }
}
