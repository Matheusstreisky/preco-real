package com.streisky.precoreal.clients.interfaces;

public interface AiClient {

    /**
     * Envia uma mensagem ao provedor de IA e retorna o texto da resposta.
     *
     * @param systemPrompt instrução de sistema que define o comportamento do modelo
     * @param userMessage  mensagem do usuário a ser processada
     * @return texto gerado pelo modelo
     */
    String chat(String systemPrompt, String userMessage);
}
