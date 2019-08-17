package br.com.bcp;

import java.sql.Date;

public class Pedido {

    private Date data;

    private String descricao;

    private Integer id;

    private Integer idMerc;

    private Integer numero;

    private Double valor;

    public Pedido(final Date pData, final String pDescricao, final Integer pNumero, final Double pValor, final Integer pIdMerc) {
        data = pData;
        descricao = pDescricao;
        idMerc = pIdMerc;
        numero = pNumero;
        valor = pValor;
    }

    public Date getData() {
        return data;
    }

    public void setData(final Date pData) {
        data = pData;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(final String pDescricao) {
        descricao = pDescricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer pId) {
        id = pId;
    }

    public Integer getIdMerc() {
        return idMerc;
    }

    public void setIdMerc(final Integer pIdMerc) {
        idMerc = pIdMerc;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(final Integer pNumero) {
        numero = pNumero;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(final Double pValor) {
        valor = pValor;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "data=" + data +
                ", descricao='" + descricao + '\'' +
                ", id=" + id +
                ", idMerc=" + idMerc +
                ", numero=" + numero +
                ", valor=" + valor +
                '}';
    }
}