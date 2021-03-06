package com.example.carlos.secondapp.libro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.carlos.secondapp.R;
import com.example.carlos.secondapp.constantes.G;
import com.example.carlos.secondapp.constantes.Utilidades;
import com.example.carlos.secondapp.pojos.Libro;
import com.example.carlos.secondapp.proveedor.Contrato;
import com.example.carlos.secondapp.proveedor.LibroProveedor;

import java.io.FileNotFoundException;

public class LibroModificarActivity extends AppCompatActivity {

    EditText editTextLibroTitulo;
    EditText editTextLibroPaginas;
    int libroId;

    ImageView imageViewLibro;

    final int PETICION_CAPTURAR_IMAGEN = 1;
    final int PETICION_ESCOGER_IMAGEN_DE_GALERIA = 2;

    Bitmap foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_detalle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_libro_detalle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// se usa para mostrar la flecha para volver al padre de la activity

        editTextLibroTitulo = (EditText) findViewById(R.id.editTextLibroTitulo);
        editTextLibroPaginas = (EditText) findViewById(R.id.editTextLibroPaginas);

        libroId = this.getIntent().getExtras().getInt("ID");
        editTextLibroTitulo.setText(this.getIntent().getExtras().getString("Titulo"));
        editTextLibroPaginas.setText(this.getIntent().getExtras().getString("Paginas"));

        imageViewLibro = (ImageView) findViewById(R.id.image_view_libro);

       // libroId = this.getIntent().getExtras().getInt(Contrato.Libro._ID);
       // Libro libro = LibroProveedor.readRecord(getContentResolver(),libroId);

       // editTextLibroTitulo.setText(libro.getTitulo());
      //  editTextLibroPaginas.setText(libro.getPaginas());

        try {
            Utilidades.loadImageFromStorage(this, "img_" + libroId + ".jpg", imageViewLibro);
            foto = ((BitmapDrawable) imageViewLibro.getDrawable()).getBitmap();
        } catch (FileNotFoundException e) {
            //El libro no tiene imagen asociada
            foto = null;
        }

        ImageButton imageButtonImagenDeGaleria = (ImageButton) findViewById(R.id.buttonImagenDeGaleria);
        imageButtonImagenDeGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirFotoDeGaleria();
            }
        });

        ImageButton imageButtonCamara = (ImageButton) findViewById(R.id.buttonCamara);
        imageButtonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacarFoto();
            }
        });

    }

    void sacarFoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PETICION_CAPTURAR_IMAGEN);
    }

    void elegirFotoDeGaleria(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, PETICION_ESCOGER_IMAGEN_DE_GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PETICION_CAPTURAR_IMAGEN:
                if(resultCode == RESULT_OK){
                    foto = (Bitmap) data.getExtras().get("data");
                    imageViewLibro.setImageBitmap(foto);
                } else {
                    // El usuario canceló la captura de foto
                }
                break;
            case PETICION_ESCOGER_IMAGEN_DE_GALERIA:
                if(resultCode == RESULT_OK){
                    imageViewLibro.setImageURI(data.getData());
                    foto = ((BitmapDrawable) imageViewLibro.getDrawable()).getBitmap();
                } else {
                    // El usuario canceló la elección de foto
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem menuItem = menu.add(Menu.NONE, G.GUARDAR, Menu.NONE, "Guardar");
        menuItem.setIcon(R.drawable.ic_save_black_24dp);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case G.GUARDAR:
               attemptGuardar();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
    //metodo para guardar los datos en la base de datos

        void  attemptGuardar(){
        EditText editTextLibroTitulo = (EditText) findViewById(R.id.editTextLibroTitulo);

        EditText editTextLibroPaginas = (EditText) findViewById(R.id.editTextLibroPaginas);

        //borrar validaciones anteriores
        editTextLibroTitulo.setError(null);
        editTextLibroPaginas.setError(null);

        //Capturamos los datos

        String titulo = String.valueOf((editTextLibroTitulo.getText()));
        String paginas = String.valueOf(editTextLibroPaginas.getText());

        //validacion de los campos de los editText

        if (TextUtils.isEmpty(titulo)){
            editTextLibroTitulo.setError(getString(R.string.campo_requerido));
            editTextLibroTitulo.requestFocus();
            return;

        }
        if (TextUtils.isEmpty(paginas)){
            editTextLibroPaginas.setError(getString(R.string.campo_requerido));
            editTextLibroPaginas.requestFocus();
            return;

        }
        //introduce los datos en el proveedor de contenidos
        Libro libro = new Libro(libroId, titulo, paginas, foto);
        LibroProveedor.updateRecord(getContentResolver(),libro, this);
        // lo finaliza
        finish();


    }
}