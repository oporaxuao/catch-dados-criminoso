package br.com.fiap;

import br.com.fiap.Model.Criminoso;
import br.com.fiap.Service.CriminosoService;
import br.com.fiap.jdbc.teste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CriminosoService criminosoService = new CriminosoService();

        List<Criminoso> criminosofbi = criminosoService.getFBI();
        criminosofbi.forEach(criminoso -> inserirDados(criminoso));
        List<Criminoso> criminosointerpol = criminosoService.getInterpol();
        criminosointerpol.forEach(criminoso -> inserirDados(criminoso));
        String sql = "create table criminosos (nome varchar(255), dataNascimento varchar(255), crimes varchar2(4000), nacionalidade varchar(255), foto varchar(255))";

    }
    public static void inserirDados(Criminoso criminoso) {
        String sql = "INSERT INTO criminosos (nome, dataNascimento, crimes, nacionalidade, foto) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = teste.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, criminoso.getNome());
            pstmt.setString(2, criminoso.getDataNascimento());
            pstmt.setString(3, criminoso.getCrimes());
            pstmt.setString(4, criminoso.getNacionalidade());
            pstmt.setString(5, criminoso.getFoto());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


