package com.streisky.precoreal.services;

import com.streisky.precoreal.models.Ibpt;
import com.streisky.precoreal.repositories.IbptRepository;
import com.streisky.precoreal.services.interfaces.IbptService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class IbptServiceImpl implements IbptService {

    private final IbptRepository ibptRepository;

    @Override
    public Page<Ibpt> findAllByUf(String uf, Pageable pageable) {
        return ibptRepository.findAllByUf(uf.toUpperCase(), pageable);
    }

    @Override
    public Ibpt findByNcmAndUf(String ncm, String uf) {
        return ibptRepository.findByNcmAndUf(ncm, uf.toUpperCase())
                .orElseThrow(() -> new NoSuchElementException(
                        "NCM %s não encontrado para UF %s".formatted(ncm, uf.toUpperCase())));
    }

    @Override
    @Transactional
    public int saveIbpt(String uf, List<Ibpt> entries) {
        return ibptRepository.upsertIbpt(uf, entries);
    }
}
