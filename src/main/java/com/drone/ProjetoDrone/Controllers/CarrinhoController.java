/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drone.ProjetoDrone.Controllers;

import com.drone.ProjetoDrone.Classes.Produto.Produto;
import com.drone.ProjetoDrone.Classes.Produto.Quantidade;
import com.drone.ProjetoDrone.Classes.Produto.QuantidadeProdutos;
import com.drone.ProjetoDrone.Classes.Venda.VendaProd;
import com.drone.ProjetoDrone.Repository.ProdutoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Rafael Rodrigues
 */
@RequestMapping("/carrinho")
@Controller
public class CarrinhoController {

    @Autowired
    private ProdutoRepository repository;

    private List<Produto> carrinho = new ArrayList<Produto>();

    @PostMapping("/{id}")
    public ModelAndView carrinhoDeCompras(@PathVariable("id") Long idProduto, HttpSession sessao) {
        Produto p = repository.obter(idProduto);

        boolean naoEstaNaLista = true;
        if (carrinho != null) {
            for (int i = 0; i < carrinho.size(); i++) {
                if (p.getIdProd() == carrinho.get(i).getIdProd()) {
                    naoEstaNaLista = false;
                }
            }

            if (naoEstaNaLista) {
                carrinho.add(p);
                sessao.setAttribute("carrinho", carrinho);
            }

        } else {
            return new ModelAndView("Cart").addObject("prodQtd", new QuantidadeProdutos());
        }

        List<Quantidade> quantidades = new ArrayList<>();
        
        for (Produto p2 : carrinho) {
            Quantidade q = new Quantidade();
            q.setId(p2.getIdProd());
            q.setQuantidade(1L);
            quantidades.add(q);
        }
        QuantidadeProdutos qp = new QuantidadeProdutos();
        qp.setQuantidade(quantidades);

        return new ModelAndView("Cart").addObject("prodQtd", qp);
    }

    @GetMapping("/telaCarrinho")
    public ModelAndView telaCarrinho() {
        List<Quantidade> quantidades = new ArrayList<>();
        
        for (Produto p2 : carrinho) {
            Quantidade q = new Quantidade();
            q.setId(p2.getIdProd());
            q.setQuantidade(1L);
            quantidades.add(q);
        }
        QuantidadeProdutos qp = new QuantidadeProdutos();
        qp.setQuantidade(quantidades);

        return new ModelAndView("Cart").addObject("prodQtd", qp);
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerDoCarrinho(@PathVariable("id") Long idProduto, HttpSession sessao) {
        Produto p = repository.obter(idProduto);
        carrinho = (List<Produto>) sessao.getAttribute("carrinho");
        for (int i = 0; i < carrinho.size(); i++) {
            if (carrinho.get(i).getIdProd() == idProduto) {
                carrinho.remove(i);
                break;
            }
        }
        
        List<Quantidade> quantidades = new ArrayList<>();
        
        for (Produto p2 : carrinho) {
            Quantidade q = new Quantidade();
            q.setId(p2.getIdProd());
            q.setQuantidade(1L);
            quantidades.add(q);
        }
        QuantidadeProdutos qp = new QuantidadeProdutos();
        qp.setQuantidade(quantidades);
        
        if (carrinho.isEmpty() || carrinho == null) {
            sessao.removeAttribute("carrinho");
        } else {
            sessao.setAttribute("carrinho", carrinho);
        }
         return new ModelAndView("Cart").addObject("prodQtd", qp);
    }

//    @PostMapping("/update/{id}")
//    public ModelAndView removerDoCarrinho(@ModelAttribute("quantidade") Quantidade qtd, @PathVariable("id") Long idProduto, HttpSession sessao) {
//        for (int i = 0; i < carrinho.size(); i++) {
//            if (carrinho.get(i).getIdProd() == idProduto) {
//                carrinho.get(i).setQuantidadeUsu((int) qtd.getQuantidade());
//            }
//
//        }
//        sessao.setAttribute("carrinho", carrinho);
//
//        return new ModelAndView("Cart").addObject("prodQtd", new Quantidade());
//    }

//    @GetMapping("/add/(id)")
//    public ModelAndView addCart(@PathVariable("id") Long idProduto, HttpSession sessao) {
//        Produto p = repository.obter(idProduto);
//
//        boolean naoEstaNaLista = true;
//        if (carrinho != null) {
//            for (int i = 0; i < carrinho.size(); i++) {
//                if (p.getIdProd() == carrinho.get(i).getIdProd()) {
//                    naoEstaNaLista = false;
//                }
//            }
//
//            if (naoEstaNaLista) {
//                carrinho.add(p);
//                sessao.setAttribute("carrinho", carrinho);
//            }
//        } else {
//            return new ModelAndView("Cart").addObject("prodQtd", new Quantidade());
//        }
//
//        return new ModelAndView("Cart").addObject("prodQtd", new Quantidade());
//    }
}
