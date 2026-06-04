package com.sistemaExemplo.gerenciarUsuarios.controller;

import com.sistemaExemplo.gerenciarUsuarios.model.Usuario;
import com.sistemaExemplo.gerenciarUsuarios.model.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class UsuarioController {

    private final UsuarioRepository usuarios;

    public UsuarioController(UsuarioRepository usuarios) {
        this.usuarios = usuarios;
    }

    // GET / — lista todos
    @GetMapping
    public String index(Model view) {
        view.addAttribute("usuarios", usuarios.findAll());
        return "listar"; // nome da View
    }

    // GET /novo — exibe formulário de criação
    @GetMapping("/novo")
    public String create(Model view) {
        view.addAttribute("usuario", new Usuario());
        view.addAttribute("acao", "Cadastrar");
        return "formulario"; // nome da View
    }

    // POST /novo — persiste novo usuário
    @PostMapping("/novo")
    public String store(@ModelAttribute Usuario usuario) {
        usuarios.save(usuario);
        return "redirect:/";
    }

    // GET //{id}/editar — exibe formulário de edição
    @GetMapping("/{id}/editar")
    public String edit(@PathVariable Long id, Model view) {
        Usuario usuario = usuarios.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));
        view.addAttribute("usuario", usuario);
        view.addAttribute("acao", "Atualizar");
        return "formulario";
    }

    // POST /{id}/editar — persiste atualização
    @PostMapping("/{id}/editar")
    public String update(@PathVariable Long id, @ModelAttribute Usuario usuario) {
        usuario.setId(id);
        usuarios.save(usuario);
        return "redirect:/";
    }

    // POST /{id}/excluir — remove registro
    @PostMapping("/{id}/excluir")
    public String destroy(@PathVariable Long id) {
        usuarios.deleteById(id);
        return "redirect:/";
    }
}
