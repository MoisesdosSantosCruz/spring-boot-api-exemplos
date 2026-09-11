package br.com.fatec.apiexemplousuario.controller;

import br.com.fatec.apiexemplousuario.model.Usuario;
import br.com.fatec.apiexemplousuario.service.UsuarioService;
import jakarta.persistence.Id;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController //Este é nosso controller (Usuários)
@RequestMapping("/usuarios") //Mapeamento
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
            this.usuarioService = usuarioService;
    }

    // GET - listar todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    // GET - buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST - adicionar usuário
    @PostMapping
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.salvar(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    // PUT - atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Integer id, @RequestBody Usuario usuario)
    {
        return usuarioService.buscarPorId(id).map(u -> {
            u.setNome(usuario.getNome());
            u.setIdade(usuario.getIdade());
            Usuario atualizado = usuarioService.salvar(u);
            return ResponseEntity.ok(atualizado);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE - remover usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (usuarioService.buscarPorId(id).isPresent()) {
            usuarioService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}

/*
    // GET - listar todos os usuários
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    // GET - buscar usuário por índice
    @GetMapping("/{indice}")
    public ResponseEntity<Usuario> buscarPorIndice(@PathVariable int indice) {
        Usuario usuario = usuarioService.buscarPoIndice(indice);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    // POST - adicionar usuário
    @PostMapping
    public ResponseEntity<Usuario> adicionar(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.adicionar(usuario);
        return ResponseEntity.status(201).body(novoUsuario);
    }

    // PUT - atualizar usuário
    @PutMapping("/{indice}")
    public ResponseEntity<Usuario> atualizar(@PathVariable int indice, @RequestBody Usuario usuario) {
        Usuario usuarioAtualizado = usuarioService.atualizar(indice, usuario);
        if (usuarioAtualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // DELETE - remover usuário
    @DeleteMapping("/{indice}")
    public ResponseEntity<Void> deletar(@PathVariable int indice) {
        usuarioService.deletar(indice);
        return ResponseEntity.noContent().build();
    }
*/