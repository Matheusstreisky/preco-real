# Planejamento de Versões

## v0.1.0

- [x] Criação dos endpoints de IBPT, NCM e cálculo de preço
- [x] Configuração do pipeline CI e documentação (Javadoc e Swagger)
- [ ] Sincronização automática via scheduler (`IbptSyncSchedule`) configurável por intervalo de dias
- [ ] Verificar e validar o fluxo de identificação de NCM por IA
- [ ] Avaliar cadastro de produtos com NCM pré-definido para consulta direta sem passar pela IA
- [ ] Health check (Spring Boot Actuator)
- [ ] Cache das consultas de impostos por NCM+UF
- [ ] Testes de integração *(opcional)*

## v0.2.0

- [ ] Pesquisar viabilidade de buscar o preço de venda de fábrica para separar lucro do lojista do valor dos impostos

## v0.3.0

- [ ] Pesquisar viabilidade de buscar valores médios de venda por cidade e estado para comparativo de preço entre lojas

## v0.4.0

- [ ] Autenticação e autorização nos endpoints
- [ ] Logs estruturados nos serviços
- [ ] Rate limiting nos endpoints de IA
- [ ] Versionamento de API (`/api/v1/...`) *(opcional)*

---

> Este planejamento é apenas um guia de intenções, não um compromisso formal. Os itens, versões e prioridades estão sujeitos a alteração a qualquer momento.
