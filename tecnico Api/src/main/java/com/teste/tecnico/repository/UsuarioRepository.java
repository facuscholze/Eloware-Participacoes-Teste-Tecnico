package com.teste.tecnico.repository;



import com.teste.tecnico.model.Usuario;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {


}
