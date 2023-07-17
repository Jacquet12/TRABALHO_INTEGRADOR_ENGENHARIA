package br.edu.projeto.dao;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import javax.inject.Inject;
import javax.sql.DataSource;

import br.edu.projeto.model.CupomFiscal;

import br.edu.projeto.util.DbUtil;

public class CupomFiscalDAO implements Serializable{
    private static final long serialVersionUID = 1L;

    @Inject
    private DataSource ds;

    public List<CupomFiscal> listAll() {
        List<CupomFiscal> cupomFiscals = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            ps = con.prepareStatement("SELECT id, cpf_funcionario, cnpj_fornecedor, codp_produto,  quantidade_adquirida, data_hora_compra,total_compra,forma_pagamento, observacoes FROM cupao_fiscal");
            rs = ps.executeQuery();
            while (rs.next()) {
                CupomFiscal c = new CupomFiscal();
                c.setId(rs.getInt("id"));
                c.setCpfFuncionario(rs.getString("cpf_funcionario"));
                c.setCnpjFornecedor(rs.getString("cnpj_fornecedor"));
                c.setCodProduto(rs.getString("codp_produto"));
                c.setQuantidadeAdquirida(rs.getInt("quantidade_adquirida"));

				LocalDate dataHoraCompra = rs.getDate("data_hora_compra").toLocalDate();
                c.setDataHoraCompra(dataHoraCompra);

                c.setFormaPagamento(rs.getString("forma_pagamento"));
				c.setObservacoes(rs.getString("observacoes"));

                cupomFiscals.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closePreparedStatement(ps);
            DbUtil.closeConnection(con);
        }
        return cupomFiscals;
    }

    public Boolean insert(CupomFiscal c) {
		Boolean resultado = false;
		Connection con = null;
		PreparedStatement ps = null;
	
		try {
			con = this.ds.getConnection();
			try {
				ps = con.prepareStatement("INSERT INTO cupao_fiscal (id, cpf_funcionario, cnpj_fornecedor, codp_produto, quantidade_adquirida, data_hora_compra, total_compra, forma_pagamento, observacoes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
				ps.setInt(1, c.getId());
				ps.setString(2, c.getCpfFuncionario());
				ps.setString(3, c.getCnpjFornecedor());
				ps.setString(4, c.getCodProduto());
				ps.setInt(5, c.getQuantidadeAdquirida());
	
			
				ps.setDate(6, java.sql.Date.valueOf(c.getDataHoraCompra()));
	
				ps.setDouble(7, c.getTotalCompra());
				ps.setString(8, c.getFormaPagamento());
				ps.setString(9, c.getObservacoes());
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

    // public boolean delete(Produto p) {
    //     Boolean resultado = false;
    // 	Connection con = null;
    // 	PreparedStatement ps = null;
    // 	try {
	//     	con = this.ds.getConnection();
	//     	try {				
	// 			ps = con.prepareStatement("DELETE FROM produto WHERE cod_produto = ?");
	// 			ps.setString(1, p.getCodProduto());
	// 			ps.execute();
	// 			resultado = true;
	// 		} catch (SQLException e) {e.printStackTrace();}
    // 	} catch (SQLException e) {e.printStackTrace();
    // 	} finally {
	// 		DbUtil.closePreparedStatement(ps);
	// 		DbUtil.closeConnection(con);
	// 	}
    // 	return resultado;
    // }

    // public boolean update(Produto p) {
    //     Boolean resultado = false;
    // 	Connection con = null;
    // 	PreparedStatement ps = null;
    // 	try {
	//     	con = this.ds.getConnection();
	//     	try {				
	// 			ps = con.prepareStatement("UPDATE produto SET nome =?, categoria = ?, preco =?,  descricao =?, quantidade_estoque = ?,fornecedor_cnpj = ?, funcionario_cpf = ? WHERE cod_produto = ?");
	// 			ps.setString(1, p.getNome());
	// 			ps.setString(2, p.getCategoria());
	// 			ps.setBigDecimal(3, p.getPreco());
	// 			ps.setString(4, p.getDescricao());
	// 			ps.setInt(5, p.getQuantidadeEstoque());
    //             ps.setString(6, p.getFornecedorCnpj());
	// 			ps.setString(7, p.getFuncionarioCpf());
    //             ps.setString(8, p.getCodProduto());
	// 			ps.execute();	
	// 			resultado = true;
	// 		} catch (SQLException e) {e.printStackTrace();}
    // 	} catch (SQLException e) {e.printStackTrace();
    // 	} finally {
	// 		DbUtil.closePreparedStatement(ps);
	// 		DbUtil.closeConnection(con);
	// 	}
    // 	return resultado;
    // }

}
