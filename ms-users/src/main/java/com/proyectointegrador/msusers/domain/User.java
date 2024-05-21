package com.proyectointegrador.msusers.domain;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class User {
    private String id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
}
