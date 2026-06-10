# Adapter Pattern - Processamento de Pagamentos

## Descrição

Este projeto implementa o padrão de design **Adapter** em Java para demonstrar como integrar múltiplos gateways de pagamento com interfaces incompatíveis em um fluxo de checkout unificado, flexível e fácil de estender.

##  O Problema (Antes)

### Abordagem sem Adapter

Antes da implementação do padrão Adapter, cada gateway de pagamento possui sua própria API com métodos, parâmetros e formatos de resposta distintos. O cliente precisa conhecer e tratar cada integração separadamente:

```java
// Exemplo conceitual
public class Checkout {
    public void finalizarCompra(String gateway, double valor) {
        if (gateway.equalsIgnoreCase("STONE")) {
            StoneGateway stone = new StoneGateway();
            stone.processarPagamento(valor); // API própria da Stone
        } else if (gateway.equalsIgnoreCase("PAYPAL")) {
            PayPalGateway paypal = new PayPalGateway();
            paypal.makePayment(valor, "BRL"); // API diferente do PayPal
        } else {
            throw new IllegalArgumentException("Gateway inválido");
        }
    }
}
```

### Problemas desta Abordagem

1. **Acoplamento forte**: O cliente fica dependente das classes concretas de cada gateway e de suas APIs específicas

2. **Violação do Open/Closed Principle (OCP)**: Para adicionar um novo gateway (ex.: Mercado Pago), é necessário modificar o código do cliente com novos condicionais

3. **Código com muitas condicionais**: A lógica de seleção e chamada de pagamento cresce conforme novos gateways são integrados

4. **Dificuldade em testes unitários**: Testar o fluxo de pagamento exige mockar APIs distintas e incomparáveis

5. **Baixa reutilização**: A lógica de integração fica espalhada e presa a pontos específicos do sistema

## A Solução (Depois - Adapter Pattern)

### Estrutura do Padrão

O padrão Adapter permite que classes com interfaces incompatíveis trabalhem juntas. Ele atua como uma ponte entre a interface esperada pelo cliente (`ProcessadorPagamento`) e a implementação concreta de cada gateway (`StoneAdapter`, `PayPalAdapter`).

```
┌─────────────────────────────────────────────────────────────────────┐
│                          ADAPTER PATTERN                             │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  ┌──────────────────────┐       ┌─────────────────────────────────┐ │
│  │ ProcessadorPagamento │◄──────│    <<interface>> (Target)       │ │
│  │     (Interface)      │       │  + processar(valor): void       │ │
│  └──────────────────────┘       └─────────────────────────────────┘ │
│           ▲                                                          │
│           │                                                          │
│       ┌───┴────────────────────────┬──────────────────────────┐     │
│       │                            │                          │     │
│  ┌─────────────┐           ┌──────────────┐                   │     │
│  │StoneAdapter │           │PayPalAdapter │                   │     │
│  ├─────────────┤           ├──────────────┤                   │     │
│  │processar()  │           │ processar()  │                   │     │
│  │  ↓ adapta   │           │  ↓ adapta    │                   │     │
│  │ API Stone   │           │ API PayPal   │                   │     │
│  └─────────────┘           └──────────────┘                   │     │
│                                                                  │     │
│  ┌────────────────────────────────────────────────────────────┐  │     │
│  │                    Main (Cliente)                          │  │     │
│  ├────────────────────────────────────────────────────────────┤  │     │
│  │ - listaProdutos: Map<String, Double>                       │  │     │
│  ├────────────────────────────────────────────────────────────┤  │     │
│  │ + seleciona produto e gateway                              │  │     │
│  │ + processadorPagamento.processar(valor)                    │  │     │
│  └────────────────────────────────────────────────────────────┘  │     │
│                                                                    │     │
└────────────────────────────────────────────────────────────────────┘
```

### Arquivos Implementados

#### 1. **ProcessadorPagamento.java** - Interface Target

Define o contrato unificado que o cliente espera para processar pagamentos:

```java
public interface ProcessadorPagamento {
    void processar(double valor);
}
```

**Responsabilidade**: Estabelecer uma interface comum, independente do gateway utilizado

---

#### 2. **StoneAdapter.java** - Adapter Concreto (Stone)

Adapta a integração com o gateway Stone para a interface `ProcessadorPagamento`:

```java
public class StoneAdapter implements ProcessadorPagamento {

    @Override
    public void processar(double valor) {
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
```

**Comportamento**: Simula o processamento via Stone e exibe o valor no formato `R$`

---

#### 3. **PayPalAdapter.java** - Adapter Concreto (PayPal)

Adapta a integração com o gateway PayPal para a mesma interface:

```java
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
```

**Comportamento**: Simula o processamento via PayPal com formato de resposta distinto do Stone

---

#### 4. **Main.java** - Cliente

Demonstra o checkout com seleção de produto e gateway de pagamento:

```java
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, Double> listaProdutos = Map.of(
                "Mouse Logitech Mx Anywhere 2s", 350.00,
                "Teclado Mecânico Keycron", 1500.0,
                "Macbook Air M1 8GB", 4000.0
        );

        // ... exibe produtos e solicita seleção ...

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
    }
}
```

**Responsabilidades**:
- Exibir catálogo de produtos e capturar a escolha do usuário
- Instanciar o adapter correto com base na opção de pagamento
- Delegar o processamento via interface comum, sem conhecer detalhes de cada gateway

##  Vantagens do Adapter Pattern

| Aspecto | Benefício |
|---------|-----------|
| **Integração** | Permite usar bibliotecas e APIs de terceiros sem alterar o código existente |
| **Open/Closed Principle** | Aberto para extensão (novos adapters), fechado para modificação (cliente não muda) |
| **Single Responsibility** | Cada adapter encapsula a tradução de uma API específica |
| **Testabilidade** | O cliente pode ser testado com mocks de `ProcessadorPagamento` |
| **Reutilização** | A interface comum pode ser usada em diferentes partes do sistema |
| **Flexibilidade** | O gateway pode ser trocado em tempo de execução |
| **Simplicidade** | Remove condicionais complexas do cliente — basta chamar `processar()` |

##  Como Executar

### Compilação

```bash
cd adapter-pattern/src
javac Main.java pagamento/*.java
```

### Execução

```bash
java Main
```

### Exemplo de Entrada/Saída

```
=== PRODUTOS DISPONIVEL ===
Mouse Logitech Mx Anywhere 2s - R$350.0
Teclado Mecânico Keycron - R$1500.0
Macbook Air M1 8GB - R$4000.0
Digite o nome do produto a ser comprado:
Mouse Logitech Mx Anywhere 2s
Produto encontrado! Valor R$350.0
Qual gateway de pagamento deverá ser utilizado? 
(1) Stone 
(2) PayPal

1
Pagamento utilizando STONE está sendo processado...
Pagamento realizado com sucesso! Valor: R$ 350.0
```

```
=== PRODUTOS DISPONIVEL ===
Mouse Logitech Mx Anywhere 2s - R$350.0
Teclado Mecânico Keycron - R$1500.0
Macbook Air M1 8GB - R$4000.0
Digite o nome do produto a ser comprado:
Macbook Air M1 8GB
Produto encontrado! Valor R$4000.0
Qual gateway de pagamento deverá ser utilizado? 
(1) Stone 
(2) PayPal

2
Pagamento utilizando Pay Pal está sendo processado...
Pagamento realizado com sucesso! Valor: $4000.0 BRL
```

##  Casos de Uso para Extensão

Com a base estabelecida, é fácil adicionar novos gateways de pagamento:

### Exemplo: Adicionar Mercado Pago

```java
public class MercadoPagoAdapter implements ProcessadorPagamento {
    @Override
    public void processar(double valor) {
        System.out.println("Pagamento via Mercado Pago está sendo processado...");
        // Adapta a API do Mercado Pago para o contrato comum
        System.out.println("Pagamento realizado com sucesso! Valor: R$ " + valor);
    }
}
```

E no `Main`, seria necessário apenas:

```java
case "3":
    processadorPagamento = new MercadoPagoAdapter();
    break;
```

O restante do fluxo de checkout permanece inalterado.

##  Conceitos de Design Patterns Aplicados

### Single Responsibility Principle (SRP)
- `Main` cuida apenas do fluxo de checkout
- Cada Adapter cuida da tradução de uma API de gateway específica

### Open/Closed Principle (OCP)
- Aberto para extensão (novos adapters)
- Fechado para modificação (código do cliente não precisa mudar)

### Liskov Substitution Principle (LSP)
- Qualquer implementação de `ProcessadorPagamento` pode ser usada no lugar da interface

### Interface Segregation Principle (ISP)
- A interface `ProcessadorPagamento` é enxuta e focada em uma única operação

### Dependency Inversion Principle (DIP)
- `Main` depende da abstração (`ProcessadorPagamento`), não de implementações concretas de gateways

##  Referências

- [Design Patterns Refactoring Guru - Adapter](https://refactoring.guru/design-patterns/adapter)

##  Conclusão

Este projeto demonstra como o padrão Adapter resolve o problema de integração com sistemas externos de interfaces incompatíveis. Ao unificar gateways distintos sob um contrato comum, o código do cliente fica limpo, desacoplado e preparado para receber novas integrações sem refatorações extensas.

---

**Desenvolvido em**: Junho de 2026  
**Padrão**: Adapter Pattern  
**Linguagem**: Java
