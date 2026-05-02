package com.streisky.precoreal.services.interfaces;

import com.streisky.precoreal.models.Ibpt;

import java.util.List;

public interface IbptService {

    Ibpt findByNcmAndUf(String ncm, String uf);

    int saveIbpt(String uf, List<Ibpt> entries);
}
