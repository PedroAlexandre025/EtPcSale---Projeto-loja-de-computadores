package interfaces;

import java.math.BigDecimal;

public interface validacao {

    // Nome é obrigatório.
    static String texto(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "O nome é obrigatório."
            );
        }

        return nome.trim();
    }

    // Telefone com DDD, contendo apenas números.
    static String telefone(String telefone) {
        if (telefone == null) {
            throw new IllegalArgumentException(
                    "Informe o telefone."
            );
        }

        String telefoneValido = telefone.trim();

        if (!telefoneValido.matches("[0-9]{10,11}")) {
            throw new IllegalArgumentException(
                    "Informe um telefone com 10 ou 11 números, "
                            + "incluindo o DDD."
            );
        }

        return telefoneValido;
    }

    // Confere o formato do CPF.
    static String cpf(String cpf) {
        if (cpf == null) {
            throw new IllegalArgumentException(
                    "Informe o CPF."
            );
        }

        String cpfValido = cpf.trim();

        if (!cpfValido.matches("[0-9]{11}")) {
            throw new IllegalArgumentException(
                    "O CPF deve conter exatamente 11 números, "
                            + "sem pontos ou traço."
            );
        }

        return cpfValido;
    }

    // Confere o formato do e-mail para serem funcional.
    static String email(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Informe o e-mail."
            );
        }

        String emailValido = email.trim();

        if (!emailValido.matches(
                "[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) {

            throw new IllegalArgumentException(
                    "Informe um e-mail válido."
            );
        }

        return emailValido;
    }

    // Preço não pode ser nulo ou negativo.
    static BigDecimal preco(BigDecimal preco) {
        if (preco == null
                || preco.compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "O preço não pode ser vazio ou negativo."
            );
        }

        return preco;
    }

    // O estoque pode começar em zero  e aumentando conforme adicionamos prod.
    static int quantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException(
                    "A quantidade não pode ser negativa."
            );
        }

        return quantidade;
    }

    // Confere o formato numérico de CNPJ usado no projeto.
    static String cnpj(String cnpj) {
        if (cnpj == null) {
            throw new IllegalArgumentException(
                    "Informe o CNPJ."
            );
        }

        String cnpjValido = cnpj.trim();

        if (!cnpjValido.matches("[0-9]{14}")) {
            throw new IllegalArgumentException(
                    "O CNPJ deve conter exatamente 14 números, "
                            + "sem pontos, barra ou traço."
            );
        }

        return cnpjValido;
    }

    // Razão social é obrigatória.
    static String razaoSocial(String razaoSocial) {
        if (razaoSocial == null
                || razaoSocial.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Informe a razão social."
            );
        }

        return razaoSocial.trim();
    }

    // Sobrecarga: valor obrigatório, utilizada no preço.
    static BigDecimal valorMonetario(String texto) {
        return valorMonetario(texto, false);
    }

    // Sobrecarga: permite tratar desconto vazio como zero.
    static BigDecimal valorMonetario(
            String texto,
            boolean permitirVazio) {

        if (texto == null || texto.trim().isEmpty()) {
            if (permitirVazio) {
                return BigDecimal.ZERO;
            }

            throw new IllegalArgumentException(
                    "Informe um valor."
            );
        }

        String valor = texto.trim();

        // Exemplos aceitos: 100, 100,50 e 1.299,90.
        String formato =
                "(?:[0-9]+|[0-9]{1,3}(?:\\.[0-9]{3})+)"
                        + "(?:,[0-9]{1,2})?";

        if (!valor.matches(formato)) {
            throw new IllegalArgumentException(
                    "Valor inválido. Use números no formato "
                            + "1.299,90, sem R$ e sem sinal negativo."
            );
        }

        // Remove os separadores de milhar e ajusta os centavos.
        String valorConvertido = valor
                .replace(".", "")
                .replace(",", ".");

        return new BigDecimal(valorConvertido);
    }
}