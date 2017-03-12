package com.punksta.apps.openrecycle

import android.os.Bundle
import com.punksta.apps.openrecycle.ui.BaseActivity
import com.punksta.apps.openrecycle.ui.MenuItem
import com.punksta.apps.openrecycle.ui.MenuItemHolder

/**
 * Created by stanislav on 3/12/17.
 */
class HelpActivity : BaseActivity(), MenuItemHolder {
    override val menuNameId: Int
        get() = MenuItem.HELP.titleRes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
    }
}