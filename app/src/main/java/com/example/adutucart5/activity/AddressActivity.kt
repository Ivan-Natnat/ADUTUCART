package com.example.adutucart5.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.adutucart5.databinding.ActivityAddressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.prefs.Preferences

class AddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var totalCost: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        totalCost  = intent.getStringExtra("totalCost").toString()



        preferences =this.getSharedPreferences("user", MODE_PRIVATE)

        loadUserInfo()

        binding.checkout.setOnClickListener{
            validateData(
                binding.userNumber.text.toString(),
                binding.userName.text.toString(),
                binding.userVillage.text.toString(),
                binding.userCity.text.toString(),
                binding.userCountry.text.toString(),
                binding.userPin.text.toString()
            )

        }

    }

    private fun validateData(number: String, name: String, village: String, city: String,
                             state: String, pinCode: String) {
            if (number.isEmpty() || name.isEmpty() || village.isEmpty() || city.isEmpty() ||
                city.isEmpty() || state.isEmpty() || pinCode.isEmpty() ){

                Toast.makeText(this,"Please all fields", Toast.LENGTH_SHORT).show()

            }else {
                storeData(village,city,state,pinCode,)
            }
    }

    private fun storeData(village: String, city: String, state: String, pinCode: String) {

        val map = hashMapOf<String, Any>()
        map["village"] = village
        map["city"] = city
        map["state"] = state
        map["pinCode"] = pinCode

        Firebase.firestore.collection("users")
            .document(preferences.getString("number", "")!!)
            .update(map).addOnSuccessListener {

                val intent = Intent (this, CheckoutActivity::class.java)
                val b = Bundle()
                b.putStringArrayList("productIds", intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost", totalCost)
                intent.putExtras(b)

                startActivity(intent)


            }.addOnFailureListener{

                Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }


    private fun loadUserInfo() {


        Firebase.firestore.collection("users").document(preferences
            .getString("number", "")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userPhoneNumber"))
                binding.userVillage.setText(it.getString("village"))
                binding.userCity.setText(it.getString("city"))
                binding.userCountry.setText(it.getString("state"))
                binding.userPin.setText(it.getString("pinCode"))

            }.addOnFailureListener{

                Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }
}

