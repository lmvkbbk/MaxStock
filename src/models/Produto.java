package models;

public class Produto {
    private int id;
    private String nome;
    private String categoria;
    private int quantidade;
    private double custo;
    private double valor;

    public Produto(int id, String nome, String categoria, int quantidade, double custo, double valor) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.custo = custo;
        this.valor = valor;
    }

    public Produto() {

    }

    public Produto( String nome, String categoria, int quantidade, double custo, double valor) {
        this.nome = nome;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.custo = custo;
        this.valor = valor;
    }

    public Produto(int id, String nome, int quantidade, double valor) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
