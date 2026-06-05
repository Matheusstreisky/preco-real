package com.streisky.precoreal.factories;

import com.streisky.precoreal.dtos.NcmResponseDto;

public class NcmFactory {

    public NcmResponseDto build() {
        return new NcmResponseDto("85171299", "Aparelhos telefônicos", "alta");
    }
}
