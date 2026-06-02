public class Pedido {

    private double peso;
    private FreteStrategy freteStrategy;

    public Pedido(double peso, String freteStrategy) {
        this.peso = peso;
    }

    public void setFreteStrategy(FreteStrategy freteStrategy) {
        this.freteStrategy = freteStrategy;
    }

    public double calcularFrete() {
        if (freteStrategy == null) {
            throw new IllegalArgumentException("Seleciona um tipo de frete!");
        }

        return freteStrategy.calcular(peso);
    }
}
