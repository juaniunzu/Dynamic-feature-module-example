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

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.raywenderlich.android.gardenplanner.db.GardenPlannerDatabase
import com.raywenderlich.android.gardenplanner.db.GardenSectionEntity
import com.raywenderlich.android.gardenplanner.model.GardenPlan
import com.raywenderlich.android.gardenplanner.model.GardenSection
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object GardenRepository {

  private const val GARDEN_WIDTH = 20

  private const val GARDEN_HEIGHT = 10

  private fun createGardenPlanLiveData(sectionsFromDatabase: List<GardenSectionEntity>) = Transformations.switchMap(
    GardenItemRepository.getAvailableItems()
  ) { gardenItems ->
    MutableLiveData<GardenPlan>(
      GardenPlan(
        GARDEN_WIDTH,
        GARDEN_HEIGHT,
        sectionsFromDatabase.map { gardenSection ->
          GardenSection(
            gardenSection.id,
            gardenSection.itemId?.let { itemId -> gardenItems.find { it.id == itemId } })
        })
    )
  }

  fun getGardenPlan(context: Context): LiveData<GardenPlan> {
    val gardenPlanDao = GardenPlannerDatabase.getInstance(context).gardenPlanDao()

    return Transformations.switchMap(gardenPlanDao.getSections()) { sections ->
      if (sections.isEmpty()) {
        GlobalScope.async { resetGarden(context) }

        Transformations.switchMap(gardenPlanDao.getSections()) { reloadedSections ->
          createGardenPlanLiveData(reloadedSections)
        }
      } else {
        createGardenPlanLiveData(sections)
      }
    }
  }

  fun updateGardenSection(context: Context, gardenSection: GardenSection) {
    val gardenPlanDao = GardenPlannerDatabase.getInstance(context).gardenPlanDao()

    GlobalScope.launch { gardenPlanDao.updateSection(GardenSectionEntity(gardenSection.id, gardenSection.item?.id ?: 0)) }
  }

  private fun resetGarden(context: Context) {
    val gardenPlanDao = GardenPlannerDatabase.getInstance(context).gardenPlanDao()

    gardenPlanDao.clearSections()

    val numberOfSections = GARDEN_WIDTH * GARDEN_HEIGHT

    // Populate empty sections.
    for (i in 0..numberOfSections) {
      gardenPlanDao.addSection(GardenSectionEntity())
    }
  }


}