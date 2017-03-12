package com.punksta.apps.openrecycle.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.ImageView
import com.punksta.apps.openrecycle.HelpActivity
import com.punksta.apps.openrecycle.MainActivity
import com.punksta.apps.openrecycle.R


/**
 * Created by stanislav on 3/11/17.
 */
class MenuFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_menu, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Dialog_NoTitle)
    }

    override fun onStart() {
        super.onStart()
        val d = dialog
        if (d != null) {
            d.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            d.window.setGravity(Gravity.TOP)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById(R.id.remove_menu).setOnClickListener { dismiss() }
        val menuItems = view.findViewById(R.id.list_view) as ViewGroup
        menuItems.removeAllViews()
        val c = context

        MenuItem.values().forEach { menuItem ->
            val item = LayoutInflater.from(context).inflate(R.layout.menu_item, menuItems, false)

            with(item.findViewById(R.id.icon) as ImageView) {
                setImageResource(menuItem.iconRes)
            }

            with(item.findViewById(R.id.text) as CheckedTextView) {
                setText(menuItem.titleRes)
                isChecked  = c is MenuItemHolder && c.menuNameId == menuItem.titleRes
            }

            item.setOnClickListener {
                onMenuClicked(menuItem)
            }

            menuItems.addView(item)
        }
    }

    fun onMenuClicked(menuItem: MenuItem) {
        val nextActivity  = when (menuItem) {
            MenuItem.SORT -> MainActivity::class.java
            MenuItem.HELP -> HelpActivity::class.java
            else -> null
        }

        if (nextActivity != null && nextActivity.isInstance(activity).not())
            startActivity(Intent(activity, nextActivity))

        dismiss()
    }
}

