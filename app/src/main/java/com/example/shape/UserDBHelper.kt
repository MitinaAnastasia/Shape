package com.example.shape

import java.sql.Connection
import java.sql.SQLException

class UserDBHelper {

    private val connectionFactory = ConnectionFactory()

    fun addUser(user: User){
        try {
            Thread {
                val connection: Connection = connectionFactory.connectionToDB()
                val statement =
                    "INSERT INTO userapp (username, email, password, gender_name, figure_id_fk) VALUES (?, ?, ?, ?, ?);"
                val queryInsert = connection.prepareStatement(statement)
                queryInsert.setString(1, user.username)
                queryInsert.setString(2, user.email)
                queryInsert.setString(3, user.password)
                queryInsert.setString(4, user.genderName)
                queryInsert.setInt(5, user.figureTypeUserId)
                queryInsert.execute()
                connection.close()
            }.start()
        } catch (e: SQLException)
        {
            e.printStackTrace()
            println("Не удалось подключиться к бд")
        }
    }

    private fun updateTypeFigure(usernameAns: String, figureIdFkNum: Int){
        try {
            Thread {
                val connection: Connection = connectionFactory.connectionToDB()
                val statement =
                    "UPDATE userapp set figure_id_fk = ? where username = ?;"
                val queryUpdate = connection.prepareStatement(statement)
                queryUpdate.setInt(1, figureIdFkNum)
                queryUpdate.setString(2, usernameAns)
                queryUpdate.executeUpdate()
                connection.close()
            }.start()
        } catch (e: SQLException)
        {
            e.printStackTrace()
            println("Не удалось подключиться к бд")
        }
    }

    fun findAndUpdateTypeFigure(usernameAns: String, figureType: String) {
        try {
            Thread {
                var id = 0
                val connection: Connection = connectionFactory.connectionToDB()
                val statement =
                    "SELECT figure_type_id FROM figuretype where figure_type_name = ?;"
                val querySelect = connection.prepareStatement(statement)
                querySelect.setString(1, figureType)
                val result = querySelect.executeQuery()
                if (result.next()) {
                    id = result.getInt(1)
                }
                connection.close()
                updateTypeFigure(usernameAns, id)
            }.start()
        } catch (e: SQLException)
        {
            e.printStackTrace()
            println("Не удалось подключиться к бд")
        }
    }

    fun findUser(email : String, password: String, threadHelper: ThreadHelper<Int>): Int {
        try {
            val thread = Thread {
                var id = -1
                val connection: Connection = connectionFactory.connectionToDB()
                val statement =
                    "SELECT user_id FROM userapp where email = ? AND password = ?;"
                val querySelect = connection.prepareStatement(statement)
                querySelect.setString(1, email)
                querySelect.setString(2, password)
                val result = querySelect.executeQuery()
                if (result.next()) {
                    id = result.getInt(1)
                }
                connection.close()
                threadHelper.value = id
            }
            thread.start()
            thread.join()
        } catch (e: SQLException)
        {
            e.printStackTrace()
            println("Не удалось подключиться к бд")
        }
        return threadHelper.value
    }

    fun checkUserByUsername(username : String, threadHelper: ThreadHelper<Int>): Int {
        try {
            val thread = Thread {
                var id = -1
                val connection: Connection = connectionFactory.connectionToDB()
                val statement =
                    "SELECT user_id FROM userapp where username = ?;"
                val querySelect = connection.prepareStatement(statement)
                querySelect.setString(1, username)
                val result = querySelect.executeQuery()
                if (result.next()) {
                    id = result.getInt(1)
                }
                connection.close()
                threadHelper.value = id
            }
            thread.start()
            thread.join()
        } catch (e: SQLException)
        {
            e.printStackTrace()
            println("Не удалось подключиться к бд")
        }
        return threadHelper.value
    }

    fun checkUserByEmail(email : String, threadHelper: ThreadHelper<Int>): Int {
        try {
            val thread = Thread {
                var id = -1
                val connection: Connection = connectionFactory.connectionToDB()
                val statement =
                    "SELECT user_id FROM userapp where email = ?;"
                val querySelect = connection.prepareStatement(statement)
                querySelect.setString(1, email)
                val result = querySelect.executeQuery()
                if (result.next()) {
                    id = result.getInt(1)
                }
                connection.close()
                threadHelper.value = id
            }
            thread.start()
            thread.join()
        } catch (e: SQLException)
        {
            e.printStackTrace()
            println("Не удалось подключиться к бд")
        }
        return threadHelper.value
    }

    fun selectUserForProfile(email : String, userProf: User): User{
        try {
            val thread = Thread {
                var usernameProf = ""
                var genderNameProf = ""
                var figureIdFkProf = -1
                val connection: Connection = connectionFactory.connectionToDB()
                val statement =
                    "SELECT username, gender_name, figure_id_fk FROM userapp where email = ?;"
                val querySelect = connection.prepareStatement(statement)
                querySelect.setString(1, email)
                val result = querySelect.executeQuery()
                if (result.next()) {
                    usernameProf = result.getString(1)
                    genderNameProf = result.getString(2)
                    figureIdFkProf = result.getInt(3)
                }
                connection.close()
                userProf.username = usernameProf
                userProf.email = email
                userProf.password = ""
                userProf.genderName = genderNameProf
                userProf.figureTypeUserId = figureIdFkProf
            }
            thread.start()
            thread.join()
        } catch (e: SQLException)
        {
            e.printStackTrace()
            println("Не удалось подключиться к бд")
        }
        return userProf
    }

    fun selectTypeFig(figureIdFk : Int, threadHelper: ThreadHelper<String>): String {
        try {
            val thread = Thread {
                var figureName = ""
                val connection: Connection = connectionFactory.connectionToDB()
                val statement =
                    "SELECT figure_type_name FROM figuretype where figure_type_id = ?;"
                val querySelect = connection.prepareStatement(statement)
                querySelect.setInt(1, figureIdFk)
                val result = querySelect.executeQuery()
                if (result.next()) {
                    figureName = result.getString(1)
                }
                connection.close()
                threadHelper.value = figureName
            }
            thread.start()
            thread.join()
        } catch (e: SQLException)
        {
            e.printStackTrace()
            println("Не удалось подключиться к бд")
        }
        return threadHelper.value
    }
}