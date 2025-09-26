package com.hospital.backendHospital.services.impl;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.models.entity.FileEntity;
import com.hospital.backendHospital.models.entity.Patient;
import com.hospital.backendHospital.repositories.FileRepository;
import com.hospital.backendHospital.repositories.PatientRepository;
import com.hospital.backendHospital.services.IPDFGeneratorService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PDFGeneratorService implements IPDFGeneratorService {

    private final FileRepository fileRepository;
    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public void export(HttpServletResponse response, Long patientId) throws IOException {
        // 1. Buscar paciente
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        // 2. Generar PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("Expediente medico de " + patient.getUser().getFirstName(), fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Paragraph paragraph2 = new Paragraph("This is a paragraph.", fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(paragraph);
        document.add(paragraph2);
        document.close();

        byte[] pdfBytes = baos.toByteArray();

        // 3. Guardar en BD
        FileEntity fileEntity = FileEntity.builder()
                .name("expediente_" + patient.getId() + ".pdf")
                .type("application/pdf")
                .data(pdfBytes)
                .build();
        fileRepository.save(fileEntity);

        // 4. Mandar PDF como descarga en response
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=expediente_" + patient.getId() + ".pdf");
        response.getOutputStream().write(pdfBytes);
    }

}