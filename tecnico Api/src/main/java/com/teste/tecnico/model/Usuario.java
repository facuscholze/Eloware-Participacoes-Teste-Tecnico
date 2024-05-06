package com.teste.tecnico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "Usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @SequenceGenerator(name = "usuario_sequence",sequenceName = "usuario_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sequence")
    private Long id;

    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false)
    private Date fechaDeNacimiento;


    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "direccion_id", referencedColumnName = "id")
    private Direccion direccionPrincipal;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "usuario_direccion",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "direccion_id")
    )
    private List<Direccion> direcciones;
/*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_principal_id", referencedColumnName = "id")

    private Direccion direccionPrincipal;

    @JsonIgnore
    public Direccion getDireccionPrincipalInitialized() {

        Hibernate.initialize(direccionPrincipal);
        return direccionPrincipal;
    }*/
}





