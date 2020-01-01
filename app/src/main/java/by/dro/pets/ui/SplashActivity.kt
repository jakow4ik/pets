package by.dro.pets.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.dro.pets.R
import by.dro.pets.MainActivity
import android.content.Intent



class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
