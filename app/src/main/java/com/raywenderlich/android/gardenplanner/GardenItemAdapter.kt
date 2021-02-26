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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.gardenplanner.model.GardenItem
import kotlinx.android.synthetic.main.item_garden_item.view.*

class GardenItemAdapter(private val gardenItems: List<GardenItem>) : RecyclerView.Adapter<GardenItemAdapter.ViewHolder>() {

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val itemIcon: ImageView = view.itemIcon
    val itemName: TextView =  view.itemName
  }

  var onItemClickListener : ((GardenItem) -> Unit)? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_garden_item, parent, false))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.itemIcon.setImageResource(gardenItems[position].iconRes)
    holder.itemName.setText(gardenItems[position].name)
    holder.itemView.setOnClickListener {
      onItemClickListener?.invoke(gardenItems[position])
    }
  }

  override fun getItemCount() = gardenItems.size
}