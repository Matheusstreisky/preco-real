package com.streisky.precoreal.services;

import com.streisky.precoreal.clients.interfaces.IbptDownloadClient;
import com.streisky.precoreal.dtos.SyncResultDto;
import com.streisky.precoreal.models.Ibpt;
import com.streisky.precoreal.parsers.interfaces.IbptParser;
import com.streisky.precoreal.repositories.IbptRepository;
import com.streisky.precoreal.services.interfaces.IbptSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IbptSyncServiceImpl implements IbptSyncService {

    private final IbptDownloadClient ibptDownloadClient;
    private final IbptParser ibptParser;
    private final IbptRepository ibptRepository;

    @Value("${ibpt.ufs}")
    private String[] ufs;

    /** Baixa e sincroniza todos os estados configurados em ibpt.ufs. */
    @Override
    public SyncResultDto syncAll() {
        int totalSynced = 0;
        int errors = 0;
        List<String> errorMessages = new ArrayList<>();

        for (String uf : ufs) {
            try {
                int count = syncByUf(uf);
                totalSynced += count;
            } catch (Exception e) {
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

    /** Baixa o CSV do IBPT para a UF e sincroniza. */
    @Override
    @Transactional
    public int syncByUf(String uf) {
        log.info("Sincronizando UF {}...", uf);
        String csv = ibptDownloadClient.downloadCsv(uf);
        int count = saveIbpt(uf, csv);
        log.info("UF {} sincronizada: {} registros", uf, count);
        return count;
    }

    /** Sincroniza uma UF a partir de um CSV fornecido diretamente. */
    @Override
    @Transactional
    public int syncByUfAndCsv(String uf, String csv) {
        return saveIbpt(uf, csv);
    }

    private int saveIbpt(String uf, String csv) {
        List<Ibpt> entries = ibptParser.parse(csv, uf);
        ibptRepository.deleteByUf(uf.toUpperCase());
        ibptRepository.saveAll(entries);
        return entries.size();
    }
}
