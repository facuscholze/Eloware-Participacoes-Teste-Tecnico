package com.teste.tecnico.service;



import com.teste.tecnico.exeptions.BadRequest;
import com.teste.tecnico.model.Direccion;
import com.teste.tecnico.model.Usuario;
import com.teste.tecnico.repository.DireccionRepository;

import com.teste.tecnico.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UsuarioService {

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;



    public Optional<Usuario> buscarxID(Long id) throws BadRequest {
        if ( usuarioRepository.findById(id).isEmpty()) {
            throw new BadRequest("No existe un usuario con id: " + id);
        }
        return usuarioRepository.findById(id);
    }


    public Usuario guardar(Usuario usuario) throws BadRequest {
        direccionRepository.save(usuario.getDireccionPrincipal());
        return usuarioRepository.save(usuario);
    }


    public void borrar(Long id) {

        usuarioRepository.deleteById(id);
    }


    public Usuario modificar(Usuario usuario) throws BadRequest {


        usuarioRepository.save(usuario);
        return usuario;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }



    public Direccion agregarDireccionAUsuario(Long usuarioId, Direccion direccion) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Direccion nuevaDireccion = direccionRepository.save(direccion);
        usuario.getDirecciones().add(nuevaDireccion);
        usuarioRepository.save(usuario);
        return nuevaDireccion;
    }


    public void seleccionarDireccionPrincipal(Long usuarioId, Long direccionId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Direccion direccionPrincipal = usuario.getDirecciones().stream()
                .filter(d -> d.getId().equals(direccionId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Dirección no encontrada"));

        usuario.setDireccionPrincipal(direccionPrincipal);
        usuarioRepository.save(usuario);
    }

    public void modificarDireccionDeUsuario(Long usuarioId, Long direccionId, Direccion direccionActualizada) {

        if (usuarioId == null || direccionId == null || usuarioId <= 0 || direccionId <= 0) {
            throw new IllegalArgumentException("Los IDs de usuario y dirección deben ser válidos.");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID proporcionado: " + usuarioId));

        Direccion direccionExistente = direccionRepository.findById(direccionId)
                .orElseThrow(() -> new IllegalArgumentException("Dirección no encontrada con el ID proporcionado: " + direccionId));


        direccionExistente.setPais(direccionActualizada.getPais());
        direccionExistente.setCalle(direccionActualizada.getCalle());
        direccionExistente.setNumero(direccionActualizada.getNumero());
        direccionExistente.setLocalidad(direccionActualizada.getLocalidad());
        direccionExistente.setProvincia(direccionActualizada.getProvincia());


        direccionRepository.save(direccionExistente);
    }

}
