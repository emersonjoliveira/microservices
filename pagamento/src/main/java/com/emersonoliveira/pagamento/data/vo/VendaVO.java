package com.emersonoliveira.pagamento.data.vo;

import com.emersonoliveira.pagamento.entity.ProdutoVenda;
import com.emersonoliveira.pagamento.entity.Venda;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@JsonPropertyOrder({ "id", "data", "produtos", "valorTotal" })
public class VendaVO extends RepresentationModel<VendaVO> implements Serializable {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "data")
    private Date data;

    @JsonProperty(value = "produtos")
    private List<ProdutoVendaVO> produtos;

    @JsonProperty(value = "valorTotal")
    private Double valorTotal;

    public static VendaVO create(Venda venda) {
        return new ModelMapper().map(venda, VendaVO.class);
    }
}
