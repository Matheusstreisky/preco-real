package com.streisky.precoreal.clients;

import com.streisky.precoreal.clients.interfaces.IbptDownloadClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class IbptDownloadClientImpl implements IbptDownloadClient {

    private final RestClient restClient = RestClient.create();

    @Value("${ibpt.download.url-template}")
    private String urlTemplate;

    @Override
    public String download(String uf) {
        if (urlTemplate == null || urlTemplate.isBlank()) {
            throw new IllegalStateException(
                "ibpt.download.url-template não configurado em application.properties");
        }
        String url = urlTemplate.replace("{uf}", uf);
        return restClient.get()
            .uri(url)
            .retrieve()
            .body(String.class);
    }
}
