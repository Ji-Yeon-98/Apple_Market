package com.example.applemarket

import android.Manifest
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarket.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var click: Boolean = false // 초기 값 설정

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //데이터 설정
        var itemList = mutableListOf<ItemModel>()
        itemList.add(ItemModel(R.drawable.sample1, "산진 한달된 선풍기 팝니다", "이사가서 필요가 없어졌어요 급하게 내놓습니다", "대현동", 1000, "서울 서대문구 창천동", 13, 25, false))
        itemList.add(ItemModel(R.drawable.sample2, "김치냉장고", "이사로인해 내놔요", "안마담", 20000, "인천 계양구 귤현동", 8, 28, false))
        itemList.add(ItemModel(R.drawable.sample3, "샤넬 카드지갑", "고퀄지갑이구요\n사용감이 있어서 싸게 내어둡니다", "코코유", 10000, "수성구 범어동", 23, 5, false))
        itemList.add(ItemModel(R.drawable.sample4, "금고", "금고\n떼서 가져가야함\n대우월드마크센텀\n미국이주관계로 싸게 팝니다", "Nicole", 10000, "해운대구 우제2동", 14, 17, false))
        itemList.add(ItemModel(R.drawable.sample5, "갤럭시Z플립3 팝니다", "갤럭시 Z플립3 그린 팝니다\n항시 케이스 씌워서 썻고 필름 한장챙겨드립니다\n화면에 살짝 스크래치난거 말고 크게 이상은없습니다!", "절명", 150000, "연제구 연산제8동", 22, 9, false))
        itemList.add(ItemModel(R.drawable.sample6, "프라다 복조리백", "까임 오염없고 상태 깨끗합니다\n정품여부모름", "미니멀하게", 50000, "수원시 영통구 원천동", 25, 16, false))
        itemList.add(ItemModel(R.drawable.sample7, "울산 동해오션뷰 60평 복층 펜트하우스 1일 숙박권 펜션 힐링 숙소 별장", "울산 동해바다뷰 60평 복층 펜트하우스 1일 숙박권\n(에어컨이 없기에 낮은 가격으로 변경했으며 8월 초 가장 더운날 다녀가신 분 경우 시원했다고 잘 지내다 가셨습니다)\n1. 인원: 6명 기준입니다. 1인 10,000원 추가요금\n2. 장소: 북구 블루마시티, 32-33층\n3. 취사도구, 침구류, 세면도구, 드라이기 2개, 선풍기 4대 구비\n4. 예약방법: 예약금 50,000원 하시면 저희는 명함을 드리며 입실 오전 잔금 입금하시면 저희는 동.호수를 알려드리며 고객님은 예약자분 신분증 앞면 주민번호 뒷자리 가리시거나 지우시고 문자로 보내주시면 저희는 카드키를 우편함에 놓아 둡니다.\n5. 33층 옥상 야외 테라스 있음, 가스버너 있음\n6. 고기 굽기 가능\n7. 입실 오후 3시, 오전 11시 퇴실, 정리, 정돈 , 밸브 잠금 부탁드립니다.\n8. 층간소음 주의 부탁드립니다.\n9. 방3개, 화장실3개, 비데 3개\n10. 저희 집안이 쓰는 별장입니다.", "굿리치", 150000, "남구 옥동", 142, 54, false))
        itemList.add(ItemModel(R.drawable.sample8, "샤넬 탑핸들 가방", "샤넬 트랜디 CC 탑핸들 스몰 램스킨 블랙 금장 플랩백 !\n" + "\n" + "색상 : 블랙\n" + "사이즈 : 25.5cm * 17.5cm * 8cm\n" + "구성 : 본품더스트\n" + "\n" + "급하게 돈이 필요해서 팝니다 ㅠ ㅠ", "난쉽", 180000, "동래구 온천제2동", 31, 7, false))
        itemList.add(ItemModel(R.drawable.sample9, "4행정 엔진분무기 판매합니다.", "3년전에 사서 한번 사용하고 그대로 둔 상태입니다. 요즘 사용은 안해봤습니다. 그래서 저렴하게 내 놓습니다. 중고라 반품은 어렵습니다.\n", "알뜰한", 30000, "원주시 명륜2동", 7, 28, false))
        itemList.add(ItemModel(R.drawable.sample10, "셀린느 버킷 가방", "22년 신세계 대전 구매입니당\n" + "셀린느 버킷백\n" + "구매해서 몇번사용했어요\n" + "까짐 스크래치 없습니다.\n" + "타지역에서 보내는거라 택배로 진행합니당!", "똑태현", 190000, "중구 동화동", 40, 6, false))


        // 어댑터 생성 후 연결
        val adapter = ItemAdapter(itemList)
        binding.rvItem.adapter = adapter
        binding.rvItem.layoutManager = LinearLayoutManager(this)
        binding.rvItem.addItemDecoration(
            DividerItemDecoration(
                binding.rvItem.context,
                LinearLayoutManager.VERTICAL
            )
        )


        //DetailActivity에서 받아온 정보로 itemList 데이터 변경 + 화면 갱신
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val modifiedItem = result.data!!.getParcelableExtra<ItemModel>("item")
                if (modifiedItem != null) {
                    for (index in itemList.indices) {
                        if (itemList[index].name == modifiedItem.name) {
                            itemList[index] = modifiedItem
                            adapter.notifyDataSetChanged()
                            break
                        }
                    }
                }
            }
        }

        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 500 }
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 500 }

        //스크롤을 밑으로 내렸을 때 fab 버튼 보여줌
        binding.rvItem.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!binding.rvItem.canScrollVertically(-1)) {
                    binding.fab.startAnimation(fadeOut)
                    binding.fab.hide()
                }else{
                    binding.fab.show()
                    binding.fab.startAnimation(fadeIn)
                }
            }
        })


        //fab 눌렀을 때 스크롤 맨 위로 이동
        binding.fab.setOnClickListener {
            binding.rvItem.scrollToPosition(0)
        }


        //fab 클릭했을 때 아이콘 색상 변경
        val iconTintStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf()
            ),
            intArrayOf(
                resources.getColor(R.color.orange),
                resources.getColor(R.color.black)
            )
        )

        binding.fab.imageTintList = iconTintStateList


        //어댑터 리스트 중 하나 클릭하면 그 정보의 DetailActivtiy로 이동
        adapter.itemClick = object : ItemAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
                val intent = Intent(view.context, DetailActivity::class.java)
                intent.putExtra("item", itemList[position])

                resultLauncher.launch(intent)

            }
        }


        //어댑터 리스트 중 하나를 롱클릭하면 다이얼로그 생성 + 삭제
        adapter.itemLongClick = object : ItemAdapter.ItemLongClick{
            override fun onLongClick(view: View, position: Int): Boolean {
                var builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("상품 삭제")
                builder.setIcon(R.drawable.comment)
                builder.setMessage("상품을 정말로 삭제하시겠습니까?")

                val listener = object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        when(p1){
                            DialogInterface.BUTTON_POSITIVE -> adapter.deleteItem(position)
                        }
                    }
                }

                builder.setPositiveButton("확인", listener)
                builder.setNegativeButton("취소", null)

                builder.show()

                return true
            }
        }


        //알림 버튼과 연결
        binding.ibNotication.setOnClickListener {
            notication()
        }

    }

    //애뮬레이터 뒤로가기 이벤트
    override fun onBackPressed() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("종료")
        builder.setIcon(R.drawable.comment)
        builder.setMessage("정말 종료하시겠습니까?")

        val listener = object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                when(p1){
                    DialogInterface.BUTTON_POSITIVE -> finishAffinity()
                }
            }
        }

        builder.setPositiveButton("확인", listener)
        builder.setNegativeButton("취소", null)

        builder.show()
    }


    //알림 이벤트
    fun notication() {
        val myNotificationID = 1
        val channelID = "default"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Android 8.0

            // 알림 채널 생성
            val channel = NotificationChannel(channelID, "default channel",
                NotificationManager.IMPORTANCE_DEFAULT)

            // 채널에 대한 설명
            channel.description = "description text of this channel."

            //NotificationManager 객체 가져옴 : 알림 관리+생성 가능
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            //채널을 NotificationManager에 등록
            notificationManager.createNotificationChannel(channel)

            val builder = NotificationCompat.Builder(this, channelID)

            builder.run {
                setSmallIcon(R.mipmap.ic_launcher)
                setWhen(System.currentTimeMillis())
                setContentTitle("키워드 알림")
                setContentText("설정한 키워드에 대한 알림이 도착했습니다!!")
            }

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            NotificationManagerCompat.from(this).notify(myNotificationID, builder.build())

        }
    }
}