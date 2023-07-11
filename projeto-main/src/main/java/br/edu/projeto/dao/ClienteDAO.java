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

import br.edu.projeto.util.DbUtil;

public class ClienteDAO implements Serializable{
    private static final long serialVersionUID = 1L;

    @Inject
    private DataSource ds;

    public List<Cliente> listAll() {
        List<Cliente> clientes = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            ps = con.prepareStatement("SELECT cpf, nome, sobrenome, data_nascimento, email FROM cliente");
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setCpf(rs.getString("cpf"));
                c.setNome(rs.getString("nome"));
                c.setData_nascimento(rs.getString("data_nascimento"));
                c.setSobrenome(rs.getString("sobrenome"));
                c.setEmail(rs.getString("email"));

                clientes.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closePreparedStatement(ps);
            DbUtil.closeConnection(con);
        }
        return clientes;
    }

    public Boolean insert(Cliente c) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("INSERT INTO cliente (cpf, nome, sobrenome, data_nascimento, email) VALUES (?,?,?,?,?)");
				ps.setString(1, c.getCpf());
				ps.setString(2, c.getNome());
                ps.setString(3, c.getSobrenome());
                ps.setString(4, c.getData_nascimento());
                ps.setString(5, c.getEmail());
                ps.setString(6, c.getFuncionario_cpf());
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

    public boolean delete(Cliente c) {
        Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("DELETE FROM cliente WHERE cpf = ?");
				ps.setString(1, c.getCpf());
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

    public boolean update(Cliente c) {
        Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("UPDATE cliente SET cpf = ?, nome = ?, sobrenome =?, data_nascimento =?, email=?, funcionario_cpf=? WHERE cpf = ?");
				ps.setString(1, c.getCpf());
				ps.setString(2, c.getNome());
				ps.setString(3, c.getNome());
				ps.setString(4, c.getData_nascimento());
				ps.setString(5, c.getEmail());
				ps.setString(6, c.getFuncionario_cpf());
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
