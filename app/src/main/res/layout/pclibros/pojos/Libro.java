package com.example.carlos.pclibros.pojos;

import com.example.carlos.pclibros.constantes.G;

public class Libro {
    private int ID;
   private String titulo;
   private String paginas;

    public Libro() {
        this.ID = G.SIN_VALOR_INT;
        this.titulo = G.SIN_VALOR_STRING;
        this.setPaginas(G.SIN_VALOR_STRING);
    };

    public Libro (int ID, String titulo, String paginas){

        this.ID = ID;
        this.titulo= titulo;
        this.setPaginas(paginas);
    }
    public  int getID(){return ID; }

    public void setID (int ID){this.ID = ID;}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPaginas() {
        return paginas;
    }

    public void setPaginas(String paginas) {
        this.paginas = paginas;
    }
}
