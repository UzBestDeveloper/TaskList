package com.developeruz.tasklist.ui.activities

import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developeruz.tasklist.R
import com.developeruz.tasklist.ui.adapters.viewpager.BasicViewPager2Adapter
import com.developeruz.tasklist.databinding.ActivityMainBinding
import com.developeruz.tasklist.ui.fragments.DoneTasksFragment
import com.developeruz.tasklist.ui.fragments.InProgressTasksFragment
import com.developeruz.tasklist.ui.fragments.allTasks.AllTasksFragment
import com.developeruz.tasklist.ui.fragments.allTasks.AllTasksViewModel
import com.developeruz.tasklist.utils.hideKeyboard
import com.developeruz.tasklist.utils.setCurrentItem
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: AllTasksViewModel by viewModels()

    private val fragments: MutableList<Fragment> = LinkedList()
    private var lastClickedId = R.id.all

    override fun setup() {

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        lastClickedId = R.id.all
                    }
                    1 -> {
                        lastClickedId = R.id.done
                    }
                    2 -> {
                        lastClickedId = R.id.in_progress
                    }
                }
                binding.bottomNavigationView.menu.getItem(position).isChecked = true
            }
        })

        fragments.add(AllTasksFragment())
        fragments.add(DoneTasksFragment())
        fragments.add(InProgressTasksFragment())
        binding.viewPager2.adapter = BasicViewPager2Adapter(this, fragments)
        binding.viewPager2.offscreenPageLimit = 3

    }

    override fun clicks() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            if (lastClickedId != item.itemId) {
                when (item.itemId) {
                    R.id.all -> {
                        binding.viewPager2.setCurrentItem(0, 150)
                        lastClickedId = R.id.all
                    }
                    R.id.done -> {
                        binding.viewPager2.setCurrentItem(1, 150)
                        lastClickedId = R.id.done
                    }
                    R.id.in_progress -> {
                        binding.viewPager2.setCurrentItem(2, 150)
                        lastClickedId = R.id.in_progress
                    }
                }
                hideKeyboard(this)
                true
            } else {
                false
            }
        }

        binding.imageViewAdd.setOnClickListener {
            val intent = Intent(this, CreateNewTaskActivity::class.java)
            startActivity(intent)
        }

    }

    override fun observe() {
        viewModel.tasks.observe(this) {
            binding.textViewAll.text = it.size.toString()
        }

        viewModel.inProgressTasks.observe(this) {
            binding.textViewInProgress.text = it.size.toString()
        }

        viewModel.doneTasks.observe(this) {
            binding.textViewDone.text = it.size.toString()
        }

        viewModel.todayTasks.observe(this) {
            binding.textViewToday.text = it.size.toString()
        }
    }

}