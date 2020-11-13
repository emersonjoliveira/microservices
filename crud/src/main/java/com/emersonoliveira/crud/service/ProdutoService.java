package com.emersonoliveira.crud.service;

import com.emersonoliveira.crud.data.vo.ProdutoVO;
import com.emersonoliveira.crud.entity.Produto;
import com.emersonoliveira.crud.exception.ResourceNotFoundException;
import com.emersonoliveira.crud.message.ProdutoSendMessage;
import com.emersonoliveira.crud.repository.ProdutoRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    public static final String ERROR_NO_RECORD_FOUND_FOR_THIS_ID = "No record found for this id.";

    private final ProdutoRepository repository;
    private final ProdutoSendMessage sendMessage;

    @Autowired
    public ProdutoService(ProdutoRepository repository, ProdutoSendMessage sendMessage) {
        this.repository = repository;
        this.sendMessage = sendMessage;
    }

    public ProdutoVO create(ProdutoVO vo) {
        final ProdutoVO voSalvo = ProdutoVO.create(repository.save(Produto.create(vo)));
        sendMessage.sendMessage(voSalvo);
        return voSalvo;
    }

    public Page<ProdutoVO> findAll(Pageable pageable) {
        var page = repository.findAll(pageable);
        return page.map(this::convertToProdutoVO);
    }

    private ProdutoVO convertToProdutoVO(Produto produto) {
        return ProdutoVO.create(produto);
    }

    public ProdutoVO findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ERROR_NO_RECORD_FOUND_FOR_THIS_ID));
        return ProdutoVO.create(entity);
    }

    public ProdutoVO update(ProdutoVO vo) {
        final Optional<Produto> entity = repository.findById(vo.getId());
        if (entity.isEmpty()) {
            new ResourceNotFoundException(ERROR_NO_RECORD_FOUND_FOR_THIS_ID);
        }
        return ProdutoVO.create(repository.save(Produto.create(vo)));
    }

    public void delete(Long id) {
        var produto = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ERROR_NO_RECORD_FOUND_FOR_THIS_ID));
        repository.delete(produto);
    }
}
