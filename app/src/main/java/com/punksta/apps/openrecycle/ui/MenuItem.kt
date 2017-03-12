package com.punksta.apps.openrecycle.ui

import com.punksta.apps.openrecycle.R

enum class MenuItem(val titleRes: Int, val iconRes: Int) {
    SORT(R.string.menu_sort, R.drawable.vec_capture),
    MAP(R.string.menu_map, R.drawable.vec_map_idle),
    HELP(R.string.menu_help, R.drawable.vec_heart)
}