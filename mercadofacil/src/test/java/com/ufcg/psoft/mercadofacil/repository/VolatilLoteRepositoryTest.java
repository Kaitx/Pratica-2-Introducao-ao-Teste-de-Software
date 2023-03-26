package com.ufcg.psoft.mercadofacil.repository;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class VolatilLoteRepositoryTest {


    @Autowired
    VolatilLoteRepository driver;


    Lote lote;
    Lote loteExtra;
    Lote resultado;
    Produto produto;

    Produto produtoExtra1;


    @BeforeEach
    void setup() {
        produto = Produto.builder()
                .id(1L)
                .nome("Produto Base")
                .codigoBarra("123456789")
                .fabricante("Fabricante Base")
                .preco(125.36)
                .build();

        produtoExtra1 = Produto.builder()
                .id(2L)
                .nome("Produto Extra 1")
                .codigoBarra("98765")
                .fabricante("Fabricante Extra 1").preco(129.36)
                .build();

        lote = Lote.builder()
                .id(1L)
                .numeroDeItens(100)
                .produto(produto)
                .build();

        loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra1)
                .build();

    }


    @AfterEach
    void tearDown() {
        produto = null;
        driver.deleteAll();
    }


    @Test
    @DisplayName("Adicionar o primeiro Lote no repositorio de dados")
    void salvarPrimeiroLote() {
        resultado = driver.save(lote);


        assertEquals(driver.findAll().size(),1);
        assertEquals(resultado.getId().longValue(), lote.getId().longValue());
        assertEquals(resultado.getProduto(), produto);
    }


    @Test
    @DisplayName("Adicionar o segundo Lote (ou posterior) no repositorio de dados")
    void salvarSegundoLoteOuPosterior() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra").preco(125.36)
                .build();

        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(loteExtra);


        resultado = driver.save(loteExtra);


        assertEquals(driver.findAll().size(),2);
        assertEquals(resultado.getId().longValue(), loteExtra.getId().longValue());
        assertEquals(resultado.getProduto(), produtoExtra);


    }
    @Test
    @DisplayName("Removendo todos os lotes")
    void removerUmLote() {
        driver.save(lote);
        driver.save(loteExtra);
        tearDown();
        assertEquals(driver.findAll().size(),0);
    }
    @Test
    @DisplayName("Removendo 1 lote")
    void removerTodosOsLotes() {
        driver.save(lote);
        driver.save(loteExtra);
        assertEquals(driver.findAll().size(),2);
        driver.delete(loteExtra);
        assertEquals(driver.findAll().size(),1);
    }

    @Test
    @DisplayName("Busca um lote pelo id")
    void buscaLoteId() {
        driver.save(lote);
        driver.save(loteExtra);
        assertEquals(lote.toString(),driver.find(0L).toString());
        assertEquals(loteExtra.toString(),driver.find(1L).toString());
    }

    @Test
    @DisplayName("Atualiza um lote existente")
    void AtualizaLote() {
        driver.save(lote);
        driver.save(loteExtra);
        Produto produtoExtra2 = Produto.builder()
                .id(3L)
                .nome("Produto Extra 2 ")
                .codigoBarra("4321")
                .fabricante("Fabricante Extra 2").preco(185.76)
                .build();

        Lote loteExtra2 = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra2)
                .build();

        driver.update(loteExtra2);

        assertEquals(driver.findAll().size(), 1);
    }
}




