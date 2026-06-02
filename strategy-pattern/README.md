# Strategy Pattern - Cálculo de Frete

## Descrição

Este projeto implementa o padrão de design **Strategy** em Java para demonstrar como resolver o problema de múltiplas formas de cálculo de frete de forma flexível, extensível e mantível.

##  O Problema (Antes)

### Abordagem sem Strategy

Antes da implementação do padrão Strategy, a lógica de cálculo de frete estava acoplada à classe `Pedido`:

```java
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
```

### Problemas desta Abordagem

1. **Violação do Single Responsibility Principle (SRP)**: A classe `Pedido` tem múltiplas responsabilidades (armazenar dados do pedido E calcular frete)

2. **Violação do Open/Closed Principle (OCP)**: A classe não é aberta para extensão, mas fechada para modificação. Para adicionar um novo tipo de frete, é necessário modificar a classe existente

3. **Código com muitas condicionais**: A lógica fica complexa conforme novos tipos de frete são adicionados

4. **Dificuldade em testes unitários**: Testar diferentes estratégias de frete requer testar toda a classe `Pedido`

5. **Baixa reutilização**: A lógica de cálculo fica presa a uma classe específica

## A Solução (Depois - Strategy Pattern)

### Estrutura do Padrão

O padrão Strategy define uma família de algoritmos, encapsula cada um deles e os torna intercambiáveis. O Strategy permite que o algoritmo varie independentemente dos clientes que o utilizam.

```
┌─────────────────────────────────────────────────────────────────────┐
│                          STRATEGY PATTERN                            │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  ┌──────────────────┐          ┌─────────────────────────────────┐ │
│  │  FreteStrategy   │◄─────────│    <<interface>>                │ │
│  │  (Interface)     │          │  + calcular(peso): double      │ │
│  └──────────────────┘          └─────────────────────────────────┤ │
│           ▲                                                        │ │
│           │                                                        │ │
│       ┌───┴────────────────────┬─────────────────────────────┐    │ │
│       │                        │                             │    │ │
│  ┌────────────┐        ┌──────────────┐          ┌──────────────┐│ │
│  │ PacStrategy│        │SedexStrategy │          │ OutrasStrat..││ │
│  ├────────────┤        ├──────────────┤          ├──────────────┤│ │
│  │calcular()  │        │ calcular()   │          │ calcular()   ││ │
│  │peso*1.5+10│        │peso*3.5+25   │          │  ...         ││ │
│  └────────────┘        └──────────────┘          └──────────────┘│ │
│                                                                    │ │
│  ┌────────────────────────────────────────────────────────────┐  │ │
│  │              Pedido (Context)                              │  │ │
│  ├────────────────────────────────────────────────────────────┤  │ │
│  │ - peso: double                                             │  │ │
│  │ - freteStrategy: FreteStrategy                             │  │ │
│  ├────────────────────────────────────────────────────────────┤  │ │
│  │ + setFreteStrategy(strategy: FreteStrategy): void          │  │ │
│  │ + calcularFrete(): double                                  │  │ │
│  └────────────────────────────────────────────────────────────┘  │ │
│                                                                    │ │
└────────────────────────────────────────────────────────────────────┘
```

### Arquivos Implementados

#### 1. **FreteStrategy.java** - Interface Strategy

Define o contrato que todas as estratégias de frete devem implementar:

```java
public interface FreteStrategy {
    double calcular(double peso);
}
```

**Responsabilidade**: Define um método comum para calcular frete independentemente da estratégia

---

#### 2. **PacStrategy.java** - Estratégia Concreta PAC

Implementa o cálculo de frete para o serviço PAC:

```java
public class PacStrategy implements FreteStrategy {
    @Override
    public double calcular(double peso) {
        return peso * 1.5 + 10.0;
    }
}
```

**Cálculo**: `peso × 1.5 + 10`
- Taxa de R$ 10,00 fixa
- R$ 1,50 por kg

---

#### 3. **SedexStrategy.java** - Estratégia Concreta SEDEX

Implementa o cálculo de frete para o serviço SEDEX:

```java
public class SedexStrategy implements FreteStrategy {
    @Override
    public double calcular(double peso) {
        return peso * 3.5 + 25.0;
    }
}
```

**Cálculo**: `peso × 3.5 + 25`
- Taxa de R$ 25,00 fixa
- R$ 3,50 por kg

---

#### 4. **Pedido.java** - Context

A classe que utiliza as estratégias:

```java
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
```

**Responsabilidades**:
- Armazenar dados do pedido (peso)
- Manter uma referência para a estratégia selecionada
- Delegar o cálculo para a estratégia apropriada

---

#### 5. **Main.java** - Cliente

Demonstra como usar o padrão:

```java
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o peso da sua encomenda: (KG)");
        double peso = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Digite o tipo do frete: (PAC/SEDEX):");
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
```

##  Vantagens do Strategy Pattern

| Aspecto | Benefício |
|---------|-----------|
| **Escalabilidade** | Adicionar novos tipos de frete é trivial - basta criar uma nova classe que implementa `FreteStrategy` |
| **Open/Closed Principle** | Aberto para extensão (novos algoritmos), Fechado para modificação (código existente não muda) |
| **Single Responsibility** | Cada classe tem uma única responsabilidade bem definida |
| **Testabilidade** | Cada estratégia pode ser testada isoladamente com testes unitários |
| **Reutilização** | As estratégias podem ser reutilizadas em diferentes contextos |
| **Flexibilidade** | O algoritmo pode ser escolhido em tempo de execução |
| **Simplicidade** | Remove a necessidade de condicionais complexas (if/else ou switch) |

##  Como Executar

### Compilação

```bash
javac src/*.java
```

### Execução

```bash
java -cp src Main
```

### Exemplo de Entrada/Saída

```
Digite o peso da sua encomenda: (KG)
5
Digite o tipo do frete: (PAC/SEDEX):
PAC

Valor do frete via PAC: R$ 17.5
```

```
Digite o peso da sua encomenda: (KG)
5
Digite o tipo do frete: (PAC/SEDEX):
SEDEX

Valor do frete via SEDEX: R$ 42.5
```

##  Casos de Uso para Extensão

Com a base estabelecida, é fácil adicionar novos tipos de frete:

### Exemplo: Adicionar UPC (Courier Internacional)

```java
public class UpacStrategy implements FreteStrategy {
    @Override
    public double calcular(double peso) {
        return peso * 5.0 + 50.0; // R$ 50 + R$ 5,00 por kg
    }
}
```

E no `Main`, seria necessário apenas:

```java
case "UPAC" -> {
    pedido.setFreteStrategy(new UpacStrategy());
    System.out.println("Valor do frete via UPAC: R$ " + pedido.calcularFrete());
}
```

##  Conceitos de Design Patterns Aplicados

### Single Responsibility Principle (SRP)
- `Pedido` só cuida de dados do pedido
- Cada Strategy cuida de um único algoritmo de cálculo

### Open/Closed Principle (OCP)
- Aberto para extensão (novas estratégias)
- Fechado para modificação (código existente não muda)

### Liskov Substitution Principle (LSP)
- Qualquer implementação de `FreteStrategy` pode ser usada no lugar da interface

### Interface Segregation Principle (ISP)
- A interface `FreteStrategy` é enxuta e específica

### Dependency Inversion Principle (DIP)
- `Pedido` depende da abstração (`FreteStrategy`), não de implementações concretas

##  Referências

- [Design Patterns Refactoring Guru](https://refactoring.guru/design-patterns/strategy)

##  Conclusão

Este projeto demonstra como o padrão Strategy transforma código rígido e acoplado em uma arquitetura flexível, extensível e fácil de manter. É um excelente exemplo para aprender a escrever código que segue os princípios SOLID.

---

**Desenvolvido em**: Junho de 2026  
**Padrão**: Strategy Pattern  
**Linguagem**: Java

