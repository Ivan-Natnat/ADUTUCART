package com.example.adutucart5.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.adutucart5.MainActivity
import com.example.adutucart5.R
import com.example.adutucart5.databinding.ActivityLoginBinding
import com.example.adutucart5.databinding.ActivityOtpactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button3.setOnClickListener{
            if(binding.userOTP.text!!.isEmpty()){
                Toast.makeText(this,"Please provide OTP", Toast.LENGTH_SHORT).show()
            }else{
                verifyUser(binding.userOTP.text.toString())
                
            }
        }
    }

    private fun verifyUser(otp: String) {

        val credential = PhoneAuthProvider.getCredential(
            intent.getStringExtra("verificationId")!!, otp)

        signInWithPhoneAuthCredential(credential)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()

                    val user = task.result?.user
                } else {
                    Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
    }
}