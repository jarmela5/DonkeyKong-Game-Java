# Donkey Kong - Java Edition

Um jogo Donkey Kong implementado em Java com padrões de design orientado a objetos.

## Características

-  3 níveis diferentes com dificuldade progressiva
-  Múltiplos inimigos: Gorila, Bastões (Bats), Carne (Beef)
-  Sistema de armas e power-ups: Banana, Bomba, Espada
-  Mecânicas diversas: Escadas, Portas, Armadilhas ocultas
-  Sistema de pontuação com Top 10 ranking
-  Interface gráfica com tiles de imagem
-  Padrão Observer para sincronização de eventos

## Tecnologias

- **Linguagem**: Java
- **Arquitetura**: MVC com padrão Observer
- **GUI**: Sistema de tiles com imagens
- **Estrutura**: OOP com interfaces e classes abstratas

## Como Jogar

1. Compile o projeto
2. Execute `Main.java`
3. Navegue com as setas do teclado
4. Colete items, derrote inimigos e chegue à Princesa
5. Complete os 3 níveis para ganhar!

## Estrutura do Projeto

O projeto está organizado da seguinte forma:

```text
src/
├── objects/            # Entidades do jogo (Jogadores, Caixas, Paredes, etc.)
├── pt/iscte/poo/
│   ├── game/           # Lógica principal e gestão do estado do jogo
│   ├── gui/            # Interface gráfica (ImageMatrixGUI)
│   ├── observer/       # Implementação do Padrão de Desenho Observer
│   └── utils/          # Classes utilitárias (Vetores, Direções, etc.)
rooms/                  # Ficheiros de texto com a configuração dos níveis
images/                 # Assets gráficos (sprites e ícones)

```
## Autores

- José Jarmela (122663)
- Tiago Nogueira (122693)





