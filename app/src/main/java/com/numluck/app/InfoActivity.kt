package com.numluck.app

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.numluck.app.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val kind = intent.getStringExtra(EXTRA_KIND) ?: KIND_NUMBER
        val title = intent.getStringExtra(EXTRA_TITLE) ?: ""
        val subtitle = intent.getStringExtra(EXTRA_SUBTITLE) ?: ""
        val body = intent.getStringExtra(EXTRA_BODY) ?: ""
        val displayValue = intent.getStringExtra(EXTRA_DISPLAY_VALUE) ?: ""
        val accentHex = intent.getStringExtra(EXTRA_ACCENT_HEX)

        binding.tvScreenTitle.text = title
        binding.tvHeroTitle.text = title
        binding.tvHeroSubtitle.text = subtitle
        binding.tvBody.text = body

        if (kind == KIND_COLOR && accentHex != null) {
            binding.tvHeroValue.visibility = android.view.View.GONE
            binding.viewHeroColor.visibility = android.view.View.VISIBLE
            binding.viewHeroColor.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(accentHex))
        } else {
            binding.tvHeroValue.text = displayValue
            binding.tvHeroValue.visibility = android.view.View.VISIBLE
            binding.viewHeroColor.visibility = android.view.View.GONE
        }

        binding.btnBack.setOnClickListener { finish() }
    }

    companion object {
        const val EXTRA_KIND = "KIND"
        const val EXTRA_TITLE = "TITLE"
        const val EXTRA_SUBTITLE = "SUBTITLE"
        const val EXTRA_BODY = "BODY"
        const val EXTRA_DISPLAY_VALUE = "DISPLAY_VALUE"
        const val EXTRA_ACCENT_HEX = "ACCENT_HEX"

        const val KIND_NUMBER = "number"
        const val KIND_DATE = "date"
        const val KIND_WEEKDAY = "weekday"
        const val KIND_COLOR = "color"
    }
}