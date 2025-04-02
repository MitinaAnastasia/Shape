package com.example.shape

import java.sql.Connection
import java.sql.DriverManager

class ConnectionFactory {
    private val host = "jdbc:postgresql://192.168.0.159:5432/Figure"
    private val login = "postgres"
    private val password = "NarutoAndSasukeL0ve"

    fun connectionToDB(): Connection
    {
        Class.forName("org.postgresql.Driver")
        return DriverManager.getConnection(host, login, password)
    }
}