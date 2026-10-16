package model.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import model.entitties.EntradaEstoque;
import model.entitties.Fornecedor;
import model.entitties.Produto;
import model.repository.Repositorio;
import model.repository.RepositorioFornecedor;
import model.repository.RepositorioProduto;

public class EntradaEstoqueService {

    private final Repositorio<EntradaEstoque, Integer> entradas;
    private final RepositorioProduto produtos;
    private final RepositorioFornecedor fornecedores;
    private int proximoId = 1;

    public EntradaEstoqueService(
            Repositorio<EntradaEstoque, Integer> entradas,
            RepositorioProduto produtos,
            RepositorioFornecedor fornecedores) {

        this.entradas = entradas;
        this.produtos = produtos;
        this.fornecedores = fornecedores;

        // Permite usar um repositório que já possui registros em memória salva.
        proximoId = entradas.listarTodos().stream()
                .mapToInt(EntradaEstoque::getId)
                .max().orElse(0) + 1;
    }

    public EntradaEstoque registrarEntrada(
            Produto selecionado,
            int quantidade,
            BigDecimal custoUnitario) {

        if (selecionado == null) {
            throw new IllegalArgumentException("Selecione um produto.");
        }

        // Resolve o cadastro atual; uma seleção antiga pode estar desatualizada.
        Produto produto = produtos.buscarPorId(selecionado.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "O produto não está mais cadastrado."
                ));

        if (produto.getFornecedor() == null) {
            throw new IllegalArgumentException(
                    "O produto precisa possuir um fornecedor."
            );
        }

        Fornecedor fornecedor = fornecedores
                .buscarPorId(produto.getFornecedor().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "O fornecedor do produto não está mais cadastrado."
                ));

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade recebida deve ser maior que zero."
            );
        }

        if (custoUnitario == null
                || custoUnitario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O custo unitário deve ser maior que zero."
            );
        }

        long novoSaldo = (long) produto.getEstoque() + quantidade;
        if (novoSaldo > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(
                    "A quantidade ultrapassa o limite do estoque."
            );
        }

        EntradaEstoque entrada = new EntradaEstoque(
                proximoId, fornecedor, produto, quantidade, custoUnitario
        );

        // Todas as validações são feitas antes de alterar saldo e histórico do produto
        produto.adicionarEstoque(quantidade);
        entradas.salvar(entrada);
        proximoId++;
        return entrada;
    }

    public List<EntradaEstoque> listarEntradas() {
        return entradas.listarTodos().stream()
                .sorted(Comparator.comparing(EntradaEstoque::getId).reversed())
                .toList();
    }

    public BigDecimal calcularCustoTotal() {
        return entradas.listarTodos().stream()
                .map(EntradaEstoque::getCustoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}