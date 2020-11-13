package com.emersonoliveira.pagamento.config;

import com.emersonoliveira.pagamento.data.vo.ProdutoVO;
import com.emersonoliveira.pagamento.entity.Produto;
import com.emersonoliveira.pagamento.repository.ProdutoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProdutoReceiveMessage {

    private final ProdutoRepository repository;

    @Autowired
    public ProdutoReceiveMessage(ProdutoRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = { "${crud.rabbitmq.queue}" })
    public void receive(@Payload ProdutoVO vo) {
        repository.save(Produto.create(vo));
    }
}
