package com.example.numluck

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.numluck.databinding.ActivityResultsBinding
import com.example.numluck.databinding.ItemColorCardBinding
import com.example.numluck.databinding.ItemDateBadgeBinding
import com.example.numluck.databinding.ItemNumberCardBinding
import com.example.numluck.databinding.ItemSectionHeaderBinding
import com.example.numluck.databinding.ItemWeekdayCardBinding
import com.google.android.material.button.MaterialButton
import java.util.*

class ResultsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultsBinding
    private var firstName: String = ""
    private var lastName: String = ""
    private var day: Int = 0
    private var month: Int = 0
    private var year: Int = 0

    private var lifePathNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firstName = intent.getStringExtra("FIRST_NAME") ?: ""
        lastName = intent.getStringExtra("LAST_NAME") ?: ""
        day = intent.getIntExtra("DAY", 1)
        month = intent.getIntExtra("MONTH", 1)
        year = intent.getIntExtra("YEAR", 1990)

        calculateLifePath()

        setupTabs()
        showLuckyNumbers() // Default tab
    }

    private fun setupTabs() {
        val buttons = listOf(binding.btnLuckyNumbers, binding.btnLuckyDates, binding.btnLuckyWeekdays, binding.btnLuckyColors)
        
        buttons.forEach { button ->
            button.setOnClickListener {
                setActiveTab(it as MaterialButton)
                when (it.id) {
                    R.id.btnLuckyNumbers -> showLuckyNumbers()
                    R.id.btnLuckyDates -> showLuckyDates()
                    R.id.btnLuckyWeekdays -> showLuckyWeekdays()
                    R.id.btnLuckyColors -> showLuckyColors()
                }
            }
        }
        
        setActiveTab(binding.btnLuckyNumbers)
    }

    private fun setActiveTab(activeButton: MaterialButton) {
        val buttons = listOf(binding.btnLuckyNumbers, binding.btnLuckyDates, binding.btnLuckyWeekdays, binding.btnLuckyColors)
        val activeTint = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.surface_3))
        val inactiveTint = ColorStateList.valueOf(Color.TRANSPARENT)
        buttons.forEach { button ->
            if (button == activeButton) {
                button.backgroundTintList = activeTint
                button.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
            } else {
                button.backgroundTintList = inactiveTint
                button.setTextColor(ContextCompat.getColor(this, R.color.text_muted))
            }
        }
    }

    private fun addSectionHeader(titleResId: Int, subtitleResId: Int) {
        val headerBinding = ItemSectionHeaderBinding.inflate(layoutInflater, binding.contentContainer, false)
        headerBinding.tvSectionTitle.setText(titleResId)
        headerBinding.tvSectionSubtitle.setText(subtitleResId)
        binding.contentContainer.addView(headerBinding.root)
    }

    private fun showLuckyNumbers() {
        binding.contentContainer.removeAllViews()
        addSectionHeader(R.string.numerology_title, R.string.numerology_subtitle)

        val gridLayout = GridLayout(this).apply {
            columnCount = 2
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        }
        binding.contentContainer.addView(gridLayout)

        val numbers = listOf(
            Triple(lifePathNumber, getString(R.string.life_path_title), getString(R.string.life_path_desc)),
            Triple(calculateDestiny(), getString(R.string.destiny_title), getString(R.string.destiny_desc)),
            Triple(calculateSoulUrge(), getString(R.string.soul_urge_title), getString(R.string.soul_urge_desc)),
            Triple(calculatePersonality(), getString(R.string.personality_title), getString(R.string.personality_desc)),
            Triple(reduceToSingleDigit(day), getString(R.string.birthday_title), getString(R.string.birthday_desc)),
            Triple(calculateMaturity(), getString(R.string.maturity_title), getString(R.string.maturity_desc)),
            Triple(calculatePersonalYear(), getString(R.string.personal_year_title), getString(R.string.personal_year_desc))
        )

        numbers.forEach { (num, title, desc) ->
            val cardBinding = ItemNumberCardBinding.inflate(layoutInflater, gridLayout, false)
            cardBinding.tvNumber.text = num.toString()
            cardBinding.tvTitle.text = title
            cardBinding.tvDescription.text = desc
            
            val params = GridLayout.LayoutParams()
            params.width = 0
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            cardBinding.root.layoutParams = params
            
            gridLayout.addView(cardBinding.root)
        }
    }

    private fun showLuckyDates() {
        binding.contentContainer.removeAllViews()
        addSectionHeader(R.string.lucky_dates_title, R.string.lucky_dates_subtitle)

        val flowLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        }
        // Simplified: use a horizontal scroll view for the badges
        val horizontalScroll = android.widget.HorizontalScrollView(this)
        horizontalScroll.addView(flowLayout)
        binding.contentContainer.addView(horizontalScroll)

        val calendar = Calendar.getInstance()
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        val target = reduceToSingleDigit(lifePathNumber)

        for (i in 1..daysInMonth) {
            if (reduceToSingleDigit(i) == target) {
                val badgeBinding = ItemDateBadgeBinding.inflate(layoutInflater, flowLayout, false)
                badgeBinding.tvDate.text = i.toString()
                flowLayout.addView(badgeBinding.root)
            }
        }
    }

    private fun showLuckyWeekdays() {
        binding.contentContainer.removeAllViews()
        addSectionHeader(R.string.lucky_weekdays_title, R.string.lucky_weekdays_subtitle)

        val weekdays = when (reduceToSingleDigit(lifePathNumber)) {
            1 -> listOf("Sunday", "Monday")
            2 -> listOf("Monday", "Friday")
            3 -> listOf("Thursday", "Sunday")
            4 -> listOf("Saturday", "Wednesday")
            5 -> listOf("Wednesday", "Thursday")
            6 -> listOf("Friday", "Tuesday")
            7 -> listOf("Monday", "Sunday")
            8 -> listOf("Saturday", "Tuesday")
            9 -> listOf("Tuesday", "Thursday")
            else -> listOf("Monday")
        }

        weekdays.forEach { dayName ->
            val cardBinding = ItemWeekdayCardBinding.inflate(layoutInflater, binding.contentContainer, false)
            cardBinding.tvWeekday.text = dayName
            binding.contentContainer.addView(cardBinding.root)
        }
    }

    private fun showLuckyColors() {
        binding.contentContainer.removeAllViews()
        addSectionHeader(R.string.lucky_colors_title, R.string.lucky_colors_subtitle)

        val colors = when (lifePathNumber) {
            1 -> listOf("Gold" to "#FFD700", "Orange" to "#FFA500")
            2 -> listOf("White" to "#FFFFFF", "Silver" to "#C0C0C0")
            3 -> listOf("Rose Pink" to "#FF66CC", "Purple" to "#800080")
            4 -> listOf("Emerald Green" to "#50C878", "Blue" to "#0000FF")
            5 -> listOf("Gray" to "#808080", "Silver" to "#C0C0C0")
            6 -> listOf("Royal Blue" to "#4169E1", "Indigo" to "#4B0082")
            7 -> listOf("Maroon" to "#800000", "Brown" to "#A52A2A")
            8 -> listOf("Navy" to "#000080", "Black" to "#000000")
            9 -> listOf("Red" to "#FF0000", "Violet" to "#EE82EE")
            11 -> listOf("Gold" to "#FFD700", "White" to "#FFFFFF")
            22 -> listOf("Royal Blue" to "#4169E1", "White" to "#FFFFFF")
            33 -> listOf("Rose Pink" to "#FF66CC", "Gold" to "#FFD700")
            else -> listOf("White" to "#FFFFFF", "Gold" to "#FFD700")
        }

        colors.forEach { (name, hex) ->
            val cardBinding = ItemColorCardBinding.inflate(layoutInflater, binding.contentContainer, false)
            cardBinding.tvColorName.text = name
            cardBinding.viewColor.backgroundTintList = ColorStateList.valueOf(Color.parseColor(hex))
            binding.contentContainer.addView(cardBinding.root)
        }
    }

    // --- Calculation Logic ---

    private fun calculateLifePath() {
        val d = reduceToSingleDigitWithMaster(day)
        val m = reduceToSingleDigitWithMaster(month)
        val y = reduceToSingleDigitWithMaster(year)
        
        var sum = d + m + y
        lifePathNumber = reduceToSingleDigitWithMaster(sum)
    }

    private fun calculateDestiny(): Int {
        val fullName = (firstName + lastName).uppercase()
        var sum = 0
        for (char in fullName) {
            if (char in 'A'..'Z') {
                val value = (char - 'A') % 9 + 1
                sum += value
            }
        }
        return reduceToSingleDigitWithMaster(sum)
    }

    private fun calculateSoulUrge(): Int {
        val vowels = "AEIOU"
        val fullName = (firstName + lastName).uppercase()
        var sum = 0
        for (char in fullName) {
            if (char in vowels) {
                val value = (char - 'A') % 9 + 1
                sum += value
            }
        }
        return reduceToSingleDigitWithMaster(sum)
    }

    private fun calculatePersonality(): Int {
        val vowels = "AEIOU"
        val fullName = (firstName + lastName).uppercase()
        var sum = 0
        for (char in fullName) {
            if (char in 'A'..'Z' && char !in vowels) {
                val value = (char - 'A') % 9 + 1
                sum += value
            }
        }
        return reduceToSingleDigitWithMaster(sum)
    }

    private fun calculateMaturity(): Int {
        return reduceToSingleDigitWithMaster(lifePathNumber + calculateDestiny())
    }

    private fun calculatePersonalYear(): Int {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val d = reduceToSingleDigitWithMaster(day)
        val m = reduceToSingleDigitWithMaster(month)
        val y = reduceToSingleDigitWithMaster(currentYear)
        return reduceToSingleDigitWithMaster(d + m + y)
    }

    private fun reduceToSingleDigit(n: Int): Int {
        var num = n
        while (num > 9) {
            var sum = 0
            while (num > 0) {
                sum += num % 10
                num /= 10
            }
            num = sum
        }
        return num
    }

    private fun reduceToSingleDigitWithMaster(n: Int): Int {
        if (n == 11 || n == 22 || n == 33) return n
        var num = n
        while (num > 9 && num != 11 && num != 22 && num != 33) {
            var sum = 0
            var tempNum = num
            while (tempNum > 0) {
                sum += tempNum % 10
                tempNum /= 10
            }
            num = sum
        }
        return num
    }
}
