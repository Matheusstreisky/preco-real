package com.streisky.precoreal.services.interfaces;

import com.streisky.precoreal.dto.SyncResultDto;

public interface IbptSyncService {

    SyncResultDto syncAll();

    int syncByUf(String uf);

    int syncByUfAndCsv(String uf, String csv);
}
