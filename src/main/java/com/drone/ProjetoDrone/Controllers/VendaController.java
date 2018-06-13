/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drone.ProjetoDrone.Controllers;

import com.drone.ProjetoDrone.Classes.Cliente.Cliente;
import com.drone.ProjetoDrone.Classes.Produto.Produto;
import com.drone.ProjetoDrone.Classes.Produto.Quantidade;
import com.drone.ProjetoDrone.Classes.Produto.QuantidadeProdutos;
import com.drone.ProjetoDrone.Classes.Venda.Venda;
import com.drone.ProjetoDrone.Classes.Venda.VendaEndereco;
import com.drone.ProjetoDrone.Classes.Venda.VendaPag;
import com.drone.ProjetoDrone.Classes.Venda.VendaProd;
import com.drone.ProjetoDrone.Repository.VendaRepository;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    private VendaRepository repository;

//    @PostMapping("/vendaFim")
//    public ModelAndView telaVenda(@ModelAttribute(value = "prodQtd") QuantidadeProdutos prodQtd, HttpSession sessao){
//        Venda venda = new Venda();
//        
//        List<Produto> carrinho = (List<Produto>) sessao.getAttribute("carrinho");
//        QuantidadeProdutos quantidadesProd =  prodQtd;
//        List<Quantidade> quantidades = prodQtd.getQuantidade();
//        
//        Set<VendaProd> vendaProd = new LinkedHashSet<>();
//        for (int i = 0; i < carrinho.size(); i++) {
//            VendaProd vendaprodCar = new VendaProd();
//            
////            vendaprodCar.setQtd(carrinho.get(i).getQuantidadeUsu());
//            for (int j = 0; j < quantidades.size(); j++) {
//                if(carrinho.get(i).getIdProd() == quantidades.get(j).getId()){
//                    vendaprodCar.setProduto(carrinho.get(i));
//                    vendaprodCar.setQtd(quantidades.get(j).getQuantidade());
//                    carrinho.get(i).setQuantidadeUsu((int) quantidades.get(j).getQuantidade());
//                }
//            }
//            vendaProd.add(vendaprodCar);
//        }
//        venda.setVendaProd(vendaProd);
//        
//        double total = 0;
//        
//        for (int i = 0; i < carrinho.size(); i++) {
////            total = total + (carrinho.get(i).getPrecos().getPreco() * carrinho.get(i).getQuantidadeUsu());
//            for (int j = 0; j < quantidades.size(); j++) {
//                if(carrinho.get(i).getIdProd() == quantidades.get(j).getId()){
//                    total = total + (carrinho.get(i).getPrecos().getIdPrrco() * quantidades.get(j).getQuantidade());
//                }
//            }
//        }
//        venda.setTotalVenda(total);
//        sessao.setAttribute("venda", venda);
//        return new ModelAndView("FinalizarCompra").addObject("venda", new Venda());
//    }
    @PostMapping("/finalizar")
    public ModelAndView finalizar(HttpSession sessao, @ModelAttribute("VendaPag") @Valid VendaPag VendaPag, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        Venda venda = (Venda) sessao.getAttribute("venda");

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
            }
            return new ModelAndView("FinalizarCompraPag");
        }

        venda.setCodigoSeguranca(VendaPag.getCodigoSeguranca());
        venda.setFormaPagamento(VendaPag.getFormaPagamento());
        venda.setNomeTitular(VendaPag.getNomeTitular());
        venda.setNumeroCartao(VendaPag.getNumeroCartao());
        venda.setParcelas(VendaPag.getParcelas());
        venda.setValidadeCartao(VendaPag.getValidadeCartao());

        try {
            String cod = repository.incluir(venda);
            sessao.setAttribute("cod", cod);
        } catch (Exception e) {
            return new ModelAndView("FinalizarCompraPag");
        }

        List<Produto> prodsComprados = (List<Produto>) sessao.getAttribute("carrinho");
        List<Produto> carrinho = null;
        sessao.setAttribute("carrinho", carrinho);
        sessao.setAttribute("prodsComp", prodsComprados);
        return new ModelAndView("ResumoCompra");
    }

    @PostMapping("/vendaFim")
    public ModelAndView telaVendaEnd(@ModelAttribute(value = "prodQtd") QuantidadeProdutos prodQtd, HttpSession sessao) {
        Venda venda = new Venda();
        Cliente cli = (Cliente) sessao.getAttribute("usuario");
        venda.setCliente(cli);

        List<Produto> carrinho = (List<Produto>) sessao.getAttribute("carrinho");
//        QuantidadeProdutos quantidadesProd = prodQtd;
        List<Quantidade> quantidades = prodQtd.getQuantidade();

        Set<VendaProd> vendaProd = new LinkedHashSet<>();
        for (int i = 0; i < carrinho.size(); i++) {
            VendaProd vendaprodCar = new VendaProd();

//            vendaprodCar.setQtd(carrinho.get(i).getQuantidadeUsu());
            for (int j = 0; j < quantidades.size(); j++) {
                
                if (carrinho.get(i).getEstoque() < quantidades.get(j).getQuantidade()) {
                    sessao.setAttribute("erroQtd", "O produto " + carrinho.get(i).getNome()
                            + " possui apenas " + carrinho.get(i).getEstoque() + " em estoque.");
                    return new ModelAndView("Cart").addObject("prodQtd", prodQtd);
                } else {
                    
                    if (carrinho.get(i).getIdProd() == quantidades.get(j).getId()) {
                        vendaprodCar.setProduto(carrinho.get(i));
                        vendaprodCar.setQtd(quantidades.get(j).getQuantidade());
                        carrinho.get(i).setQuantidadeUsu((int) quantidades.get(j).getQuantidade());
                    }
                }
            }
            vendaProd.add(vendaprodCar);
        }
        venda.setVendaProd(vendaProd);

        double total = 0;

        for (int i = 0; i < carrinho.size(); i++) {
            total = total + (carrinho.get(i).getPrecos().getPreco() * carrinho.get(i).getQuantidadeUsu());
//            for (int j = 0; j < quantidades.size(); j++) {
//                if (carrinho.get(i).getIdProd() == quantidades.get(j).getId()) {
//                    total = total + (carrinho.get(i).getPrecos().getIdPrrco() * quantidades.get(j).getQuantidade());
//                }
//            }
        }

        VendaEndereco ven = new VendaEndereco();
        ven.setCep(cli.getCep());
        ven.setCidade(cli.getCidade());
        ven.setEstado(cli.getEstado());
        ven.setNumero(cli.getNumero());
        ven.setRua(cli.getRua());

        venda.setTotalVenda(total);
        sessao.setAttribute("venda", venda);
        return new ModelAndView("FinalizarCompraEnd").addObject("vendaEndereco", ven);
    }

    @PostMapping("/vendaEnd")
    public ModelAndView telaEnderecoVenda(HttpSession sessao, @ModelAttribute("vendaEndereco") @Valid VendaEndereco vendaEndereco,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        Venda venda = (Venda) sessao.getAttribute("venda");
        venda.setCelular(vendaEndereco.getCelular());
        venda.setCep(vendaEndereco.getCep());
        venda.setCidade(vendaEndereco.getCidade());
        venda.setEstado(vendaEndereco.getEstado());
        venda.setNome(vendaEndereco.getNome());
        venda.setNumero(vendaEndereco.getNumero());
        venda.setRua(vendaEndereco.getRua());

        if (bindingResult.hasErrors()) {
            return new ModelAndView("FinalizarCompraEnd");
        }
        sessao.setAttribute("venda", venda);

        return new ModelAndView("FinalizarCompraPag").addObject("VendaPag", new VendaPag());
    }
}
