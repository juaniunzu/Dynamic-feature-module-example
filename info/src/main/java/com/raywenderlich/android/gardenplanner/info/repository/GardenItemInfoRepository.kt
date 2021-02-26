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
package com.raywenderlich.android.gardenplanner.info.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raywenderlich.android.gardenplanner.info.R

import com.raywenderlich.android.gardenplanner.info.model.GardenItemInfo
import com.raywenderlich.android.gardenplanner.model.GardenItem

object GardenItemInfoRepository {

  fun getItemInfo(gardenItem: GardenItem): LiveData<GardenItemInfo?> {
    val gardenItemInfos = listOf(
      GardenItemInfo(1, R.string.cabbage_notes),
      GardenItemInfo(2, R.string.carrot_notes),
      GardenItemInfo(3, R.string.grass_notes),
      GardenItemInfo(4, R.string.lettuce_notes),
      GardenItemInfo(5, R.string.onions_notes),
      GardenItemInfo(6, R.string.path_notes),
      GardenItemInfo(7, R.string.peppers_notes),
      GardenItemInfo(8, R.string.tulips_notes)
    )

    return MutableLiveData(gardenItemInfos.find { it.gardenItemId == gardenItem.id })
  }
}