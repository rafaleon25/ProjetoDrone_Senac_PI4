/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drone.ProjetoDrone.Classes.Produto;

import com.drone.ProjetoDrone.Classes.Venda.VendaProd;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Rafael Rodrigues
 */
@Entity
@Table(name = "Produtos")
public class Produto implements Serializable {

    @OneToMany(mappedBy = "produto", fetch = FetchType.LAZY,
          cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    //@JoinColumn(name = "produto_id", nullable = false)
    private Set<VendaProd> vendaProd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preco_id", nullable = false)
    private Precos precos;

    @Id
    @Column(name = "produto_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProd;

//    private int precoId;
    //estoque 
    @NotNull
    @Digits(integer = 3, fraction = 0)
    @Column(name = "estoque", precision = 3, scale = 0, nullable = false)
    private int estoque;

    //velicidade maxima 
    @NotNull
    @Digits(integer = 3, fraction = 0)
    @Column(name = "velocidadeMax", precision = 3, scale = 0, nullable = false)
    private int velocidadeMax;
    //duração da bateria
    @NotNull
    @Digits(integer = 2, fraction = 0)
    @Column(name = "bateriaDuracao", precision = 2, scale = 0, nullable = false)
    private int bateriaDuracao;
    //camera
    @NotNull
    @Digits(integer = 3, fraction = 0)
    @Column(name = "camera", precision = 1, scale = 0, nullable = false)
    private int camera;
    //nome 
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nome", length = 45, nullable = false, unique = false)
    private String nome;
    //descrição
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "descricao", length = 1000, nullable = false, unique = false)
    private String descricao;
    //fabricante 
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "fabricante", length = 45, nullable = false, unique = false)
    private String fabricante;
    //peso
    @NotNull
    @Digits(integer = 3, fraction = 0)
    @Column(name = "peso", precision = 3, scale = 2, nullable = false)
    private double peso;
    
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "url", length = 200, nullable = false, unique = false)
    private String imagem_url;
    

    @Transient
    private int quantidadeUsu;

    //-------------------------Construtores--------------------
    public Produto() {
        quantidadeUsu = 1;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public Produto(int velocidadeMax, int bateriaDuracao, int camera, String nome, String descricao, String fabricante, double peso) {
        this.velocidadeMax = velocidadeMax;
        this.bateriaDuracao = bateriaDuracao;
        this.camera = camera;
        this.nome = nome;
        this.descricao = descricao;
        this.fabricante = fabricante;
        this.peso = peso;
    }

    //--------------------Fim dos construtores------------------
    public long getIdProd() {
        return idProd;
    }

    public void setIdProd(long idProd) {
        this.idProd = idProd;
    }
//
//    public int getPrecoId() {
//        return precoId;
//    }
//
//    public void setPrecoId(int precoId) {
//        this.precoId = precoId;
//    }

    public int getVelocidadeMax() {
        return velocidadeMax;
    }

    public void setVelocidadeMax(int velocidadeMax) {
        this.velocidadeMax = velocidadeMax;
    }

    public int getBateriaDuracao() {
        return bateriaDuracao;
    }

    public void setBateriaDuracao(int bateriaDuracao) {
        this.bateriaDuracao = bateriaDuracao;
    }

    public int getCamera() {
        return camera;
    }

    public void setCamera(int camera) {
        this.camera = camera;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getImagem_url() {
        return imagem_url;
    }

    public void setImagem_url(String imagem_url) {
        this.imagem_url = imagem_url;
    }

    public Precos getPrecos() {
        return precos;
    }

    public void setPrecos(Precos precos) {
        this.precos = precos;
    }

    public Set<VendaProd> getVendaProd() {
        return vendaProd;
    }

    public void setVendaProd(Set<VendaProd> vendaProd) {
        this.vendaProd = vendaProd;
    }

    public int getQuantidadeUsu() {
        return quantidadeUsu;
    }

    public void setQuantidadeUsu(int qtd) {
        this.quantidadeUsu = qtd;
    }
  

    
}
