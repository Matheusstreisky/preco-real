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
- Exceção: interfaces de controller **não usam Javadoc** — a documentação é feita via anotações Swagger (ver abaixo)

## Validação de Entrada

- Controllers devem ter `@Validated` na implementação para ativar validação de parâmetros de método
- DTOs de entrada devem ter anotações Bean Validation (`@NotNull`, `@NotBlank`, `@Size`, `@Positive`, etc.) nos campos obrigatórios
- As constraints dos DTOs devem estar alinhadas com os constraints do banco de dados (ex: `@Size(max=10)` para `VARCHAR(10)`)
- Parâmetros `@RequestParam` obrigatórios devem ter `@NotBlank` na interface do controller
- Parâmetros `@PathVariable` não precisam de validação — o roteamento já garante a presença
- Campos opcionais (ex: flags booleanas) não precisam de validação
- `GlobalExceptionHandler` deve tratar `MethodArgumentNotValidException` (400) e `HandlerMethodValidationException` (400)

## Swagger / OpenAPI

- Todo endpoint público deve ter documentação Swagger na interface do controller
- Usar `@Tag` na interface para agrupar os endpoints
- Usar `@Operation(summary, description)` em cada método
- Usar `@Parameter` nos parâmetros de path e query
- Usar `@ApiResponse` para cada status HTTP possível (200, 400, 404, 500, etc.)
