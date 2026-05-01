package com.streisky.precoreal.controllers;

import com.streisky.precoreal.controllers.interfaces.NcmController;
import com.streisky.precoreal.dtos.NcmResponseDto;
import com.streisky.precoreal.services.interfaces.NcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class NcmControllerImpl implements NcmController {

    private final NcmService ncmService;

    /** Ex: GET /api/products/ncm?productName=Notebook Dell 15 */
    @Override
    @GetMapping("/ncm")
    public NcmResponseDto findNcm(@RequestParam String productName) {
        return ncmService.findNcm(productName);
    }
}
