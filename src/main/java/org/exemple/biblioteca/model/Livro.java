package org.exemple.biblioteca.model;

public class Livro {
    private int livroID;
    private String titulo;
    private String autor;

    // Construtor padrão
    public Livro() {}

    // Construtor com parâmetros
    public Livro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    // Getters e Setters
    public int getLivroID () {
        return livroID;
    }

    public void setLivroID(int livroID) {
        this.livroID = livroID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}