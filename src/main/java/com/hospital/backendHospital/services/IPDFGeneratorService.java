package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.entity.User;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IPDFGeneratorService {

    public void export(HttpServletResponse response, Long patientId) throws IOException;
}
