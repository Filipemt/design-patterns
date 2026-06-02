public class Pedido {

    private double peso;
    private String tipoFrete;

    public Pedido(double peso, String tipoFrete) {
        this.peso = peso;
        this.tipoFrete = tipoFrete;
    }

    public double calcularFrete() {
        if (tipoFrete.equalsIgnoreCase("PAC")) {
            return peso * 1.5 + 10.0;
        } else if (tipoFrete.equalsIgnoreCase("SEDEX")) {
            return peso * 3.5 + 25;
        }
        else {
            throw new IllegalArgumentException("Tipo de frete invalido");
        }
    }
}
