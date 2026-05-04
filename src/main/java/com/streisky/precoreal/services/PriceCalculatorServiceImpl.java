package com.streisky.precoreal.services;

import com.streisky.precoreal.dtos.PriceCalculationRequestDto;
import com.streisky.precoreal.dtos.PriceCalculationResponseDto;
import com.streisky.precoreal.models.Ibpt;
import com.streisky.precoreal.services.interfaces.IbptService;
import com.streisky.precoreal.services.interfaces.PriceCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class PriceCalculatorServiceImpl implements PriceCalculatorService {

    // Casas decimais intermediárias para manter precisão antes de arredondar o preço final
    private static final int RATE_SCALE = 6;

    private final IbptService ibptService;

    /**
     * Calcula o preço sem impostos usando a fórmula:
     * preço_sem_imposto = preço / (1 + alíquota_total / 100)
     *
     * Nota: simplificação — na prática, ICMS é "por dentro" e IPI é "por fora".
     */
    @Override
    public PriceCalculationResponseDto calculate(PriceCalculationRequestDto request) {
        Ibpt ibpt = ibptService.findByNcmAndUf(request.getNcm(), request.getUf());

        boolean isImported = Boolean.TRUE.equals(request.getImported());
        BigDecimal federal = isImported ? ibpt.getAliquotaImportado() : ibpt.getAliquotaNacional();
        BigDecimal estadual = ibpt.getAliquotaEstadual();
        BigDecimal municipal = ibpt.getAliquotaMunicipal();

        BigDecimal totalRate = federal.add(estadual).add(municipal);
        BigDecimal divisor = BigDecimal.ONE.add(
            totalRate.divide(new BigDecimal("100"), RATE_SCALE, RoundingMode.HALF_UP));
        BigDecimal priceWithoutTax = request.getPrice().divide(divisor, 2, RoundingMode.HALF_UP);
        BigDecimal taxAmount = request.getPrice().subtract(priceWithoutTax);

        return PriceCalculationResponseDto.builder()
            .originalPrice(request.getPrice())
            .priceWithoutTax(priceWithoutTax)
            .taxAmount(taxAmount)
            .totalRate(totalRate)
            .breakdown(PriceCalculationResponseDto.TaxBreakdown.builder()
                .federal(federal)
                .state(estadual)
                .municipal(municipal)
                .build())
            .build();
    }
}
