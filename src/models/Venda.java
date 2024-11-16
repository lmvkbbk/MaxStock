package models;

public class Venda {
    private int idVenda;
    private String dataVenda;
    private String metodoPagamento;
    private double totalVenda;
    private String clienteNome;

    public Venda(int idVenda, String dataVenda, String metodoPagamento, double totalVenda, String clienteNome){
        this.idVenda = idVenda;
        this.dataVenda = dataVenda;
        this.metodoPagamento = metodoPagamento;
        this.totalVenda = totalVenda;
        this.clienteNome = clienteNome;
    }

    public Venda() {

    }

    public Venda(int idVenda, String dataVenda, double totalVenda, String clienteNome) {
        this.idVenda = idVenda;
        this.dataVenda = dataVenda;
        this.totalVenda = totalVenda;
        this.clienteNome = clienteNome;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public double getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(double totalVenda) {
        this.totalVenda = totalVenda;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }
}
