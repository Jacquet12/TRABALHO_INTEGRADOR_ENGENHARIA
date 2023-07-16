package br.edu.projeto.dao;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import br.edu.projeto.model.Cliente;
import br.edu.projeto.model.Produto;

import br.edu.projeto.util.DbUtil;

public class ProdutoDAO implements Serializable{
    private static final long serialVersionUID = 1L;

    @Inject
    private DataSource ds;

    public List<Produto> listAll() {
        List<Produto> produtos = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            ps = con.prepareStatement("SELECT cod_produto, nome, categoria, preco,  descricao, quantidade_estoque,fornecedor_cnpj, funcionario_cpf FROM produto");
            rs = ps.executeQuery();
            while (rs.next()) {
                Produto p = new Produto();
                p.setCodProduto(rs.getString("cod_produto"));
                p.setCategoria(rs.getString("categoria"));
                p.setPreco(rs.getDouble("preco"));
                p.setDescricao(rs.getString("descricao"));
                p.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                p.setFornecedorCnpj(rs.getString("fornecedor_cnpj"));
                p.setFuncionarioCpf(rs.getString("funcionario_cpf"));

                produtos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closePreparedStatement(ps);
            DbUtil.closeConnection(con);
        }
        return produtos;
    }

    public Boolean insert(Produto p) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("INSERT INTO produto (cod_produto, nome, categoria, preco,  descricao, quantidade_estoque,fornecedor_cnpj, funcionario_cpf ) VALUES (?,?,?,?,?,?,?,?)");
				ps.setString(1, p.getCodProduto());
				ps.setString(2, p.getNome());
                ps.setString(3, p.getCategoria());
                ps.setDouble(4, p.getPreco());
                ps.setString(5, p.getDescricao());
                ps.setInt(6, p.getQuantidadeEstoque());
                ps.setString(7, p.getFornecedorCnpj());
                ps.setString(8, p.getFuncionarioCpf());
				ps.execute();
				resultado = true;
			} catch (SQLException e) {e.printStackTrace();}
    	} catch (SQLException e) {e.printStackTrace();
    	} finally {
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
    	return resultado;
    }

    public boolean delete(Produto p) {
        Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("DELETE FROM produto WHERE cod_produto = ?");
				ps.setString(1, p.getCodProduto());
				ps.execute();
				resultado = true;
			} catch (SQLException e) {e.printStackTrace();}
    	} catch (SQLException e) {e.printStackTrace();
    	} finally {
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
    	return resultado;
    }

    public boolean update(Produto p) {
        Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("UPDATE produto SET nome =?, categoria = ?, preco =?,  descricao =?, quantidade_estoque = ?,fornecedor_cnpj = ?, funcionario_cpf = ? WHERE cod_produto = ?");
				ps.setString(1, p.getNome());
				ps.setString(2, p.getCategoria());
				ps.setDouble(3, p.getPreco());
				ps.setString(4, p.getDescricao());
				ps.setInt(5, p.getQuantidadeEstoque());
                ps.setString(6, p.getFornecedorCnpj());
				ps.setString(7, p.getFuncionarioCpf());
                ps.setString(8, p.getCodProduto());
				ps.execute();	
				resultado = true;
			} catch (SQLException e) {e.printStackTrace();}
    	} catch (SQLException e) {e.printStackTrace();
    	} finally {
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
    	return resultado;
    }

}
