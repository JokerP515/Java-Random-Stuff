package med.voll.api.domain.consulta.validaciones.cancelamiento;

import med.voll.api.domain.cancelamiento.DatosCancelamientoConsulta;

public interface ValidadorCancelamientoDeConsulta {
    void validar(DatosCancelamientoConsulta datos);
}