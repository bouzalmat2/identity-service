package com.smartmaintain.identityservice.services;

import com.smartmaintain.identityservice.entities.*;
import com.smartmaintain.identityservice.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email ma-kaynch: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getMotDePasse())
                .roles(user.getRole())
                .build();
    }


    public Client saveClient(Client client) {

        client.setMotDePasse(passwordEncoder.encode(client.getMotDePasse()));

        return utilisateurRepository.save(client);
    }


    public Admin saveAdmin(Admin admin) {

        String encodedPassword = passwordEncoder.encode(admin.getMotDePasse());
        admin.setMotDePasse(encodedPassword);
        return utilisateurRepository.save(admin);
    }

    public Manager saveManager(Manager manager) {

        manager.setMotDePasse(passwordEncoder.encode(manager.getMotDePasse()));
        return utilisateurRepository.save(manager);
    }

    public Ingenieur saveIngenieur(Ingenieur ingenieur) {
        ingenieur.setMotDePasse(passwordEncoder.encode(ingenieur.getMotDePasse()));
        return utilisateurRepository.save(ingenieur);
    }

    public Operateur saveOperateur(Operateur operateur) {
        operateur.setMotDePasse(passwordEncoder.encode(operateur.getMotDePasse()));
        return utilisateurRepository.save(operateur);
    }
}