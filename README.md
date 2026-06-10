# Design Patterns — Mini projetos de estudo

Este repositório contém uma coleção de mini-projetos e exemplos implementados para estudar e exemplificar padrões de projeto (design patterns) em Java. A ideia é manter cada padrão em um diretório próprio e implementar exemplos simples, fáceis de entender e executar.

> Observação: Este é um portfólio em progresso. Atualmente há dois desafios implementados (Simple Factory e Strategy). Novos padrões serão adicionados gradualmente.


## Estrutura do repositório

Cada padrão tem sua própria pasta e contém código-fonte em `src/` junto com um `README.md` local explicando o exemplo quando aplicável.

- `simple-factory-pattern/` — Exemplo do padrão Simple Factory
  - `src/` contém `Main.java` e as classes do exemplo em `notification/`
- `strategy-pattern/` — Exemplo do padrão Strategy
  - `src/` contém `Main.java`, `Pedido.java` e estratégias de frete

Exemplo de estrutura:

```
design-patterns/
├─ simple-factory-pattern/
│  └─ src/
│     ├─ Main.java
│     └─ notification/
        ├─ NotificationEmail.java
        └─ ...
├─ strategy-pattern/
│  └─ src/
     ├─ Main.java
     └─ ...
```

## Como executar os exemplos (rápido)

Os exemplos são projetos Java simples sem build system (Maven/Gradle). Você pode compilar e rodar a partir da linha de comando usando `javac` e `java`.

1) Exemplo: Simple Factory

```bash
cd simple-factory-pattern/src
javac Main.java notification/*.java
java Main
```

2) Exemplo: Strategy

```bash
cd strategy-pattern/src
javac Main.java Pedido.java *.java
java Main
```

Observações:
- Se preferir, importe cada pasta como um projeto no seu IDE (IntelliJ IDEA, Eclipse, VS Code) e rode `Main` diretamente.
- Se futuramente adicionarmos Maven/Gradle, as instruções de build serão atualizadas.

## Objetivos do repositório

- Fornecer implementações curtas e didáticas de padrões de projeto em Java.
- Servir como material de estudo e portfólio para entrevistas e revisão técnica.
- Permitir que outras pessoas olhem o código, sugiram melhorias e contribuam.

## Licença

Este repositório está disponível sob a licença MIT — sinta-se livre para usar os exemplos para estudo ou referência.
