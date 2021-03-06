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
package com.raywenderlich.android.gardenplanner.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.raywenderlich.android.gardenplanner.notes.R
import com.raywenderlich.android.gardenplanner.setActionbarTitle
import kotlinx.android.synthetic.main.fragment_notes.*


class NotesFragment : Fragment() {

  private val args: NotesFragmentArgs by navArgs()

  private lateinit var viewModel: NotesViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_notes, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val titleRes = if (args.gardenSection != null) {
      R.string.section_notes
    } else {
      R.string.garden_notes
    }

    setActionbarTitle(titleRes, true)

    viewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)

    notesRecyclerView.adapter = NoteAdapter().also { adapter ->
      adapter.onNoteClickLIstener = { note ->
        findNavController().navigate(NotesFragmentDirections.actionShowNote(note))
      }

      args.gardenSection?.let { gardenSection ->
        viewModel.getSectionNotes(gardenSection).observe(viewLifecycleOwner, Observer {
          adapter.updateNotes(it)
        })
      } ?: viewModel.generalNotes.observe(viewLifecycleOwner, Observer {
        adapter.updateNotes(it)
      })
    }

    fabAddNote.setOnClickListener {
      findNavController().navigate(NotesFragmentDirections.actionAddNote(args.gardenSection))
    }
  }

}
