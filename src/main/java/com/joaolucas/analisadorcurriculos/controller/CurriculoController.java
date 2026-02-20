package com.joaolucas.analisadorcurriculos.controller;

import com.joaolucas.analisadorcurriculos.dto.CandidatoDTO;
import com.joaolucas.analisadorcurriculos.service.GeminiService;
import com.joaolucas.analisadorcurriculos.service.PdfReaderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/curriculos")
public class CurriculoController {

    private final PdfReaderService pdfReaderService;
    private final GeminiService geminiService;

    public CurriculoController(PdfReaderService pdfReaderService, GeminiService geminiService) {
        this.pdfReaderService = pdfReaderService;
        this.geminiService = geminiService;
    }

    @PostMapping("/analisar")
    public ResponseEntity<CandidatoDTO> analisarCurriculo(@RequestParam("file") MultipartFile file) {
        String textoDoCurriculo = pdfReaderService.extrairTexto(file);

        CandidatoDTO candidatoAnalisado = geminiService.analisarCurriculo(textoDoCurriculo);

        return ResponseEntity.ok(candidatoAnalisado);
    }
}
