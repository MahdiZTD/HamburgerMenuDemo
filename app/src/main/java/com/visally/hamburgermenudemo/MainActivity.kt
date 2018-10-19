package com.visally.hamburgermenudemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.visally.hamburgertogglemenu.HamburgerToggleMenu

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var hamburgerMenu = findViewById<HamburgerToggleMenu>(R.id.hamburgerMenu)
        var hamburgerMenu2 = findViewById<HamburgerToggleMenu>(R.id.hamburgerMenu2)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        hamburgerMenu2.setOnClickListener {
            hamburgerMenu.toggleMenu()
            hamburgerMenu2.toggleMenu()
        }
    }
}
