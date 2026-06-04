package com.streisky.precoreal.services;

import com.streisky.precoreal.clients.interfaces.IbptDownloadClient;
import com.streisky.precoreal.dtos.SyncResultDto;
import com.streisky.precoreal.models.Ibpt;
import com.streisky.precoreal.parsers.interfaces.IbptParser;
import com.streisky.precoreal.services.interfaces.IbptService;
import com.streisky.precoreal.services.interfaces.IbptSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IbptSyncServiceImpl implements IbptSyncService {

    private final IbptDownloadClient ibptDownloadClient;
    private final IbptParser ibptParser;
    private final IbptService ibptService;

    @Value("${ibpt.ufs}")
    private String[] ufs;

    @Override
    public SyncResultDto syncAll() {
        int totalSynced = 0;
        int errors = 0;
        List<String> errorMessages = new ArrayList<>();

        for (String uf : ufs) {
            try {
                int count = syncByUf(uf);
                totalSynced += count;
            } catch (RuntimeException e) {
                errors++;
                errorMessages.add("UF " + uf + ": " + e.getMessage());
                log.error("Erro ao sincronizar UF {}: {}", uf, e.getMessage());
            }
        }

        return SyncResultDto.builder()
            .ufsProcessed(ufs.length - errors)
            .ufsWithErrors(errors)
            .totalEntriesSynced(totalSynced)
            .errors(errorMessages)
            .build();
    }

    @Override
    public int syncByUf(String uf) {
        log.info("Sincronizando UF {}...", uf);
        String json = ibptDownloadClient.download(uf);
        List<Ibpt> entries = ibptParser.parse(json, uf);
        int count = ibptService.saveIbpt(uf, entries);
        log.info("UF {} sincronizada: {} registros", uf, count);
        return count;
    }

    @Override
    public int syncByUfAndCsv(String uf, String json) {
        List<Ibpt> entries = ibptParser.parse(json, uf);
        return ibptService.saveIbpt(uf, entries);
    }
}
