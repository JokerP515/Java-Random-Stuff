package med.voll.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.domain.consulta.DatosDetalleConsulta;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.consulta.ReservaDeConsultas;
import med.voll.api.domain.medico.Especialidad;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DatosReservaConsulta> datosReservaConsultaJson;

    @Autowired
    private JacksonTester<DatosDetalleConsulta> datosDetalleConsultaJson;

    @MockBean
    private ReservaDeConsultas reservaDeConsultas;

    @Test
    void testCancelar() {

    }

    @Test
    @DisplayName("Reservar consulta, devuelve 400 si la request no tiene datos")
    @WithMockUser
    void testReservar_escenario1() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/consultas")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Reservar consulta, devuelve 200 si la request recibe datos válidos")
    @WithMockUser
    void testReservar_escenario2() throws Exception {

        LocalDateTime fecha = LocalDateTime.now().plusHours(1);
        Especialidad especialidad = Especialidad.CARDIOLOGIA;
        DatosDetalleConsulta detalleConsulta = new DatosDetalleConsulta(null, 2L, 5L, fecha);

        when(reservaDeConsultas.reservar(any())).thenReturn(detalleConsulta);

        MockHttpServletResponse response = mockMvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(datosReservaConsultaJson.write(
                        new DatosReservaConsulta(2L, 5L, fecha, especialidad)).getJson()))
                .andReturn()
                .getResponse();

        String jsonEsperado = datosDetalleConsultaJson.write(
                detalleConsulta).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}
