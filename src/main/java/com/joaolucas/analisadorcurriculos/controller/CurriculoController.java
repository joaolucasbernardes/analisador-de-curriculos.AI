package com.joaolucas.analisadorcurriculos.controller;

import com.joaolucas.analisadorcurriculos.dto.CandidatoDTO;
import com.joaolucas.analisadorcurriculos.dto.MatchDTO;
import com.joaolucas.analisadorcurriculos.entity.CurriculoAnalisado;
import com.joaolucas.analisadorcurriculos.repository.CurriculoRepository;
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
    private final CurriculoRepository curriculoRepository;

    public CurriculoController(PdfReaderService pdfReaderService, GeminiService geminiService,
            CurriculoRepository curriculoRepository) {
        this.pdfReaderService = pdfReaderService;
        this.geminiService = geminiService;
        this.curriculoRepository = curriculoRepository;
    }

    @PostMapping("/analisar")
    public ResponseEntity<CandidatoDTO> analisarCurriculo(@RequestParam("file") MultipartFile file) {
        String textoDoCurriculo = pdfReaderService.extrairTexto(file);

        CandidatoDTO candidatoAnalisado = geminiService.analisarCurriculo(textoDoCurriculo);

        CurriculoAnalisado entidade = new CurriculoAnalisado(
                candidatoAnalisado.nome(),
                candidatoAnalisado.email(),
                candidatoAnalisado.telefone(),
                candidatoAnalisado.linkLinkedin(),
                candidatoAnalisado.linkGithub(),
                candidatoAnalisado.listaDeHabilidades(),
                candidatoAnalisado.resumoExperiencia(),
                candidatoAnalisado.nivelEstimado());
        curriculoRepository.save(entidade);

        return ResponseEntity.ok(candidatoAnalisado);
    }

    @PostMapping("/match")
    public ResponseEntity<MatchDTO> analisarMatch(
            @RequestParam("file") MultipartFile file,
            @RequestParam("vaga") String descricaoVaga) {

        String textoDoCurriculo = pdfReaderService.extrairTexto(file);
        MatchDTO matchAnalisado = geminiService.analisarMatchVaga(textoDoCurriculo, descricaoVaga);

        return ResponseEntity.ok(matchAnalisado);
    }
}
