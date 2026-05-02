package com.streisky.precoreal.repositories.custom;

import com.streisky.precoreal.models.Ibpt;

import java.util.List;

public interface IbptRepositoryCustom {

    int upsertIbpt(String uf, List<Ibpt> entries);
}
