package com.smartmaintain.identityservice.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("INGENIEUR")
public class Ingenieur extends Utilisateur{
    private String specialite;

}
