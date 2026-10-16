# EtPcSale — Loja de computadores

## Objetivo e domínio

Aplicação desktop para gerenciar uma loja de computadores e componentes, desenvolvida em Java com Swing. 
O projeto reúne cadastros, consultas, vendas, cancelamentos e controle de estoque, aplicando conceitos de programação orientada a objetos e programação funcional.

## Integrantes

- Thalles Henrique Baltazar Ribeiro
- Pedro Alexandre de Souza da Silva
- Ryan dos Santos

## Funcionalidades

- Cadastrar, consultar, alterar e remover clientes, fornecedores e produtos.
- Localizar automaticamente o cliente pelo CPF na tela de vendas, com o nome preenchido em um campo somente leitura.
- Pesquisar clientes por nome, CPF ou e-mail.
- Consultar a quantidade de vendas finalizadas e o total gasto por cliente.
- Consultar produtos por nome, ID e categoria, combinando critérios.
- Ordenar resultados e visualizar o fornecedor de cada produto.
- Criar vendas com cliente, produtos, quantidades, pagamento e desconto.
- Validar a disponibilidade de estoque e efetuar a baixa na finalização.
- Cancelar vendas abertas e consultar seu histórico.
- Registrar entradas de estoque, com produto, fornecedor, quantidade, custo unitário e histórico.
- Exibir resumos dos resultados filtrados.

## Entidades e classes de consulta

| Classe | Responsabilidade |
|---|---|
| Pessoa | Classe abstrata com os dados comuns de cliente e fornecedor. |
| Cliente | Representa o comprador, com CPF e e-mail. |
| Fornecedor | Representa o fornecedor, com CNPJ e razão social. |
| Produto | Representa um item comercializado, com categoria, preço, estoque e fornecedor. |
| ItemVenda | Relaciona produto, quantidade e preço unitário de uma venda. |
| Venda | Relaciona cliente, itens, desconto, pagamento e situação da operação. |
| EntradaEstoque | Registra o recebimento de produto, fornecedor, quantidade, custo e data, aumentando o saldo disponível. |

`ResumoCliente` é uma classe de consulta que reúne cliente, quantidade de vendas finalizadas e total gasto. Seus valores são calculados a partir dos repositórios em memória.

`EntradaEstoque` participa de um movimento integrado. O histórico preserva os nomes do produto e do fornecedor no momento do recebimento, mesmo depois de alterações nos cadastros.

## Cadastros

1. Cadastro de clientes.
2. Cadastro de produtos.
3. Cadastro de fornecedores.

Os formulários permitem cadastrar, selecionar, alterar, remover e limpar os dados, respeitando as regras de relacionamento.

## Consultas

1. **Clientes:** pesquisa por nome, CPF ou e-mail, ordenação nas colunas, quantidade de vendas finalizadas e total gasto. O resumo inferior acompanha os resultados do filtro.
2. **Produtos:** pesquisa por nome ou ID, filtro de categoria, ordenação, preço, estoque, fornecedor e quantidade de resultados.

## Movimentos

1. **Realizar venda:** relaciona cliente, produtos e itens; valida quantidades, pagamento, desconto e disponibilidade; na finalização, reduz o estoque e muda a situação da venda.
2. **Cancelar venda aberta:** altera a situação para cancelada, mantém o registro no histórico e encerra a edição. Como a baixa ocorre na finalização, o cancelamento da venda aberta não reduz o estoque.

3. **Entrada de estoque:** registra produto e fornecedor, valida quantidade e custo e aumenta o estoque disponível. O histórico mantém a data, os dados da operação e seu custo total.

## Regras de negócio

- Campos obrigatórios devem ser informados.
- Cliente não pode repetir CPF, e-mail ou telefone já utilizado por outro cliente.
- Produtos não podem repetir nome, desconsiderando diferenças entre letras maiúsculas e minúsculas.
- Produto deve possuir categoria e fornecedor.
- Entrada de estoque exige produto e fornecedor cadastrados, quantidade inteira positiva e custo unitário maior que zero.
- Uma entrada que ultrapassaria o limite inteiro do estoque é bloqueada antes da alteração.
- O custo de recebimento é registrado no histórico; o preço de venda continua definido no cadastro do produto.
- Quantidade de item de venda deve ser maior que zero.
- A quantidade total de um produto na venda deve respeitar seu estoque.
- Venda deve possuir pelo menos um item e forma de pagamento para ser finalizada.
- Desconto não pode ser negativo nem superar o subtotal.
- A inclusão e a remoção de itens devem validar o desconto contra o subtotal resultante antes de alterar os itens da venda.
- Durante uma venda aberta, o CPF do cliente fica bloqueado para impedir a troca do comprador.
- Venda finalizada ou cancelada não pode continuar sendo editada.
- Cliente com vendas associadas não pode ser removido.
- Fornecedor com produtos associados não pode ser removido.
- Produto associado a venda aberta não pode ser removido.

As validações de CPF e CNPJ verificam o formato numérico adotado pelo projeto; não calculam seus dígitos verificadores.

## Conceitos utilizados

- **Herança e classe abstrata:** Pessoa é a base de Cliente e Fornecedor.
- **Sobrescrita:** implementação de getDocumento e dos métodos dos modelos de tabela.
- **Interfaces:** Identificavel e seus contratos de identificação.
- **Sobrecarga:** métodos botao, Transformar, valorMonetario, adicionarItem e removerItem.
- **Collections e Generics:** repositórios tipados e modelos de tabela genéricos.
- **Enums:** CategoriaProduto, FormaPagamentos e StatusVenda.
- **Comparator:** ordenação dos produtos por estoque e nome.
- **Lambdas e Predicate:** validações, eventos e composição dos critérios de consulta.
- **Function e transformação genérica:** extração de linhas das tabelas e transformação dos objetos do repositório.
- **Separação de responsabilidades:** entidades, serviços, repositórios, controllers e views.

## Organização

```text
src/
├── Main.java
├── ResourceIcons/
├── controller/
├── interfaces/
├── model/
│   ├── consulta/
│   ├── entitties/
│   ├── enums/
│   ├── repository/
│   └── service/
└── view/
    ├── cadastro/
    ├── consulta/
    ├── movimento/
    └── tablemodel/
```

O nome do pacote `entitties` foi mantido conforme a estrutura do projeto.

## Armazenamento

Os dados são mantidos em Collections durante a execução. Não é utilizado banco de dados. Ao fechar a aplicação, os cadastros e movimentos da sessão são perdidos.

## Execução pelo IntelliJ IDEA

1. Extraia o ZIP em uma pasta local.
2. Abra a pasta do projeto no IntelliJ IDEA.
3. Configure um JDK 21 como SDK do projeto.
4. Confirme que a pasta src está marcada como Sources Root.
5. Execute o método main da classe Main.

A pasta ResourceIcons deve permanecer dentro de src e ser incluída no classpath da execução, pois contém a imagem utilizada pela tela principal.

## Compilação pelo PowerShell

Com o JDK 21 instalado e java/javac disponíveis no PATH, abra o PowerShell na pasta que contém src e execute:

```powershell
New-Item -ItemType Directory -Path bin -Force | Out-Null
$fontes = @(Get-ChildItem -Path .\src -Recurse -Filter *.java |
    Select-Object -ExpandProperty FullName)
javac -encoding UTF-8 -d .\bin $fontes
Copy-Item -LiteralPath .\src\ResourceIcons -Destination .\bin -Recurse -Force
java -cp .\bin Main
```

## Uso inicial

1. Cadastre um fornecedor.
2. Cadastre produtos associados a ele, informando seus estoques.
3. Cadastre um cliente.
4. Abra Movimento de vendas e digite o CPF do cliente, com ou sem pontos e traço.
5. Ao completar o CPF de um cliente cadastrado, o nome será preenchido automaticamente. Se o CPF não existir, cadastre o cliente primeiro.
6. Selecione um produto, informe a quantidade e adicione o item. Repita para os demais produtos.
7. Se desejar um desconto, informe um valor monetário no formato brasileiro, por exemplo 10,50. O desconto não pode superar o subtotal resultante da operação.
8. Se a remoção de um item fizer o desconto superar o subtotal, reduza o desconto no campo e tente remover novamente.
9. Escolha a forma de pagamento e finalize a venda. O estoque é reduzido na finalização.
10. Consulte as operações na aba Vendas registradas e abra Consulta de clientes para visualizar quantidade de vendas finalizadas e total gasto.

O nome do comprador é somente leitura. O CPF fica bloqueado durante a venda aberta e volta a ficar disponível ao finalizar, cancelar ou limpar a venda. Na tela de vendas, Limpar cancela a venda aberta e limpa o formulário.

O programa pode iniciar sem dados cadastrados. Dados de demonstração no Main não são necessários para utilizar a aplicação.

## Registrar uma entrada de estoque

1. Cadastre o fornecedor e um produto associado a ele. O produto pode começar com estoque zero.
2. Abra **Entrada de estoque** no menu.
3. Selecione o produto; fornecedor e saldo atual são preenchidos automaticamente.
4. Informe a quantidade recebida e o custo por unidade, por exemplo 25,50.
5. Clique em **Registrar entrada**. O saldo aumenta e a operação aparece no histórico.
6. Confira a atualização em Cadastro de produtos, Consulta de produtos e Movimento de vendas.

A seleção de fornecedor acompanha o cadastro do produto. **Atualizar** recarrega os cadastros e o histórico. **Limpar** apaga apenas o formulário de entrada, preservando os registros e o estoque. As entradas já registradas não são alteradas ou removidas por esta tela. Os dados de todas as telas continuam em memória e são perdidos ao fechar a aplicação.


## Crédito da imagem

Ícone de ET criado por Paul J., disponível no [Flaticon](https://www.flaticon.com/br/icones-gratis/et).

