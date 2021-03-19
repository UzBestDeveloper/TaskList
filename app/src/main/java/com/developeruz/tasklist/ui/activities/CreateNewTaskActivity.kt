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
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CreateNewTaskActivity : AppCompatActivity() {

    private var mode: String = "create"
    private var status: Int = 0
    private lateinit var binding: ActivityCreateNewTaskBinding
    private var date: Long = Calendar.getInstance().time.time
    private var task: Task? = null

    private val viewModel: AllTasksViewModel by viewModels {
        AllTasksViewModelFactory((application as TaskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewTaskBinding.inflate(layoutInflater)
        initView()
        setContentView(binding.root)
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        clicks()

        editMode()

    }

    @SuppressLint("SimpleDateFormat")
    private fun editMode() {
        task = intent.getSerializableExtra("task") as Task?
        if (task != null) {
            mode = "edit"

            val format = SimpleDateFormat("dd/MM/yyyy")
            try {
                date = format.parse(task!!.due_date).time
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            binding.calendarView.date = date
            binding.buttonCreate.text = getString(R.string.update)
            binding.editTextName.setText(task!!.name)
            status = task!!.status

            if (status == 1) {
                binding.textViewStatus.text = resources.getString(R.string.done)
                changeButtonBKG(binding.buttonDone, binding.buttonInProgress)
            }else{
                binding.textViewStatus.text = resources.getString(R.string.in_progress)
                changeButtonBKG(binding.buttonInProgress, binding.buttonDone)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun clicks() {
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
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
            if (binding.editTextName.text.toString() != "") {
                if (mode == "edit" && task != null) {
                    val sdf = SimpleDateFormat("dd/MM/yyyy")
                    val selectedDate: String = sdf.format(Date(date))

                    task!!.name = binding.editTextName.text.toString()
                    task!!.status = status
                    task!!.due_date = selectedDate

                    viewModel.update(task!!)
                    finish()
                } else {
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
            }else{
                Snackbar.make(binding.root,getString(R.string.please_type_task_name),Snackbar.LENGTH_SHORT).show()
            }
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