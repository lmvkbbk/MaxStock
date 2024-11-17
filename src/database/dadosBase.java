package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class dadosBase {

    public static void InsertingDataIntoTables() {
        try (Connection conexao = DriverManager.getConnection("jdbc:sqlite:Banco.db")) {
            Statement stmt = conexao.createStatement();

            if (isTableEmpty(stmt, "Produto")) {
                String sqlInsertProdutos = "INSERT INTO Produto (nome, categoria, quantidade, custo, valor) VALUES " +
                        "('Arroz 5kg', 'Grãos', 50, 15.00, 20.00), " +
                        "('Feijão 1kg', 'Grãos', 75, 4.00, 8.00), " +
                        "('Açúcar 1kg', 'Adoçantes', 100, 2.50, 5.00), " +
                        "('Sal 1kg', 'Condimentos', 60, 1.20, 2.50), " +
                        "('Óleo 900ml', 'Óleos', 45, 4.50, 8.00), " +
                        "('Macarrão 500g', 'Massas', 90, 2.00, 4.00), " +
                        "('Leite 1L', 'Laticínios', 80, 3.50, 6.00), " +
                        "('Farinha de Trigo 1kg', 'Farinhas', 40, 2.80, 5.50), " +
                        "('Café 500g', 'Bebidas', 30, 7.50, 10.00), " +
                        "('Refrigerante 2L', 'Bebidas', 50, 4.00, 7.00), " +
                        "('Detergente 500ml', 'Limpeza', 60, 1.00, 2.00), " +
                        "('Sabão em Pó 1kg', 'Limpeza', 35, 6.00, 10.00), " +
                        "('Papel Higiênico 4un', 'Higiene', 80, 3.00, 5.50), " +
                        "('Creme Dental', 'Higiene', 70, 2.50, 4.50), " +
                        "('Shampoo 200ml', 'Higiene', 40, 4.00, 8.50), " +
                        "('Condicionador 200ml', 'Higiene', 30, 4.50, 9.00), " +
                        "('Sabonete 90g', 'Higiene', 100, 1.00, 2.00), " +
                        "('Carne Bovina 1kg', 'Carnes', 25, 20.00, 35.00), " +
                        "('Frango 1kg', 'Carnes', 50, 10.00, 20.00), " +
                        "('Peito de Frango 1kg', 'Carnes', 45, 12.00, 22.00), " +
                        "('Maçã 1kg', 'Frutas', 80, 3.50, 5.00), " +
                        "('Banana 1kg', 'Frutas', 70, 2.00, 3.50), " +
                        "('Laranja 1kg', 'Frutas', 60, 2.00, 4.00), " +
                        "('Batata 1kg', 'Hortifruti', 90, 1.50, 3.00), " +
                        "('Cebola 1kg', 'Hortifruti', 80, 1.80, 3.50), " +
                        "('Alho 200g', 'Hortifruti', 50, 5.00, 9.00), " +
                        "('Tomate 1kg', 'Hortifruti', 75, 2.50, 5.00), " +
                        "('Alface', 'Hortifruti', 100, 1.00, 2.00), " +
                        "('Biscoito 200g', 'Lanches', 85, 1.50, 3.00), " +
                        "('Chocolate 100g', 'Lanches', 40, 3.50, 6.00), " +
                        "('Iogurte 200ml', 'Laticínios', 65, 2.00, 4.00), " +
                        "('Queijo 500g', 'Laticínios', 30, 10.00, 18.00), " +
                        "('Presunto 200g', 'Frios', 50, 4.50, 8.00), " +
                        "('Margarina 250g', 'Laticínios', 75, 2.00, 4.00), " +
                        "('Molho de Tomate 340g', 'Molhos', 90, 1.80, 3.50), " +
                        "('Ketchup 350g', 'Condimentos', 60, 2.50, 4.50), " +
                        "('Mostarda 350g', 'Condimentos', 55, 2.00, 3.50), " +
                        "('Pão de Forma 500g', 'Padaria', 85, 3.00, 5.50), " +
                        "('Bolacha 400g', 'Lanches', 100, 2.00, 3.50), " +
                        "('Farinha Lactea', 'Alimento Infantil', 100, 5.00, 10.00);";
                stmt.execute(sqlInsertProdutos);
                System.out.println("Registros inseridos na tabela Produto.");
            }

            if (isTableEmpty(stmt, "Cliente")) {
                String sqlInsertClientes = "INSERT INTO Cliente (nome, cpf, data_nascimento, email, telefone) VALUES " +
                        "('João Silva', '111.222.333-44', '1985-01-10', 'joao.silva@email.com', '(11) 91234-5678'), " +
                        "('Maria Souza', '222.333.444-55', '1990-02-15', 'maria.souza@email.com', '(21) 92345-6789'), " +
                        "('Carlos Pereira', '333.444.555-66', '1975-03-20', 'carlos.pereira@email.com', '(31) 93456-7890'), " +
                        "('Ana Oliveira', '444.555.666-77', '1982-04-25', 'ana.oliveira@email.com', '(41) 94567-8901'), " +
                        "('Roberto Costa', '555.666.777-88', '1993-05-30', 'roberto.costa@email.com', '(51) 95678-9012'), " +
                        "('Beatriz Lima', '666.777.888-99', '1988-06-05', 'beatriz.lima@email.com', '(61) 96789-0123'), " +
                        "('Ricardo Almeida', '777.888.999-00', '1992-07-10', 'ricardo.almeida@email.com', '(71) 97890-1234'), " +
                        "('Fernanda Santos', '888.999.111-22', '1994-08-15', 'fernanda.santos@email.com', '(81) 98901-2345'), " +
                        "('Gustavo Fernandes', '999.111.222-33', '1991-09-20', 'gustavo.fernandes@email.com', '(91) 99012-3456'), " +
                        "('Juliana Rocha', '111.222.333-00', '1989-10-25', 'juliana.rocha@email.com', '(11) 90123-4567'), " +
                        "('Paulo Ramos', '222.333.444-11', '1987-11-30', 'paulo.ramos@email.com', '(21) 91234-5678'), " +
                        "('Carla Gomes', '333.444.555-22', '1986-12-05', 'carla.gomes@email.com', '(31) 92345-6789'), " +
                        "('Lucas Andrade', '444.555.666-33', '1995-01-10', 'lucas.andrade@email.com', '(41) 93456-7890'), " +
                        "('Isabela Teixeira', '555.666.777-44', '1983-02-15', 'isabela.teixeira@email.com', '(51) 94567-8901'), " +
                        "('Thiago Martins', '666.777.888-55', '1990-03-20', 'thiago.martins@email.com', '(61) 95678-9012'), " +
                        "('Mariana Dias', '777.888.999-66', '1981-04-25', 'mariana.dias@email.com', '(71) 96789-0123'), " +
                        "('André Barbosa', '888.999.111-77', '1992-05-30', 'andre.barbosa@email.com', '(81) 97890-1234'), " +
                        "('Larissa Sousa', '999.111.222-88', '1984-06-05', 'larissa.sousa@email.com', '(91) 98901-2345'), " +
                        "('Rafael Correia', '111.222.333-99', '1989-07-10', 'rafael.correia@email.com', '(11) 99012-3456'), " +
                        "('Gabriela Vieira', '222.333.444-00', '1993-08-15', 'gabriela.vieira@email.com', '(21) 90123-4567'), " +
                        "('Pedro Carvalho', '333.444.555-11', '1987-09-20', 'pedro.carvalho@email.com', '(31) 91234-5678'), " +
                        "('Amanda Castro', '444.555.666-22', '1990-10-25', 'amanda.castro@email.com', '(41) 92345-6789'), " +
                        "('Renato Borges', '555.666.777-33', '1985-11-30', 'renato.borges@email.com', '(51) 93456-7890'), " +
                        "('Priscila Melo', '666.777.888-44', '1983-12-05', 'priscila.melo@email.com', '(61) 94567-8901'), " +
                        "('Leandro Araújo', '777.888.999-55', '1991-01-10', 'leandro.araujo@email.com', '(71) 95678-9012'), " +
                        "('Natália Guimarães', '888.999.111-66', '1982-02-15', 'natalia.guimaraes@email.com', '(81) 96789-0123'), " +
                        "('Rogério Cunha', '999.111.222-77', '1994-03-20', 'rogerio.cunha@email.com', '(91) 97890-1234'), " +
                        "('Monique Santana', '111.222.333-88', '1989-04-25', 'monique.santana@email.com', '(11) 98901-2345'), " +
                        "('Marcelo Nogueira', '222.333.444-99', '1987-05-30', 'marcelo.nogueira@email.com', '(21) 99012-3456'), " +
                        "('Aline Cardoso', '333.444.555-00', '1995-06-05', 'aline.cardoso@email.com', '(31) 90123-4567'), " +
                        "('Daniel Antunes', '444.555.666-11', '1988-07-10', 'daniel.antunes@email.com', '(41) 91234-5678'), " +
                        "('Tatiane Silva', '555.666.777-22', '1990-08-15', 'tatiane.silva@email.com', '(51) 92345-6789'), " +
                        "('Henrique Moura', '666.777.888-33', '1986-09-20', 'henrique.moura@email.com', '(61) 93456-7890'), " +
                        "('Camila Carvalho', '777.888.999-44', '1983-10-25', 'camila.carvalho@email.com', '(71) 94567-8901'), " +
                        "('Douglas Lima', '888.999.111-55', '1991-11-30', 'douglas.lima@email.com', '(81) 95678-9012'), " +
                        "('Débora Mendes', '999.111.222-66', '1984-12-05', 'debora.mendes@email.com', '(91) 96789-0123'), " +
                        "('Fábio Freitas', '111.222.333-77', '1992-01-10', 'fabio.freitas@email.com', '(11) 97890-1234'), " +
                        "('Cíntia Amaral', '222.333.444-88', '1988-02-15', 'cintia.amaral@email.com', '(21) 98901-2345'), " +
                        "('Jonas Farias', '333.444.555-99', '1986-03-20', 'jonas.farias@email.com', '(31) 99012-3456'), " +
                        "('Vanessa Ribeiro', '444.555.666-00', '1990-04-25', 'vanessa.ribeiro@email.com', '(41) 90123-4567');";
                stmt.execute(sqlInsertClientes);
                System.out.println("Registros inseridos na tabela Cliente.");
            }

            if (isTableEmpty(stmt, "Venda")) {
                String sqlInsertVendas = "INSERT INTO Venda (dataVenda, metodoPagamento, totalVenda, clienteNome) VALUES " +
                        "('2024-01-01', 'Cartão de Crédito', 40.00, 'João Silva'), " +
                        "('2024-01-02', 'Dinheiro', 15.00, 'Maria Souza'), " +
                        "('2024-01-03', 'Pix', 57.25, 'Carlos Pereira'), " +
                        "('2024-01-04', 'Cartão de Débito', 45.50, 'Ana Oliveira'), " +
                        "('2024-01-05', 'Cartão de Crédito', 15.75, 'Roberto Costa'), " +
                        "('2024-01-06', 'Pix', 44.00, 'Beatriz Lima'), " +
                        "('2024-01-07', 'Cartão de Débito', 30.00, 'Ricardo Almeida'), " +
                        "('2024-01-08', 'Pix', 54.00, 'Fernanda Santos'), " +
                        "('2024-01-09', 'Dinheiro', 52.50, 'Gustavo Fernandes'), " +
                        "('2024-01-10', 'Cartão de Crédito', 51.25, 'Juliana Rocha'), " +
                        "('2024-01-11', 'Cartão de Débito', 73.50, 'Paulo Ramos'), " +
                        "('2024-01-12', 'Pix', 133.75, 'Carla Gomes'), " +
                        "('2024-01-13', 'Dinheiro', 69.00, 'Lucas Andrade'), " +
                        "('2024-01-14', 'Cartão de Crédito', 47.50, 'Isabela Teixeira'), " +
                        "('2024-01-15', 'Pix', 58.00, 'Thiago Martins'), " +
                        "('2024-01-16', 'Dinheiro', 60.20, 'Mariana Dias'), " +
                        "('2024-01-17', 'Cartão de Débito', 24.90, 'André Barbosa'), " +
                        "('2024-01-18', 'Cartão de Crédito', 167.50, 'Larissa Sousa'), " +
                        "('2024-01-19', 'Pix', 0.00, 'Rafael Correia'), " +
                        "('2024-01-20', 'Dinheiro', 0.00, 'Gabriela Vieira'), " +
                        "('2024-01-21', 'Cartão de Crédito', 0.00, 'Pedro Carvalho'), " +
                        "('2024-01-22', 'Pix', 0.00, 'Amanda Castro'), " +
                        "('2024-01-23', 'Pix', 0.00, 'Renato Borges'), " +
                        "('2024-01-24', 'Cartão de Débito', 0.00, 'Priscila Melo'), " +
                        "('2024-01-25', 'Cartão de Crédito', 0.00, 'Leandro Araújo'), " +
                        "('2024-01-26', 'Pix', 0.00, 'Natália Guimarães'), " +
                        "('2024-01-27', 'Dinheiro', 00.00, 'Rogério Cunha'), " +
                        "('2024-01-28', 'Cartão de Crédito', 0.00, 'Monique Santana'), " +
                        "('2024-01-29', 'Cartão de Débito', 0.00, 'Marcelo Nogueira'), " +
                        "('2024-01-30', 'Pix', 0.00, 'Aline Cardoso'), " +
                        "('2024-01-31', 'Dinheiro', 0.00, 'Daniel Antunes'), " +
                        "('2024-02-01', 'Cartão de Crédito', 177.50, 'Tatiane Silva'), " +
                        "('2024-02-02', 'Dinheiro', 17.50, 'Henrique Moura'), " +
                        "('2024-02-03', 'Pix', 16.00, 'Camila Carvalho'), " +
                        "('2024-02-04', 'Cartão de Débito', 30.00, 'Douglas Lima'), " +
                        "('2024-02-05', 'Dinheiro', 0.00, 'Débora Mendes'), " +
                        "('2024-02-06', 'Cartão de Crédito', 0.00, 'Fábio Freitas'), " +
                        "('2024-02-07', 'Pix', 22.50, 'Cíntia Amaral'), " +
                        "('2024-02-08', 'Dinheiro', 32.50, 'Jonas Farias'), " +
                        "('2024-02-09', 'Pix', 52.00, 'Vanessa Ribeiro');";
                stmt.execute(sqlInsertVendas);
                System.out.println("Registros inseridos na tabela Venda.");
            }
            if (isTableEmpty(stmt, "ItensVenda")) {
                String sqlInsertItensVenda = "INSERT INTO ItensVenda (idProduto, quantidade, precoUnitario, idVenda) VALUES " +
                        "(1, 2, 10.00, 1), " +
                        "(2, 1, 20.00, 1), " +
                        "(3, 3, 5.00, 2), " +
                        "(4, 2, 25.50, 3), " +
                        "(5, 1, 6.25, 3), " +
                        "(6, 2, 10.00, 4), " +
                        "(7, 3, 8.50, 4), " +
                        "(8, 1, 15.75, 5), " +
                        "(9, 2, 7.00, 6), " +
                        "(10, 1, 30.00, 6), " +
                        "(11, 3, 10.00, 7), " +
                        "(12, 2, 12.50, 8), " +
                        "(13, 4, 7.25, 8), " +
                        "(14, 1, 20.00, 9), " +
                        "(15, 5, 6.50, 9), " +
                        "(16, 2, 11.00, 10), " +
                        "(17, 3, 9.75, 10), " +
                        "(18, 1, 13.50, 11), " +
                        "(19, 2, 15.00, 11), " +
                        "(20, 4, 25.75, 12), " +
                        "(21, 3, 10.25, 12), " +
                        "(22, 4, 9.00, 13), " +
                        "(23, 2, 16.50, 13), " +
                        "(24, 1, 18.00, 14), " +
                        "(25, 2, 14.75, 14), " +
                        "(26, 1, 27.00, 15), " +
                        "(27, 5, 6.20, 15), " +
                        "(28, 2, 15.50, 16), " +
                        "(29, 4, 7.30, 16), " +
                        "(30, 1, 24.90, 17), " +
                        "(31, 1, 10.50, 18), " +
                        "(32, 2, 20.00, 18), " +
                        "(33, 3, 5.00, 18), " +
                        "(34, 4, 25.50, 18), " +
                        "(1,5,20.00,32), " +
                        "(2,5,8.00,32), " +
                        "(3,5,5.00,32), " +
                        "(4,5,2.50,32), " +
                        "(14,2,4.50,40), " +
                        "(15,2,8.50,40), " +
                        "(16,2,9.00,40), " +
                        "(17,4,2.00,40), " +
                        "(5,1,8.00,39), " +
                        "(1,1,20.00,39), " +
                        "(36,1,4.50,39), " +
                        "(6,1,4.00,38), " +
                        "(7,1,6.00,38), " +
                        "(8,1,5.50,38), " +
                        "(10,1,7.00,38), " +
                        "(12,1,10.00,33), " +
                        "(13,1,5.50,33)," +
                        "(11,1,2.00,33)," +
                        "(27,1,5.00,34)," +
                        "(26,1,9.00,34)," +
                        "(28,1,2.00,34)," +
                        "(30,5,6.00,35)," +
                        "(19,2,15.00,11)";
                stmt.execute(sqlInsertItensVenda);
                System.out.println("Registros inseridos na tabela ItensVenda.");
            }

            if (isTableEmpty(stmt, "Usuarios")) {
                String sqlInsertUsers = "INSERT INTO Usuarios (nome, senha, adm) VALUES " +
                        "('adm', 'maxstockadm', 1)," +
                        "('funcionario', 'maxstockfun', 0);";
                stmt.execute(sqlInsertUsers);
                System.out.println("Registros inseridos na tabela Usuario.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao inserir Banco de dados Base: "+ e.getMessage());
        }
    }

    private static boolean isTableEmpty(Statement stmt, String tableName) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName);
            rs.next();
            return rs.getInt(1) == 0;
        } catch (Exception e) {
            System.out.println("Erro na verificação da tabela "+ tableName +", Erro:"+e.getMessage());
            return true;
        }
    }
}
