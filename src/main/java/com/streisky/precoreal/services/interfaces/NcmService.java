package com.streisky.precoreal.services.interfaces;

import com.streisky.precoreal.dto.NcmResponseDto;

public interface NcmService {

    NcmResponseDto findNcm(String productName);
}
