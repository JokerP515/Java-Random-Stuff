package med.voll.api.domain.consulta.validaciones.cancelamiento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.cancelamiento.DatosCancelamientoConsulta;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;

@Component
public class ValidadorHorarioConAnticipacion implements ValidadorCancelamientoDeConsulta {
 
@Autowired
private ConsultaRepository repository;
 
@Override
public void validar(DatosCancelamientoConsulta datos) {
    Consulta consulta = repository.getReferenceById(datos.idConsulta());
    LocalDateTime ahora = LocalDateTime.now();
    Long diferenciaEnHoras = Duration.between(ahora, consulta.getFecha()).toHours();
 
    if (diferenciaEnHoras < 24) {
        throw new ValidacionException("¡La consulta solo puede ser cancelada con anticipación mínima de 24 horas!");
    }
}
}