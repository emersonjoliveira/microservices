package com.emersonoliveira.crud.entity;

import com.emersonoliveira.crud.data.vo.ProdutoVO;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "produto")
@Entity
public class Produto  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 10)
    private  Integer estoque;

    @Column(nullable = false, length = 10)
    private Double preco;

    public static Produto create(ProdutoVO vo) {
        return new ModelMapper().map(vo, Produto.class);
    }
}
