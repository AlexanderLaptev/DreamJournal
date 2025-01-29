package app.dreamjournal.ui.util

import android.icu.text.BreakIterator

// TODO: get this working properly
fun String.countWords(): Int {
    val boundary = BreakIterator.getWordInstance()
    boundary.setText(this)

    var count = 0
    var end = boundary.next()
    while (end != BreakIterator.DONE) {
        count++
        end = boundary.next()
    }
    return count
}
