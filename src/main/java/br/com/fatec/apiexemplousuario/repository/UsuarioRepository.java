package br.com.fatec.apiexemplousuario.repository;

import br.com.fatec.apiexemplousuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
