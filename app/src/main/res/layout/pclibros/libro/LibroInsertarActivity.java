package com.example.carlos.pclibros.libro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.carlos.pclibros.R;
import com.example.carlos.pclibros.proveedor.LibroProveedor;

public class LibroInsertarActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_detalle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detalle_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// se usa para mostrar la flecha para volver al padre de la activity


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem menuItem = menu.add(Menu.NONE, com.example.carlos.pclibros.constantes.G.GUARDAR, Menu.NONE, "Guardar");
        menuItem.setIcon(R.drawable.ic_action_guardar);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case com.example.carlos.pclibros.constantes.G.GUARDAR:
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
        com.example.carlos.pclibros.pojos.Libro libro = new com.example.carlos.pclibros.pojos.Libro(com.example.carlos.pclibros.constantes.G.SIN_VALOR_INT, titulo, paginas);
        LibroProveedor.insertRecord(getContentResolver(),libro);
        // lo finaliza
        finish();


    }
}