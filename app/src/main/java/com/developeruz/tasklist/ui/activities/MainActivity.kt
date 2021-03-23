package com.developeruz.tasklist.ui.activities

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.developeruz.tasklist.R
import com.developeruz.tasklist.TaskApplication
import com.developeruz.tasklist.adapters.viewpager.BasicViewPager2Adapter
import com.developeruz.tasklist.databinding.ActivityMainBinding
import com.developeruz.tasklist.ui.fragments.allTasks.AllTasksFragment
import com.developeruz.tasklist.ui.fragments.DoneTasksFragment
import com.developeruz.tasklist.ui.fragments.InProgressTasksFragment
import com.developeruz.tasklist.ui.fragments.allTasks.AllTasksViewModel
import com.developeruz.tasklist.ui.fragments.allTasks.AllTasksViewModelFactory
import com.developeruz.tasklist.utils.hideKeyboard
import com.developeruz.tasklist.utils.vibrate
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragments: MutableList<Fragment> = LinkedList()
    private var lastClickedId = R.id.all

    private val viewModel: AllTasksViewModel by viewModels {
        AllTasksViewModelFactory((application as TaskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
        initViewPager();    }

    private fun initViewPager() {

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

    private fun initView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(
            mOnNavigationItemSelectedListener
        )

        binding.imageViewAdd.setOnClickListener {
            val intent = Intent(this, CreateNewTaskActivity::class.java)
            startActivity(intent)
        }


        observeData()
    }

    private fun observeData() {
        viewModel.tasks.observe(this, {
            binding.textViewAll.text = it.size.toString()
        })

        viewModel.inProgressTasks.observe(this, {
            binding.textViewInProgress.text = it.size.toString()
        })

        viewModel.doneTasks.observe(this, {
            binding.textViewDone.text = it.size.toString()
        })

        viewModel.todayTasks.observe(this, {
            binding.textViewToday.text = it.size.toString()
        })
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        if (lastClickedId != item.itemId) {
            when (item.itemId) {
                R.id.all -> {
                    binding.viewPager2.setCurrentItem(0,150)
                    lastClickedId = R.id.all
                }
                R.id.done -> {
                    binding.viewPager2.setCurrentItem(1,150)
                    lastClickedId = R.id.done
                }
                R.id.in_progress -> {
                    binding.viewPager2.setCurrentItem(2,150)
                    lastClickedId = R.id.in_progress
                }
            }
            vibrate(this)
            hideKeyboard(this)
            true
        } else {
            false
        }
    }


    private fun ViewPager2.setCurrentItem(
        item: Int,
        duration: Long,
        interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
        pagePxWidth: Int = width
    ) {
        val pxToDrag: Int = pagePxWidth * (item - currentItem)
        val animator = ValueAnimator.ofInt(0, pxToDrag)
        var previousValue = 0
        animator.addUpdateListener { valueAnimator ->
            val currentValue = valueAnimator.animatedValue as Int
            val currentPxToDrag = (currentValue - previousValue).toFloat()
            fakeDragBy(-currentPxToDrag)
            previousValue = currentValue
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) { beginFakeDrag() }
            override fun onAnimationEnd(animation: Animator?) { endFakeDrag() }
            override fun onAnimationCancel(animation: Animator?) { /* Ignored */ }
            override fun onAnimationRepeat(animation: Animator?) { /* Ignored */ }
        })
        animator.interpolator = interpolator
        animator.duration = duration
        animator.start()
    }


}