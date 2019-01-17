package net.iplace.iplacehelper.paint

import android.graphics.Path

/**
 * Created by ${DANavarro} on 28/12/2018.
 */
data class FingerPath(
        val color: Int,
        val emboss: Boolean,
        val blur: Boolean,
        val strokeWidth: Int,
        val path: Path
)