package com.example.penapple.projetoac2.bean;

import android.support.annotation.NonNull;

public class Problem {
    private String tipo;
    private String rua;
    private String bairro;
    private String numero;
    private String descricao;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @NonNull
    @Override
    public String toString() {
        String problema =
                "Tipo: "+ getTipo() + "\n" +
                "Rua: "+ getRua() + "\n" +
                "Bairro:"+ getBairro() + " \n" +
                "Número: "+ getNumero() + "\n" +
                "Descrição: "+ getDescricao() ;

        return problema;
    }
}
