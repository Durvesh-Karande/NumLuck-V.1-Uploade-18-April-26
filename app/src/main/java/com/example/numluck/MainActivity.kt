package com.example.numluck

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.numluck.databinding.ActivityMainBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var selectedDay: Int = 0
    private var selectedMonth: Int = 0
    private var selectedYear: Int = 0

    private val displayFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etDateOfBirth.setOnClickListener { showDatePicker() }
        binding.etDateOfBirth.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) showDatePicker()
        }

        binding.btnSubmit.setOnClickListener { validateAndSubmit() }

        binding.tvPrivacyPolicy.setOnClickListener {
            startActivity(Intent(this, PolicyActivity::class.java).apply {
                putExtra(PolicyActivity.EXTRA_TYPE, PolicyActivity.TYPE_PRIVACY)
            })
        }
        binding.tvTerms.setOnClickListener {
            startActivity(Intent(this, PolicyActivity::class.java).apply {
                putExtra(PolicyActivity.EXTRA_TYPE, PolicyActivity.TYPE_TERMS)
            })
        }
    }

    private fun showDatePicker() {
        val constraints = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointBackward.now())
            .build()

        val initialSelection = if (selectedYear != 0) {
            Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                set(selectedYear, selectedMonth - 1, selectedDay, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
        } else {
            MaterialDatePicker.todayInUtcMilliseconds()
        }

        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.pick_date_title)
            .setCalendarConstraints(constraints)
            .setSelection(initialSelection)
            .build()

        picker.addOnPositiveButtonClickListener { millis ->
            val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                timeInMillis = millis
            }
            selectedDay = cal.get(Calendar.DAY_OF_MONTH)
            selectedMonth = cal.get(Calendar.MONTH) + 1
            selectedYear = cal.get(Calendar.YEAR)
            binding.etDateOfBirth.setText(displayFormat.format(cal.time))
        }

        picker.show(supportFragmentManager, "dob_picker")
    }

    private fun validateAndSubmit() {
        val firstName = binding.etFirstName.text.toString().trim()
        val lastName = binding.etLastName.text.toString().trim()

        if (firstName.isEmpty() || lastName.isEmpty() || selectedYear == 0) {
            Toast.makeText(this, R.string.error_fill_all, Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidDate(selectedDay, selectedMonth, selectedYear)) {
            Toast.makeText(this, R.string.error_invalid_date, Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, ResultsActivity::class.java).apply {
            putExtra("FIRST_NAME", firstName)
            putExtra("LAST_NAME", lastName)
            putExtra("DAY", selectedDay)
            putExtra("MONTH", selectedMonth)
            putExtra("YEAR", selectedYear)
        }
        startActivity(intent)
    }

    private fun isValidDate(day: Int, month: Int, year: Int): Boolean {
        if (year < 1900 || year > 2100) return false
        if (month < 1 || month > 12) return false

        val maxDays = when (month) {
            4, 6, 9, 11 -> 30
            2 -> if (isLeapYear(year)) 29 else 28
            else -> 31
        }

        return day in 1..maxDays
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }
}
