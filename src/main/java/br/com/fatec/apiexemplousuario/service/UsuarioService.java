package br.com.fatec.apiexemplousuario.service;

import br.com.fatec.apiexemplousuario.model.Usuario;
import br.com.fatec.apiexemplousuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //Métodos para interações com Banco de dados
    //Listar e Buscar
    public List<Usuario> listar(){return usuarioRepository.findAll(Sort.by("id").ascending());}

    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    //Método de Cadastrar
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Método de Exlusão
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

    //Métodos para interações com Arquivos .txt e .csv

    private void validarArquivo(MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException(
                    "Selecione um arquivo para importar."
            );
        }
        String nomeArquivo = arquivo.getOriginalFilename();
        if (nomeArquivo == null) {
            throw new IllegalArgumentException(
                    "O arquivo selecionado é inválido."
            );
        }
        String nomeEmMinusculo = nomeArquivo.toLowerCase();
        if (!nomeEmMinusculo.endsWith(".txt")
                && !nomeEmMinusculo.endsWith(".csv")) {
            throw new IllegalArgumentException(
                    "Selecione um arquivo TXT ou CSV."
            );
        }
    }
    @Transactional
    public int importarArquivo(MultipartFile arquivo) {
        validarArquivo(arquivo);
        List<Usuario> usuariosImportados = new ArrayList<>();
        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(
                        arquivo.getInputStream(),
                        StandardCharsets.UTF_8))) {
            String linha;
            int numeroLinha = 0;
            while ((linha = leitor.readLine()) != null) {
                numeroLinha++;
                linha = linha.trim();
                if (linha.isEmpty()) {
                    throw new IllegalArgumentException(
                            "A linha " + numeroLinha + " está vazia."
                    );
                }
                String[] dados = linha.split(";", -1);
                if (dados.length != 2) {
                    throw new IllegalArgumentException(
                            "Formato inválido na linha " + numeroLinha
                                    + ". Utilize nome;idade."
                    );
                }
                String nome = dados[0].trim();
                String idadeTexto = dados[1].trim();
                if (numeroLinha == 1) {
                    nome = nome.replace("\uFEFF", "");
                }
                if (nome.isEmpty()) {
                    throw new IllegalArgumentException(
                            "O nome está vazio na linha " + numeroLinha + "."
                    );
                }
                int idade;

                try {
                    idade = Integer.parseInt(idadeTexto);
                } catch (NumberFormatException erro) {
                    throw new IllegalArgumentException(
                            "A idade da linha " + numeroLinha
                                    + " deve ser um número inteiro."
                    );
                }
                if (idade < 0 || idade > 180) {
                    throw new IllegalArgumentException(
                            "A idade da linha " + numeroLinha
                                    + " deve estar entre 0 e 180 anos."
                    );
                }
                Usuario usuario = new Usuario();
                usuario.setNome(nome);
                usuario.setIdade(idade);
                usuariosImportados.add(usuario);
            }
            if (usuariosImportados.isEmpty()) {
                throw new IllegalArgumentException(
                        "O arquivo não contém usuários."
                );
            }
            usuarioRepository.saveAll(usuariosImportados);
            return usuariosImportados.size();
        } catch (IllegalArgumentException erro) {
            throw erro;
        } catch (Exception erro) {
            throw new IllegalArgumentException(
                    "Não foi possível ler o arquivo."
            );
        }
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




