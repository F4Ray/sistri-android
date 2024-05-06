package coid.bcafinance.sistriandroid

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coid.bcafinance.sistriandroid.config.RetrofitInstance
import coid.bcafinance.sistriandroid.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)


        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)


        binding.loginBtn.setOnClickListener {
            val username = binding.txtEmail.text.toString()
            val password = binding.txtPassword.text.toString()

            println("halooo")
            loginUser(username, password)
        }
    }

    private fun loginUser(username: String, password: String) {
        val credentials = hashMapOf(
            "username" to username,
            "password" to password
        )

        CoroutineScope(Dispatchers.IO).launch {
            val response = try {
                RetrofitInstance.authAPI.login(credentials)
            } catch (e: Exception) {
                println(e)
                // Tangani error
                return@launch

            }

            withContext(Dispatchers.Main) {
                if (response.success) {
                    // Login berhasil, simpan token
                    saveToken(response.data?.token,response.data?.firstName,response.data?.lastName,response.data?.email)
                    // Navigasi ke halaman berikutnya
                    navigateToMainActivity()
                } else {
                    // Tampilkan pesan error
                    showLoginErrorToast(response.message)
                }
            }
        }
    }

    private fun saveToken(token: String?,firstName:String?, lastName:String?, email:String?) {
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.putString("firstName", firstName)
        editor.putString("lastName", lastName)
        editor.putString("email", email)

        editor.apply()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun showLoginErrorToast(response:String) {
        Toast.makeText(this, "Login error : $response", Toast.LENGTH_SHORT).show()
    }
}