package com.emersonoliveira.pagamento.service;

import com.emersonoliveira.pagamento.data.vo.VendaVO;
import com.emersonoliveira.pagamento.entity.ProdutoVenda;
import com.emersonoliveira.pagamento.entity.Venda;
import com.emersonoliveira.pagamento.exception.ResourceNotFoundException;
import com.emersonoliveira.pagamento.repository.ProdutoVendaRepository;
import com.emersonoliveira.pagamento.repository.VendaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VendaService {

    public static final String ERROR_NO_RECORD_FOUND_FOR_THIS_ID = "No record found for this id.";

    private final VendaRepository repository;

    private final ProdutoVendaRepository produtoVendaRepository;

    @Autowired
    public VendaService(VendaRepository repository, ProdutoVendaRepository produtoVendaRepository) {
        this.repository = repository;
        this.produtoVendaRepository = produtoVendaRepository;
    }

    public VendaVO create(VendaVO vo) {
        final Venda venda = repository.save(Venda.create(vo));

        List<ProdutoVenda> produtosSalvos = new ArrayList<>();
        vo.getProdutos().forEach(produto -> {
            ProdutoVenda produtoVenda = ProdutoVenda.create(produto);
            produtoVenda.setVenda(venda);
            produtosSalvos.add(produtoVendaRepository.save(produtoVenda));
        });
        venda.setProdutos(produtosSalvos);

        return VendaVO.create(venda);
    }

    public Page<VendaVO> findAll(Pageable pageable) {
        var page = repository.findAll(pageable);
        return page.map(this::convertToProdutoVO);
    }

    private VendaVO convertToProdutoVO(Venda Venda) {
        return VendaVO.create(Venda);
    }

    public VendaVO findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ERROR_NO_RECORD_FOUND_FOR_THIS_ID));
        return VendaVO.create(entity);
    }

    public VendaVO update(VendaVO vo) {
        final Optional<Venda> entity = repository.findById(vo.getId());
        if (entity.isEmpty()) {
            new ResourceNotFoundException(ERROR_NO_RECORD_FOUND_FOR_THIS_ID);
        }
        return create(vo);
    }

    public void delete(Long id) {
        var Venda = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ERROR_NO_RECORD_FOUND_FOR_THIS_ID));
        repository.delete(Venda);
    }
}
