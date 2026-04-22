package com.smartmaintain.identityservice.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends Utilisateur{
    private String nomSociete;
    private String ice;
    private String siegeSocial;
}
