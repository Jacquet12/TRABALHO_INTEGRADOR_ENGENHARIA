
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

import br.edu.projeto.model.Fornecedor;

import br.edu.projeto.util.DbUtil;

public class FornecedorDAO implements  Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private DataSource ds;

    public List<Fornecedor> listAll() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            ps = con.prepareStatement("SELECT cnpj, nome_fornecedor, tipo_de_fornecedor, contato, pais, tipo_de_produto FROM fornecedor");
            rs = ps.executeQuery();
            while (rs.next()) {
                Fornecedor f = new Fornecedor();
                f.setCnpj(rs.getString("cnpj"));
                f.setNomeFornecedor(rs.getString("nome_fornecedor"));
                f.setTipoDeFornecedor(rs.getString("tipo_de_fornecedor"));
                f.setContato(rs.getString("contato"));
                f.setPais(rs.getString("pais"));
				f.setTipoDeProduto(rs.getString("tipo_de_produto"));
                fornecedores.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closePreparedStatement(ps);
            DbUtil.closeConnection(con);
        }
        return fornecedores;
    }

    public Boolean insert(Fornecedor f) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("INSERT INTO fornecedor (cnpj, nome_fornecedor, contato,pais, funcionario_cpf, tipo_de_fornecedor,tipo_de_produto,tipo_funcionario) VALUES (?,?,?,?,?,?,?,?)");
				ps.setString(1, f.getCnpj());
				ps.setString(2, f.getNomeFornecedor());
                ps.setString(3, f.getContato());
                ps.setString(4, f.getPais());
                ps.setString(5, f.getFuncionarioCpf());
                ps.setString(6, f.getTipoDeFornecedor());
				ps.setString(7, f.getTipoDeProduto());
				ps.setString(8, f.getTipoFuncionario());
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

    public boolean delete(Fornecedor f) {
        Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("DELETE FROM fornecedor WHERE cnpj = ?");
				ps.setString(1, f.getCnpj());
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

    public boolean update(Fornecedor f) {
        Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("UPDATE fornecedor SET nome_fornecedor = ?, tipo_de_fornecedor = ?, contato =?, pais =?, funcionario_cpf=?, tipo_funcionario =?, tipo_de_produto=? WHERE cnpj = ?");
				ps.setString(1, f.getNomeFornecedor());
				ps.setString(2, f.getTipoDeFornecedor());
				ps.setString(3, f.getContato());
				ps.setString(4, f.getPais());
				ps.setString(5, f.getFuncionarioCpf());
				ps.setString(6, f.getCnpj());
				ps.setString(7, f.getTipoDeProduto());
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

    public Fornecedor buscarPorCNPJ(String cnpjFornecedor) {
        Fornecedor fornecedor = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = ds.getConnection();
            ps = con.prepareStatement("SELECT * FROM fornecedor WHERE cnpj = ?");
            ps.setString(1, cnpjFornecedor);
            rs = ps.executeQuery();

            if (rs.next()) {
                fornecedor = new Fornecedor();
                fornecedor.setCnpj(rs.getString("cnpj"));
                fornecedor.setNomeFornecedor(rs.getString("nome_fornecedor"));
                fornecedor.setTipoDeFornecedor(rs.getString("tipo_de_fornecedor"));
                fornecedor.setContato(rs.getString("contato"));
                fornecedor.setPais(rs.getString("pais"));
                fornecedor.setTipoDeProduto(rs.getString("tipo_de_produto"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closePreparedStatement(ps);
            DbUtil.closeConnection(con);
        }

        return fornecedor;
    }
}
