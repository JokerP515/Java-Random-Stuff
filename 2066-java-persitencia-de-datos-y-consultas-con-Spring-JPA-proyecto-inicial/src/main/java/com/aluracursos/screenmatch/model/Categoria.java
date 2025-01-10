package com.aluracursos.screenmatch.model;

public enum Categoria {
    ACCION("Action", "Acción"),
    ANIMACION("Animation", "Animacion"),
    AVENTURA("Adventure", "Aventura"),
    BELICO("War", "Belico"),
    CIENCIA_FICCION("Sci-Fi", "Ciencia Ficcion"),
    COMEDIA("Comedy", "Comedia"),
    CRIMEN("Crime", "Crimen"),
    DOCUMENTAL("Documentary", "Documental"),
    DRAMA("Drama", "Drama"),
    ROMANCE("Romance", "Romance"),;

    private String categoriaOmbd;
    private String categoriaSpanish;

    Categoria(String categoriaOmbd, String categoriaSpanish) {
        this.categoriaOmbd = categoriaOmbd;
        this.categoriaSpanish = categoriaSpanish;
    }

    public static Categoria getCategoria(String categoria) {
        for (Categoria c : Categoria.values()) {
            if (c.categoriaOmbd.equalsIgnoreCase(categoria)) {
                return c;
            }
        }
        return null;
    }

    public static Categoria getCategoriaSpanish(String categoria) {
        for (Categoria c : Categoria.values()) {
            if (c.categoriaSpanish.equalsIgnoreCase(categoria)) {
                return c;
            }
        }
        return null;
    }
}
