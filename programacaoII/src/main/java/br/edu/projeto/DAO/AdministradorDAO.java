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
import br.edu.projeto.modelo.Administrador;

@Stateful
public class AdministradorDAO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private DataSource ds;

    public List<Aministrador> listAll() {
        List <Administrador> administrador = new ArrayList<Administrador>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.ds.getConnection();
            ps = con.prepareStatement("SELECT nome, sobre_nome, cpf,email from administrador");
            rs = ps.executeQuery();
            while (rs.next()) {
                Administrador a = new Administrador();
                a.setNome(rs.getString(nome));
                a.setSobre_nome(rs.getString(sobre_nome));
                a.setCpf(rs.getString(cpf));
                a.setCpf(rs.getString(email));
                c.setAltura(rs.getDouble("altura_cliente"));
                administrador.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closePreparedStatement(ps);
            DbUtil.closeConnection(con);
        }
        return administrador;
    }

    public Boolean insert(Administrador a) {
        Boolean resultado = false;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = this.ds.getConnection();
            try {
                ps = con.prepareStatement("INSERT INTO administrador (nome, sobre_nome, cpf, email, senha) VALUES ( ?, ?, ?, ?, ?)");
                ps.setString(1, a.getNome());
                ps.setString(2, a.getSobre_nome());
                ps.setString(3, a.getCpf());
                ps.setString(4, a.getEmail());
                ps.setString(5, a.getSenha());
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
    
    public boolean update(Administrador a){
        Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("UPDATE admnistrador SET nome=? , sobre_nome=? ,email=?,senha=? WHERE cpf = ?");
				
                ps.setString(1, a.getNome());
				ps.setDouble(2, a.getSobre_nome());
				ps.setInt(3, a.getEmail());
				ps.setString(4, a.getSenha());
				ps.setString(5, a.getCpf());           
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

    public Boolean delete(Administrador a) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("DELETE FROM administrador WHERE cpf = ?");
				ps.setString(1, a.getCpf());
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
