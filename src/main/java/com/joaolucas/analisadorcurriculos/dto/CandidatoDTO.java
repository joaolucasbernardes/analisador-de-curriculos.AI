package com.joaolucas.analisadorcurriculos.dto;

import java.util.List;

public record CandidatoDTO(
                String nome,
                String email,
                String telefone,
                String linkLinkedin,
                String linkGithub,
                List<String> listaDeHabilidades,
                String resumoExperiencia,
                String nivelEstimado) {
}
