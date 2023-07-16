package br.edu.projeto.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import br.edu.projeto.model.Cliente;
import br.edu.projeto.util.DbUtil;

public class ClienteDAO implements Serializable {
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
                c.setSobrenome(rs.getString("sobrenome"));

                // Conversão da data para java.time.LocalDate
                LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
                c.setData_nascimento(dataNascimento);

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
		// ... (código anterior)
	
		try {
			con = this.ds.getConnection();
			try {
				ps = con.prepareStatement("INSERT INTO cliente (cpf, nome, sobrenome, data_nascimento, email,funcionario_cpf) VALUES (?,?,?,?,?,?)");
				ps.setString(1, c.getCpf());
				ps.setString(2, c.getNome());
				ps.setString(3, c.getSobrenome());
	
				// Conversão da data para java.sql.Date
				java.sql.Date dataNascimentoSql = java.sql.Date.valueOf(c.getData_nascimento());
				ps.setDate(4, dataNascimentoSql);
	
				ps.setString(5, c.getEmail());
				ps.setString(6, c.getFuncionario_cpf());
				ps.execute();
				resultado = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                ps = con.prepareStatement("UPDATE cliente SET cpf = ?, nome = ?, sobrenome = ?, data_nascimento = ?, email = ?, funcionario_cpf = ? WHERE cpf = ?");
                ps.setString(1, c.getCpf());
                ps.setString(2, c.getNome());
                ps.setString(3, c.getSobrenome());

                // Conversão da data para String no formato "dd/MM/yyyy"
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String dataNascimentoStr = sdf.format(c.getData_nascimento());
                ps.setString(4, dataNascimentoStr);

                ps.setString(5, c.getEmail());
                ps.setString(6, c.getFuncionario_cpf());
                ps.setString(7, c.getCpf()); // O campo CPF está sendo usado no WHERE
                ps.execute();
                resultado = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.closePreparedStatement(ps);
            DbUtil.closeConnection(con);
        }
        return resultado;
    }
}
