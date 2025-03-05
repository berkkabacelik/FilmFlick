package com.example.filmflick.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.filmflick.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding ile XML bağlama
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Login butonuna tıklanınca
        binding.loginButton.setOnClickListener { view ->
            val email = binding.loginEmail.text.toString().trim()
            val password = binding.loginPassword.text.toString().trim()

            if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (password.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Giriş başarılı!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Giriş başarısız! ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    binding.loginPassword.error = "Şifre boş olamaz!"
                }
            } else {
                binding.loginEmail.error = "Geçerli bir e-posta giriniz!"
            }

            // Klavyeyi gizle
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        // Kayıt ol sayfasına yönlendirme
        binding.signupRedirectText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
