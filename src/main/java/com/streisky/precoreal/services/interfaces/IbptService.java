package com.streisky.precoreal.services.interfaces;

import com.streisky.precoreal.models.Ibpt;

public interface IbptService {

    Ibpt findByNcmAndUf(String ncm, String uf);
}
