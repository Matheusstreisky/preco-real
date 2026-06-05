package com.streisky.precoreal.controllers;

import com.streisky.precoreal.controllers.interfaces.IbptSyncController;
import com.streisky.precoreal.dtos.IbptResponseDto;
import com.streisky.precoreal.dtos.SyncResultDto;
import com.streisky.precoreal.services.interfaces.IbptService;
import com.streisky.precoreal.services.interfaces.IbptSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/ibpt")
@RequiredArgsConstructor
public class IbptSyncControllerImpl implements IbptSyncController {

    private static final String ENTRIES_SYNCED = "entriesSynced";

    private final IbptService ibptService;
    private final IbptSyncService ibptSyncService;

    @Override
    @GetMapping
    public Page<IbptResponseDto> findAllByUf(@RequestParam String uf, @PageableDefault(size = 20) Pageable pageable) {
        return ibptService.findAllByUf(uf, pageable).map(IbptResponseDto::fromEntity);
    }

    @Override
    @PostMapping("/sync")
    public SyncResultDto syncAll() {
        return ibptSyncService.syncAll();
    }

    @Override
    @PostMapping("/sync/{uf}")
    public Map<String, Object> syncByUf(@PathVariable String uf) {
        int count = ibptSyncService.syncByUf(uf);
        return Map.of("uf", uf.toUpperCase(), ENTRIES_SYNCED, count);
    }

    @Override
    @PostMapping(value = "/sync/{uf}", consumes = "text/plain")
    public Map<String, Object> syncByUfAndCsv(@PathVariable String uf, @RequestBody String csv) {
        int count = ibptSyncService.syncByUfAndCsv(uf, csv);
        return Map.of("uf", uf.toUpperCase(), ENTRIES_SYNCED, count);
    }
}
