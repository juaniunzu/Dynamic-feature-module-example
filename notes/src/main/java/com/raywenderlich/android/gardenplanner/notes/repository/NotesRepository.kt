/*
 * Copyright (c) 2019 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.raywenderlich.android.gardenplanner.notes.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.raywenderlich.android.gardenplanner.model.GardenSection
import com.raywenderlich.android.gardenplanner.notes.db.NoteDatabase
import com.raywenderlich.android.gardenplanner.notes.db.NoteEntity
import com.raywenderlich.android.gardenplanner.notes.model.Note
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object NotesRepository {

  fun getGeneralNotes(context: Context): LiveData<List<Note>> {
    val notesDao = NoteDatabase.getInstance(context).notesDao()

    return Transformations.map(notesDao.getGeneralNotes()) {notesList ->
      notesList.map { Note(it.id, it.date, it.text) }
    }
  }

  fun getNotesForSection(context: Context, gardenSection: GardenSection): LiveData<List<Note>> {
    val notesDao = NoteDatabase.getInstance(context).notesDao()

    return Transformations.map(notesDao.getSectionNotes(gardenSection.id)) {noteList ->
      noteList.map { Note(it.id, it.date, it.text) }
    }
  }

  fun addNoteForSecition(context: Context, gardenSection: GardenSection, note: Note) {
    val notesDao = NoteDatabase.getInstance(context).notesDao()

    GlobalScope.launch {
      notesDao.addNote(NoteEntity(sectionId = gardenSection.id, date = note.date, text = note.text))
    }
  }

  fun addGeneralNote(context: Context, note: Note) {
    val notesDao = NoteDatabase.getInstance(context).notesDao()

    GlobalScope.launch { notesDao.addNote(NoteEntity(date = note.date, text = note.text)) }
  }

  fun deleteNote(context: Context, note: Note) {
    val notesDao = NoteDatabase.getInstance(context).notesDao()

    GlobalScope.launch {
      notesDao.deleteNote(NoteEntity(note.id, null, note.date, note.text))
    }
  }
}