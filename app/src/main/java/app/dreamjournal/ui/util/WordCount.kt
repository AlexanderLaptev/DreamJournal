package app.dreamjournal.ui.util

import android.icu.text.BreakIterator
import java.util.Locale

fun String.countWords(locale: Locale = Locale.getDefault()): Int {
    val iterator = BreakIterator.getWordInstance(locale)
    iterator.setText(this)

    var count = 0
    var start = 0
    var end = iterator.first()
    while (end != BreakIterator.DONE) {
        val candidate = this.substring(start, end)
        if (candidate.isWord()) count++
        start = end
        end = iterator.next()
    }
    return count
}

fun String.isWord(): Boolean = if (length == 1) this[0].isLetterOrDigit() else isNotBlank()
