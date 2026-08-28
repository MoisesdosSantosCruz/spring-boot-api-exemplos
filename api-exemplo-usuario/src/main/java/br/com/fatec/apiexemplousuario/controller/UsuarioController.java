package br.com.fatec.apiexemplousuario.controller;

import br.com.fatec.apiexemplousuario.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController //Este é nosso controller (Usuários)
@RequestMapping("/usuarios") //Mapeamento
public class UsuarioController {

private List<Usuario> listaUsuarios = new ArrayList<>();


    @GetMapping
    public List<Usuario> listarUsuario() {
        return listaUsuarios;
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        listaUsuarios.add(usuario);
        return ResponseEntity.status(201).body(usuario);
    }

    @GetMapping("/{indice}")
    public ResponseEntity<Usuario> buscarPorIndice(@PathVariable int indice) {
            if(indice < 0 || indice>= listaUsuarios.size()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(listaUsuarios.get(indice));
    }

    @DeleteMapping("/{indice}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable int indice){
            if(indice < 0 || indice >= listaUsuarios.size()){
                    return ResponseEntity.notFound().build();
            }
            listaUsuarios.remove(indice);
            return ResponseEntity.noContent().build();

    }

    @PutMapping("/{indice}")
    public Usuario atualizarUsuario(@PathVariable int indice, @RequestBody Usuario usuarioAtualizado){
            listaUsuarios.set(indice, usuarioAtualizado);
            return usuarioAtualizado;
    }
}
