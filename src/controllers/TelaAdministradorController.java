package controllers;

import database.Conexao;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.*;
import DAO.*;
import util.AlertUtils;

import java.io.IOException;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class TelaAdministradorController {

    private static final String DATABASE_URL = "jdbc:sqlite:Banco.db";

    AlertUtils alerta = new AlertUtils();

    @FXML
    private Label precoTotalItemVenda;
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
    private AnchorPane CenaAlteracaoCliente, CenaAlteracaoProduto, CenaPrincipalCliente,
            CenaPrincipalProduto, CenaCadastroCliente, CenaCadastrarProduto,
            CenaPrincipalVendas, TelaCadastroListaVendas, TelaCadastroVenda,
            TelaVerListaVenda, TelaAlteracaoVenda, TelaRelatorioVenda,
            TelaRelatorios, RelatorioEstoque
            ;

    @FXML
    private TextField ClienteAlteracaoCPF, ClienteAlteracaoEmail, ClienteAlteracaoNome,
            ClienteAlteracaoTelefone, ClienteCadastroCPF, ClienteCadastroEmail,
            ClienteCadastroNome, ClienteCadastroTelefone, PesquisaSQLCliente,
            produtoAlteracaoNome, PesquisaSQLProduto, produtoAlteracaoCategoria,
            produtoAlteracaoCusto, produtoAlteracaoQuantidade, produtoAlteracaoValor,
            produtoCadastroCategoria, produtoCadastroQuantidade, produtoCadastroValor,
            produtoCadastroNome, produtoCadastroCusto, nomeClienteVenda,
            quantidadeProdutoVenda,alteracaoNomeClienteVenda, pesquisaSQLProduto,
            pesquisaSQLVenda
                    ;

    @FXML
    private DatePicker ClienteAlteracaoData, ClienteCadastroData, DataVenda,
            DataVendaAlteracao, inicio, fim,
            NovoRelatorioVendasInicio, NovoRelatorioVendasFim
            ;

    @FXML
    private ChoiceBox<String> TiposDePagamento, PagamentoAlteracao;

    public String[] pagamentos = {"Cartão de Crédito","Cartão de Débito","Dinheiro","Pix"};

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
    private  TableView<ItensVenda> TabelaItensVenda;

    @FXML
    private TableColumn<ItensVenda, Integer> colunaIdItemVenda;
    @FXML
    private TableColumn<ItensVenda, String> colunaNomeProduto;
    @FXML
    private TableColumn<ItensVenda, Integer> colunaQuantidade;
    @FXML
    private TableColumn<ItensVenda, Double> colunaPrecoUnitario;


    @FXML
    private TableView<Produto> TabelaProdutosVenda;

    @FXML
    private TableColumn<Produto, Integer> tabelaIdVenda;
    @FXML
    private TableColumn<Produto, String> tabelaNomeVenda;
    @FXML
    private TableColumn<Produto, Integer>tabelaQuantidadeVenda;
    @FXML
    private TableColumn<Produto, Double>tabelaValorVenda;


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

        TiposDePagamento.getItems().addAll(pagamentos);
        PagamentoAlteracao.getItems().addAll(pagamentos);

        Telas();
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
        tabelaIdVenda.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabelaNomeVenda.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tabelaQuantidadeVenda.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tabelaValorVenda.setCellValueFactory(new PropertyValueFactory<>("valor"));

        colunaIdVenda.setCellValueFactory(new PropertyValueFactory<>("idVenda"));
        colunaIdDataVenda.setCellValueFactory(new PropertyValueFactory<>("dataVenda"));
        colunaFormaPagamentoVenda.setCellValueFactory(new PropertyValueFactory<>("metodoPagamento"));
        colunaTotalVenda.setCellValueFactory(new PropertyValueFactory<>("totalVenda"));
        colunaClienteVenda.setCellValueFactory(new PropertyValueFactory<>("clienteNome"));
    }

    private void configurarTabelaItensVenda() {
        colunaIdItemVenda.setCellValueFactory(new PropertyValueFactory<>("idItemVenda"));
        colunaNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colunaPrecoUnitario.setCellValueFactory(new PropertyValueFactory<>("precoUnitario"));

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

    private void Telas(){
        CenaPrincipalCliente.setVisible(false);
        CenaPrincipalProduto.setVisible(false);
        CenaPrincipalVendas.setVisible(false);
        TelaRelatorios.setVisible(false);
        TelaRelatorioVenda.setVisible(false);
        TelaCadastroListaVendas.setVisible(false);
        RelatorioEstoque.setVisible(false);
    }

    @FXML
    void AcaoTelaCliente(ActionEvent event) {
        CenaPrincipalProduto.setVisible(false);
        CenaPrincipalVendas.setVisible(false);
        TelaCadastroListaVendas.setVisible(false);

        TelaRelatorios.setVisible(false);
        TelaRelatorioVenda.setVisible(false);
        RelatorioEstoque.setVisible(false);
        tabelaListaComprasVendaRelatorio.getItems().clear();

        CenaPrincipalCliente.setVisible(true);
        CenaCadastroCliente.setVisible(false);
        CenaAlteracaoCliente.setVisible(false);

        carregarBancodeDadosCliente();
    }

    @FXML
    void AcaoTelaProdutos(ActionEvent event) {
        CenaPrincipalCliente.setVisible(false);
        CenaPrincipalVendas.setVisible(false);
        TelaCadastroListaVendas.setVisible(false);

        TelaRelatorios.setVisible(false);
        TelaRelatorioVenda.setVisible(false);
        RelatorioEstoque.setVisible(false);
        tabelaListaComprasVendaRelatorio.getItems().clear();

        CenaPrincipalProduto.setVisible(true);
        CenaAlteracaoProduto.setVisible(false);
        CenaCadastrarProduto.setVisible(false);

        carregarBancodeDadosProduto();
    }

    @FXML
    void acaoTelaVendas(ActionEvent event){
        CenaPrincipalCliente.setVisible(false);
        CenaPrincipalProduto.setVisible(false);

        TelaCadastroListaVendas.setVisible(false);

        TelaRelatorios.setVisible(false);
        TelaRelatorioVenda.setVisible(false);
        RelatorioEstoque.setVisible(false);
        tabelaListaComprasVendaRelatorio.getItems().clear();

        CenaPrincipalVendas.setVisible(true);

        TelaCadastroVenda.setVisible(false);
        TelaVerListaVenda.setVisible(false);
        TelaAlteracaoVenda.setVisible(false);

        carregarBancoDadosVendas();
    }

    // Metodos da tela produto
    public void carregarBancodeDadosCliente() {

        ObservableList<Cliente> clientes = FXCollections.observableArrayList();
        String query = "SELECT id, nome, cpf, data_nascimento, email, telefone FROM Cliente";

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conexao.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("data_nascimento"),
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

    @FXML
    void AcaoTelaCadastroCliente(ActionEvent event) {
        CenaCadastroCliente.setVisible(true);
        CenaAlteracaoCliente.setVisible(false);
    }

    @FXML
    void acaoVoltarCadastroCliente(ActionEvent event){
        CenaCadastroCliente.setVisible(false);
        limparCamposCadastroCliente();
    }

    @FXML
    void acaoCadastroCliente(ActionEvent event) {

        String nomeCliente = ClienteCadastroNome.getText();
        String cpfCliente = ClienteCadastroCPF.getText();
        String emailCliente = ClienteCadastroEmail.getText();
        String telefoneCliente = ClienteCadastroTelefone.getText();
        LocalDate dataNascCliente = ClienteCadastroData.getValue();

        if (dataNascCliente == null) {
            String detalhes = """
                    Esta informação é necessária para concluir o cadastro corretamente.
                    
                    Dica:
                    -Lembre-se de usar o formato dd/mm/aaaa caso tenha digitado a Data""";

            alerta.warning("Atenção", "Data de Nascimento Não Selecionada",
                    "Por favor, insira uma data de nascimento antes de prosseguir.", detalhes);
            return;
        }

        ClienteDAO clienteDAO = new ClienteDAO();
        boolean sucesso = clienteDAO.cadastrarCliente(nomeCliente, cpfCliente, dataNascCliente, emailCliente, telefoneCliente);

        if (sucesso) {
            carregarBancodeDadosCliente();
            limparCamposCadastroCliente();
            alerta.information("Cadastro Cliente", "Cliente Cadastrado Com Sucesso", null, null);
        } else {
            String detalhes = """
                    
                    Possíveis causas:
                    - Conexão com o banco de dados interrompida.
                    - Dados inválidos ou em branco.
                    - Erro interno no sistema.""";
            alerta.error("Erro ao Cadastrar Cliente", "Não foi possível completar o cadastro do cliente",
                    "Ocorreu um erro durante o cadastro do cliente. Verifique as informações e tente novamente.", detalhes);
        }
    }

    public void limparCamposCadastroCliente(){
        ClienteCadastroNome.setText(null);
        ClienteCadastroCPF.setText(null);
        ClienteCadastroEmail.setText(null);
        ClienteCadastroTelefone.setText(null);
        ClienteCadastroData.setValue(null);
    }

    @FXML
    void acaoRemoverCliente(ActionEvent event) {
        Cliente item = TabelaSQLCliente.getSelectionModel().getSelectedItem();

        if (item != null) {
            ClienteDAO clienteDAO = new ClienteDAO();
            boolean sucesso = clienteDAO.removerCliente(item.getId());

            if (sucesso) {
                ObservableList<Cliente> items = TabelaSQLCliente.getItems();
                items.remove(item);
                TabelaSQLCliente.getSelectionModel().clearSelection();
                alerta.information("Remoção Cliente", "Cliente Removido Com Sucesso", null, null);
            } else {
                String detalhes = """
                        
                        Possíveis causas:
                        - Cliente ainda associado a registros de vendas.
                        - Problemas de conexão com o banco de dados.
                        - Permissões insuficientes para realizar a operação.""";
                alerta.error("Erro ao Remover Cliente", "Não foi possível remover o cliente",
                        "Ocorreu um erro ao tentar remover o cliente. Verifique as informações e tente novamente.", detalhes);
            }
        } else {
            alerta.information("Atenção", "Nenhum Cliente Selecionado",
                    "Por favor, selecione um cliente da lista antes de tentar removê-lo.", null);
        }
    }

    @FXML
    void AcaoTelaAlteracaoCliente(ActionEvent event) {
        Cliente item = TabelaSQLCliente.getSelectionModel().getSelectedItem();
        if(item != null) {
            CenaCadastroCliente.setVisible(false);
            CenaAlteracaoCliente.setVisible(true);

            ClienteAlteracaoNome.setText(item.getNome());
            ClienteAlteracaoCPF.setText(item.getCpf());
            ClienteAlteracaoEmail.setText(item.getEmail());
            ClienteAlteracaoTelefone.setText(item.getTelefone());
            ClienteAlteracaoData.setValue(LocalDate.parse(item.getDataNascimento()));
        } else {
            alerta.warning("Atenção", "Nenhum Cliente Selecionado",
                    "Por favor, selecione um Cliente da tabela antes de tentar alterá-lo.", null);
        }
    }

    @FXML
    void acaoVoltarAlteracaoCliente(ActionEvent event){
        CenaAlteracaoCliente.setVisible(false);
        TabelaSQLCliente.getSelectionModel().clearSelection();
    }

    @FXML
    void acaoUpdateCliente(ActionEvent event){
        Cliente item = TabelaSQLCliente.getSelectionModel().getSelectedItem();

        if (item == null) {
            alerta.warning("Atenção", "Nenhum Cliente Selecionado",
                    "É possível que o cliente selecionado para alteração tenha sido removido ou que tenha ocorrido algum problema de conexão.", null);

            CenaAlteracaoCliente.setVisible(false);
            return;
        }

        item.setNome(ClienteAlteracaoNome.getText());
        item.setCpf(ClienteAlteracaoCPF.getText());
        item.setEmail(ClienteAlteracaoEmail.getText());
        item.setTelefone(ClienteAlteracaoTelefone.getText());
        item.setDataNascimento(String.valueOf(ClienteAlteracaoData.getValue()));

        ClienteDAO clienteDAO = new ClienteDAO();
        boolean sucesso = clienteDAO.atualizarCliente(item);

        if (sucesso) {
            carregarBancodeDadosCliente();
            TabelaSQLCliente.getSelectionModel().clearSelection();
            CenaAlteracaoCliente.setVisible(false);
            alerta.information("Atualização Cliente", "Cliente Atualizado Com Sucesso", null, null);
        } else {
            String detalhes = """
                    
                    Possíveis causas:
                    - Conexão com o banco de dados interrompida.
                    - Dados incorretos ou campos obrigatórios em branco.
                    - Permissões insuficientes para realizar a atualização.""";

            alerta.error("Erro ao Atualizar Cliente", "Não foi possível atualizar as informações do cliente",
                    "Ocorreu um erro durante a atualização do cliente. Verifique as informações e tente novamente.", detalhes);
        }
    }

    // Metodos da tela produto

    public void carregarBancodeDadosProduto() {
        ObservableList<Produto> produtos = FXCollections.observableArrayList();
        String query = "SELECT id, nome, categoria, quantidade, custo, valor FROM Produto";

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conexao.prepareStatement(query);
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

    @FXML
    void AcaoTelaCadastroProduto(ActionEvent event) {
        CenaAlteracaoProduto.setVisible(false);
        CenaCadastrarProduto.setVisible(true);
    }

    @FXML
    void acaoVoltarCadastroProduto(ActionEvent event){
        CenaCadastrarProduto.setVisible(false);
        limparCamposCadastroProduto();
    }

    @FXML
    void acaoCadastroProduto(ActionEvent event) {
        String nomeProduto = produtoCadastroNome.getText();
        String categoriaProduto = produtoCadastroCategoria.getText();
        int quantidadeProduto;
        double custoProduto;
        double valorProduto;

        try {
            quantidadeProduto = Integer.parseInt(produtoCadastroQuantidade.getText());
            custoProduto = Double.parseDouble(produtoCadastroCusto.getText());
            valorProduto = Double.parseDouble(produtoCadastroValor.getText());
        } catch (NumberFormatException e) {
            String detalhes = """
                    
                    Certifique-se de que:
                    - Todos os valores inseridos são numéricos.
                    - Utilize apenas números inteiros ou decimais, conforme necessário.
                    - Evite caracteres não numéricos, como letras ou símbolos.""";

            alerta.error("Erro de Entrada", "Entrada Inválida Detectada",
                    "Por favor, insira valores numéricos válidos para os campos: quantidade, custo e valor.", detalhes);
            return;
        }

        Produto produto = new Produto(nomeProduto, categoriaProduto, quantidadeProduto, custoProduto, valorProduto);
        ProdutoDAO produtoDAO = new ProdutoDAO();

        if (produtoDAO.cadastrarProduto(produto)) {
            carregarBancodeDadosProduto();
            limparCamposCadastroProduto();
            alerta.information("Cadastro Produto", "Produto Cadastrado Com Sucesso", null, null);
        } else {
            String detalhes = """
                    
                    Possíveis causas:
                    - Conexão com o banco de dados interrompida.
                    - Campos obrigatórios não preenchidos.
                    - Dados inválidos (por exemplo, preço ou quantidade).""";

            alerta.error("Erro ao Cadastrar Produto", "Não foi possível cadastrar o produto",
                    "Ocorreu um erro durante o cadastro do produto. Verifique as informações e tente novamente.", detalhes);
        }
    }

    private void limparCamposCadastroProduto(){
        produtoCadastroNome.setText(null);
        produtoCadastroCategoria.setText(null);
        produtoCadastroQuantidade.setText(null);
        produtoCadastroCusto.setText(null);
        produtoCadastroValor.setText(null);
    }

    @FXML
    void acaoRemoverProduto(ActionEvent event) {
        Produto item = tabelaSQLProduto.getSelectionModel().getSelectedItem();

        if (item == null) {
            alerta.warning("Atenção", "Nenhum Produto Selecionado",
                    "Por favor, selecione um produto da lista antes de tentar removê-lo.", null);
            return;
        }

        ProdutoDAO produtoDAO = new ProdutoDAO();
        if (produtoDAO.removerProduto(item.getId())) {
            ObservableList<Produto> items = tabelaSQLProduto.getItems();
            items.remove(item);
            tabelaSQLProduto.getSelectionModel().clearSelection();
            alerta.information("Remoção Produto", "Produto Removido Com Sucesso", null, null);
        } else {
            String detalhes = """
                    
                    Possíveis causas:
                    - Conexão com o banco de dados interrompida.
                    - Produto ainda associado a pedidos ou registros de vendas.
                    - Permissões insuficientes para realizar a remoção.""";

            alerta.error("Erro ao Remover Produto", "Não foi possível remover o produto",
                    "Ocorreu um erro ao tentar remover o produto. Verifique as informações e tente novamente.", detalhes);
        }
    }

    @FXML
    void AcaoTelaAlteracaoProduto(ActionEvent event) {

        Produto item = tabelaSQLProduto.getSelectionModel().getSelectedItem();
        if(item != null) {
            CenaAlteracaoProduto.setVisible(true);
            CenaCadastrarProduto.setVisible(false);

            produtoAlteracaoNome.setText(item.getNome());
            produtoAlteracaoCategoria.setText(item.getCategoria());
            produtoAlteracaoQuantidade.setText(String.valueOf(item.getQuantidade()));
            produtoAlteracaoCusto.setText(String.valueOf(item.getCusto()));
            produtoAlteracaoValor.setText(String.valueOf(item.getValor()));

        } else {
            alerta.warning("Atenção", "Nenhum Produto Selecionado", "Por favor, selecione um produto da lista antes de tentar alterá-lo.", null);
        }
    }

    @FXML
    void acaoVoltarAlteracaoProduto(ActionEvent event) {
        CenaAlteracaoProduto.setVisible(false);
        tabelaSQLProduto.getSelectionModel().clearSelection();
    }

    @FXML
    void acaoUpdateProduto(ActionEvent event) {
        Produto item = tabelaSQLProduto.getSelectionModel().getSelectedItem();

        if (item == null) {
            alerta.warning("Atenção", "Nenhum Produto Selecionado",
                    "É possível que o Produto selecionado para alteração tenha sido removido ou que tenha ocorrido algum problema de conexão.", null);
            CenaAlteracaoProduto.setVisible(false);
            return;
        }

        String nome = produtoAlteracaoNome.getText();
        String categoria = produtoAlteracaoCategoria.getText();
        int quantidade;
        double custo;
        double valor;

        try {
            quantidade = Integer.parseInt(produtoAlteracaoQuantidade.getText());
            custo = Double.parseDouble(produtoAlteracaoCusto.getText());
            valor = Double.parseDouble(produtoAlteracaoValor.getText());
        } catch (NumberFormatException e) {
            String detalhes = """
                    
                    Certifique-se de que:
                    - A quantidade seja um número inteiro positivo.
                    - O custo e o valor sejam números, podendo incluir decimais.
                    - Não utilize letras ou caracteres especiais.""";
            alerta.error("Erro de Entrada", "Valores Inválidos Detectados",
                    "Por favor, insira valores numéricos válidos em algum dos campos: quantidade, custo e valor.", detalhes);
            return;
        }

        item.setNome(nome);
        item.setCategoria(categoria);
        item.setQuantidade(quantidade);
        item.setCusto(custo);
        item.setValor(valor);

        ProdutoDAO produtoDAO = new ProdutoDAO();
        if (produtoDAO.atualizarProduto(item)) {
            carregarBancodeDadosProduto();
            tabelaSQLProduto.getSelectionModel().clearSelection();
            CenaAlteracaoProduto.setVisible(false);
            alerta.information("Atualização Produto", "Produto Atualizado Com Sucesso", null, null);
        } else {
            String detalhes = """
                    
                    Possíveis causas:
                    - Conexão com o banco de dados perdida.
                    - Dados inválidos inseridos (como quantidade ou valor).
                    - Falta de permissões para atualizar registros.""";
            alerta.error("Error", null, "Erro ao atualizar o produto.", detalhes);
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

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idVenda = rs.getInt("idVenda");
                String dataVenda = rs.getString("dataVenda");
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
            TelaCadastroVenda.setVisible(false);
            TelaVerListaVenda.setVisible(true);
            TelaAlteracaoVenda.setVisible(false);

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
    void AcaoTelaCadastroVenda(ActionEvent event){
        TelaCadastroVenda.setVisible(true);
        TelaVerListaVenda.setVisible(false);
        TelaAlteracaoVenda.setVisible(false);
    }

    @FXML
    void acaoVoltarCadastroVenda(ActionEvent event){
        TelaCadastroVenda.setVisible(false);
        limparCadastrovenda();
    }

    void limparCadastrovenda(){
        DataVenda.setValue(null);
        TiposDePagamento.setValue(null);
        nomeClienteVenda.setText(null);
    }

    @FXML
    void acaoCadastroVenda(ActionEvent event) {
        String dataVenda = String.valueOf(DataVenda.getValue());
        if (dataVenda == null || dataVenda.isBlank() ) {
            String detalhes = """
                    Esta informação é necessária para concluir o cadastro corretamente.
                    
                    Dica:
                    -Lembre-se de usar o formato dd/mm/aaaa caso tenha digitado a Data""";

            alerta.warning("Atenção", "Data da Venda Não Selecionada ou Não Inserida",
                    "Por favor, insira uma data de nascimento antes de prosseguir.", detalhes);
            return;
        }

        String formaDePagamento;
        if (TiposDePagamento.getValue() != null) {
            formaDePagamento = TiposDePagamento.getValue();
        } else {
            alerta.warning("Atenção", "Tipo de Pagamento Não Selecionado",
                    "Por favor, escolha o tipo de pagamento para concluir a compra.", null);
            return;
        }

        String nomeCliente;
        if (nomeClienteVenda == null || nomeClienteVenda.getText() == null || nomeClienteVenda.getText().isEmpty()) {
            nomeCliente = "Anonimo";
        } else
            nomeCliente = nomeClienteVenda.getText();

        setIdVenda(IdVenda(dataVenda, formaDePagamento, nomeCliente));
        if (idVenda > 0) {
            chamarTelaCadastroListaVendas();
            carregarDadosProdutoVenda();
        }
    }

    public int IdVenda(String dataVenda, String Pagamento, String nomeCliente){
        String sqlVenda = "INSERT INTO Venda (dataVenda, metodoPagamento, totalVenda, clienteNome) VALUES (?, ?, 0, ?)";
        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmtVenda = conexao.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS)) {

            stmtVenda.setString(1, dataVenda);
            stmtVenda.setString(2, Pagamento);
            stmtVenda.setString(3, nomeCliente);
            stmtVenda.executeUpdate();
            try (ResultSet rs = stmtVenda.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            String detalhes = """
                    
                    Possíveis causas:
                    - Dados inválidos ou incompletos.
                    - Conexão com o banco de dados falhou.
                    - Falta de permissões para adicionar vendas.""";

            alerta.error("Erro ao Adicionar Venda", "Não foi possível adicionar a venda",
                    "Ocorreu um erro ao tentar adicionar a venda. Verifique as informações inseridas e tente novamente.", detalhes);
        }
        return -1;
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

    @FXML
    void acaoRemoverVenda(ActionEvent event){
        Venda venda = TabelaSQLVendas.getSelectionModel().getSelectedItem();

        if (venda != null) {
            int idVenda = venda.getIdVenda();
            VendaDAO vendaDAO = new VendaDAO();

            if (vendaDAO.removerVenda(idVenda)) {
                carregarBancoDadosVendas();
                alerta.information("Remoção Venda", "Venda Removida Com Sucesso", null, null);
            } else {
                String detalhes = """
                        
                        Possíveis causas:
                        - A venda pode estar associada a itens ou registros que não permitem remoção.
                        - Falha na comunicação com o banco de dados. Verifique sua conexão.""";
                alerta.error("Erro ao Remover Venda", "Não foi possível remover a venda",
                        "Ocorreu um erro ao tentar remover a venda. Verifique se a venda está associada a outros registros ou se há problemas com a conexão.", detalhes);
            }
        } else {
            alerta.warning("Atenção", "Nenhuma Venda Selecionada", "Por favor, selecione uma venda na lista para poder removê-la.", null);
        }
    }

    @FXML
    void acaoVoltarAlteracaoVenda(ActionEvent event){
        TelaAlteracaoVenda.setVisible(false);
        TabelaSQLVendas.getSelectionModel().clearSelection();
    }

    @FXML
    void AcaoTelaAlteracaoVenda(ActionEvent event) {
        Venda venda = TabelaSQLVendas.getSelectionModel().getSelectedItem();
        if (venda != null) {
            TelaCadastroVenda.setVisible(false);
            TelaVerListaVenda.setVisible(false);
            TelaAlteracaoVenda.setVisible(true);

            DataVendaAlteracao.setValue(LocalDate.parse(venda.getDataVenda()));
            PagamentoAlteracao.setValue(venda.getMetodoPagamento());
            alteracaoNomeClienteVenda.setText(venda.getClienteNome());
            setIdVenda(venda.getIdVenda());

        }else {
            alerta.warning("Atenção", "Nenhuma Venda Selecionada", "Por favor, selecione uma venda da lista para poder alterá-la.", null);
        }
    }

    @FXML
    void acaoUpdateVenda(ActionEvent event){
        String dataVenda = String.valueOf( DataVendaAlteracao.getValue());
        if (dataVenda == null || dataVenda.isBlank() ) {
            String detalhes = """
                    Esta informação é necessária para concluir o cadastro corretamente.
                    
                    Dica:
                    -Lembre-se de usar o formato dd/mm/aaaa caso tenha digitado a Data""";

            alerta.warning("Atenção", "Data da Venda Não Selecionada ou Não Inserida",
                    "Por favor, insira uma data de nascimento antes de prosseguir.", detalhes);
            return;
        }

        String formaDePagamento;
        if (PagamentoAlteracao.getValue() != null) {
            formaDePagamento = PagamentoAlteracao.getValue();
        } else {
            alerta.warning("Atenção", "Tipo de Pagamento Não Selecionado",
                    "Por favor, escolha o tipo de pagamento para concluir a compra.", null);
            return;
        }

        String nomeCliente;
        if (alteracaoNomeClienteVenda.getText().isEmpty()) {
            nomeCliente = "Anonimo";
        } else
            nomeCliente = alteracaoNomeClienteVenda.getText();

        IdVendaUpdate(dataVenda, formaDePagamento, nomeCliente);
        TelaAlteracaoVenda.setVisible(false);
        carregarBancoDadosVendas();
        alerta.information("Atualização Venda", "Venda Atualizada Com Sucesso", null, null);
    }

    @FXML
    void acaoUpdateVendaComLista(ActionEvent event){
        String dataVenda = String.valueOf( DataVendaAlteracao.getValue());
        if (dataVenda == null || dataVenda.isBlank() ) {
            String detalhes = """
                    Esta informação é necessária para concluir o cadastro corretamente.
                    
                    Dica:
                    -Lembre-se de usar o formato dd/mm/aaaa caso tenha digitado a Data""";

            alerta.warning("Atenção", "Data da Venda Não Selecionada ou Não Inserida",
                    "Por favor, insira uma data de nascimento antes de prosseguir.", detalhes);
            return;
        }

        String formaDePagamento;
        if (PagamentoAlteracao.getValue() != null) {
            formaDePagamento = PagamentoAlteracao.getValue();
        } else {
            alerta.warning("Atenção", "Tipo de Pagamento Não Selecionado",
                    "Por favor, escolha o tipo de pagamento para concluir a compra.", null);
            return;
        }

        String nomeCliente;
        if (alteracaoNomeClienteVenda.getText().isEmpty()) {
            nomeCliente = "Anonimo";
        } else
            nomeCliente = alteracaoNomeClienteVenda.getText();

        setAtualizacao(true);
        setIdVenda(IdVendaUpdate(dataVenda, formaDePagamento, nomeCliente));
        chamarTelaCadastroListaVendas();
        carregarDadosProdutoVenda();
        mostrarItensDaVendaAtual(getIdVenda());
        double total = calcularPrecoTotal(getIdVenda());
        precoTotalItemVenda.setText(String.valueOf(total));

    }

    public int IdVendaUpdate(String novaDataVenda, String novoPagamento, String novoNomeCliente) {
        String sqlUpdate = "UPDATE Venda SET dataVenda = ?, metodoPagamento = ?, clienteNome = ? WHERE idVenda = ?";

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmtUpdate = conexao.prepareStatement(sqlUpdate)) {

            stmtUpdate.setString(1, novaDataVenda);
            stmtUpdate.setString(2, novoPagamento);
            stmtUpdate.setString(3, novoNomeCliente);
            stmtUpdate.setInt(4, getIdVenda());

            int rowsAffected = stmtUpdate.executeUpdate();

            if (rowsAffected > 0) {
                return getIdVenda();
            }
        } catch (SQLException e) {
            String detalhes = """
                    
                    Possíveis causas:
                    - Conexão com o banco de dados falhou.
                    - Dados incorretos ou incompletos para a atualização da venda.""";

            alerta.error("Erro ao Atualizar Venda", "Falha na Atualização da Venda",
                    "Ocorreu um erro ao tentar atualizar a venda. Verifique a conexão com o banco de dados e se todos os dados foram preenchidos corretamente.", detalhes);
        }
        return -1;
    }

    double calcularPrecoTotal(int idVenda) {
        String sql = "SELECT SUM(iv.quantidade * iv.precoUnitario) AS total " +
                "FROM ItensVenda iv " +
                "WHERE iv.idVenda = ?";

        double total = 0.0;

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, idVenda);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }

        } catch (SQLException e) {
            String detalhes = """
                    
                    Possíveis causas:
                    - Preço ou quantidade de itens inválidos ou não informados.
                    - Problema no cálculo interno. Verifique se todos os campos foram preenchidos corretamente.""";

            alerta.error("Erro ao Calcular Preço Total", "Falha no Cálculo do Preço Total",
                    "Ocorreu um erro ao tentar calcular o preço total da venda. Verifique os dados de preço e quantidade inseridos.", detalhes);
            total = 0;
        }

        return total;
    }

    void mostrarItensDaVendaAtual(int idVenda) {
        String sql = "SELECT iv.idItemVenda, p.nome, iv.quantidade, iv.precoUnitario " +
                "FROM ItensVenda iv " +
                "JOIN Produto p ON iv.idProduto = p.id " +
                "WHERE iv.idVenda = ?";

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idVenda);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idItemVenda = rs.getInt("idItemVenda");
                String nomeProduto = rs.getString("nome");
                int quantidade = rs.getInt("quantidade");
                double precoUnitario = rs.getDouble("precoUnitario");


                ItensVenda itemVenda = new ItensVenda(idItemVenda, nomeProduto, quantidade, precoUnitario);
                TabelaItensVenda.getItems().add(itemVenda);
            }

        } catch (SQLException e) {
            String detalhes = """
                    
                    Possíveis causas:
                    - Dados incompletos ou incorretos relacionados aos itens da venda.
                    - Problema na conexão com o banco de dados ao tentar recuperar os itens.""";

            alerta.error("Erro ao Exibir Itens da Venda", "Falha ao Mostrar Itens da Venda",
                    "Ocorreu um erro ao tentar exibir os itens da venda. Verifique se todos os dados da venda estão corretos e completos.", detalhes);
            System.out.println(e.getMessage());
        }
    }

    public void carregarDadosProdutoVenda() {
        ObservableList<Produto> produtos = FXCollections.observableArrayList();
        String sql = "SELECT id, nome, quantidade, valor FROM Produto";

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int quantidade = rs.getInt("quantidade");
                double valor = rs.getDouble("valor");

                produtos.add(new Produto(id, nome, quantidade, valor));
            }

            TabelaProdutosVenda.setItems(produtos);
        } catch (SQLException e) {
            alerta.error("Erro ao Carregar Dados do Produto", "Falha ao Carregar os Dados do Produto",
                    "Ocorreu um erro ao tentar carregar os dados do produto. Verifique a conexão com o banco de dados e os dados inseridos.", e.getMessage());
        }
    }

    @FXML
    void AcaoAdicionarItemLista(ActionEvent event) {
        Produto item = TabelaProdutosVenda.getSelectionModel().getSelectedItem();

        if (item != null) {
            try {
                int idVenda = getIdVenda();
                int idProduto = item.getId();
                int quantidade = Integer.parseInt(quantidadeProdutoVenda.getText());
                double valor = item.getValor();

                if (quantidade > item.getQuantidade()) {
                    alerta.warning("Estoque Insuficiente", "Quantidade Excedida",
                            "A quantidade selecionada excede o estoque disponível. Verifique a quantidade e tente novamente.", null);
                    return;
                }
                ProdutoDAO produtoDAO = new ProdutoDAO();
                ItensVendaDAO itensVendaDAO = new ItensVendaDAO();
                boolean sucesso = itensVendaDAO.adicionarItem(idProduto, quantidade, valor, idVenda);

                if (sucesso) {
                    boolean estoqueAtualizado = produtoDAO.atualizarQuantidadeProduto(idProduto, quantidade);

                    if (estoqueAtualizado) {
                        TabelaProdutosVenda.getSelectionModel().clearSelection();
                        TabelaItensVenda.getItems().clear();
                        mostrarItensDaVendaAtual(idVenda);

                        double total = calcularPrecoTotal(idVenda);
                        precoTotalItemVenda.setText(String.valueOf(total));
                    } else {
                        alerta.error("Erro no Estoque", "Falha ao Atualizar Estoque",
                                "Ocorreu um erro ao atualizar a quantidade do produto no estoque.", null);
                    }
                } else {
                    String detalhes = """
                        
                        Possíveis causas:
                        - Dados incompletos ou inválidos no item a ser adicionado.
                        - Problema na comunicação com o banco de dados ou sistema de gestão de estoque.""";

                    alerta.error("Erro ao Adicionar Item à Venda", "Falha ao Adicionar Item",
                            "Ocorreu um erro ao tentar adicionar o item à venda. Verifique se todos os campos estão preenchidos corretamente.", detalhes);
                }
            } catch (NumberFormatException e) {
                String detalhes = """
                    A quantidade deve ser um valor numérico maior que zero.
                    Verifique se o valor inserido está correto e tente novamente.""";

                alerta.warning("Atenção", "Quantidade Inválida",
                        "Por favor, insira uma quantidade válida para o produto. A quantidade deve ser um número positivo.", detalhes);
            }
        } else {
            alerta.warning("Atenção", "Produto Não Selecionado", "Por favor, selecione um produto antes de adicioná-lo à lista de venda.", null);
        }
    }


    @FXML
    void acaoPesquisaProdutoVenda(ActionEvent event){
        String pesquisa = pesquisaSQLProduto.getText();
        ProdutoDAO produtoDAO = new ProdutoDAO();

        ObservableList<Produto> produtos = produtoDAO.pesquisarProdutosVenda(pesquisa);

        if (produtos.isEmpty()) {
            alerta.information("Resultado da Pesquisa", "Nenhum Produto Encontrado",
                    "Não foram encontrados produtos correspondentes ao termo pesquisado. Tente utilizar palavras-chave diferentes ou verifique a ortografia.", null);

        } else {
            TabelaProdutosVenda.setItems(produtos);
        }
    }

    @FXML
    void RemoverProdutoItemVenda(ActionEvent event){
        ItensVenda iv = TabelaItensVenda.getSelectionModel().getSelectedItem();
        if (iv != null) {
            ItensVendaDAO itensVendaDAO = new ItensVendaDAO();
            boolean itemRemovido = itensVendaDAO.removerItemVenda(iv.getIdItemVenda());

            if (itemRemovido) {
                ObservableList<ItensVenda> items = TabelaItensVenda.getItems();
                items.remove(iv);
                TabelaItensVenda.getSelectionModel().clearSelection();

                TabelaItensVenda.getItems().clear();
                mostrarItensDaVendaAtual(getIdVenda());
                double total = calcularPrecoTotal(getIdVenda());
                precoTotalItemVenda.setText(String.valueOf(total));

            } else {
                alerta.error("Erro ao Remover Item", "Falha ao Remover Item da Venda",
                        "Ocorreu um erro ao tentar remover o item da venda. Verifique se o item foi selecionado corretamente.", null);
            }
        } else {
            alerta.warning("Atenção", "Item Não Selecionado",
                    "Por favor, selecione um item da lista antes de tentar removê-lo.", null);
        }
    }

    @FXML
    void FinalizaCadastroVenda(ActionEvent event){
        double precoFinal = Double.parseDouble(precoTotalItemVenda.getText());
        int idVenda = getIdVenda();

        VendaDAO vendaDAO = new VendaDAO();
        boolean sucesso = vendaDAO.atualizarTotalVenda(idVenda, precoFinal);

        if (sucesso) {
            TelaCadastroListaVendas.setVisible(false);
            TabelaProdutosVenda.getItems().clear();
            TabelaItensVenda.getItems().clear();
            carregarBancoDadosVendas();

            TelaCadastroVenda.setVisible(false);
            TelaVerListaVenda.setVisible(false);
            TelaAlteracaoVenda.setVisible(false);

            if (getAtualizacao()){
                alerta.information("Atualização Venda", "Venda Atualizada Com Sucesso", null, null);
                setAtualizacao(false);
            }else {
                alerta.information("Cadastro Venda", "Venda Cadastrada Com Sucesso", null, null);
            }
        } else {
            String detalhes = """
                    
                    Possíveis causas:
                    - Problema de conexão com o banco de dados.
                    - Falha na validação dos itens da venda.
                    - Problema na comunicação com o sistema de pagamento.""";
            alerta.error("Erro ao Finalizar Venda", "Falha ao Finalizar a Venda", "Ocorreu um erro ao tentar finalizar a venda. Verifique os detalhes e tente novamente.", detalhes);
        }
        quantidadeProdutoVenda.setText(null);
    }

    public void chamarTelaCadastroListaVendas(){
        TelaCadastroListaVendas.setVisible(true);
        precoTotalItemVenda.setText("0.00");
    }

    //Metodos Tela Relatorio
    @FXML
    void acaoTelaRelatorios(ActionEvent event){
        CenaPrincipalCliente.setVisible(false);
        CenaPrincipalProduto.setVisible(false);
        CenaPrincipalVendas.setVisible(false);
        TelaCadastroListaVendas.setVisible(false);

        tabelaListaComprasVendaRelatorio.getItems().clear();

        TelaRelatorios.setVisible(true);
        RelatorioEstoque.setVisible(false);
        TelaRelatorioVenda.setVisible(false);
    }

    @FXML
    void carregarRelatorioVendaPorPeriodo(ActionEvent event) {
        String dataInicioP = String.valueOf(inicio.getValue());
        if (dataInicioP == null) {
            String detalhes = """
                    Esta informação é necessária para concluir o Relatorio corretamente.
                    
                    Dica:
                    -Lembre-se de usar o formato dd/mm/aaaa caso tenha digitado a Data""";

            alerta.warning("Atenção", "Data Inicial Não Selecionada",
                    "Por favor, selecione uma data inicial antes de prosseguir.", detalhes);
            return;
        }

        String dataFimP = String.valueOf(fim.getValue());
        if (dataFimP == null) {
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
        String dataInicioP = String.valueOf(NovoRelatorioVendasInicio.getValue());
        if (dataInicioP == null) {
            String detalhes = """
                    Esta informação é necessária para concluir o Relatorio corretamente.
                    
                    Dica:
                    -Lembre-se de usar o formato dd/mm/aaaa caso tenha digitado a Data""";

            alerta.warning("Atenção", "Data Inicial Não Selecionada",
                    "Por favor, selecione uma data inicial antes de prosseguir.", detalhes);
            return;
        }

        String dataFimP = String.valueOf(NovoRelatorioVendasFim.getValue());
        if (dataFimP == null) {
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

    public void RelatorioVendaPorPeriodo(String inicio, String fim){
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