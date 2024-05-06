package coid.bcafinance.sistriandroid

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import coid.bcafinance.sistriandroid.Fragments.ConsumerFragment
import coid.bcafinance.sistriandroid.Fragments.ProfileFragment
import coid.bcafinance.sistriandroid.Fragments.ProfileFragmentOld
import coid.bcafinance.sistriandroid.adapter.MainPagerAdapter
import coid.bcafinance.sistriandroid.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        initializeViewPager()

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)

        if (token == null) {
            // Pengguna belum login, navigasi ke LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun initializeViewPager() {
        val adapter = MainPagerAdapter(this)
        adapter.addFragment(ConsumerFragment())
        adapter.addFragment(ProfileFragment())
        with(binding) {
            vpMain.adapter = adapter
            TabLayoutMediator(tabMain, vpMain) { tab, position ->
                when(position) {
                    0 -> tab.text = "Consumer"
                    1 -> tab.text = "Profile"
                }
            }.attach()
        }
    }
}
