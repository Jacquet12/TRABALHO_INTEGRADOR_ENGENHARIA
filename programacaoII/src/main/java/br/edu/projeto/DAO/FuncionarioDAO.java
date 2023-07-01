package br.edu.projeto.DAO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.sql.DataSource;
import br.edu.projeto.util.DbUtil;
import br.edu.projeto.model.Funcionario;



@Stateful
public class Funcionario implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private DataSource ds;

		public List<Funcionario> listAll() {
			List<Funcionario> funcionario = new ArrayList<Funcionario>();
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				con = this.ds.getConnection();
				ps = con.prepareStatement("SELECT cpf, nome, sobre_nome, email, senha, confirmacao_senha, tipo_funcionario FROM funcionario");
				rs = ps.executeQuery();
				while (rs.next()) {
					Funcionario f = new Funcionario();
					f.setCpf(rs.getString("cpf"));
					f.setNome(rs.getString("nome"));
                    f.setSobre_nome(rs.getString("sobre_nome"));
                    f.setEmail(rs.getString("email"));
                    f.setSenha(rs.getString("senha"));
                    f.setConfirmacao_senha(rs.getString("confirmacao_senha"));
                    f.setTipo_foncionario(rs.getString("tipo_funcionario"));
					funcionario.add(f);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DbUtil.closeResultSet(rs);
				DbUtil.closePreparedStatement(ps);
				DbUtil.closeConnection(con);
			}
			return funcionario;
	}
       
	public Boolean insert(Funcionario f) {
    Boolean resultado = false;
    Connection con = null;
    PreparedStatement ps = null;
    try {
        con = this.ds.getConnection();
        try {
            ps = con.prepareStatement("INSERT INTO funcionario (cpf, nome, sobre_nome, email, senha, confirmacao_senha, tipo_funcionario) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, f.getCpf());
            ps.setString(2, f.getNome());
            ps.setString(3, f.getSobre_nome());
            ps.setString(4, f.getEmail());
            ps.setString(5, f.getSenha());
            ps.setString(6, f.getConfirmacao_senha());
            ps.setString(7, f.getTipo_foncionario());
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


    
    public Boolean update(Cliente c) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("UPDATE cliente SET cliente_nome_social=? , altura_cliente=? ,massa_cliente=?,genero_cliente=?,idade_cliente=?,email_cliente=?,telefone_cliente=?,endereco_cliente=? WHERE cpf_cliente = ?");
				
                ps.setString(1, c.getNomeSocial());
				ps.setDouble(2, c.getAltura());
				ps.setInt(3, c.getMassa());
				ps.setString(4, c.getGenero());
				ps.setInt(5, c.getIdade());
				ps.setString(6, c.getEmail());
				ps.setString(7, c.getTelefone());
				ps.setString(8, c.getEndereco()); 
				ps.setString(9, c.getCpf());           
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
    
    public Boolean delete(Cliente c) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("DELETE FROM cliente WHERE cpf_cliente = ?");
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

	

	public List<Cliente> ClienteFiltrado(String nomeFiltrado) {
		List<Cliente> clientesFiltrados = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = this.ds.getConnection();
			ps = con.prepareStatement("SELECT c.cliente_nome, c.cliente_nome_social, c.cpf_cliente, c.altura_cliente, c.massa_cliente, c.genero_cliente, c.idade_cliente, c.email_cliente, c.telefone_cliente, c.endereco_cliente, n.tipo_nacionalidade FROM cliente c JOIN nacionalidades n ON c.tipo_nacionalidade_id = n.id WHERE cliente_nome LIKE ?");
			ps.setString(1, "%" + nomeFiltrado + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				Cliente c = new Cliente();
				c.setNome(rs.getString("cliente_nome"));
				c.setNomeSocial(rs.getString("cliente_nome_social"));
				c.setCpf(rs.getString("cpf_cliente"));
				c.setAltura(rs.getDouble("altura_cliente"));
				c.setMassa(rs.getInt("massa_cliente"));
				c.setGenero(rs.getString("genero_cliente"));
				c.setIdade(rs.getInt("idade_cliente"));
				c.setEmail(rs.getString("email_cliente"));
				c.setTelefone(rs.getString("telefone_cliente"));
				c.setEndereco(rs.getString("endereco_cliente"));
				c.setTipo_nacionalidade(rs.getString("tipo_nacionalidade"));
				if (verificarNomeFiltrado(c.getNome(), nomeFiltrado)) {
					clientesFiltrados.add(c);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return clientesFiltrados;
	}

	private boolean verificarNomeFiltrado(String nomeCliente, String nomeFiltrado) {
		if (nomeCliente.length() >= 2) {
			for (int i = 0; i <= nomeCliente.length() - 2; i++) {
				if (nomeCliente.substring(i, i + 2).equalsIgnoreCase(nomeFiltrado.substring(0, 2))) {
					return true;
				}
			}
		}
		return false;
	}


	public List<Cliente> ClienteFiltradoPorgenero(String generoFiltrado) {
		List<Cliente> clientesFiltradoPorgenero = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = this.ds.getConnection();
			if ("Homem".equals(generoFiltrado) || "Mulher".equals(generoFiltrado) || "Outro".equals(generoFiltrado)) {
				ps = con.prepareStatement("SELECT c.cliente_nome, c.cliente_nome_social, c.cpf_cliente, c.altura_cliente, c.massa_cliente, c.genero_cliente, c.idade_cliente, c.email_cliente, c.telefone_cliente, c.endereco_cliente, n.tipo_nacionalidade FROM cliente c JOIN nacionalidades n ON c.tipo_nacionalidade_id = n.id WHERE genero_cliente LIKE ?");
				ps.setString(1, "%" + generoFiltrado + "%");
				rs = ps.executeQuery();
				while (rs.next()) {
					Cliente c = new Cliente();
					c.setNome(rs.getString("cliente_nome"));
					c.setNomeSocial(rs.getString("cliente_nome_social"));
					c.setCpf(rs.getString("cpf_cliente"));
					c.setAltura(rs.getDouble("altura_cliente"));
					c.setMassa(rs.getInt("massa_cliente"));
					c.setGenero(rs.getString("genero_cliente"));
					c.setIdade(rs.getInt("idade_cliente"));
					c.setEmail(rs.getString("email_cliente"));
					c.setTelefone(rs.getString("telefone_cliente"));
					c.setEndereco(rs.getString("endereco_cliente"));
					c.setTipo_nacionalidade(rs.getString("tipo_nacionalidade"));
					clientesFiltradoPorgenero.add(c);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return clientesFiltradoPorgenero;
	}
	
}




