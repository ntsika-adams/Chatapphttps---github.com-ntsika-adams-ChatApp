package com.mycompany.chatapp;

public class Login {

    // Fields
    private String username;
    private String password;
    private String phoneNumber;

    // ===============================================================
    // USERNAME VALIDATION
    // ===============================================================
    // Username must contain "_" and be no more than 5 characters
    public boolean checkUserName(String username) {

        return username.contains("_")
                && username.length() <= 5;
    }

    // ===============================================================
    // PASSWORD VALIDATION
    // ===============================================================
    public boolean checkPassword(String password) {

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {

            char c = password.charAt(i);

            if (Character.isUpperCase(c)) {

                hasCapital = true;

            } else if (Character.isDigit(c)) {

                hasNumber = true;

            } else if (!Character.isLetterOrDigit(c)) {

                hasSpecial = true;
            }
        }

        return password.length() >= 8
                && hasCapital
                && hasNumber
                && hasSpecial;
    }

    // ===============================================================
    // PHONE NUMBER VALIDATION
    // ===============================================================
    public boolean phoneNumber(String phone) {

        return phone.startsWith("+27")
                && phone.length() == 12;
    }

    // ===============================================================
    // REGISTER USER
    // ===============================================================
    public String registerUser(String username,
                               String password,
                               String phoneNumber) {

        // Username validation
        if (!checkUserName(username)) {

            return "Username is not correctly formatted;"
                    + "please ensure that your username contains an underscore "
                    + "and is no more than five characters in length ";
        }

        // Password validation
        if (!checkPassword(password)) {

            return "Password is not correctly formatted; "
                    + "please ensure that the password contains at least "
                    + "eight characters, a capital letter, a number, "
                    + "and a special character.";
        }

        // Phone number validation
        if (!phoneNumber(phoneNumber)) {

            return "Phone number incorrectly formatted "
                    + "or does not contain international code.";
        }

        // Save details
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;

        return "User registered successfully.";
    }

    // ===============================================================
    // LOGIN USER
    // ===============================================================
    public boolean loginUser(String username,
                             String password) {

        return this.username.equals(username)
                && this.password.equals(password);
    }

    // ===============================================================
    // RETURN LOGIN STATUS
    // ===============================================================
    public String returnLoginStatus(boolean success) {

        if (success) {

            return "Welcome "
                    + this.username
                    + ", it is great to see you again.";

        } else {

            return "Username or password is incorrect, "
                    + "please try again.";
        }
    }

    // ===============================================================
    // RETURNS
    // ===============================================================
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}