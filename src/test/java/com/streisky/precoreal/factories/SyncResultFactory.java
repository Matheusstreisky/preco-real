package com.streisky.precoreal.factories;

import com.streisky.precoreal.dtos.SyncResultDto;

import java.util.List;

public class SyncResultFactory {

    public SyncResultDto build() {
        return SyncResultDto.builder()
                .ufsProcessed(2)
                .ufsWithErrors(0)
                .totalEntriesSynced(100)
                .errors(List.of())
                .build();
    }
}
