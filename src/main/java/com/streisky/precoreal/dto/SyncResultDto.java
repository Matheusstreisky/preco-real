package com.streisky.precoreal.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SyncResultDto {
    private int ufsProcessed;
    private int ufsWithErrors;
    private int totalEntriesSynced;
    private List<String> errors;
}
