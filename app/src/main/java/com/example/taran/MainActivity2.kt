package com.example.taran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.databinding.DataBindingUtil
import java.util.regex.Pattern.matches
import android.util.Patterns
import androidx.core.widget.doOnTextChanged
import com.example.taran.databinding.ActivityMain2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity2 : AppCompatActivity() {
    lateinit var rdbtn1: RadioButton
    lateinit var rdbtn2: RadioButton
    lateinit var binding: ActivityMain2Binding
    val database = Firebase.database
    lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)



        binding.rdgrp.setOnCheckedChangeListener() { group, checkedId ->
            if (checkedId == R.id.rdbtn1) {
                Toast.makeText(this, binding.rdbtn1.text.toString(), Toast.LENGTH_SHORT).show()
            }
            if (checkedId == R.id.rdbtn2) {
                Toast.makeText(this, binding.rdbtn2.text.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        binding.fname.doOnTextChanged { text, start, before, count ->
            if (!TextUtils.isEmpty(text)) {
                binding.tilfname.error = null
            } else if (TextUtils.isEmpty(text)) {
                binding.tilfname.error = resources.getString(R.string.enter_first_name)
            }
        }
        binding.lname.doOnTextChanged { text, start, before, count ->
            if (!TextUtils.isEmpty(text)) {
                binding.tillname.error = null
            } else if (TextUtils.isEmpty(text)) {
                binding.tillname.error = resources.getString(R.string.enter_last_name)
            }
        }
        binding.email.doOnTextChanged { text, start, before, count ->
            if (!TextUtils.isEmpty(text)) {
                binding.tilemail.error = null
            } else if (TextUtils.isEmpty(text)) {

                binding.tilemail.error = resources.getString(R.string.enter_email)
            }
        }
        binding.pswrd.doOnTextChanged { text, start, before, count ->
            if (!TextUtils.isEmpty(text)) {
                binding.tilpswrd.error = null
            } else if (TextUtils.isEmpty(text)) {

                binding.tilpswrd.error = resources.getString(R.string.enter_password)
            }
        }
        binding.phn.doOnTextChanged { text, start, before, count ->
            if (!TextUtils.isEmpty(text)) {
                binding.tilphn.error = null
            } else if (TextUtils.isEmpty(text)) {

                binding.tilphn.error = resources.getString(R.string.enter_phone)
            }
        }
        binding.conpswrd.doOnTextChanged { text, start, before, count ->
            if (!TextUtils.isEmpty(text)) {
                binding.tilconpswrd.error = null
            } else if (TextUtils.isEmpty(text)) {

                binding.tilconpswrd.error = resources.getString(R.string.enter_valid_password)
            }
        }


        binding.btn.setOnClickListener {


            if (TextUtils.isEmpty(binding.fname.text.toString())) {
                //  Toast.makeText(this@MainActivity3, "enter full name", Toast.LENGTH_LONG).show
                binding.tilfname.error = resources.getString(R.string.enter_first_name)
            } else if (TextUtils.isEmpty(binding.lname.text.toString())) {
                //   Toast.makeText(this@MainActivity3, "enter last name", Toast.LENGTH_SHORT).show()
                binding.tillname.error = resources.getString(R.string.enter_last_name)
            } else if (TextUtils.isEmpty(binding.email.text.toString())) {
                // Toast.makeText(this@MainActivity3, "enter email", Toast.LENGTH_LONG).show()
                binding.tilemail.error = resources.getString(R.string.enter_email)
            } else if (!matches(Patterns.EMAIL_ADDRESS.toString(), binding.email.text.toString())) {
                //Toast.makeText(this@MainActivity3, "enter valid email", Toast.LENGTH_LONG).show()

                binding.tilemail.error = resources.getString(R.string.enter_valid_email)
            } else if (TextUtils.isEmpty(binding.pswrd.text.toString())) {
                // Toast.makeText(this@MainActivity3, resources.getString(R.string.enter_password), Toast.LENGTH_LONG).show()
                binding.tilpswrd.error = resources.getString(R.string.enter_password)

            } else if (binding.pswrd.text.toString().length < 6) {
                // Toast.makeText(this@MainActivity3, "password should be of 6 digit length", Toast.LENGTH_LONG).show()
                binding.tilpswrd.error = resources.getString(R.string.short_password)
            } else if (!binding.conpswrd.text.toString().equals(binding.pswrd.text.toString())) {
                //  Toast.makeText(this@MainActivity3, "password and confirm password should match", Toast.LENGTH_LONG).show()
                binding.tilconpswrd.error = resources.getString(R.string.password_not_matched)

            } else if (binding.phn.text.toString().length < 10) {
                //Toast.makeText(this@MainActivity3, "mobile number should not be less than 10 digits", Toast.LENGTH_LONG).show()
                binding.tilphn.error = resources.getString(R.string.invalid_phn)

            } else if (binding.rdgrp.checkedRadioButtonId == -1) {
                Toast.makeText(
                    this@MainActivity2,
                    "Select Gender",
                    Toast.LENGTH_LONG
                ).show()
            } else if (!binding.chcbx.isChecked) {
                Toast.makeText(
                    this@MainActivity2,
                    "Accept terms",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
         fun createAccount(email: String, password: String) {
            // [START create_user_with_email]
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "createUserWithEmail:success")
                        val user = auth.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            // [END create_user_with_email]
        }

         fun updateRealTimeDatabase() {
            val user = auth.currentUser
            var myRef: DatabaseReference = database.getReference(user!!.uid)

            myRef.setValue(user!!.email)
            startActivity(Intent(this@MainActivity2, ProfileActivity::class.java))
            this.finish()
        }
    }


}

