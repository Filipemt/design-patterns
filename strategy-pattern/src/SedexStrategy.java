public class SedexStrategy implements FreteStrategy {

    @Override
    public double calcular(double peso) {
        return peso * 3.5 + 25.0;
    }
}
