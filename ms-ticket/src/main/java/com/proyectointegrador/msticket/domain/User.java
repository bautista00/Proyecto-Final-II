package com.proyectointegrador.msticket.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(description = "Detalles de User")
public class User {
    @Schema(description = "ID del Usuario", example = "f7049e0e-0a21-4e19-819d-bf8915f2998f")
    private String id;

    @Schema(description = "Nombre de usuario", example = "ticketrekpi")
    private String userName;

    @Schema(description = "Nombre del usuario", example = "Juan")
    private String firstName;

    @Schema(description = "Apellido del usuario", example = "Perez")
    private String lastName;

    @Schema(description = "Correo electronico del usuario", example = "ticketrekpi@gmail.com")
    private String email;
}
