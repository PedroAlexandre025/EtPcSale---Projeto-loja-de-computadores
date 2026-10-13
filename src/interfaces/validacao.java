package interfaces;

import java.math.BigDecimal;

public interface validacao {

//CLIENTE
    static String texto(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }

        return nome.trim();
    }
//aqui a logica [0-9[ aceiuta nuemro de 0 a 9, o é a formatação que deve ter de 10 a 11 numero com o dd ex 043 99969969
    static String telefone(String telefone) {
        if (telefone == null || !telefone.matches("[0-9]{10,11}")) {
            throw new IllegalArgumentException(
                    "Informe um telefone com 10 ou 11 números, incluindo o DDD."
            );
        }

        return telefone;
    }

    static String cpf(String cpf){
        if (cpf.length()!=11){
            throw new IllegalArgumentException("CPF inválido");
        }
        return cpf;
    }

    static String email(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Informe o e-mail.");
        }

        String emailValido = email.trim();

        if (!emailValido.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) {
            throw new IllegalArgumentException("Informe um e-mail válido.");
        }

        return emailValido;
    }


    //PRODUTO

    static BigDecimal preco(BigDecimal preco){

        if(preco==null || preco.compareTo(BigDecimal.ZERO) == -1){
            throw new IllegalArgumentException("Informe um valor válido para o produto.");
        }
        return preco;
    }
    static int quantidade(int quantidade){
        if (quantidade<0){
            throw new IllegalArgumentException("Informe uma quantidade válida.");
        }
        return quantidade;
    }

    // FORNECEDOR

    static  String cnpj(String cnpj){

        if (cnpj.length()!=14){
            throw new IllegalArgumentException("CNPJ inválido");

        }
        String nums = "1234567890";

        for (int i = 0; i<cnpj.length(); i++){
            int cont  = 0;

            for (int j = 0; j<nums.length(); j++){
                if (cnpj.charAt(i) == nums.charAt(j)){
                    break;
                }
                cont++;
            }

            if (cont == nums.length()){//comparou todas os numeros e nao é nenhum
                throw new IllegalArgumentException("CNPJ inválido.");
            }
        }
        return cnpj;
    }

    static String razaoSocial(String razaoSocial){
        if (razaoSocial ==null){
            throw new IllegalArgumentException("Insira uma razão social.");
        }
        return razaoSocial;
    }
}


