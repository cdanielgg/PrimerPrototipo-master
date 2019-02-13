package com.example.carlos.pclibros.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class LibroProveedor {

    //metodo para pasar por parametros al proveedor de contenidos

    public static void insertRecord(ContentResolver resolver, com.example.carlos.pclibros.pojos.Libro libro){

        Uri uri = Contrato.Libro.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.Libro.TITULO, libro.getTitulo());
        values.put(Contrato.Libro.PAGINAS, libro.getPaginas());

       resolver.insert(uri, values);

    }
    static  public void deleteRecord(ContentResolver resolver, int libroId){

        Uri uri = Uri.parse(Contrato.Libro.CONTENT_URI + "/" + libroId);
              resolver.delete(uri, null,null);

    }

    static public void updateRecord(ContentResolver resolver, com.example.carlos.pclibros.pojos.Libro libro){
        Uri uri = Uri.parse(Contrato.Libro.CONTENT_URI + "/" + libro.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Libro.TITULO, libro.getTitulo());
        values.put(Contrato.Libro.PAGINAS, libro.getPaginas());

        resolver.update(uri,values, null, null);

    }
// se usa para leer un registro
    static public com.example.carlos.pclibros.pojos.Libro readRecord(ContentResolver resolver, int libroId){
        Uri uri = Uri.parse(Contrato.Libro.CONTENT_URI + "/" + libroId);

        String[] projection ={
                Contrato.Libro.TITULO,
                Contrato.Libro.PAGINAS

        };
        //El Cursor devuelve el conjunto de registros, uno o ninguno.
        Cursor cursor = resolver.query(uri, projection, null, null,null);

        if (cursor.moveToFirst()){

            com.example.carlos.pclibros.pojos.Libro libro = new com.example.carlos.pclibros.pojos.Libro();
            libro.setID(libroId);
            libro.setTitulo(cursor.getString(cursor.getColumnIndex(Contrato.Libro.TITULO)));
            libro.setPaginas(cursor.getString(cursor.getColumnIndex(Contrato.Libro.PAGINAS)));

            return libro;
        }
        // en caso de no encontrar el registro devuelve nulo
        return null;
    }
}
