package com.example.numluck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.numluck.databinding.ActivityPolicyBinding

class PolicyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPolicyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getStringExtra(EXTRA_TYPE) ?: TYPE_PRIVACY
        val (titleRes, bodyRes) = when (type) {
            TYPE_TERMS -> R.string.terms_title to R.string.terms_body
            else -> R.string.privacy_title to R.string.privacy_body
        }

        binding.tvScreenTitle.setText(titleRes)
        binding.tvPolicyTitle.setText(titleRes)
        binding.tvPolicyBody.setText(bodyRes)

        binding.btnBack.setOnClickListener { finish() }
    }

    companion object {
        const val EXTRA_TYPE = "POLICY_TYPE"
        const val TYPE_PRIVACY = "privacy"
        const val TYPE_TERMS = "terms"
    }
}
