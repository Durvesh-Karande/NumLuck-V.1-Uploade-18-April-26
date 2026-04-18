package com.numluck.app

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.numluck.app.databinding.ActivityResultsBinding
import com.numluck.app.databinding.ItemColorCardBinding
import com.numluck.app.databinding.ItemNumberCardBinding
import com.numluck.app.databinding.ItemSectionHeaderBinding
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

        binding.btnBack.setOnClickListener { finish() }

        binding.tvHeroGreeting.text = getString(R.string.hero_greeting, firstName)
        binding.tvHeroNumber.text = lifePathNumber.toString()

        binding.tvHeroNumber.setOnClickListener {
            openNumberInfo("Life Path", lifePathNumber)
        }

        setupTabs()
        binding.segmentedTabs.check(R.id.btnLuckyNumbers)
        showLuckyNumbers()
    }

    private fun setupTabs() {
        binding.segmentedTabs.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            updateTabVisuals(checkedId)
            when (checkedId) {
                R.id.btnLuckyNumbers -> showLuckyNumbers()
                R.id.btnLuckyDates -> showLuckyDates()
                R.id.btnLuckyWeekdays -> showLuckyWeekdays()
                R.id.btnLuckyColors -> showLuckyColors()
            }
        }
        updateTabVisuals(R.id.btnLuckyNumbers)
    }

    private fun updateTabVisuals(activeId: Int) {
        val buttons = listOf(
            binding.btnLuckyNumbers,
            binding.btnLuckyDates,
            binding.btnLuckyWeekdays,
            binding.btnLuckyColors
        )
        val activeBg = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.surface_3))
        val inactiveBg = ColorStateList.valueOf(Color.TRANSPARENT)
        val activeText = ContextCompat.getColor(this, R.color.text_primary)
        val inactiveText = ContextCompat.getColor(this, R.color.text_muted)
        buttons.forEach { btn ->
            if (btn.id == activeId) {
                btn.backgroundTintList = activeBg
                btn.setTextColor(activeText)
            } else {
                btn.backgroundTintList = inactiveBg
                btn.setTextColor(inactiveText)
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
            useDefaultMargins = false
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(-dp(6), 0, -dp(6), 0) }
            layoutParams = lp
        }
        binding.contentContainer.addView(gridLayout)

        data class NumEntry(val num: Int, val title: String, val desc: String, val kind: String)
        val numbers = listOf(
            NumEntry(calculateDestiny(), getString(R.string.destiny_title), getString(R.string.destiny_desc), "Destiny"),
            NumEntry(calculateSoulUrge(), getString(R.string.soul_urge_title), getString(R.string.soul_urge_desc), "Soul Urge"),
            NumEntry(calculatePersonality(), getString(R.string.personality_title), getString(R.string.personality_desc), "Personality"),
            NumEntry(reduceToSingleDigit(day), getString(R.string.birthday_title), getString(R.string.birthday_desc), "Birthday"),
            NumEntry(calculateMaturity(), getString(R.string.maturity_title), getString(R.string.maturity_desc), "Maturity"),
            NumEntry(calculatePersonalYear(), getString(R.string.personal_year_title), getString(R.string.personal_year_desc), "Personal Year")
        )

        numbers.forEach { entry ->
            val cardBinding = ItemNumberCardBinding.inflate(layoutInflater, gridLayout, false)
            cardBinding.tvNumber.text = entry.num.toString()
            cardBinding.tvTitle.text = entry.title
            cardBinding.tvDescription.text = entry.desc

            val params = GridLayout.LayoutParams()
            params.width = 0
            params.height = GridLayout.LayoutParams.WRAP_CONTENT
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL)
            params.setMargins(dp(6), dp(6), dp(6), dp(6))
            cardBinding.root.layoutParams = params

            cardBinding.root.isClickable = true
            cardBinding.root.isFocusable = true
            cardBinding.root.setOnClickListener {
                openNumberInfo(entry.kind, entry.num)
            }

            gridLayout.addView(cardBinding.root)
        }
    }

    private fun showLuckyDates() {
        binding.contentContainer.removeAllViews()
        addSectionHeader(R.string.lucky_dates_title, R.string.lucky_dates_subtitle)

        val calendar = Calendar.getInstance()
        val monthName = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(calendar.time)

        val monthLabel = TextView(this).apply {
            text = monthName
            setTextColor(ContextCompat.getColor(this@ResultsActivity, R.color.text_primary))
            textSize = 16f
            typeface = android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL)
            gravity = Gravity.CENTER
            setPadding(0, dp(4), 0, dp(12))
        }
        binding.contentContainer.addView(monthLabel)

        val calendarCard = com.google.android.material.card.MaterialCardView(this).apply {
            radius = dp(20).toFloat()
            cardElevation = 0f
            setCardBackgroundColor(ContextCompat.getColor(this@ResultsActivity, R.color.surface_1))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams = lp
        }
        binding.contentContainer.addView(calendarCard)

        val grid = GridLayout(this).apply {
            columnCount = 7
            setPadding(dp(12), dp(16), dp(12), dp(16))
        }
        calendarCard.addView(grid)

        val weekdayLabels = listOf("M", "T", "W", "T", "F", "S", "S")
        weekdayLabels.forEach { label ->
            val tv = TextView(this).apply {
                text = label
                setTextColor(ContextCompat.getColor(this@ResultsActivity, R.color.text_muted))
                textSize = 11f
                typeface = android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL)
                gravity = Gravity.CENTER
            }
            val lp = GridLayout.LayoutParams().apply {
                width = 0
                height = dp(28)
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }
            grid.addView(tv, lp)
        }

        val monthCal = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val daysInMonth = monthCal.getActualMaximum(Calendar.DAY_OF_MONTH)
        val firstDayOfWeek = monthCal.get(Calendar.DAY_OF_WEEK) // Sun=1..Sat=7
        val leadingBlanks = (firstDayOfWeek + 5) % 7 // Convert so Mon=0

        for (i in 0 until leadingBlanks) {
            val blank = View(this)
            val lp = GridLayout.LayoutParams().apply {
                width = 0
                height = dp(44)
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }
            grid.addView(blank, lp)
        }

        val today = Calendar.getInstance()
        val todayDay = if (today.get(Calendar.MONTH) == monthCal.get(Calendar.MONTH) &&
                          today.get(Calendar.YEAR) == monthCal.get(Calendar.YEAR)) {
            today.get(Calendar.DAY_OF_MONTH)
        } else -1

        val target = reduceToSingleDigit(lifePathNumber)

        for (d in 1..daysInMonth) {
            val isLucky = reduceToSingleDigit(d) == target
            val isToday = d == todayDay
            val cell = buildCalendarCell(d, isLucky, isToday)
            if (isLucky) {
                cell.isClickable = true
                cell.isFocusable = true
                cell.setOnClickListener { openDateInfo(d) }
            }
            val lp = GridLayout.LayoutParams().apply {
                width = 0
                height = dp(44)
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }
            grid.addView(cell, lp)
        }
    }

    private fun buildCalendarCell(day: Int, isLucky: Boolean, isToday: Boolean): View {
        val container = FrameLayout(this)
        val disc = View(this).apply {
            val size = dp(36)
            val lp = FrameLayout.LayoutParams(size, size, Gravity.CENTER)
            layoutParams = lp
            background = when {
                isLucky -> ContextCompat.getDrawable(this@ResultsActivity, R.drawable.bg_hero_disc)
                isToday -> ContextCompat.getDrawable(this@ResultsActivity, R.drawable.bg_today_ring)
                else -> null
            }
        }
        container.addView(disc)

        val tv = TextView(this).apply {
            text = day.toString()
            textSize = 14f
            gravity = Gravity.CENTER
            val color = when {
                isLucky -> ContextCompat.getColor(this@ResultsActivity, R.color.white)
                else -> ContextCompat.getColor(this@ResultsActivity, R.color.text_primary)
            }
            setTextColor(color)
            typeface = android.graphics.Typeface.create(
                "sans-serif-medium",
                if (isLucky) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL
            )
            val lp = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT,
                Gravity.CENTER
            )
            layoutParams = lp
        }
        container.addView(tv)

        return container
    }

    private fun showLuckyWeekdays() {
        binding.contentContainer.removeAllViews()
        addSectionHeader(R.string.lucky_weekdays_title, R.string.lucky_weekdays_subtitle)

        val lucky = when (reduceToSingleDigit(lifePathNumber)) {
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

        val weekLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dp(20) }
            layoutParams = lp
        }
        binding.contentContainer.addView(weekLayout)

        val weekOrder = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        val letters = listOf("M", "T", "W", "T", "F", "S", "S")
        weekOrder.forEachIndexed { idx, dayName ->
            val isLucky = lucky.contains(dayName)
            val cell = FrameLayout(this).apply {
                val lp = LinearLayout.LayoutParams(0, dp(52), 1f).apply {
                    setMargins(dp(3), 0, dp(3), 0)
                }
                layoutParams = lp
                background = ContextCompat.getDrawable(
                    this@ResultsActivity,
                    if (isLucky) R.drawable.bg_weekday_active else R.drawable.bg_weekday_inactive
                )
            }
            val tv = TextView(this).apply {
                text = letters[idx]
                textSize = 15f
                typeface = android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.BOLD)
                gravity = Gravity.CENTER
                setTextColor(
                    ContextCompat.getColor(
                        this@ResultsActivity,
                        if (isLucky) R.color.white else R.color.text_muted
                    )
                )
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            }
            cell.addView(tv)
            weekLayout.addView(cell)
        }

        lucky.forEach { dayName ->
            val card = com.google.android.material.card.MaterialCardView(this).apply {
                radius = dp(16).toFloat()
                cardElevation = 0f
                setCardBackgroundColor(ContextCompat.getColor(this@ResultsActivity, R.color.surface_1))
                isClickable = true
                isFocusable = true
                setOnClickListener { openWeekdayInfo(dayName) }
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { bottomMargin = dp(10) }
                layoutParams = lp
            }
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(dp(20), dp(18), dp(20), dp(18))
            }
            val dot = View(this).apply {
                val lp = LinearLayout.LayoutParams(dp(8), dp(8)).apply {
                    marginEnd = dp(16)
                }
                layoutParams = lp
                background = ContextCompat.getDrawable(this@ResultsActivity, R.drawable.bg_accent_dot)
            }
            val name = TextView(this).apply {
                text = dayName
                setTextColor(ContextCompat.getColor(this@ResultsActivity, R.color.text_primary))
                textSize = 17f
                typeface = android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL)
            }
            row.addView(dot)
            row.addView(name)
            card.addView(row)
            binding.contentContainer.addView(card)
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
            cardBinding.tvColorHex.text = hex.uppercase()
            cardBinding.viewColor.backgroundTintList = ColorStateList.valueOf(Color.parseColor(hex))
            cardBinding.root.isClickable = true
            cardBinding.root.isFocusable = true
            cardBinding.root.setOnClickListener { openColorInfo(name, hex) }
            binding.contentContainer.addView(cardBinding.root)
        }
    }

    private fun openNumberInfo(kind: String, value: Int) {
        val title = "$kind Number"
        val subtitle = getString(R.string.info_number_subtitle)
        val body = NumerologyInterpretations.numberMeaning(kind, value)
        val intent = Intent(this, InfoActivity::class.java).apply {
            putExtra(InfoActivity.EXTRA_KIND, InfoActivity.KIND_NUMBER)
            putExtra(InfoActivity.EXTRA_TITLE, title)
            putExtra(InfoActivity.EXTRA_SUBTITLE, subtitle)
            putExtra(InfoActivity.EXTRA_BODY, body)
            putExtra(InfoActivity.EXTRA_DISPLAY_VALUE, value.toString())
        }
        startActivity(intent)
    }

    private fun openDateInfo(date: Int) {
        val title = getString(R.string.lucky_date_title)
        val subtitle = getString(R.string.info_date_subtitle)
        val body = NumerologyInterpretations.dateMeaning(date, lifePathNumber)
        val intent = Intent(this, InfoActivity::class.java).apply {
            putExtra(InfoActivity.EXTRA_KIND, InfoActivity.KIND_DATE)
            putExtra(InfoActivity.EXTRA_TITLE, title)
            putExtra(InfoActivity.EXTRA_SUBTITLE, subtitle)
            putExtra(InfoActivity.EXTRA_BODY, body)
            putExtra(InfoActivity.EXTRA_DISPLAY_VALUE, date.toString())
        }
        startActivity(intent)
    }

    private fun openWeekdayInfo(dayName: String) {
        val title = dayName
        val subtitle = getString(R.string.info_weekday_subtitle)
        val body = NumerologyInterpretations.weekdayMeaning(dayName, lifePathNumber)
        val displayValue = dayName.take(1).uppercase()
        val intent = Intent(this, InfoActivity::class.java).apply {
            putExtra(InfoActivity.EXTRA_KIND, InfoActivity.KIND_WEEKDAY)
            putExtra(InfoActivity.EXTRA_TITLE, title)
            putExtra(InfoActivity.EXTRA_SUBTITLE, subtitle)
            putExtra(InfoActivity.EXTRA_BODY, body)
            putExtra(InfoActivity.EXTRA_DISPLAY_VALUE, displayValue)
        }
        startActivity(intent)
    }

    private fun openColorInfo(colorName: String, hex: String) {
        val subtitle = getString(R.string.info_color_subtitle)
        val body = NumerologyInterpretations.colorMeaning(colorName, lifePathNumber)
        val intent = Intent(this, InfoActivity::class.java).apply {
            putExtra(InfoActivity.EXTRA_KIND, InfoActivity.KIND_COLOR)
            putExtra(InfoActivity.EXTRA_TITLE, colorName)
            putExtra(InfoActivity.EXTRA_SUBTITLE, subtitle)
            putExtra(InfoActivity.EXTRA_BODY, body)
            putExtra(InfoActivity.EXTRA_ACCENT_HEX, hex)
        }
        startActivity(intent)
    }

    private fun dp(v: Int): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, v.toFloat(), resources.displayMetrics
    ).toInt()

    private fun calculateLifePath() {
        val d = reduceToSingleDigitWithMaster(day)
        val m = reduceToSingleDigitWithMaster(month)
        val y = reduceToSingleDigitWithMaster(year)

        val sum = d + m + y
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
