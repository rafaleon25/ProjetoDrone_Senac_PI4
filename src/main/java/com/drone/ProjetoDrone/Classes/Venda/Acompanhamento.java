/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drone.ProjetoDrone.Classes.Venda;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.Subselect;

/**
 *
 * @author Rafael Rodrigues
 */
@Entity
//@Subselect("select v.dt_venda data_venda, v.codigo_compra, "
//                        + "p.nome, vp.qtd, v.forma_pagamento, v.status_pedido "
//                        + "from vendas v " +
//                            "inner join venda_prod vp on vp.venda_id = v.vendaid " +
//                            "inner join produtos p on p.produto_id = vp.produto_id " +
//                            "where v.cliente_id = ?1")
public class Acompanhamento implements Serializable {

    @Id
    private String nome;
    private String codigoCompra;
    @Id
    private Date data_venda;
    private int qtd;
    private String forma_pagamento;
    private String statusPedido;

    public Acompanhamento() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigoCompra() {
        return codigoCompra;
    }

    public void setCodigoCompra(String codigoCompra) {
        this.codigoCompra = codigoCompra;
    }

    public Date getDataVenda() {
        return data_venda;
    }

    public void setDataVenda(Date dataVenda) {
        this.data_venda = dataVenda;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public Date getData_venda() {
        return data_venda;
    }

    public void setData_venda(Date data_venda) {
        this.data_venda = data_venda;
    }

    public String getForma_pagamento() {
        return forma_pagamento;
    }

    public void setForma_pagamento(String forma_pagamento) {
        this.forma_pagamento = forma_pagamento;
    }

}
