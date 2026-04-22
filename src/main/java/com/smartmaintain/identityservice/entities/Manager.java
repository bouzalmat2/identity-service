package com.smartmaintain.identityservice.entities;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MANAGER")
public class Manager extends Utilisateur{
    private String departement;
}
