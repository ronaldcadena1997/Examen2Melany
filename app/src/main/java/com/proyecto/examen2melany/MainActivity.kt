package com.proyecto.examen2melany

import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.proyecto.examen2melany.databinding.ActivityMainBinding
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar) // Asegúrate de que el ID coincide

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Agregar los TextViews y el botón al layout principal
        val palabra1EditText: EditText = findViewById(R.id.editTextPalabra1)
        val palabra2EditText: EditText = findViewById(R.id.editTextPalabra2)
        val guardarButton: Button = findViewById(R.id.buttonGuardar)

        guardarButton.setOnClickListener {
            val palabra1 = palabra1EditText.text.toString()
            val palabra2 = palabra2EditText.text.toString()
            SaveDataTask().execute(palabra1, palabra2)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private inner class SaveDataTask : AsyncTask<String, Void, Boolean>() {
        override fun doInBackground(vararg params: String): Boolean {
            val palabra1 = params[0]
            val palabra2 = params[1]

               var connection: Connection? = null

            try {

                connection = SQLConnectionHelper.getConnection()
                val query = "INSERT INTO Cliente (nombre, apellido) VALUES (?, ?)"
              var  preparedStatement: PreparedStatement? = connection?.prepareStatement(query)
                preparedStatement?.setString(1, palabra1)
                preparedStatement?.setString(2, palabra2)
                preparedStatement?.executeUpdate()
                return true


                return true
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
                return false
            } catch (e: SQLException) {
                e.printStackTrace()
                return false
            } finally {

                connection?.close()
            }
        }

        override fun onPostExecute(result: Boolean) {
            super.onPostExecute(result)
            if (result) {
                Toast.makeText(this@MainActivity, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Error al guardar los datos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
