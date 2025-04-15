package com.example.lista_de_contatos.utils;

import android.util.Patterns;

//Classe com métodos utilitários comuns para validação e formatação de dados.
public class Utilitarios {

    //Valida se o e-mail informado possui um formato correto.
    public static boolean isValidEmail(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Formata um número de telefone para um padrão mais legível.
     * Este exemplo realiza uma formatação simples, removendo caracteres não numéricos
     * e aplicando um formato conforme o tamanho do número.
     */
    public static String formatarTelefone(String telefone) {
        if (telefone == null || telefone.isEmpty()) {
            return telefone;
        }
        // Remove todos os caracteres que não são dígitos
        telefone = telefone.replaceAll("\\D", "");

        // Exemplo de formatação para números com 11 dígitos (celular no Brasil)
        if (telefone.length() == 11) {
            // Formato: (XX) XXXXX-XXXX
            return String.format("(%s) %s-%s",
                    telefone.substring(0, 2),
                    telefone.substring(2, 7),
                    telefone.substring(7));
        }
        // Exemplo de formatação para números com 10 dígitos (fixo no Brasil)
        else if (telefone.length() == 10) {
            // Formato: (XX) XXXX-XXXX
            return String.format("(%s) %s-%s",
                    telefone.substring(0, 2),
                    telefone.substring(2, 6),
                    telefone.substring(6));
        }
        // Retorna o telefone sem formatação se não corresponder aos formatos acima
        else {
            return telefone;
        }
    }
}
