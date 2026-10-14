package com.shafi.myjetpackapplication.models

data class Footnote(val number: String, val text: String)

/** A run of translation text; [isMarker] runs hold a footnote number such as "2". */
data class TranslationSegment(val text: String, val isMarker: Boolean)

private val leadingAyahNumber = Regex("""^\(\d+\)\s*""")
private val footnoteMarker = Regex("""\[(\d+)]""")
private val footnoteLine = Regex("""^\[(\d+)]-?\s*(.*)$""")

/** Splits a translation into plain text and footnote markers, dropping the "(1) " ayah prefix. */
fun translationSegments(translation: String): List<TranslationSegment> {
    val text = translation.replaceFirst(leadingAyahNumber, "")
    val segments = mutableListOf<TranslationSegment>()
    var last = 0
    for (match in footnoteMarker.findAll(text)) {
        if (match.range.first > last) {
            segments += TranslationSegment(text.substring(last, match.range.first), isMarker = false)
        }
        segments += TranslationSegment(match.groupValues[1], isMarker = true)
        last = match.range.last + 1
    }
    if (last < text.length) segments += TranslationSegment(text.substring(last), isMarker = false)
    return segments
}

/** Translation without the ayah prefix or footnote markers, for compact previews. */
fun plainTranslation(translation: String): String =
    translationSegments(translation).filterNot { it.isMarker }.joinToString("") { it.text }.trim()

/** Parses "[2]- note" lines; lines without a marker continue the previous note. */
fun parseFootnotes(footnotes: String): List<Footnote> {
    val result = mutableListOf<Footnote>()
    for (line in footnotes.lines().map { it.trim() }.filter { it.isNotEmpty() }) {
        val match = footnoteLine.matchEntire(line)
        when {
            match != null -> result += Footnote(match.groupValues[1], match.groupValues[2].trim())
            result.isNotEmpty() -> result[result.lastIndex] =
                result.last().let { it.copy(text = it.text + "\n" + line) }
            else -> result += Footnote("", line)
        }
    }
    return result
}

fun Int.toArabicIndicDigits(): String =
    toString().map { if (it in '0'..'9') '٠' + (it - '0') else it }.joinToString("")

fun pluralize(count: Int, singular: String, plural: String = singular + "s"): String =
    "$count ${if (count == 1) singular else plural}"

fun surahName(number: Int): String = SURAH_NAMES.getOrNull(number - 1) ?: "Surah $number"

val SURAH_NAMES = listOf(
    "Al-Fatihah", "Al-Baqarah", "Ali 'Imran", "An-Nisa", "Al-Ma'idah", "Al-An'am", "Al-A'raf",
    "Al-Anfal", "At-Tawbah", "Yunus", "Hud", "Yusuf", "Ar-Ra'd", "Ibrahim", "Al-Hijr", "An-Nahl",
    "Al-Isra", "Al-Kahf", "Maryam", "Taha", "Al-Anbya", "Al-Hajj", "Al-Mu'minun", "An-Nur",
    "Al-Furqan", "Ash-Shu'ara", "An-Naml", "Al-Qasas", "Al-'Ankabut", "Ar-Rum", "Luqman",
    "As-Sajdah", "Al-Ahzab", "Saba", "Fatir", "Ya-Sin", "As-Saffat", "Sad", "Az-Zumar", "Ghafir",
    "Fussilat", "Ash-Shura", "Az-Zukhruf", "Ad-Dukhan", "Al-Jathiyah", "Al-Ahqaf", "Muhammad",
    "Al-Fath", "Al-Hujurat", "Qaf", "Adh-Dhariyat", "At-Tur", "An-Najm", "Al-Qamar", "Ar-Rahman",
    "Al-Waqi'ah", "Al-Hadid", "Al-Mujadila", "Al-Hashr", "Al-Mumtahanah", "As-Saff", "Al-Jumu'ah",
    "Al-Munafiqun", "At-Taghabun", "At-Talaq", "At-Tahrim", "Al-Mulk", "Al-Qalam", "Al-Haqqah",
    "Al-Ma'arij", "Nuh", "Al-Jinn", "Al-Muzzammil", "Al-Muddaththir", "Al-Qiyamah", "Al-Insan",
    "Al-Mursalat", "An-Naba", "An-Nazi'at", "'Abasa", "At-Takwir", "Al-Infitar", "Al-Mutaffifin",
    "Al-Inshiqaq", "Al-Buruj", "At-Tariq", "Al-A'la", "Al-Ghashiyah", "Al-Fajr", "Al-Balad",
    "Ash-Shams", "Al-Layl", "Ad-Duha", "Ash-Sharh", "At-Tin", "Al-'Alaq", "Al-Qadr", "Al-Bayyinah",
    "Az-Zalzalah", "Al-'Adiyat", "Al-Qari'ah", "At-Takathur", "Al-'Asr", "Al-Humazah", "Al-Fil",
    "Quraysh", "Al-Ma'un", "Al-Kawthar", "Al-Kafirun", "An-Nasr", "Al-Masad", "Al-Ikhlas",
    "Al-Falaq", "An-Nas",
)
