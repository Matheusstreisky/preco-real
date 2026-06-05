package com.streisky.precoreal.controllers.interfaces;

import com.streisky.precoreal.dtos.IbptResponseDto;
import com.streisky.precoreal.dtos.SyncResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Tag(name = "IBPT", description = "Sincronização e consulta da tabela IBPT de alíquotas tributárias")
public interface IbptSyncController {

    @Operation(summary = "Listar registros por UF", description = "Retorna os registros IBPT de uma UF de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Página de registros IBPT da UF informada")
    @ApiResponse(responseCode = "400", description = "UF inválida")
    Page<IbptResponseDto> findAllByUf(
            @NotBlank @Size(min = 2, max = 2) @Parameter(description = "Sigla do estado", example = "SP") @RequestParam String uf,
            Pageable pageable);

    @Operation(summary = "Sincronizar todos os estados", description = "Baixa e sincroniza a tabela IBPT de todos os estados configurados.")
    @ApiResponse(responseCode = "200", description = "Resultado com totais de UFs processadas, erros e registros sincronizados")
    SyncResultDto syncAll();

    @Operation(summary = "Sincronizar por UF", description = "Baixa e sincroniza a tabela IBPT de uma UF específica.")
    @ApiResponse(responseCode = "200", description = "UF e quantidade de registros sincronizados")
    @ApiResponse(responseCode = "404", description = "UF não encontrada ou sem dados disponíveis")
    Map<String, Object> syncByUf(
            @Parameter(description = "Sigla do estado", example = "SP") @PathVariable String uf);

    @Operation(summary = "Sincronizar por UF via CSV", description = "Sincroniza a tabela IBPT de uma UF a partir de CSV enviado no corpo da requisição.")
    @ApiResponse(responseCode = "200", description = "UF e quantidade de registros sincronizados")
    @ApiResponse(responseCode = "400", description = "Conteúdo CSV inválido ou ausente")
    Map<String, Object> syncByUfAndCsv(
            @Parameter(description = "Sigla do estado", example = "SP") @PathVariable String uf,
            @NotBlank @RequestBody String csv);
}
