/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drone.ProjetoDrone.Services;

import com.drone.ProjetoDrone.Classes.Venda.Venda;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Josué
 */
public interface AcompanhaPedido extends Serializable {
     public List<Venda> listarVendas(long idCliente);
}
