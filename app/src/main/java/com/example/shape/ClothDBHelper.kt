package com.example.shape

import java.sql.Connection
import java.sql.SQLException

class ClothDBHelper {
    private val connectionFactory = ConnectionFactory()

    fun findTypeFigure(figureType: String, threadHelper: ThreadHelper<Int>): Int {
        try {
            val thread = Thread {
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

    private fun emptyArr(clothes: ArrayList<Cloth>, size: Int): ArrayList<Cloth> {
        var i = 0
        while (i < size) {
            clothes.add(Cloth(-1, "", "", "", "", -1, "", "", ""))
            i++
        }
        return clothes
    }

    fun findClothes(figureId : Int, gender: String, threadHelper: ThreadHelper<ArrayList<Cloth>>): ArrayList<Cloth> {
        try {
            val thread = Thread {
                var i = 0
                var clothes = ArrayList<Cloth>()
                if(gender == "женщина"){
                    clothes = emptyArr(ArrayList(), 20)
                }
                if(gender == "мужчина"){
                    clothes = emptyArr(ArrayList(), 10)
                }
                val connection: Connection = connectionFactory.connectionToDB()
                val statement =
                    "SELECT * FROM clothes where figure_id_fk = ? AND gender = ?;"
                val querySelect = connection.prepareStatement(statement)
                querySelect.setInt(1, figureId)
                querySelect.setString(2, gender)
                val result = querySelect.executeQuery()
                while (result.next()) {
                    clothes[i].clothId = result.getInt(1)
                    clothes[i].clothesName = result.getString(2)
                    clothes[i].imageName = result.getString(3)
                    clothes[i].description = result.getString(4)
                    clothes[i].linkLong = result.getString(5)
                    clothes[i].figureIdFk = result.getInt(6)
                    clothes[i].gender = result.getString(7)
                    clothes[i].category = result.getString(8)
                    clothes[i].linkShort = result.getString(9)
                    i++
                }
                connection.close()
                threadHelper.value = clothes
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