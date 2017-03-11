package com.punksta.apps.openrecycle.ui

import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.punksta.apps.openrecycle.R

/**
 * Created by stanislav on 3/11/17.
 */
open class BaseActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        when (this) {
            is MenuItemHolder -> {
                (findViewById(R.id.toolbar_title) as? TextView)?.setText(menuNameId)
                findViewById(R.id.toolbar_menu)?.setOnClickListener {
                    showMenu()
                }

//                if (Build.VERSION.SDK_INT >= 21) {
//                    window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//                    findViewById(R.id.toolbar)?.also {
//                        (it.layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin = statusBarHeight
//
//                    }
//                }
            }
        }
    }

    private fun showMenu() {
        val dialogFrament = MenuFragment()
        dialogFrament.show(supportFragmentManager, "menu")
    }

    private val statusBarHeight
        get() : Int {
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            return if (resourceId > 0) {
                resources.getDimensionPixelSize(resourceId)
            } else {
                0
            }
        }
}