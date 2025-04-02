package com.example.shape

import org.junit.Assert
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class FigureUnitTest {

    @Test
    fun selectUserByEmail_isSuccess(){
        var email = "testUser@yandex.ru"
        val user = User("", "", "", "", -1)
        val userTest = User("testUser", "testUser@yandex.ru", "123456789qwerty", "мужчина", 3)
        val db = UserDBHelper()
        db.addUser(userTest)
        val userProf : User = db.selectUserForProfile(email, user)
        Assert.assertEquals(userTest.username, userProf.username)
    }
}