package com.proyectointegrador.msticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Detalles de EmailDTO")
public class EmailDTO {
    @Schema(description = "Correo electronico de quien recibira el email")
    private String receiver;

    @Schema(description = "Asunto del email")
    private String subject;

    @Schema(description = "Mensaje que contiene el email")
    private String message;
}
