package com.streisky.precoreal.controllers.interfaces;

import com.streisky.precoreal.dtos.SyncResultDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface IbptSyncController {

    SyncResultDto syncAll();

    Map<String, Object> syncByUf(@PathVariable String uf);

    Map<String, Object> syncByUfAndCsv(@PathVariable String uf, @RequestBody String csv);
}
