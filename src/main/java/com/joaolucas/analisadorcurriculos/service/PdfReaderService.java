package com.joaolucas.analisadorcurriculos.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PdfReaderService {

    public String extrairTexto(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("O arquivo enviado está vazio ou é nulo.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("application/pdf")) {
            throw new IllegalArgumentException("O arquivo deve ser um PDF válido.");
        }

        try (PDDocument document = Loader.loadPDF(file.getBytes())) {

            if (document.isEncrypted()) {
                throw new IllegalArgumentException("Não é possível ler PDFs protegidos por senha.");
            }

            PDFTextStripper stripper = new PDFTextStripper();
            String textoExtraido = stripper.getText(document);

            if (textoExtraido == null || textoExtraido.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Não foi possível extrair nenhum texto do PDF. O arquivo pode ser uma imagem (PDF escaneado).");
            }

            return textoExtraido;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar a leitura do arquivo PDF: " + e.getMessage(), e);
        }
    }
}
