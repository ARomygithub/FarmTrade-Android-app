package com.bangkit.farmtrade.ui.detailproduct

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.farmtrade.databinding.ActivityDetailProductBinding
import com.bumptech.glide.Glide

class ActivityDetailProduct: AppCompatActivity() {
    private var _binding: ActivityDetailProductBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            detailTitle.text = ActivityDetailProductArgs.fromBundle(intent.extras!!).title
            detailProducer.text = ActivityDetailProductArgs.fromBundle(intent.extras!!).producer
            val image = ActivityDetailProductArgs.fromBundle(intent.extras!!).image
            Glide.with(this@ActivityDetailProduct).load(image).into(detailImage)
            detailDeskripsi.text = ActivityDetailProductArgs.fromBundle(intent.extras!!).description
            priceAfter.text = ActivityDetailProductArgs.fromBundle(intent.extras!!).priceAfter
            priceBefore.text = ActivityDetailProductArgs.fromBundle(intent.extras!!).priceBefore
            priceBefore.paintFlags = priceBefore.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            val phoneNumber = ActivityDetailProductArgs.fromBundle(intent.extras!!).phoneNumber
            val wa = "https://wa.me/$phoneNumber"
            btnBuy.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(wa)
                startActivity(i)
            }
            btnBack.setOnClickListener {
                finish()
            }
        }
    }
}