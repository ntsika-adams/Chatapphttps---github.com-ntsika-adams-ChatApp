/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

/**
 *
 * @author Student
*/
//These are only imports we need for JUnit & texting

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
//this allows us to write @Test above a method
//@Test tells NetBeans that this is a unit test method


public class LoginTest {
    
    //
    Login login = new Login();
    
   // =================================================================================
   //                     USERMAME TEST
   //==================================================================================
    
    @Test
    public void testInvalidUsernameMessage() {
    /*
     *we provide an incorrect username"kyle!!!!"
     *we expect registerUser() to return the correct ERROR message
     *this matches the test data given in the POE
    */   
    String result = login.registerUser("kyle!!!!!!!!!","Ch&&sec8ke99!", "+27838968976");
       
            //assert equals checks if the actual result is EXACTLY the same as expected.
        assertEquals(
    "Username is not correctly formatted;please ensure that your username contains an underscore and is no more than five characters in length ",
    result

         );
    }
      
    @Test 
    public void testValidUsernameBoolean()  {
         assertTrue(login.checkUserName("kyl_1"));
    }
    
    @Test
    public void testInvalidUsernameBoolean() {
        assertFalse(login.checkUserName("kyle!!!!!"));
    }
      
   @Test
    public void testValidPasswordBoolean() {
        assertTrue(login.checkPassword("Ch&&sec@ke99!"));
    }
    
     @Test
    public void testInvalidPasswordBoolean() {
        assertFalse(login.checkPassword("cheesewizz"));
    }
    
    @Test
    public void testValidPhonenumberBoolean() {
        assertTrue(login.phoneNumber("+27838968976")); 
    }
     @Test
    public void testInvalidPhonenumberBoolean() {
        assertFalse(login.phoneNumber("+278966553")); 
    }
    
       
}
