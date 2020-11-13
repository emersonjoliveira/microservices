package com.emersonoliveira.pagamento.controller;

import com.emersonoliveira.pagamento.data.vo.VendaVO;
import com.emersonoliveira.pagamento.service.VendaService;
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
@RequestMapping("/venda")
public class VendaController {

    private final VendaService service;

    private final PagedResourcesAssembler<VendaVO> assembler;

    @Autowired
    public VendaController(VendaService service, PagedResourcesAssembler<VendaVO> assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
    public VendaVO findById(@PathVariable("id") Long id) {
        VendaVO vo = service.findById(id);
        vo.add(linkTo(methodOn(VendaController.class).findById(id)).withSelfRel());
        return vo;
    }

    @GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "12") int limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        Pageable pageable = PageRequest.of(page, limit,
                Sort.by("desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC, "data"));
        Page<VendaVO> vendas = service.findAll(pageable);
        vendas.forEach(p -> p.add(linkTo(methodOn(VendaController.class).findById(p.getId())).withSelfRel()));
        return new ResponseEntity<>(assembler.toModel(vendas), HttpStatus.OK);
    }

    @PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
            "application/json", "application/xml", "application/x-yaml" })
    public VendaVO create(@RequestBody VendaVO vo) {
        var venda = service.create(vo);
        venda.add(linkTo(methodOn(VendaController.class).findById(venda.getId())).withSelfRel());
        return venda;
    }

    @PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
            "application/json", "application/xml", "application/x-yaml" })
    public VendaVO update(@RequestBody VendaVO vo) {
        var venda = service.update(vo);
        venda.add(linkTo(methodOn(VendaController.class).findById(venda.getId())).withSelfRel());
        return venda;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
