package com.joaolucas.analisadorcurriculos.service;

import com.joaolucas.analisadorcurriculos.dto.CandidatoDTO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GeminiService {

    private final ChatClient chatClient;

    public GeminiService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public CandidatoDTO analisarCurriculo(String textoCurriculo) {

        var outputConverter = new BeanOutputConverter<>(CandidatoDTO.class);

        String promptText = """
                Você é um especialista em Recrutamento e Seleção de TI (Tech Recruiter).
                Eu vou te passar o texto extraído de um currículo. Seu objetivo é analisar esse texto
                e extrair as informações principais do candidato, preenchendo todos os campos solicitados.

                Regras IMPORTANTES:
                - Se não encontrar alguma informação, devolva nulo para Strings ou lista vazia se for lista.
                - Avalie a experiência no currículo e defina o "nivelEstimado" como Junior, Pleno ou Senior, ou nulo se não souber.
                - Retorne APENAS o JSON no formato exato solicitado, sem blocos de texto, formatação markdown ou explicações antes ou depois.

                Formato esperado:
                {format}

                Texto do currículo:
                {curriculo}
                """;

        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        Prompt prompt = promptTemplate.create(
                Map.of(
                        "format", outputConverter.getFormat(),
                        "curriculo", textoCurriculo));

        var respostaInjetada = chatClient.prompt(prompt).call().content();

        return outputConverter.convert(respostaInjetada);
    }
}
