# Regras do Projeto

## Testes Unitários

- Criar testes unitários para: clients, controllers, parsers e services
- **Não** criar testes para: config, constants, dtos, exceptions, models e repositories

## Factories de Testes

- Toda criação de objetos de domínio e DTOs nos testes deve ser extraída para classes Factory
- As factories ficam em `src/test/java/.../factories/`
- Factories agrupam objetos por domínio: `PriceCalculationFactory` constrói request e response; `IbptFactory` constrói `Ibpt`; etc.
- Factories de tipo único usam o método `build()`; factories de múltiplos tipos usam métodos nomeados: `buildRequest()`, `buildResponse()`, etc.
- Métodos com parâmetros usam sobrecarga: `build(String ncm, String uf)`, `buildRequest(boolean imported)`, etc.
- Nunca criar objetos de teste inline nos métodos de teste — sempre usar a factory correspondente

## Javadoc

- Adicionar Javadoc **somente nos métodos públicos das interfaces**, nunca nas implementações
- Implementações usam apenas `@Override`, sem repetir a documentação
- Exceção: métodos públicos que não pertencem a nenhuma interface devem ter Javadoc na própria classe (ex: `@ExceptionHandler` em `GlobalExceptionHandler`)
- Todo Javadoc deve incluir `@param`, `@return` e `@throws` quando aplicável
