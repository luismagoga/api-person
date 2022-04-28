package com.company.apiperson.model.response;

import java.util.List;

public class ErrorRS {

    private List<ErrorMessage> errores;

    public ErrorRS(List<ErrorMessage> errores) {
        this.errores = errores;
    }

    public List<ErrorMessage> getErrores() {
        return errores;
    }

    public void setErrores(List<ErrorMessage> errores) {
        this.errores = errores;
    }
}
