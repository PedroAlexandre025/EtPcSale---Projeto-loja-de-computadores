package interfaces;

public interface validacao {


    static String  texto(String nome){
        return nome;
    }

    static String telefone(String telefone){
        String nums = "01234567889";

        if (telefone.length()<10 || telefone.length()>11){
            throw new IllegalArgumentException("Telefone inválido\n");
        }
        for (int i = 0; i<telefone.length(); i++) {
            for (int j = 0; j < nums.length(); j++) {
                if (telefone.charAt(i) != nums.charAt(j)) {
                    throw new IllegalArgumentException("Telefone inválido");
                }
            }
        }
        return telefone;
    }

    static String cpf(String cpf){
        if (cpf.length()!=11){
            throw new IllegalArgumentException("CPF inválido");
        }
        return cpf;
    }

    static String email(String email){
        if (email == null || email.trim().matches("[^@]+@[^@]+\\.[^@]+")){
            throw new IllegalArgumentException("Email inválido. O email deve conter um '@' e um domínio válido.");
        }
        return email;
    }
}
