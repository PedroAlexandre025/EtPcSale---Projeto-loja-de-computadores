package interfaces;

public interface validacao {


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
        if (cpf.length()!=11)g{
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
}
