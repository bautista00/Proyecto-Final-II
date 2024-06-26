package com.proyectointegrador.msticket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {
    private String id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
}
