# Simple Factory Pattern - Notificações

## Descrição

Este projeto serve para estudos do padrão de projeto **Simple Factory** em Java. Ele contém duas abordagens para criar e enviar notificações: uma forma recomendada utilizando a *Simple Factory* (`NotificationFactory`) e uma forma tradicional (sem padrão) — explicada como comparação conceitual.

O objetivo é demonstrar como a Simple Factory centraliza a lógica de criação de objetos (instanciação), reduzindo condicionais espalhadas pelo código e deixando o cliente (`Main`) mais limpo.

---

##  O Problema (Abordagem sem Simple Factory)

Antes de aplicar a Simple Factory seria comum ver o cliente responsável por instanciar implementações concretas, por exemplo:

```java
// Exemplo conceitual (não presente como arquivo separado)
if (notificationType.equalsIgnoreCase("SMS")) {
    new NotificationSms().send(mensagem);
} else if (notificationType.equalsIgnoreCase("EMAIL")) {
    new NotificationEmail().send(mensagem);
} else if (notificationType.equalsIgnoreCase("SLACK")) {
    new NotificationSlack().send(mensagem);
} else {
    throw new IllegalArgumentException("Tipo de notificação inválido: " + notificationType);
}
```

Problemas dessa abordagem:

- Código do cliente fica acoplado às implementações concretas
- Repetição de condicionais em vários pontos do sistema quando a criação é necessária
- Difícil de manter e estender (para adicionar um novo tipo de notificação é preciso modificar todos os clientes)

---

## A Solução (Simple Factory)

Com a Simple Factory, toda a lógica de criação de objetos é centralizada em uma classe (a fábrica). O cliente apenas pede um tipo e recebe um objeto já configurado que implementa uma interface comum.

Arquitetura (resumida):

Interface: `NotificationTypeInterface` (contrato comum)
Implementações concretas: `NotificationSms`, `NotificationEmail`, `NotificationSlack`
Fábrica: `NotificationFactory` (responsável por instanciar as classes concretas)
Cliente: `Main` (usa a fábrica para obter a implementação desejada e chama `send`)

```
┌──────────────────────────────────────────────────┐
│                   Simple Factory                  │
├───────────────────────────────────────────────────┤
│                                                   │
│  Client (Main)  ──requests type──▶  NotificationFactory  ──returns──▶ NotificationTypeInterface  
│                                                   │
│               ▲                ▲                 ▲
│               │                │                 │
│        NotificationSms   NotificationEmail   NotificationSlack
│                                                   │
└──────────────────────────────────────────────────┘
```

---

## Arquivos no projeto

Local: `simple-factory-pattern/src`

- `Main.java` - Cliente que lê entrada do usuário, pede a fábrica e envia a mensagem
- `notification/NotificationTypeInterface.java` - Interface que define `void send(String mensagem)`
- `notification/NotificationFactory.java` - Implementação da Simple Factory
- `notification/NotificationSms.java` - Implementação concreta (SMS)
- `notification/NotificationEmail.java` - Implementação concreta (Email)
- `notification/NotificationSlack.java` - Implementação concreta (Slack)

### Contenido principal (resumo das classes)

`NotificationTypeInterface`:

```java
package notification;

public interface NotificationTypeInterface {
    void send(String mensagem);
}
```

`NotificationSms` / `NotificationEmail` / `NotificationSlack`:

Cada classe implementa `NotificationTypeInterface` e imprime no console uma linha simulando o envio:

```java
System.out.println("Enviando SMS: " + mensagem);
// ou
System.out.println("Enviando EMAIL: " + mensagem);
// ou
System.out.println("Enviando SLACK: " + mensagem);
```

`NotificationFactory`:

```java
package notification;

public class NotificationFactory {
    public NotificationTypeInterface create(String notificationType) {
        return switch (notificationType) {
            case "SMS" -> new NotificationSms();
            case "EMAIL" -> new NotificationEmail();
            case "SLACK" -> new NotificationSlack();
            default -> throw new IllegalArgumentException("Tipo de notificação inválida: " + notificationType);
        };
    }
}
```

`Main` (cliente):

```java
import notification.NotificationFactory;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a forma da notificação a ser enviada:");
        String notificationType = scanner.nextLine();

        System.out.println("Digite a mensagem a ser enviada");
        String mensagem = scanner.nextLine();

        NotificationFactory notificationFactory = new NotificationFactory();
        notificationFactory.create(notificationType).send(mensagem);
    }
}
```

---

## Como executar

Compile os arquivos .java (assumindo que você está no diretório `simple-factory-pattern`):

```bash
cd /Users/filipemota.dev/www/design-patterns/simple-factory-pattern
javac src/*.java src/notification/*.java
```

Execute a aplicação:

```bash
java -cp src Main
```

Exemplo de execução (entrada do usuário):

```
Digite a forma da notificação a ser enviada:
SMS
Digite a mensagem a ser enviada
Olá via SMS!

Enviando SMS: Olá via SMS!
```

Observação: A fábrica espera os tipos exatamente como `SMS`, `EMAIL` ou `SLACK` (case-sensitive no código atual). Para melhorar a experiência do usuário, pode-se normalizar a entrada (ex.: `notificationType = notificationType.trim().toUpperCase()`).

---

## Vantagens do Simple Factory (em comparação com a abordagem sem padrão)

- Centraliza a criação de objetos, removendo condicionais do cliente
- Facilita a manutenção: ao adicionar um novo tipo de notificação, só é necessário modificar a fábrica e adicionar a nova classe concreta
- Melhora a testabilidade do cliente (o cliente consome uma interface)
- Reduz duplicação de código quando a criação é necessária em vários locais

---

## Desvantagens e cuidados

- A Simple Factory concentra a lógica de criação em um único local, o que pode virar um ponto de acoplamento se a fábrica crescer demais
- Para cenários mais complexos (injeção de dependências, configuração por IoC/DI, múltiplas variantes de criação) padrões como Factory Method ou Abstract Factory e frameworks de DI são mais indicados

---

## Melhorias sugeridas

1. Normalizar a entrada do usuário em `Main` (trim e toUpperCase) para evitar erros por maiúsculas/minúsculas.
2. Tornar a fábrica case-insensitive ou aceitar aliases.
3. Adicionar testes unitários para cada implementação de `NotificationTypeInterface` e para `NotificationFactory`.
4. Se for necessário configurar provedores (ex.: credenciais, endpoints), adicionar um mecanismo de configuração e injeção ao criar as instâncias na fábrica.
5. Substituir o uso de `switch` por um registro (Map<String, Supplier<NotificationTypeInterface>>) se quiser permitir registro dinâmico de tipos sem modificar a fábrica.

---

## Conceitos SOLID aplicados

- Single Responsibility Principle: cada classe concreta tem a única responsabilidade de enviar um tipo de notificação.
- Open/Closed Principle: para adicionar novas notificações, estendemos com novas classes sem modificar o cliente (apenas alterando a fábrica).
- Liskov Substitution Principle: as implementações substituem a interface `NotificationTypeInterface`.
- Interface Segregation Principle: a interface é pequena e específica (`send`).
- Dependency Inversion Principle: `Main` depende da abstração `NotificationTypeInterface` (via fábrica).

---

## Referências e leituras adicionais

- Padrões de Criação (Creational Patterns) — Simple Factory (variações e alternativas)
- Refactoring Guru — Factory Pattern: https://refactoring.guru/pt-br/design-patterns/factory-method

---

Desenvolvido para fins de estudo — Junho de 2026

