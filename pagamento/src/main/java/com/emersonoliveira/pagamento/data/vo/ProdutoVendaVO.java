package com.emersonoliveira.pagamento.data.vo;

import com.emersonoliveira.pagamento.entity.ProdutoVenda;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({ "id", "idProduto", "quantidade" })
public class ProdutoVendaVO extends RepresentationModel<ProdutoVendaVO> implements Serializable {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "idProduto")
    private Long idProduto;

    @JsonProperty(value = "quantidade")
    private Integer quantidade;

    public static ProdutoVendaVO create(ProdutoVenda produtoVenda) {
        return new ModelMapper().map(produtoVenda, ProdutoVendaVO.class);
    }
}
