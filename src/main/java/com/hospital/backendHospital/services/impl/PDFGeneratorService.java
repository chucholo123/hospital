package com.hospital.backendHospital.services.impl;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.models.entity.MedicalRecord;
import com.hospital.backendHospital.repositories.MedicalRecordRepository;
import com.hospital.backendHospital.services.IPDFGeneratorService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PDFGeneratorService implements IPDFGeneratorService {

    private final MedicalRecordRepository medicalRecordRepository;

    @Override
    @Transactional
    public void export(HttpServletResponse response, Long patientId) throws IOException {
        MedicalRecord medicalRecord = medicalRecordRepository.findByPatientId(patientId).orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("Expediente medico de " + medicalRecord.getPatient().getUser().getFirstName(), fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Paragraph paragraph2 = new Paragraph("This is a paragraph.", fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(paragraph);
        document.add(paragraph2);
        document.close();
    }
}