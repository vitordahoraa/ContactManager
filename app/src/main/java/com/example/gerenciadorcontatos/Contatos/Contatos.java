package com.example.gerenciadorcontatos.Contatos;

public class Contatos {
    private int id;
    private String nome;
    private int tipo_telefone;
    private String Telefone;

    public int getId(){return id;}
    public void setId(int novoId){this.id = novoId;}

    public String getNome(){ return nome;}
    public void setNome(String novoNome){ this.nome = novoNome;}

    public String getTelefone(){return Telefone;}
    public void setTelefone(String novoTelefone){this.Telefone=novoTelefone;}

    public int getTipoTelefone(){return tipo_telefone;}
    public void setTipoTelefone(int novoTipoTelefone){this.tipo_telefone=novoTipoTelefone;}

}
