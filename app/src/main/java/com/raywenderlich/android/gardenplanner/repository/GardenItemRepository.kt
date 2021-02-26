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
package com.raywenderlich.android.gardenplanner.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raywenderlich.android.gardenplanner.R
import com.raywenderlich.android.gardenplanner.model.GardenItem

object GardenItemRepository {

  /** Returns hardcoded list of possible items to put in your garden. */
  fun getAvailableItems(): LiveData<List<GardenItem>> {
    val gardenItems = listOf(
      GardenItem(1,R.string.cabbage, R.drawable.icon_cabbage, R.drawable.tile_cabbage),
      GardenItem(2, R.string.carrot, R.drawable.icon_carrot, R.drawable.tile_carrot),
      GardenItem(3, R.string.grass, R.drawable.icon_grass, R.drawable.tile_grass),
      GardenItem(4, R.string.lettuce, R.drawable.icon_lettuce, R.drawable.tile_lettuce),
      GardenItem(5, R.string.onion, R.drawable.icon_onion, R.drawable.tile_onion),
      GardenItem(6, R.string.path, R.drawable.icon_path, R.drawable.tile_path),
      GardenItem(7, R.string.pepper, R.drawable.icon_pepper, R.drawable.tile_pepper),
      GardenItem(8, R.string.tulip, R.drawable.icon_tulip, R.drawable.tile_tulip)
    )

    return MutableLiveData<List<GardenItem>>(gardenItems)
  }
}