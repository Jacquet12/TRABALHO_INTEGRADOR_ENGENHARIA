package br.edu.projeto.dao;

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

import br.edu.projeto.model.Funcionario;
import br.edu.projeto.util.DbUtil;

//Classe DAO responsável pelas regras de negócio envolvendo operações de persistência de dados
//Indica-se a acriação de um DAO específico para cada Modelo

//Anotação EJB que indica que Bean (objeto criado para a classe) será comum para toda a aplicação
//Isso faz com que recursos computacionais sejam otimizados e garante maior segurança nas transações com o banco
@Stateful
public class FuncionarioDAO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private DataSource ds;
    
    public List<Funcionario> listAll() {
    	List<Funcionario> funcionarios = new ArrayList<Funcionario>();
    	Connection con = null;//Conexão com a base
    	PreparedStatement ps = null;//Instrução SQL
    	ResultSet rs = null;//Resposta do SGBD
    	try {
			con = this.ds.getConnection();//Pegar um conexão
			ps = con.prepareStatement("SELECT cpf, nome, sobre_nome,tipo_funcionario, email, phone_number, senha, confirmacao_senha  FROM funcionario");
			rs = ps.executeQuery();
			while (rs.next()) {//Pega próxima linha do retorno
				Funcionario f = new Funcionario();
				f.setCpf(rs.getString("cpf"));
				f.setNome(rs.getString("nome"));
                f.setSobre_nome(rs.getString("sobre_nome"));
                f.setTipo_funcionario(rs.getString("tipo_funcionario"));
                f.setEmail(rs.getString("email"));
                f.setPhone_number(rs.getString("phone_number"));
                f.setSenha(rs.getString("senha"));
                f.setConfirmacao_senha(rs.getString("confirmacao_senha"));
                
			}
		} catch (SQLException e) {e.printStackTrace();
		} finally {
			DbUtil.closeResultSet(rs);
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
        return funcionarios;
    }
    
    
       
    public Boolean insert(Funcionario f) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("INSERT INTO funcionario (cpf, nome, sobre_nome,tipo_funcionario, email, phone_number, senha, confirmacao_senha) VALUES (?,?,?,?,?,?,?,?)");
				ps.setString(1, f.getCpf());
				ps.setString(2, f.getNome());
                ps.setString(3, f.getSobre_nome());
                ps.setString(4, f.getTipo_funcionario());
                ps.setString(5, f.getEmail());
                ps.setString(6, f.getPhone_number());
                ps.setString(7, f.getSenha());
                ps.setString(8, f.getConfirmacao_senha());
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
    
    public Boolean update(Funcionario f) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("UPDATE funcionario SET nome = ?, sobre_nome = ?, tipo_funcionario =?, email =?, phone_number=?, senha =?, confirmacao_senha =? WHERE cpf = ?");
				ps.setString(1, f.getNome());
				ps.setString(2, f.getSobre_nome());
				ps.setString(3, f.getTipo_funcionario());
				ps.setString(4, f.getEmail());
				ps.setString(5, f.getPhone_number());
				ps.setString(6, f.getSenha());
				ps.setString(7, f.getConfirmacao_senha());
				ps.setString(8, f.getCpf());
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
    
    public Boolean delete(Funcionario f) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("DELETE FROM funcionario WHERE cpf = ?");
				ps.setString(1, f.getCpf());
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



	public Funcionario buscarFuncionarioPorEmailSenha(String email, String senha) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement("SELECT cpf, nome, sobre_nome, tipo_funcionario, email, phone_number, senha, confirmacao_senha FROM funcionario WHERE email = ? AND senha = ?");
			ps.setString(1, email);
			ps.setString(2, senha);
			rs = ps.executeQuery();
			if (rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setCpf(rs.getString("cpf"));
				funcionario.setNome(rs.getString("nome"));
				funcionario.setSobre_nome(rs.getString("sobre_nome"));
				funcionario.setTipo_funcionario(rs.getString("tipo_funcionario"));
				funcionario.setEmail(rs.getString("email"));
				funcionario.setPhone_number(rs.getString("phone_number"));
				funcionario.setSenha(rs.getString("senha"));
				funcionario.setConfirmacao_senha(rs.getString("confirmacao_senha"));
				return funcionario;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.closeResultSet(rs);
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
		return null;
	}



	public Funcionario buscarPorCPF(String cpfFuncionario) {
        Funcionario funcionario = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = ds.getConnection();
            ps = con.prepareStatement("SELECT * FROM funcionario WHERE cpf = ?");
            ps.setString(1, cpfFuncionario);
            rs = ps.executeQuery();

            if (rs.next()) {
                funcionario = new Funcionario();
                funcionario.setCpf(rs.getString("cpf"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setSobre_nome(rs.getString("sobre_nome"));
                funcionario.setTipo_funcionario(rs.getString("tipo_funcionario"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setPhone_number(rs.getString("phone_number"));
                funcionario.setSenha(rs.getString("senha"));
                funcionario.setConfirmacao_senha(rs.getString("confirmacao_senha"));
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
	
}
