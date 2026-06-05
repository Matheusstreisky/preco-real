# Regras do Projeto

## Javadoc

- Adicionar Javadoc **somente nos métodos públicos das interfaces**, nunca nas implementações
- Implementações usam apenas `@Override`, sem repetir a documentação
- Exceção: métodos públicos que não pertencem a nenhuma interface devem ter Javadoc na própria classe (ex: `@ExceptionHandler` em `GlobalExceptionHandler`)
- Todo Javadoc deve incluir `@param`, `@return` e `@throws` quando aplicável
