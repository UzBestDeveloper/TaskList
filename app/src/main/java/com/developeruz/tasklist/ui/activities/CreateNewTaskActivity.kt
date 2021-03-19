package com.developeruz.tasklist.ui.activities

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.developeruz.tasklist.R
import com.developeruz.tasklist.TaskApplication
import com.developeruz.tasklist.databinding.ActivityCreateNewTaskBinding
import com.developeruz.tasklist.db.Task
import com.developeruz.tasklist.ui.fragments.allTasks.AllTasksViewModel
import com.developeruz.tasklist.ui.fragments.allTasks.AllTasksViewModelFactory
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CreateNewTaskActivity : AppCompatActivity() {

    private var status: Int = 0
    private lateinit var binding: ActivityCreateNewTaskBinding
    private var date : Long = Calendar.getInstance().time.time

    private val viewModel: AllTasksViewModel by viewModels {
        AllTasksViewModelFactory((application as TaskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewTaskBinding.inflate(layoutInflater)
        initView()
        setContentView(binding.root)
    }

    @SuppressLint("SimpleDateFormat")
    private fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            // set the calendar date as calendar view selected date
            calendar.set(year,month,dayOfMonth)
            date = calendar.time.time
        }

        binding.buttonDone.setOnClickListener {
            status = 1
            binding.textViewStatus.text = resources.getString(R.string.done)
            changeButtonBKG(binding.buttonDone, binding.buttonInProgress)
        }

        binding.buttonInProgress.setOnClickListener {
            status = 0
            binding.textViewStatus.text = resources.getString(R.string.in_progress)
            changeButtonBKG(binding.buttonInProgress, binding.buttonDone)
        }

        binding.buttonCreate.setOnClickListener {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val selectedDate: String = sdf.format(Date(date))

            val task = Task(
                0,
                binding.editTextName.text.toString(),
                status,
                selectedDate
            )
            viewModel.insert(task)
            finish()
        }
    }

    private fun changeButtonBKG(btn: Button, btn2: Button) {
        btn.setBackgroundTintList(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    this,
                    R.color.black
                )
            )
        )
        btn2.setBackgroundTintList(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    this,
                    R.color.main_color
                )
            )
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true

    }

}