package com.example.applemarket

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.example.applemarket.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar


class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemModel = intent.getParcelableExtra<ItemModel>("item")

        itemModel?.let {
            binding.ivItem.setImageResource(itemModel.itemImg)
            binding.ivProfile.clipToOutline = true

            binding.tvName.text = itemModel.name
            binding.tvAddress.text = itemModel.address
            binding.tvTitle.text = itemModel.title
            binding.tvDescription.text = itemModel.description
            binding.tvPrice.text = priceText(itemModel.price)

            if(itemModel.heartClicked == false){
                binding.ibHeart.setImageResource(R.drawable.heart)
            }else if(itemModel.heartClicked == true){
                binding.ibHeart.setImageResource(R.drawable.full_heart)
            }

            binding.ibHeart.scaleType = ImageView.ScaleType.CENTER_INSIDE
        }

        //하트 버튼 눌렀을 때 이벤트
        binding.ibHeart.setOnClickListener {
            itemModel?.heartClicked = !itemModel?.heartClicked!!

            //클릭되지 않았을 때
            if(itemModel?.heartClicked == false){

                binding.ibHeart.setImageResource(R.drawable.heart)
                itemModel?.heartClicked = false
                itemModel?.heart = itemModel?.heart?.plus(-1)!!

            }else if(itemModel.heartClicked == true){

                binding.ibHeart.setImageResource(R.drawable.full_heart)
                itemModel?.heartClicked = true
                itemModel?.heart = itemModel?.heart?.plus(1)!!

                //snackbar 출력
                var snackbar = Snackbar.make(binding.detailLayout, "관심 목록에 추가되었습니다.", Snackbar.LENGTH_LONG)
                snackbar.show()
            }

            binding.ibHeart.scaleType = ImageView.ScaleType.CENTER_INSIDE
        }


        //뒤로가기 버튼 눌렀을 때 이벤트
        binding.ibBack.setOnClickListener {
            val intent = Intent()
            intent.putExtra("item", itemModel) // 수정된 아이템을 인텐트에 추가
            setResult(RESULT_OK, intent)
            finish()
        }

    }

    fun priceText(price: Int): String {
        var result = ""

        val priceStr = price.toString()
        val length = priceStr.length

        for (i in 0 until length) {
            val digit = priceStr[length - 1 - i]
            result = digit + result
            if (i > 0 && i % 3 == 2 && i != length - 1) {
                result = ",$result"
            }
        }

        return result + "원"
    }
}