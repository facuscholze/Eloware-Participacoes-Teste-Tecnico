package com.teste.tecnico.controller;


import com.teste.tecnico.exeptions.BadRequest;
import com.teste.tecnico.model.Direccion;
import com.teste.tecnico.model.Usuario;
import com.teste.tecnico.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) throws BadRequest {try {



        ResponseEntity.ok(usuarioService.guardar(usuario));
        return ResponseEntity.status(HttpStatus.OK).body("Usuario creado correctamente");
    } catch (BadRequest e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<ArrayList<Usuario>> listarUsuarios(){
        return ResponseEntity.ok((ArrayList<Usuario>) usuarioService.listarUsuarios());
    }
    @PutMapping("/modificar")
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody Usuario usuario) throws BadRequest {
        ResponseEntity<Usuario> response = null;

        if (usuario.getId() != null && usuarioService.buscarxID(usuario.getId()).isPresent()) {
            // Obtén el usuario existente de la base de datos
            Optional<Usuario> usuarioExistenteOptional = usuarioService.buscarxID(usuario.getId());

            if (usuarioExistenteOptional.isPresent()) {
                Usuario usuarioExistente = usuarioExistenteOptional.get();

                if (usuario.getNombre() != null) {
                    usuarioExistente.setNombre(usuario.getNombre());
                }
                if (usuario.getApellido() != null) {
                    usuarioExistente.setApellido(usuario.getApellido());
                }


                response = ResponseEntity.ok(usuarioService.modificar(usuarioExistente));
            }
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return response;
    }



    @GetMapping("/buscar/{id}")
    public ResponseEntity<Optional<Optional<Usuario>>> buscarUsuarioPorId(@PathVariable Long id)throws BadRequest {
        Optional<Optional<Usuario>> usuario = Optional.ofNullable(usuarioService.buscarxID(id));


        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/{usuarioId}/direcciones")
    public ResponseEntity<Object> agregarDireccionAUsuario(@PathVariable Long usuarioId, @RequestBody Direccion direccion) {
        Direccion direccionAgregada = usuarioService.agregarDireccionAUsuario(usuarioId, direccion);
        if (direccionAgregada != null) {
            return ResponseEntity.ok(direccionAgregada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{usuarioId}/direcciones/principal/{direccionId}")
    public ResponseEntity<String> seleccionarDireccionPrincipal(@PathVariable Long usuarioId, @PathVariable Long direccionId) {
        usuarioService.seleccionarDireccionPrincipal(usuarioId, direccionId);
        return ResponseEntity.ok("Dirección principal seleccionada con éxito");
    }


    @PutMapping("/{usuarioId}/direcciones/{direccionId}")
    public ResponseEntity<String> modificarDireccionDeUsuario(
            @PathVariable Long usuarioId,
            @PathVariable Long direccionId,
            @RequestBody Direccion direccionActualizada) {
        try {
            usuarioService.modificarDireccionDeUsuario(usuarioId, direccionId, direccionActualizada);
            return ResponseEntity.ok("Dirección modificada correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}










