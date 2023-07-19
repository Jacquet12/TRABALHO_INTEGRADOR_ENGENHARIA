package br.edu.projeto.dao;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import br.edu.projeto.model.NotaFiscal;

import br.edu.projeto.model.Fornecedor;

import br.edu.projeto.util.DbUtil;

public class NotafiscalDAO implements  Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private DataSource ds;

     public List<NotaFiscal> listAll() {
        List<NotaFiscal> notaFiscals = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            ps = con.prepareStatement("SELECT cod_notafiscal, produto_cod_produto, cliente_cpf, data,  funcionario_cpf, quantidade_comprada,preco_total FROM nota_fiscal");
            rs = ps.executeQuery();
            while (rs.next()) {
                NotaFiscal n = new NotaFiscal();
                n.setCodNotaFiscal(rs.getString("cod_notafiscal"));
                n.setProdutoCodProduto(rs.getString("produto_cod_produto"));
                n.setClienteCpf(rs.getString("cliente_cpf"));
                LocalDate dataHoraCompra = rs.getDate("data").toLocalDate();
                n.setDataHoraCompra(dataHoraCompra);
                n.setFuncionarioCpf(rs.getString("funcionario_cpf"));
				n.setQuantidadeComprada(rs.getInt("quantidade_comprada"));
                n.setPrecoTotal(rs.getBigDecimal("preco_total"));

                notaFiscals.add(n);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closePreparedStatement(ps);
            DbUtil.closeConnection(con);
        }
        return notaFiscals;
    }

    public Boolean insert(NotaFiscal n) {
		Boolean resultado = false;
		Connection con = null;
		PreparedStatement ps = null;
	
		try {
			con = this.ds.getConnection();
			try {
				ps = con.prepareStatement("INSERT INTO nota_fiscal (cod_notafiscal, produto_cod_produto, cliente_cpf, data,  funcionario_cpf, quantidade_comprada,preco_total ) VALUES (?, ?, ?, ?, ?, ?, ?)");
				ps.setString(1, n.getCodNotaFiscal());
				ps.setString(2, n.getProdutoCodProduto());
				ps.setString(3, n.getClienteCpf());
				ps.setDate(4, java.sql.Date.valueOf(n.getDataHoraCompra()));
				ps.setString(5, n.getFuncionarioCpf());
                ps.setInt(6, n.getQuantidadeComprada());
                ps.setBigDecimal(7,n.getPrecoTotal());
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

    public boolean delete(NotaFiscal n) {
        Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("DELETE FROM nota_fiscal WHERE cod_notafiscal = ?");
				ps.setString(1, n.getCodNotaFiscal());
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


	public boolean deleteByProductCode(String codProduto) {
        Boolean resultado = false;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = this.ds.getConnection();
            try {
                ps = con.prepareStatement("DELETE FROM cupao_fiscal WHERE codp_produto = ?");
                ps.setString(1, codProduto);
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

