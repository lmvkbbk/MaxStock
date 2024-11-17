# MaxStock - Sistema de Vendas e Estoque

**MaxStock** é um sistema desenvolvido durante um projeto acadêmico, com o objetivo de gerenciar vendas e estoques de forma simples e eficiente. O sistema foi projetado para pequenas empresas, oferecendo funcionalidades básicas de controle e visualização de dados relacionados a produtos, clientes e vendas.

## 📋 Funcionalidades Principais
- **Gerenciamento de Produtos**: Cadastro, atualização e remoção de produtos com informações como nome, categoria, quantidade e preço.
- **Controle de Vendas**: Registro de vendas, cálculo de totais e histórico completo das transações.
- **Relatórios**: Geração de relatórios por período, com detalhes das vendas realizadas.
- **Autenticação**: Sistema de login para diferenciar usuários (administradores e funcionários).
- **Interface Gráfica (JavaFX)**: Interface amigável e intuitiva para interação com o sistema.
- **Banco de Dados (SQLite)**: Armazenamento de dados persistente para produtos, clientes e vendas.

## 🛠️ Tecnologias Utilizadas
- **Linguagem de Programação**: Java (versão 23.0.1)
- **Interface Gráfica**: JavaFX
- **Banco de Dados**: SQLite, utilizando JDBC para a conexão e manipulação de dados.
- **Bibliotecas**: JDBC para integração com o banco de dados SQLite.

## 🚀 Como Executar 
1. Clone o repositório:
    ```bash
        git clone https://github.com/lmvkbbk/MaxStock.git
2. Certifique-se de ter o JavaFX SDK instalado:

3. O banco de dados SQLite já está integrado no projeto. Não é necessário configurar um servidor de banco de dados externo:

4. Copie o caminho do jar 

4. Compile e execute o projeto:
    ```bash
        java -jar --module-path "caminho-do-javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml "caminho-para-jar/MaxStock.jar

## 🎓 Sobre o Projeto
- **Programação orientada a objetos**
- **Integração com banco de dados SQLite**

## 📦 Instalação
O sistema pode ser baixado como um executável para Windows, gerado com a ferramenta jpackage.
