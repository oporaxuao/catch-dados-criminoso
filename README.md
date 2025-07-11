# Catch de Dados de Criminosos

Este projeto tem como objetivo principal permitir a busca de dados de criminosos através de APIs externas (FBI e Interpol), processar essas informações e armazená-las de forma organizada em um banco de dados Oracle.

## 📝 Documentação Detalhada do Projeto IDwall

Este repositório contém a implementação do sistema desenvolvido para o projeto da IDwall, com foco na captura e gestão de dados de criminosos.

---

## 🎯 Objetivo do Projeto

Os principais objetivos deste projeto são:

* **Permitir a busca de dados de criminosos:** Realizar solicitações a APIs externas (FBI e Interpol) para obter informações sobre criminosos.
* **Armazenar os dados buscados:** Inserir os dados coletados no banco de dados Oracle para persistência.
* **Aplicar a lógica de negócios:** Implementar as regras de negócio na camada de aplicação para processar e gerenciar os dados.

---

## 🏗️ Arquitetura do Projeto

O projeto segue uma arquitetura modular, dividida em camadas para garantir a organização, manutenibilidade e escalabilidade do sistema. As camadas principais são:

* **Camada de Acesso à API:** Responsável por fazer as solicitações e receber as respostas das APIs.
* **Camada de Processamento:** Responsável por processar as respostas JSON recebidas e transformá-las em objetos Java.
* **Camada de Aplicação:** O ponto de entrada do programa, onde a lógica de negócios é aplicada e a inserção no banco de dados é orquestrada.
* **Camada de Inserção no Banco de Dados:** Responsável por armazenar os dados processados no banco de dados.

### Detalhes das Camadas:

#### Camada de Acesso à API (`br.com.fiap.Service.CriminosoService`)

Esta camada é encarregada de interagir diretamente com as APIs externas (FBI e Interpol).
* Realiza as solicitações às APIs para buscar os dados dos criminosos utilizando `HttpURLConnection`.
* Recebe as respostas das APIs em formato JSON.
* Lida com a paginação dos resultados para coletar todos os dados disponíveis.
* Implementa tratamento básico de erros para lidar com falhas de comunicação HTTP.

#### Camada de Processamento (`br.com.fiap.Service.CriminosoService` e `br.com.fiap.Model.Criminoso`)

Esta camada lida com a manipulação dos dados recebidos da API e sua conversão para objetos Java.
* Recebe as respostas JSON das APIs.
* Utiliza a biblioteca `org.json` para fazer o *parse* dos dados JSON.
* Transforma os dados JSON em objetos Java da classe `br.com.fiap.Model.Criminoso`, extraindo informações como nome, data de nascimento, nacionalidade, crimes e foto.
* Os objetos `Criminoso` são armazenados em uma lista para posterior inserção no banco de dados.

#### Camada de Aplicação (`br.com.fiap.Main`)

Esta é a camada central onde a inteligência do negócio reside e a orquestração do fluxo de dados acontece.
* Instancia a `CriminosoService` para buscar os dados.
* Chama os métodos `getFBI()` e `getInterpol()` para obter listas de criminosos.
* Itera sobre as listas de criminosos e chama o método `inserirDados()` para cada `Criminoso` processado.
* Contém o método `main` que inicia a execução do programa.
* Define a estrutura da tabela `criminosos` no banco de dados (embora a criação da tabela seja uma linha de comentário, o README assume que a tabela será criada).

#### Camada de Inserção no Banco de Dados (`br.com.fiap.jdbc.teste` e `br.com.fiap.Main`)

Responsável pela persistência dos dados no sistema.
* A classe `br.com.fiap.jdbc.teste` é responsável por estabelecer a conexão com o Banco de Dados Oracle FIAP, utilizando `DriverManager`. As credenciais (`RM95226`, `080401`) são hardcoded nesta classe.
* O método `inserirDados` na classe `br.com.fiap.Main` utiliza um `PreparedStatement` para inserir os objetos `Criminoso` no banco de dados.
* Os dados são estruturados e organizados de acordo com o modelo de dados definido para a tabela `criminosos`.

---

## 🌊 Fluxo de Dados

O fluxo de dados no sistema segue os seguintes passos:

1.  A aplicação Java (`Main`) inicializa o `CriminosoService`.
2.  O `CriminosoService` faz solicitações às APIs (FBI e Interpol) utilizando `HttpURLConnection`.
3.  As APIs respondem com os dados em formato JSON.
4.  O `CriminosoService` processa os dados JSON utilizando a biblioteca `org.json` e os transforma em objetos `Criminoso`.
5.  Os objetos `Criminoso` são armazenados em listas.
6.  A classe `Main` itera sobre essas listas e, para cada `Criminoso`, invoca o método `inserirDados`.
7.  O método `inserirDados` utiliza a conexão estabelecida por `br.com.fiap.jdbc.teste` para inserir os dados na tabela `criminosos` do Banco de Dados Oracle FIAP.

---

## 💾 Armazenamento dos Dados

Os dados buscados pelas APIs (FBI e Interpol) são automaticamente armazenados na tabela `criminosos` do Banco de Dados da Oracle FIAP. A inserção é gerenciada pelo método `inserirDados` na classe `Main`, que interage com a camada de conexão (`br.com.fiap.jdbc.teste`).

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem de Programação:** Java
* **APIs Consumidas:**
    * FBI (Federal Bureau of Investigation) `https://api.fbi.gov/@wanted`
    * Interpol `https://ws-public.interpol.int/notices/v1/red`
* **Manipulação JSON:** `org.json`
* **Conexão HTTP:** `java.net.HttpURLConnection`
* **Banco de Dados:** Oracle FIAP
* **Driver JDBC:** `ojdbc8.jar` (Oracle JDBC Driver)

---

## 🎬 Demonstração do Projeto

Assista aos vídeos de demonstração para entender o funcionamento completo do projeto.

<p align="center">
  <a href="https://youtu.be/ySwWF0UhNjE">
    <img src="https://img.youtube.com/vi/ySwWF0UhNjE/hqdefault.jpg" alt="Programa rodando no IntelliJ" width="80%">
  </a>
  <br>
 
</p>

<br>

<p align="center">
  <a href="https://youtu.be/Tvm5IOr6hg0">
    <img src="https://img.youtube.com/vi/Tvm5IOr6hg0/hqdefault.jpg" alt="Sendo upado e consultando no OracleDB" width="80%">
  </a>
  <br>
  
</p>


## 🚀 Como Rodar o Projeto

**Pré-requisitos:**

* **JDK (Java Development Kit)**: Versão 8 ou superior.
* **Banco de Dados Oracle FIAP**: Acesso e credenciais válidas (`RM95226`, `080401`).
* **Driver JDBC Oracle (ojdbc8.jar)**: Certifique-se de que o `ojdbc8.jar` esteja incluído no *classpath* do seu projeto (conforme indicado pelo arquivo `ojdbc8.xml`, ele deve estar na pasta `lib`).

**Passos para Execução:**

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/oporaxuao/catch-dados-criminosos.git](https://github.com/oporaxuao/catch-dados-criminosos.git)
    ```
    *(Ajustei o link de clone para o repositório correto `catch-dados-criminosos`.)*
2.  **Navegue até o diretório do projeto:**
    ```bash
    cd catch-dados-criminosos
    ```
3.  **Configuração do Projeto (Maven/Gradle/IDE):**
    * **Para projetos Maven/Gradle:** Certifique-se de que o `ojdbc8.jar` esteja configurado como uma dependência no seu `pom.xml` ou `build.gradle`, ou adicione-o manualmente ao `lib` e ao *classpath* do seu projeto se não estiver usando um gerenciador de dependências.
    * **Para IDEs (IntelliJ IDEA, Eclipse):** Importe o projeto como um projeto Java existente. Adicione o `ojdbc8.jar` (localizado na pasta `lib` do projeto clonado) como uma biblioteca externa ao módulo/projeto.

4.  **Criação da Tabela no Banco de Dados:**
    Antes de executar a aplicação, certifique-se de que a tabela `criminosos` exista no seu esquema do Banco de Dados Oracle FIAP. Você pode usar a seguinte DDL (Data Definition Language) para criar a tabela:
    ```sql
    CREATE TABLE criminosos (
        nome VARCHAR2(255),
        dataNascimento VARCHAR2(255),
        crimes VARCHAR2(4000), -- Ajustado para suportar textos mais longos
        nacionalidade VARCHAR2(255),
        foto VARCHAR2(255)
    );
    ```
    *(**Observação:** A linha de `create table` no `Main.java` está comentada ou não é executada; a tabela deve ser criada manualmente ou via script antes da execução do programa para evitar erros de SQL.)*

5.  **Compile e Execute a Aplicação:**
    * **Via IDE:** Execute a classe `br.com.fiap.Main`.
    * **Via linha de comando (após compilação):**
        * Compile os arquivos `.java`:
            ```bash
            # Exemplo usando javac, incluindo o driver JDBC no classpath
            javac -cp lib/ojdbc8.jar src/main/java/br/com/fiap/*.java src/main/java/br/com/fiap/Model/*.java src/main/java/br/com/fiap/Service/*.java src/main/java/br/com/fiap/jdbc/*.java
            ```
            *(Atenção: o comando `javac` acima é simplificado. Para projetos maiores, um gerenciador de build como Maven/Gradle é recomendado.)*
        * Execute a classe principal:
            ```bash
            # Certifique-se de que o diretório 'src/main/java' está no classpath
            java -cp .;lib/ojdbc8.jar src/main/java/br/com/fiap/Main
            # Para Linux/macOS
            # java -cp .:lib/ojdbc8.jar src/main/java/br/com/fiap/Main
            ```
            *(Se você empacotar o projeto em um JAR, o comando seria mais simples: `java -jar seu-projeto.jar`)*

---

## Contato

* João Alfredo de Sousa Siqueira
* **GitHub:** [@oporaxuao](https://github.com/oporaxuao)
* **LinkedIn:** https://www.linkedin.com/in/oporaxuao/
