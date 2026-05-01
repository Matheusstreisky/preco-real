package com.streisky.precoreal.services.interfaces;

import com.streisky.precoreal.dtos.NcmResponseDto;

public interface NcmService {

    NcmResponseDto findNcm(String productName);
}
