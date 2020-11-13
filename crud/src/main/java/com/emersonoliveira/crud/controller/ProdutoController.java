package com.emersonoliveira.crud.controller;

import com.emersonoliveira.crud.data.vo.ProdutoVO;
import com.emersonoliveira.crud.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private PagedResourcesAssembler<ProdutoVO> assembler;

    @GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
    public ProdutoVO findById(@PathVariable("id") Long id) {
        ProdutoVO vo = service.findById(id);
        vo.add(linkTo(methodOn(ProdutoController.class).findById(id)).withSelfRel());
        return vo;
    }

    @GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "12") int limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        Pageable pageable = PageRequest.of(page, limit,
                Sort.by("desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC, "nome"));
        Page<ProdutoVO> produtos = service.findAll(pageable);
        produtos.forEach(p -> p.add(linkTo(methodOn(ProdutoController.class).findById(p.getId())).withSelfRel()));
        return new ResponseEntity<>(assembler.toModel(produtos), HttpStatus.OK);
    }

    @PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
            "application/json", "application/xml", "application/x-yaml" })
    public ProdutoVO create(@RequestBody ProdutoVO vo) {
        var produto = service.create(vo);
        produto.add(linkTo(methodOn(ProdutoController.class).findById(produto.getId())).withSelfRel());
        return produto;
    }

    @PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
            "application/json", "application/xml", "application/x-yaml" })
    public ProdutoVO update(@RequestBody ProdutoVO vo) {
        var produto = service.update(vo);
        produto.add(linkTo(methodOn(ProdutoController.class).findById(produto.getId())).withSelfRel());
        return produto;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
