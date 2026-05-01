package com.streisky.precoreal.controllers;

import com.streisky.precoreal.controllers.interfaces.IbptSyncController;
import com.streisky.precoreal.dtos.SyncResultDto;
import com.streisky.precoreal.services.interfaces.IbptSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ibpt")
@RequiredArgsConstructor
public class IbptSyncControllerImpl implements IbptSyncController {

    private final IbptSyncService ibptSyncService;

    /** Baixa e sincroniza todos os estados a partir da URL configurada. */
    @Override
    @PostMapping("/sync")
    public SyncResultDto syncAll() {
        return ibptSyncService.syncAll();
    }

    /** Baixa e sincroniza somente a UF informada. Ex: POST /api/ibpt/sync/SP */
    @Override
    @PostMapping("/sync/{uf}")
    public Map<String, Object> syncByUf(@PathVariable String uf) {
        int count = ibptSyncService.syncByUf(uf);
        return Map.of("uf", uf.toUpperCase(), "entriesSynced", count);
    }

    /**
     * Sincroniza uma UF a partir de um CSV enviado no corpo da requisição.
     * Content-Type: text/plain — Ex: POST /api/ibpt/sync/SP (com body CSV)
     */
    @Override
    @PostMapping(value = "/sync/{uf}", consumes = "text/plain")
    public Map<String, Object> syncByUfAndCsv(@PathVariable String uf, @RequestBody String csv) {
        int count = ibptSyncService.syncByUfAndCsv(uf, csv);
        return Map.of("uf", uf.toUpperCase(), "entriesSynced", count);
    }
}
