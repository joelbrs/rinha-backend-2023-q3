package br.com.joelf.rinha_backend_2023_q3.application.commons;

public class ValidationConstants {
    public static final String REGEX_JUST_LETTERS_STRING = "^[a-zA-Zá-úÁ-Ú# ]+$";	
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String MSG_INVALID_NAME = "Nome deve ser string e não número";
    public static final String MSG_INVALID_STACK = "Stack deve ser um array de apenas strings";
}
