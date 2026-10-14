package com.shafi.myjetpackapplication

import com.shafi.myjetpackapplication.models.Footnote
import com.shafi.myjetpackapplication.models.SURAH_NAMES
import com.shafi.myjetpackapplication.models.TranslationSegment
import com.shafi.myjetpackapplication.models.parseFootnotes
import com.shafi.myjetpackapplication.models.plainTranslation
import com.shafi.myjetpackapplication.models.surahName
import com.shafi.myjetpackapplication.models.toArabicIndicDigits
import com.shafi.myjetpackapplication.models.translationSegments
import org.junit.Assert.assertEquals
import org.junit.Test

class VerseFormattingTest {

    @Test
    fun translationSegments_splitsMarkersAndDropsAyahPrefix() {
        assertEquals(
            listOf(
                TranslationSegment("In the name of Allāh,", false),
                TranslationSegment("2", true),
                TranslationSegment(" the Merciful.", false),
                TranslationSegment("3", true),
            ),
            translationSegments("(1) In the name of Allāh,[2] the Merciful.[3]")
        )
    }

    @Test
    fun plainTranslation_keepsBracketedWords() {
        assertEquals(
            "[All] praise is [due] to Allāh, Lord of the worlds -",
            plainTranslation("(2) [All] praise is [due] to Allāh, Lord[4] of the worlds -")
        )
    }

    @Test
    fun parseFootnotes_readsEachNote() {
        assertEquals(
            listOf(Footnote("2", "First note."), Footnote("3", "Second note.\ncontinued")),
            parseFootnotes("[2]- First note.\n[3]- Second note.\ncontinued")
        )
        assertEquals(emptyList<Footnote>(), parseFootnotes(""))
    }

    @Test
    fun surahNames_coverAllSurahs() {
        assertEquals(114, SURAH_NAMES.size)
        assertEquals("Al-Fatihah", surahName(1))
        assertEquals("At-Takathur", surahName(102))
        assertEquals("Al-Ikhlas", surahName(112))
        assertEquals("An-Nas", surahName(114))
    }

    @Test
    fun arabicIndicDigits() {
        assertEquals("١٠٢", 102.toArabicIndicDigits())
    }
}
