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
package com.raywenderlich.android.gardenplanner

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_garden_plan.*


class GardenPlanFragment : Fragment() {

  private lateinit var viewModel: GardenPlanViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_garden_plan, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setActionbarTitle(R.string.app_name, false)

    val adapter = GardenPlanAdapter()
    gardenPlanRecyclerView.adapter = adapter

    gardenPlanRecyclerView.isNestedScrollingEnabled = true

    fabAddNote.setOnClickListener {
      findNavController().navigate(GardenPlanFragmentDirections.actionAddNote(null))
    }

    viewModel = ViewModelProviders.of(this).get(GardenPlanViewModel::class.java)
    viewModel.gardenPlan.observe(viewLifecycleOwner, Observer {
      if(gardenPlanRecyclerView.layoutManager != null) {
        gardenPlanRecyclerView.layoutManager =
          GridLayoutManager(context, it.height, GridLayoutManager.HORIZONTAL, false)
      }

      adapter.updateSections(it)

      adapter.onSectionClickListener = { section ->
        if(section.item == null) {
          findNavController().navigate(GardenPlanFragmentDirections.actionSetSection(section))
        } else {
          findNavController().navigate(GardenPlanFragmentDirections.actionSectionDetails(section))
        }
      }
    })
  }

}
