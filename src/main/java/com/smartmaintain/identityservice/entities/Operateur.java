package com.smartmaintain.identityservice.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("OPERATEUR")
public class Operateur extends Utilisateur{
    private String matriculeTechnique;

}
