/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicionario;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author helle
 */
public class TADDicionarioV2 {
    private LinkedList vetBuckets[] = null; //lista principal
    private double fator_de_carga = 0.75; 
    private int qtd_entradas = 0;
    
    public TADDicionarioV2(int qtdEntradas) {
        int tam = (int)(qtdEntradas/fator_de_carga);
        
        vetBuckets = new LinkedList[tam];
        
        for( int i = 0; i < tam; i++) {
            vetBuckets[i] = new LinkedList<TDicItem>();
        }
    }
    
    public TADDicionarioV2() {
        vetBuckets = new LinkedList[100];
        
        for( int i = 0; i < 100; i++) {
            vetBuckets[i] = new LinkedList<TDicItem>();
        }
    }
    
    public int getSizeVetBuckets() {
        return vetBuckets.length;
    }
    
    /* funcao de calculo de hash */
    private long hashFunc(String s) {
        long soma = 0;
        
        for(int i = 0; i < s.length(); i++) {
            soma = soma + (int)s.charAt(i);
        }
        
        System.out.println("Hash gerado: " + soma);
        return soma;
    }
    
    // 31 elevado
    private long hashFuncPol(String s) {
        long soma = 0;
        
        for(int i = 0; i < s.length(); i++) {
            soma = soma + 31^(int)s.charAt(i);
        }
        
        System.out.println("Hash gerado: " + soma);
        return soma;
    }
    
    /* funcoes do livro */
    public int size() {
        return qtd_entradas;
    }
    
    public boolean isEmpty() {
        if(qtd_entradas == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public LinkedList<TDicItem> elements() {
        LinkedList<TDicItem> iterador = new LinkedList<TDicItem>();
        
        if(isEmpty()) {
            return iterador;
        }
        else {
            for(int posVet = 0; posVet < getSizeVetBuckets(); posVet++) {
                if(vetBuckets[posVet].size() > 0) {
                    for(int posList = 0; posList < vetBuckets[posVet].size(); posList++) {
                        iterador.add(((TDicItem)vetBuckets[posVet].get(posList)));
                    }
                }
            }
            
            return iterador;
        }
    }
    
    public void insertItem(String chave, Object valor) {
        TDicItem aux = findElement(chave);
        
        if(aux == null) {
            long cod_hash = hashFunc(chave);
            //garante que meu indice nunca seja maior que o tamanho do vetor
            int indice = (int)cod_hash % getSizeVetBuckets();
            
            vetBuckets[indice].add(new TDicItem(chave, valor));
            qtd_entradas++;   
        }
        else {
            aux.setValor(((TDicItem)(valor)).getValor());
        } 
    }
    
    public TDicItem findElement(String chave) {
        long cod_hash = hashFunc(chave);
        int indice = (int)cod_hash % getSizeVetBuckets();
        
        int posList = 0;
        while(posList < vetBuckets[indice].size()) {
            if((((TDicItem)vetBuckets[indice].get(posList))).getChave().equalsIgnoreCase(chave))
                return (TDicItem)vetBuckets[indice].get(posList);
            posList++;
        }
        
        return null;
    }
    
    /* enunciado pede para retornar regdados*/
    public TDicItem removeElement(String chave) {
        TDicItem aux = findElement(chave);
        
        if(aux == null) {
            return null;
        }
        else {
            long cod_hash = hashFunc(chave);
            int indice = (int)cod_hash % getSizeVetBuckets();

            int posList = 0;
            while(posList < vetBuckets[indice].size()) {
                posList++;
            }
            
            vetBuckets[indice].remove(posList-1);
            qtd_entradas--;
            
            return aux;
        }
    }
    
    public LinkedList<String> keys() {
        LinkedList<String> iterador = new LinkedList<String>();
        
        if(isEmpty()) {
            return null;
        }
        else {
            for(int posVet = 0; posVet < getSizeVetBuckets(); posVet++) {
                if(vetBuckets[posVet].size() > 0) {
                    for(int posList = 0; posList < vetBuckets[posVet].size(); posList++) {
                        iterador.add(((TDicItem)vetBuckets[posVet].get(posList)).getChave());
                    }
                }
            }
            return iterador;
        }
        
        
    }
    
    
    // retorna a qtd de colisoes por posicao do vetor de buckets
    public int[] getColisoes() {
        
        if(isEmpty()) {
            return null;
        }
        else {
            int vetColisoes[] = new int[getSizeVetBuckets()];
            
            for(int posVet = 0; posVet < getSizeVetBuckets(); posVet++) {
                vetColisoes[posVet] = vetBuckets[posVet].size();
            }
            
            return vetColisoes;
        }
    }
    
    //gera diagrama usando o matplotlib, relação colisao eixo y e pos no vetbuckets em grafico de barras
    public void exibeDiagrama(int vet[]) throws IOException { //vou gerar .csv pois nao tenho matplotlib
        
        FileWriter writer = null;
        try {
            writer = new FileWriter(".\\diagrama.csv", true);
            
            for(int posVet = 0;posVet < vet.length; posVet++) {
                writer.append(vet[posVet] + "," + posVet + "\n");
            }
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            writer.close();
        }
    }
}
        