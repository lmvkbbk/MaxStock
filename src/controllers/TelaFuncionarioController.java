package controllers;

import database.Conexao;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.*;
import DAO.*;
import util.AlertUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.*;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TelaFuncionarioController {

    AlertUtils alerta = new AlertUtils();

    @FXML
    private Label mostraValorTotal;
    @FXML
    private Label LabelRelatorioVenda;
    @FXML
    private Label faturamentoRelatorio;
    @FXML
    private Label lucroTotalRelatorio;

    @FXML
    private Label labelData;
    @FXML
    private Label labelRelogio;

    private final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private AnchorPane CenaPrincipalCliente, CenaPrincipalProduto, CenaPrincipalVendas,
            TelaVerListaVenda, TelaRelatorioVenda, TelaRelatorios, RelatorioEstoque
            ;

    @FXML
    private TextField  PesquisaSQLCliente, PesquisaSQLProduto, pesquisaSQLVenda;

    @FXML
    private DatePicker inicio, fim,
            NovoRelatorioVendasInicio, NovoRelatorioVendasFim
            ;



    @FXML
    private TableView<Produto> TabelaRelatorioEstoqueBaixo;

    @FXML
    private TableColumn<Produto, Integer> RelatorioId;
    @FXML
    private TableColumn<Produto, String> RelatorioNome;
    @FXML
    private TableColumn<Produto, String> RelatorioCategoria;
    @FXML
    private TableColumn<Produto, Integer> RelatorioQuantidade;
    @FXML
    private TableColumn<Produto, Double> RelatorioValor;

    @FXML
    private TableView<Produto> TabelaRelatorioEstoqueCritico;

    @FXML
    private TableColumn<Produto, Integer> RelatorioCriticoId;
    @FXML
    private TableColumn<Produto, String> RelatorioCriticoNome;
    @FXML
    private TableColumn<Produto, String> RelatorioCriticoCategoria;
    @FXML
    private TableColumn<Produto, Integer> RelatorioCriticoQuantidade;
    @FXML
    private TableColumn<Produto, Double> RelatorioCriticoValor;


    @FXML
    private TableView<ItensVenda> tabelaSqlListaComprasVenda;

    @FXML
    private TableColumn<ItensVenda, String> ColunaNomeProduto;
    @FXML
    private TableColumn<ItensVenda, Integer>ColunaQuantidadeProduto;
    @FXML
    private TableColumn<ItensVenda, Double>ColunaValorProduto;


    @FXML
    private TableView<Venda> tabelaVendasPorPeriodo;

    @FXML
    private TableColumn<Venda, Integer> colunaIdVendaRelatorio;
    @FXML
    private TableColumn<Venda, LocalDate> colunaDataVendaRelatorio;
    @FXML
    private TableColumn<Venda, Double> colunaTotalVendaRelatorio;
    @FXML
    private TableColumn<Venda, String> colunaClienteRelatorio;

    @FXML
    private  TableView<ItensVenda> tabelaListaComprasVendaRelatorio;

    @FXML
    private TableColumn<ItensVenda, String> ColunaNomeProdutoRelatorio;
    @FXML
    private TableColumn<ItensVenda, Integer>ColunaQuantidadeProdutoRelatorio;
    @FXML
    private TableColumn<ItensVenda, Double>ColunaValorProdutoRelatorio;

    @FXML
    private TableView<Venda> TabelaSQLVendas;

    @FXML
    private TableColumn<Venda, Integer> colunaIdVenda;
    @FXML
    private TableColumn<Venda,LocalDate> colunaIdDataVenda;
    @FXML
    private TableColumn<Venda,String> colunaFormaPagamentoVenda;
    @FXML
    private TableColumn<Venda,Double> colunaClienteVenda;
    @FXML
    private TableColumn<Venda,String> colunaTotalVenda;

    @FXML
    private TableView<Cliente> TabelaSQLCliente;

    @FXML
    private TableColumn<Cliente, Integer> tabelaIdCliente;
    @FXML
    private TableColumn<Cliente, String> tabelaNomeCliente;
    @FXML
    private TableColumn<Cliente, String> tabelaCpfCliente;
    @FXML
    private TableColumn<Cliente, LocalDate> tabelaDataNascCliente;
    @FXML
    private TableColumn<Cliente, String> tabelaEmailCliente;
    @FXML
    private TableColumn<Cliente, String> tabelaTelefoneCliente;


    @FXML
    private TableView<Produto> tabelaSQLProduto;

    @FXML
    private TableColumn<Produto, Integer> tabelaIdProduto;
    @FXML
    private TableColumn<Produto, String> tabelaNomeProduto;
    @FXML
    private TableColumn<Produto, Integer> tabelaQuantidadeProduto;
    @FXML
    private TableColumn<Produto, Double> tabelaValorProduto;
    @FXML
    private TableColumn<Produto, String> tabelaCategoriaProduto;
    @FXML
    private TableColumn<Produto, Double> tabelaCustoProduto;

    @FXML
    public void initialize() {
        iniciarRelogio();
        mostrarDataAtual();

        configurarTabelaClientes();
        configurarTabelaProdutos();
        configurarTabelaVendas();
        configurarTabelaItensVenda();
        configurarTabelaRelatorioVendas();
        configurarTabelaListaVendasRelatorio();
        configurarTabelaRelatorioEstoquebaixo();
        configurarTabelaRelatorioEstoqueCritico();

        Telas();
    }

    @FXML
    public void logoff(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/TelaLogin.fxml"));
            Scene sceneLogin = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(sceneLogin);
        } catch (IOException e) {
            System.out.println("Erro ao carregar TelaLogin.fxml: " + e.getMessage());
        }
    }

    private void iniciarRelogio() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalTime horaAtual = LocalTime.now();
            labelRelogio.setText(horaAtual.format(formatoHora));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private void mostrarDataAtual() {
        LocalDate dataAtual = LocalDate.now();
        labelData.setText(dataAtual.format(formatoData));
    }

    private void configurarTabelaClientes() {
        tabelaIdCliente.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabelaNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tabelaCpfCliente.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tabelaDataNascCliente.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        tabelaEmailCliente.setCellValueFactory(new PropertyValueFactory<>("email"));
        tabelaTelefoneCliente.setCellValueFactory(new PropertyValueFactory<>("telefone"));
    }

    private void configurarTabelaProdutos() {
        tabelaIdProduto.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabelaNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tabelaCategoriaProduto.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tabelaQuantidadeProduto.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tabelaValorProduto.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tabelaCustoProduto.setCellValueFactory(new PropertyValueFactory<>("custo"));
    }

    private void configurarTabelaVendas() {
        colunaIdVenda.setCellValueFactory(new PropertyValueFactory<>("idVenda"));
        colunaIdDataVenda.setCellValueFactory(new PropertyValueFactory<>("dataVenda"));
        colunaFormaPagamentoVenda.setCellValueFactory(new PropertyValueFactory<>("metodoPagamento"));
        colunaTotalVenda.setCellValueFactory(new PropertyValueFactory<>("totalVenda"));
        colunaClienteVenda.setCellValueFactory(new PropertyValueFactory<>("clienteNome"));
    }

    private void configurarTabelaItensVenda() {
        ColunaNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        ColunaQuantidadeProduto.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        ColunaValorProduto.setCellValueFactory(new PropertyValueFactory<>("precoUnitario"));
    }

    private void configurarTabelaRelatorioVendas() {
        colunaIdVendaRelatorio.setCellValueFactory(new PropertyValueFactory<>("idVenda"));
        colunaDataVendaRelatorio.setCellValueFactory(new PropertyValueFactory<>("dataVenda"));
        colunaTotalVendaRelatorio.setCellValueFactory(new PropertyValueFactory<>("totalVenda"));
        colunaClienteRelatorio.setCellValueFactory(new PropertyValueFactory<>("clienteNome"));
    }

    private void configurarTabelaListaVendasRelatorio(){
        ColunaNomeProdutoRelatorio.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        ColunaQuantidadeProdutoRelatorio.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        ColunaValorProdutoRelatorio.setCellValueFactory(new PropertyValueFactory<>("precoUnitario"));
    }

    private void configurarTabelaRelatorioEstoquebaixo(){
        RelatorioId.setCellValueFactory(new PropertyValueFactory<>("id"));
        RelatorioNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        RelatorioCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        RelatorioQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        RelatorioValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }

    private void configurarTabelaRelatorioEstoqueCritico(){
        RelatorioCriticoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        RelatorioCriticoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        RelatorioCriticoCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        RelatorioCriticoQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        RelatorioCriticoValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }

    private void Telas(){
        CenaPrincipalCliente.setVisible(false);
        CenaPrincipalProduto.setVisible(false);
        CenaPrincipalVendas.setVisible(false);
        TelaRelatorios.setVisible(false);
        TelaRelatorioVenda.setVisible(false);
        RelatorioEstoque.setVisible(false);
    }

    @FXML
    void AcaoTelaCliente(ActionEvent event) {
        CenaPrincipalProduto.setVisible(false);
        CenaPrincipalVendas.setVisible(false);


        TelaRelatorios.setVisible(false);
        TelaRelatorioVenda.setVisible(false);
        RelatorioEstoque.setVisible(false);
        tabelaListaComprasVendaRelatorio.getItems().clear();

        CenaPrincipalCliente.setVisible(true);

        carregarBancodeDadosCliente();
    }

    @FXML
    void AcaoTelaProdutos(ActionEvent event) {
        CenaPrincipalCliente.setVisible(false);
        CenaPrincipalVendas.setVisible(false);

        TelaRelatorios.setVisible(false);
        TelaRelatorioVenda.setVisible(false);
        RelatorioEstoque.setVisible(false);
        tabelaListaComprasVendaRelatorio.getItems().clear();

        CenaPrincipalProduto.setVisible(true);

        carregarBancodeDadosProduto();
    }

    @FXML
    void acaoTelaVendas(ActionEvent event){
        CenaPrincipalCliente.setVisible(false);
        CenaPrincipalProduto.setVisible(false);

        TelaRelatorios.setVisible(false);
        TelaRelatorioVenda.setVisible(false);
        RelatorioEstoque.setVisible(false);
        tabelaListaComprasVendaRelatorio.getItems().clear();

        CenaPrincipalVendas.setVisible(true);

        TelaVerListaVenda.setVisible(false);

        carregarBancoDadosVendas();
    }

    public void carregarBancodeDadosCliente() {

        ObservableList<Cliente> clientes = FXCollections.observableArrayList();
        String query = "SELECT id, nome, cpf, data_nascimento, email, telefone FROM Cliente";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getDate("data_nascimento").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("telefone")
                );
                clientes.add(cliente);
            }
            TabelaSQLCliente.setItems(clientes);

        } catch (SQLException e) {
            alerta.error("Erro de Conexão", "Falha ao carregar dados do cliente" ,
                    "Ocorreu um erro ao tentar carregar os dados de cliente. Por favor, tente novamente ou contate o suporte.", e.getMessage());
        }
    }

    @FXML
    void acaoPesquisaCliente(ActionEvent event){
        String pesquisa = PesquisaSQLCliente.getText();
        ClienteDAO clienteDAO = new ClienteDAO();

        ObservableList<Cliente> clientes = clienteDAO.pesquisarClientes(pesquisa);
        if (clientes.isEmpty()) {
            alerta.information("Resultado da Pesquisa", "Nenhum Cliente Encontrado",
                    "Não foi possível localizar clientes correspondentes ao termo de pesquisa. Verifique o termo e tente novamente.", null);

        } else {
            TabelaSQLCliente.setItems(clientes);
        }
    }

    // Metodos da tela produto

    public void carregarBancodeDadosProduto() {
        ObservableList<Produto> produtos = FXCollections.observableArrayList();
        String query = "SELECT id, nome, categoria, quantidade, custo, valor FROM Produto";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("categoria"),
                        rs.getInt("quantidade"),
                        rs.getDouble("custo"),
                        rs.getDouble("valor")
                );
                produtos.add(produto);
            }
            tabelaSQLProduto.setItems(produtos);
            tabelaSQLProduto.setRowFactory(Ss -> new TableRow<Produto>() {
                @Override
                protected void updateItem(Produto item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setStyle("");
                    } else {
                        if (item.getQuantidade() == 0) {
                            setStyle("-fx-background-color: red;");
                        } else if (item.getQuantidade() <= 40) {
                            setStyle("-fx-background-color: orange;");
                        } else {
                            setStyle("");
                        }
                    }
                }
            });

        } catch (SQLException e) {
            alerta.error("Erro de Conexão", "Falha ao carregar dados dos Produtos" ,
                    "Ocorreu um erro ao tentar carregar os dados de vendas. Por favor, tente novamente ou contate o suporte.", e.getMessage());
        }
    }

    @FXML
    void acaoPesquisaProdutos(ActionEvent event){
        String pesquisa = PesquisaSQLProduto.getText();
        ProdutoDAO produtoDAO = new ProdutoDAO();

        ObservableList<Produto> produtos = produtoDAO.pesquisarProdutos(pesquisa);

        if (produtos.isEmpty()) {
            alerta.information("Resultado da Pesquisa", "Nenhum Produto Encontrado",
                    "Não foi possível localizar os Produtos correspondentes ao termo de pesquisa. Verifique o termo e tente novamente.", null);

        }else {
            tabelaSQLProduto.setItems(produtos);
        }
    }

    //Metodos da tela Venda

    private int idVenda;
    private boolean atualizacao = false;

    public int getIdVenda(){
        return this.idVenda;
    }
    private void setIdVenda(int idVenda){
        this.idVenda = idVenda;
    }

    private boolean getAtualizacao(){
        return this.atualizacao;
    }
    private void setAtualizacao(boolean atualizacao){
        this.atualizacao = atualizacao;
    }


    public void carregarBancoDadosVendas(){
        ObservableList<Venda> vendas = FXCollections.observableArrayList();
        String sql = "SELECT idVenda, dataVenda, metodoPagamento, totalVenda, clienteNome FROM Venda";

        try (Connection conexao = Conexao.getConnection();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idVenda = rs.getInt("idVenda");
                LocalDate dataVenda = rs.getTimestamp("dataVenda").toLocalDateTime().toLocalDate();
                String metodoPagamento = rs.getString("metodoPagamento");
                double totalVenda = rs.getDouble("totalVenda");
                String clienteNome = rs.getString("clienteNome");

                vendas.add(new Venda(idVenda, dataVenda, metodoPagamento, totalVenda, clienteNome));
            }
        } catch (SQLException e) {

            alerta.error("Erro ao Carregar Dados de Vendas", "Não foi possível acessar o banco de dados de vendas",
                    "Ocorreu um erro ao tentar carregar os dados de vendas. Por favor, tente novamente ou contate o suporte.", e.getMessage());
        }
        TabelaSQLVendas.setItems(vendas);
    }

    @FXML
    void acaoVoltarItemList(ActionEvent event){
        TelaVerListaVenda.setVisible(false);
        TabelaSQLVendas.getSelectionModel().clearSelection();
    }

    @FXML
    void acaoVerListaVenda(ActionEvent event){
        Venda vendas = TabelaSQLVendas.getSelectionModel().getSelectedItem();

        if (vendas != null) {
            int idVenda = vendas.getIdVenda();
            double precoTotal = vendas.getTotalVenda();

            tabelaSqlListaComprasVenda.getItems().clear();
            TelaVerListaVenda.setVisible(true);

            mostraValorTotal.setText(String.valueOf(precoTotal));

            ItensVendaDAO itensVendaDAO = new ItensVendaDAO();
            ObservableList<ItensVenda> itensVenda = itensVendaDAO.buscarItensVendaPorId(idVenda);

            if (!itensVenda.isEmpty()) {
                tabelaSqlListaComprasVenda.getItems().setAll(itensVenda);
            } else {
                alerta.warning("Atenção", "Venda Sem Itens",
                        "Não há itens associados a esta venda. Adicione itens para prosseguir com o registro.", null);
            }

        } else {
            alerta.warning("Atenção", "Nenhuma Venda Selecionada",
                    "Por favor, selecione uma venda da lista para visualizar os itens associados.", null);
        }
    }

    @FXML
    void acaoPesquisaVendas(ActionEvent event) {
        String pesquisa = pesquisaSQLVenda.getText();

        VendaDAO vendaDAO = new VendaDAO();
        ObservableList<Venda> vendas = vendaDAO.pesquisarVendas(pesquisa);

        if (vendas.isEmpty()) {
            alerta.information("Resultado da Pesquisa", "Nenhuma Venda Encontrada",
                    "Não foram encontradas vendas que correspondam aos critérios de pesquisa. Verifique os dados inseridos.", null);

        } else {
            TabelaSQLVendas.setItems(vendas);
        }
    }

    //Metodos Tela Relatorio

    @FXML
    void acaoTelaRelatorios(ActionEvent event){
        CenaPrincipalCliente.setVisible(false);
        CenaPrincipalProduto.setVisible(false);
        CenaPrincipalVendas.setVisible(false);

        tabelaListaComprasVendaRelatorio.getItems().clear();

        TelaRelatorios.setVisible(true);
        RelatorioEstoque.setVisible(false);
        TelaRelatorioVenda.setVisible(false);
    }

    @FXML
    void carregarRelatorioVendaPorPeriodo(ActionEvent event) {
        LocalDate dataInicioPeriodo = inicio.getValue();
        Date dataInicioP;
        if (dataInicioPeriodo != null) {
            dataInicioP = java.sql.Date.valueOf(dataInicioPeriodo);
        } else {
            String detalhes = """
                    Esta informação é necessária para concluir o Relatorio corretamente.
                    
                    Dica:
                    -Lembre-se de usar o formato dd/mm/aaaa caso tenha digitado a Data""";

            alerta.warning("Atenção", "Data Inicial Não Selecionada",
                    "Por favor, selecione uma data inicial antes de prosseguir.", detalhes);
            return;
        }

        LocalDate dataFimPeriodo = fim.getValue();
        Date dataFimP;
        if (dataFimPeriodo != null) {
            dataFimP = java.sql.Date.valueOf(dataFimPeriodo);
        } else {
            String detalhes = """
                    Esta informação é necessária para concluir o Relatorio corretamente.
                    
                    Dica:
                    -Lembre-se de usar o formato dd/mm/aaaa caso tenha digitado a Data""";

            alerta.warning("Atenção", "Data Final Não Selecionada",
                    "Por favor, selecione uma data final antes de prosseguir.", detalhes);
            return;
        }

        RelatorioVendaPorPeriodo(dataInicioP,dataFimP);
        inicio.setValue(null);
        fim.setValue(null);
    }

    @FXML
    void acaoVoltarRelatorio(){
        TelaRelatorios.setVisible(true);
        TelaRelatorioVenda.setVisible(false);

        tabelaListaComprasVendaRelatorio.getItems().clear();

        NovoRelatorioVendasInicio.setValue(null);
        NovoRelatorioVendasFim.setValue(null);
    }

    @FXML
    void novaConsultaRelatorioVendaPorPeriodo(ActionEvent event){
        LocalDate dataInicioPeriodo = NovoRelatorioVendasInicio.getValue();
        Date dataInicioP;
        if (dataInicioPeriodo != null) {
            dataInicioP = java.sql.Date.valueOf(dataInicioPeriodo);
        } else {
            String detalhes = """
                    Esta informação é necessária para concluir o Relatorio corretamente.
                    
                    Dica:
                    -Lembre-se de usar o formato dd/mm/aaaa caso tenha digitado a Data""";

            alerta.warning("Atenção", "Data Inicial Não Selecionada",
                    "Por favor, selecione uma data inicial antes de prosseguir.", detalhes);
            return;
        }

        LocalDate dataFimPeriodo = NovoRelatorioVendasFim.getValue();
        Date dataFimP;
        if (dataFimPeriodo != null) {
            dataFimP = java.sql.Date.valueOf(dataFimPeriodo);
        } else {
            String detalhes = """
                    Esta informação é necessária para concluir o Relatorio corretamente.
                    
                    Dica:
                    -Lembre-se de usar o formato dd/mm/aaaa caso tenha digitado a Data""";

            alerta.warning("Atenção", "Data Final Não Selecionada",
                    "Por favor, selecione uma data final antes de prosseguir.", detalhes);
            return;
        }

        RelatorioVendaPorPeriodo(dataInicioP,dataFimP);
        NovoRelatorioVendasInicio.setValue(null);
        NovoRelatorioVendasFim.setValue(null);
        tabelaListaComprasVendaRelatorio.getItems().clear();
    }

    public void RelatorioVendaPorPeriodo(Date inicio, Date fim){
        VendaDAO vendaDAO = new VendaDAO();
        ObservableList<Venda> vendas = vendaDAO.buscarVendasPorPeriodo(inicio, fim);

        if (!vendas.isEmpty()) {
            double totalArrecadado = vendaDAO.calcularTotalArrecadado(vendas);
            double lucroBruto = vendaDAO.calcularLucroBruto(inicio, fim);
            TelaRelatorios.setVisible(false);
            TelaRelatorioVenda.setVisible(true);
            LabelRelatorioVenda.setText("Periodo de " + inicio + " e " + fim);
            faturamentoRelatorio.setText(String.format("%.2f", totalArrecadado));
            lucroTotalRelatorio.setText(String.format("%.2f", lucroBruto));

            tabelaVendasPorPeriodo.setItems(vendas);
        } else {
            String detalhes = "Verifique as datas selecionadas e tente um intervalo diferente para obter resultados.";

            alerta.information("Resultado do Relatório", "Nenhum Resultado Encontrado",
                    "Nenhum resultado encontrado para as datas " + inicio + " e " + fim + ". Verifique as datas selecionadas e tente um intervalo diferente para obter resultados.", detalhes);
        }
    }

    @FXML
    public void acaoVerListaVendaRelatorio(MouseEvent mouseEvent) {
        Venda vendas = tabelaVendasPorPeriodo.getSelectionModel().getSelectedItem();

        if (vendas != null) {
            int idVenda = vendas.getIdVenda();
            tabelaListaComprasVendaRelatorio.getItems().clear();

            ItensVendaDAO itensVendaDAO = new ItensVendaDAO();
            ObservableList<ItensVenda> itensVenda = itensVendaDAO.buscarItensVendaPorId(idVenda);

            if (!itensVenda.isEmpty()) {
                tabelaListaComprasVendaRelatorio.getItems().setAll(itensVenda);
            } else {
                alerta.warning("Atenção", "Venda Sem Itens",
                        "Não há itens associados a esta venda. Adicione itens para prosseguir com o registro.", null);
            }

        } else {
            alerta.warning("Atenção", "Nenhuma Venda Selecionada",
                    "Por favor, selecione uma venda da lista para visualizar os itens associados.", null);
        }
    }

    @FXML
    public void acaoVoltarRelatorioEstoque(ActionEvent event){
        TelaRelatorios.setVisible(true);
        RelatorioEstoque.setVisible(false);
    }

    @FXML
    public void carregarRelatorioEstoque(ActionEvent event){
        TelaRelatorios.setVisible(false);
        RelatorioEstoque.setVisible(true);

        ProdutoDAO produtoDAO = new ProdutoDAO();
        int nivelBaixo = 40;
        int nivelCritico = 0;

        try {
            ObservableList<Produto> produtos = produtoDAO.buscarProdutosComEstoqueZerado(nivelCritico);
            TabelaRelatorioEstoqueCritico.setItems(produtos);
        } catch (Exception e) {
            alerta.error("Erro ao Carregar Produtos", "Problema ao Obter Informações de Estoque Baixo",
                    "Não foi possível carregar a lista de produtos com nivel baixo no estoque. Verifique sua conexão e tente novamente.", null );
        }

        try {
            ObservableList<Produto> produtosCriticos = produtoDAO.buscarProdutosComQuantidadeBaixo(nivelCritico, nivelBaixo);
            TabelaRelatorioEstoqueBaixo.setItems(produtosCriticos);
        } catch (Exception e) {
            alerta.error("Erro ao Carregar Produtos", "Problema ao Obter Informações de Estoque Critico",
                    "Não foi possível carregar a lista de produtos com nivel critico no  estoque. Verifique sua conexão e tente novamente.", null);
        }
    }
}
