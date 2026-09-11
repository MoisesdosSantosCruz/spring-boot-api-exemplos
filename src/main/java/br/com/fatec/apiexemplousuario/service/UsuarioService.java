package br.com.fatec.apiexemplousuario.service;

import br.com.fatec.apiexemplousuario.model.Usuario;
import br.com.fatec.apiexemplousuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

//import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deletar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    // Metodo de Atualização (Update)
    public Optional<Usuario> atualizar(Integer id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id).map(usuarioExistente -> {
            usuarioExistente.setNome(usuarioAtualizado.getNome());
            usuarioExistente.setIdade(usuarioAtualizado.getIdade());
            return usuarioRepository.save(usuarioExistente);
        });
    }

}

/*

    private final ArrayList<Usuario> listaUsuarios = new ArrayList<>();

    // listar todos
    public List<Usuario> listar() {
        return listaUsuarios;

    }

    // buscar por índice
    public Usuario buscarPorIndice(int indice) {
        if (indice < 0 || indice >= listaUsuarios.size()) {
            return null;
        }
        return listaUsuarios.get(indice);
    }

    // adicionar
    public Usuario adicionar(Usuario usuario) {
        listaUsuarios.add(usuario);
        return usuario;
    }

    // atualizar
    public Usuario atualizar(int indice, Usuario usuario) {
        if (indice < 0 || indice >= listaUsuarios.size()) {
            return null;
        }
        listaUsuarios.set(indice, usuario);
        return usuario;
    }

    // deletar
    public boolean deletar(int indice) {
        if (indice < 0 || indice >= listaUsuarios.size()) {
            return false;
        }
        listaUsuarios.remove(indice);
        return true;
    }
*/




