package com.streisky.precoreal.controllers.interfaces;

import com.streisky.precoreal.dtos.NcmResponseDto;
import org.springframework.web.bind.annotation.RequestParam;

public interface NcmController {

    NcmResponseDto findNcm(@RequestParam String productName);
}
