package com.example.course.controllers;

import com.example.course.models.Produto;
import com.example.course.repositories.ProdutoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    public Produto save (@RequestBody Produto produto){
        System.out.println("Produto recebido: " + produto);
        var id = UUID.randomUUID().toString();
        produto.setId(id);
        this.produtoRepository.save(produto);
        return produto;
    }

    @GetMapping
    public List<Produto> findAll() {
        return this.produtoRepository.findAll();
    }

    @GetMapping("{id}")
    public Produto findById(@PathVariable String id){
        return this.produtoRepository.findById(id).orElse(null);
    }

    @PutMapping("{id}")
    public Produto update(@PathVariable String id, @RequestBody Produto produto){
        Optional<Produto> result = this.produtoRepository.findById(id);
        if (result.isPresent()) {
            Produto existingProduto = result.get();
            existingProduto.setNome(produto.getNome());
            existingProduto.setPreco(produto.getPreco());
            return this.produtoRepository.save(existingProduto);
        }
        return null;
    }

    @DeleteMapping
    public Optional<Produto> deleteById(@RequestParam String id){
        try{
            Optional<Produto> produtoDeleted = this.produtoRepository.findById(id);
            if(produtoDeleted.isPresent()){
                this.produtoRepository.deleteById(id);
                return produtoDeleted;
            }
        } catch (Exception e){
            System.out.println("Erro ao deletar produto: " + e.getMessage());
        }
        return Optional.empty();
    }
}