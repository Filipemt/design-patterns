public class PacStrategy implements FreteStrategy {

    @Override
    public double calcular(double peso) {
        return peso * 1.5 + 10.0;
    }
}
