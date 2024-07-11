package com.proyecto.examen2melany

import android.os.StrictMode
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object SQLConnectionHelper {

    // Método para obtener la conexión
    fun getConnection(): Connection? {
        var connection: Connection? = null
        val ip = "192.168.56.1" // Cambia esto si usas un dispositivo físico
        val port = "1433" // Puerto por defecto de SQL Server
        val dbName = "Exa2"
        
        val url = "jdbc:jtds:sqlserver://$ip:$port/$dbName;integratedSecurity=true;"

        // Permitir operaciones de red en el hilo principal (solo para pruebas, no recomendado para producción)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connection = DriverManager.getConnection(url)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return connection
    }
}

