package com.example.sqlite

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAlta.setOnClickListener {
            alta()
        }

        binding.btnPorCodigo.setOnClickListener {
            buscarPorCodigo()
        }
        binding.btnBorrar.setOnClickListener {
            borrarPorCodigo()
        }
        binding.btnActualizar.setOnClickListener {
            modificarArticulo()
        }
        binding.btnPorDescripcion.setOnClickListener {
            buscarPorDescripcion()
        }
    }


    fun alta(){
        val admin = AdminSQLiteOpenHelper(this,"administracion", null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("codigo", binding.txtCodigo.text.toString())
        registro.put("descripcion", binding.txtDescripcion.text.toString())
        registro.put("precio",binding.txtPrecio.text.toString())
        bd.insert("articulos",null,registro)
        bd.close()
        binding.txtCodigo.setText("")
        binding.txtDescripcion.setText("")
        binding.txtPrecio.setText("")
        Toast.makeText(this, "Se inserto correctamente el articulo", Toast.LENGTH_SHORT).show()
    }


    fun buscarPorCodigo(){
        val admin = AdminSQLiteOpenHelper(this,"administracion", null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("SELECT descripcion, precio FROM articulos WHERE codigo=${binding.txtCodigo.text}", null)

        if(fila.moveToFirst()){
            binding.txtDescripcion.setText(fila.getString(0))
            binding.txtPrecio.setText(fila.getString(1))
        }else{
            Toast.makeText(this, "No hubo coincidencias con ese codigo", Toast.LENGTH_SHORT).show()
        }
    }

    fun borrarPorCodigo(){
        val admin = AdminSQLiteOpenHelper(this,"administracion", null, 1)
        val bd = admin.writableDatabase
        val cant = bd.delete("articulos","codigo=${binding.txtCodigo.text.toString()}", null)
        bd.close()
        if(cant == 1){
            Toast.makeText(this, "El articulo se borro", Toast.LENGTH_SHORT).show()
            binding.txtCodigo.setText("")
            binding.txtDescripcion.setText("")
            binding.txtPrecio.setText("")
        }else{
            Toast.makeText(this, "No hubo coincidencias con ese codigo", Toast.LENGTH_SHORT).show()
        }
    }

    fun modificarArticulo(){
        val admin = AdminSQLiteOpenHelper(this,"administracion", null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("descripcion", binding.txtDescripcion.text.toString())
        registro.put("precio",binding.txtPrecio.text.toString())
        val cant = bd.update("articulos", registro,"codigo=${binding.txtCodigo.text.toString()}", null)
        bd.close()
        if(cant == 1){
            Toast.makeText(this, "El articulo se actualizo", Toast.LENGTH_SHORT).show()
            binding.txtCodigo.setText("")
            binding.txtDescripcion.setText("")
            binding.txtPrecio.setText("")
        }else{
            Toast.makeText(this, "No hubo coincidencias con ese codigo", Toast.LENGTH_SHORT).show()
        }
    }

    fun buscarPorDescripcion(){
        val admin = AdminSQLiteOpenHelper(this,"administracion", null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("SELECT codigo, precio FROM articulos WHERE descripcion='${binding.txtDescripcion.text.toString()}'", null)

        if(fila.moveToFirst()){

            binding.txtCodigo.setText(fila.getString(0))
            binding.txtPrecio.setText(fila.getString(1))
        }else{
            Toast.makeText(this, "No hubo coincidencias con ese codigo", Toast.LENGTH_SHORT).show()
        }
    }
}