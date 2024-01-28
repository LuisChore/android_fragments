package com.luischore.viewpagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager2
    lateinit var adapter: MyPagerAdapter
    lateinit var tablayout: TabLayout

    var tabNames = arrayOf("ONE","TWO","THREE")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewPager = findViewById(R.id.viewPager2)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        adapter = MyPagerAdapter(supportFragmentManager,lifecycle)
        adapter.addFragmentToList(FragmentOne())
        adapter.addFragmentToList(FragmentTwo())
        adapter.addFragmentToList(FragmentThree())

        // connecting the Adapter with ViewPager2
        viewPager.adapter = adapter

        //connecting TabLayout with ViewPager
        tablayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(
            tablayout,
            viewPager
        ){
            tab,position ->
            tab.text = tabNames[position]

        }.attach()

    }
}