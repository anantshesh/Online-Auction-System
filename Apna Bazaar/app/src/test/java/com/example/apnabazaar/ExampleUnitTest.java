package com.example.apnabazaar;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testEmailValid(){
        String testEmail = "anantsheshkatti@gmail.com";
        Assert.assertThat(String.format("Email Validity Test failed for %s", testEmail),
                Utils.checkEmailForValidity(testEmail), is(true));
    }

    @Test
    public void testPasswordValid(){
        String testPassword = "Meghana@123";
        Assert.assertThat(String.format("Password validity failed for %s", testPassword),
            Utils.checkPasswordForValidity(testPassword), is(true));
    }



}