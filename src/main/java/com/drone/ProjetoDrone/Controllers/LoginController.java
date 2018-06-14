/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drone.ProjetoDrone.Controllers;

import com.drone.ProjetoDrone.Classes.Cliente.Cliente;
import com.drone.ProjetoDrone.Classes.Login.CriptoSenha;
import com.drone.ProjetoDrone.Classes.Login.Login;
import com.drone.ProjetoDrone.Repository.ClienteRepository;
import java.util.ArrayList;
import com.drone.ProjetoDrone.Classes.Produto.Produto;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Rafael Rodrigues
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ClienteRepository repository;
    CriptoSenha criptografia = new CriptoSenha();

    @GetMapping("/telaLogin")
    public ModelAndView login() {
        return new ModelAndView("Login").addObject("login", new Login());
    }

    @PostMapping("/logando")
    public ModelAndView logando(@ModelAttribute("login") @Valid Login login, BindingResult bindingResult,
            RedirectAttributes redirectAttributes, HttpSession session) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("Login");
        }

        Cliente cli = new Cliente();
        try {
            cli = repository.logar(login.getUser());
        } catch (Exception e) {
            return new ModelAndView("Login");
        }

        if (cli == null) {
            return new ModelAndView("Login");
        } else {

            if (cli.getEmail().equals(login.getUser()) && cli.getSenha().equals(criptografia.cripto(login.getSenha()))) {
                session.setAttribute("usuario", cli);
                return new ModelAndView("redirect:/home/paginaInicial");
            } else {
                return new ModelAndView("Login");
            }
        }
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
//        session.removeAttribute("usuario");
//        session.removeAttribute("venda");
//        
//        try {
//            List<Produto> carrinho = (List<Produto>) session.getAttribute("carrinho");
//
//            if (!carrinho.isEmpty() && carrinho != null) {
//                for (int i = 0; i < carrinho.size(); i++) {
//                    carrinho.remove(i);
//                }
//                carrinho = null;
//                session.setAttribute("carrinho", carrinho);
//            }
//        } catch (Exception e) {
//            return new ModelAndView("Home");
//        }
////        session.removeAttribute("carrinho");
////        List<Produto> carrinho = null;
////        session.setAttribute("carrinho", carrinho);
//
//        return new ModelAndView("Home");

        List<Produto> carrinho = (List<Produto>) session.getAttribute("carrinho");
        if (carrinho != null) {
            for (int i = 0; i < carrinho.size(); i++) {
                carrinho.remove(i);
            }
            session.setAttribute("carrinho", carrinho);
        }

        session.invalidate();
        return new ModelAndView("Home");
    }

    @GetMapping("/telaLoginCart")
    public ModelAndView loginCart() {
        return new ModelAndView("Login_cart").addObject("login", new Login());
    }

    @PostMapping("/logandoCart")
    public ModelAndView logandoCart(@ModelAttribute("login") @Valid Login login, BindingResult bindingResult,
            RedirectAttributes redirectAttributes, HttpSession session) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("Login");
        }

        Cliente cli = new Cliente();
        try {
            cli = repository.logar(login.getUser());
        } catch (Exception e) {
            return new ModelAndView("Login");
        }

        if (cli == null) {
            return new ModelAndView("Login");
        } else {

            if (cli.getEmail().equals(login.getUser()) && cli.getSenha().equals(criptografia.cripto(login.getSenha()))) {
                session.setAttribute("usuario", cli);
                return new ModelAndView("redirect:/carrinho/telaCarrinho");
            } else {
                return new ModelAndView("Login");
            }
        }
    }
}
