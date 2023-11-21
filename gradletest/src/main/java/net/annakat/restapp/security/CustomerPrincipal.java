package net.annakat.restapp.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CustomerPrincipal implements Principal {

    private Integer id;
    private String name;


    @Override
    public String getName() {
        return null;
    }
}
