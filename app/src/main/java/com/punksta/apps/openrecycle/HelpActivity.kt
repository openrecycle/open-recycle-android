package com.punksta.apps.openrecycle

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.punksta.apps.openrecycle.model.Model.garbageTypes
import com.punksta.apps.openrecycle.ui.BaseActivity
import com.punksta.apps.openrecycle.ui.MenuItem
import com.punksta.apps.openrecycle.ui.MenuItemHolder

/**
 * Created by stanislav on 3/12/17.
 */
class HelpActivity : BaseActivity(), MenuItemHolder {
    override val menuNameId: Int
        get() = MenuItem.HELP.titleRes

    val spinner
        get() = findViewById(R.id.spinner) as Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, garbageTypes.map { it.second })
    }

}