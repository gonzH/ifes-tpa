/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicionario.v2;

/**
 *
 * @author helle
 */
public class TDicItemV2 {
    private String chave;
    private Object valor;
    
    public TDicItemV2(String chave, Object valor) {
        this.chave = chave;
        this.valor = valor;
    }
    
    public String getChave() {
        return chave;
    }
    
    public Object getValor() {
        return valor;
    }
    
    public void setChave(String chave) {
        this.chave = chave;
    }
    
    public void setValor(Object valor) {
        this.valor = valor;
    }
    
    
}