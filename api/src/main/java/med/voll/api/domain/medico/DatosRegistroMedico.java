package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.Direccion;

public record DatosRegistroMedico(
    @NotBlank String nombre, 
    @NotBlank @Email String email, 
    @NotBlank @Pattern(regexp = "\\d{4,6}") String documento, 
    @NotNull Especialidad especialidad,
    @NotNull @Valid Direccion direccion,
    @NotBlank String telefono
    ) {
    
}
