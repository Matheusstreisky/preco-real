package com.streisky.precoreal.services;

import com.streisky.precoreal.factories.IbptFactory;
import com.streisky.precoreal.models.Ibpt;
import com.streisky.precoreal.repositories.IbptRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IbptServiceImplTest {

    @Mock
    private IbptRepository ibptRepository;

    @InjectMocks
    private IbptServiceImpl service;

    private final IbptFactory ibptFactory = new IbptFactory();

    @Test
    void findByNcmAndUf_whenFound_returnsIbpt() {
        Ibpt ibpt = ibptFactory.build("12345678", "SP");
        when(ibptRepository.findByNcmAndUf("12345678", "SP")).thenReturn(Optional.of(ibpt));

        Ibpt result = service.findByNcmAndUf("12345678", "sp");

        assertThat(result).isEqualTo(ibpt);
    }

    @Test
    void findByNcmAndUf_normalizesUfToUpperCase() {
        when(ibptRepository.findByNcmAndUf("12345678", "SP")).thenReturn(Optional.of(ibptFactory.build("12345678", "SP")));

        service.findByNcmAndUf("12345678", "sp");

        verify(ibptRepository).findByNcmAndUf("12345678", "SP");
    }

    @Test
    void findByNcmAndUf_whenNotFound_throwsNoSuchElementException() {
        when(ibptRepository.findByNcmAndUf(any(), any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findByNcmAndUf("12345678", "SP"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("12345678")
                .hasMessageContaining("SP");
    }

    @Test
    void saveIbpt_delegatesToRepository() {
        List<Ibpt> entries = List.of(ibptFactory.build("12345678", "SP"));
        when(ibptRepository.upsertIbpt("SP", entries)).thenReturn(1);

        int result = service.saveIbpt("SP", entries);

        assertThat(result).isEqualTo(1);
        verify(ibptRepository).upsertIbpt("SP", entries);
    }

    @Test
    void findAllByUf_normalizesUfAndReturnsPage() {
        PageRequest pageable = PageRequest.of(0, 20);
        Page<Ibpt> page = new PageImpl<>(List.of(ibptFactory.build("12345678", "SP")));
        when(ibptRepository.findAllByUf(eq("SP"), eq(pageable))).thenReturn(page);

        Page<Ibpt> result = service.findAllByUf("sp", pageable);

        assertThat(result).isEqualTo(page);
        verify(ibptRepository).findAllByUf("SP", pageable);
    }
}
