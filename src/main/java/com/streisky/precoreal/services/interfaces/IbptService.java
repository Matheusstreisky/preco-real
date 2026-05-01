package com.streisky.precoreal.services.interfaces;

import com.streisky.precoreal.model.Ibpt;

public interface IbptService {

    Ibpt findByNcmAndUf(String ncm, String uf);
}
