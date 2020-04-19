package com.vanced.manager.ui.fragments

import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.content.pm.PackageManager
import android.content.Intent
import android.view.*
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vanced.manager.adapter.SectionPageAdapter
import com.vanced.manager.R
import com.vanced.manager.ui.core.BaseFragment

class HomeFragment : BaseFragment() {

    private lateinit var sectionPageAdapter: SectionPageAdapter
    private lateinit var viewPager: ViewPager2

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.title_home)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        sectionPageAdapter = SectionPageAdapter(this)
        val tabLayout = view.findViewById(R.id.tablayout) as TabLayout
        viewPager = view.findViewById(R.id.viewpager)
        viewPager.adapter = sectionPageAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Vanced"
                1 -> tab.text = "MicroG"
            }
        }.attach()

        val pm = activity?.packageManager

        val microginstallbtn = getView()?.findViewById(R.id.microg_installbtn) as Button
        val microguninstallbtn = getView()?.findViewById(R.id.microg_uninstallbtn) as Button
        val microgsettingsbtn = getView()?.findViewById(R.id.microg_settingsbtn) as Button
        val vancedinstallbtn = getView()?.findViewById(R.id.vanced_installbtn) as Button

        val bravebtn = getView()?.findViewById(R.id.brave_button) as Button
        val websitebtn = getView()?.findViewById(R.id.website_button) as Button
        val discordbtn = getView()?.findViewById(R.id.discordbtn) as Button
        val telegrambtn = getView()?.findViewById(R.id.tgbtn) as Button
        val twitterbtn = getView()?.findViewById(R.id.twitterbtn) as Button
        val redditbtn = getView()?.findViewById(R.id.redditbtn) as Button

        val microgStatus = pm?.let { isPackageInstalled("com.mgoogle.android.gms", it) }
        val vancedStatus = pm?.let { isPackageInstalled("com.vanced.android.youtube", it)}

        vancedinstallbtn.setOnClickListener{
            view.findNavController().navigate(R.id.toInstallThemeFragment)
        }

        microginstallbtn.setOnClickListener {
            openUrl("https://youtu.be/dQw4w9WgXcQ", R.color.YT)
        }

        if (microgStatus!!) {
            microguninstallbtn.setOnClickListener {
                val uri = Uri.parse("package:com.mgoogle.android.gms")
                val mgUninstall = Intent(Intent.ACTION_DELETE, uri)
                startActivity(mgUninstall)
            }

            microgsettingsbtn.setOnClickListener {
                val intent = Intent()
                intent.component = ComponentName(
                    "com.mgoogle.android.gms",
                    "org.microg.gms.ui.SettingsActivity"
                )
                startActivity(intent)
            }
        }
        else {
            microgsettingsbtn.visibility = View.INVISIBLE
            microguninstallbtn.visibility = View.INVISIBLE
        }

         bravebtn.setOnClickListener {
            openUrl("https://brave.com/van874", R.color.Brave)

        }
        websitebtn.setOnClickListener {
            openUrl("https://vanced.app", R.color.Vanced)
        }
        discordbtn.setOnClickListener {
            openUrl("https://discord.gg/TUVd7rd", R.color.Discord)

        }
        telegrambtn.setOnClickListener {
            openUrl("https://t.me/joinchat/AAAAAEHf-pi4jH1SDIAL4w", R.color.Telegram)

        }
        twitterbtn.setOnClickListener {
            openUrl("https://twitter.com/YTVanced", R.color.Twitter)

        }
        redditbtn.setOnClickListener {
            openUrl("https://reddit.com/r/vanced", R.color.Reddit)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.about -> view?.findNavController()?.navigate(R.id.toAboutFragment)
        else -> null
    }?.let { true } ?: super.onOptionsItemSelected(item)

    private fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

}

