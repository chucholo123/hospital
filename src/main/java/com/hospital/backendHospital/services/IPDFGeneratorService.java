package com.hospital.backendHospital.services;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IPDFGeneratorService {

    public void export(HttpServletResponse response) throws IOException;
}
