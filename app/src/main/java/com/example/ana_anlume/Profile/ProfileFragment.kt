package com.example.ana_anlume.Profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ana_anlume.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val linkedin = view.findViewById<TextView>(R.id.btnLinkedin)
        val github = view.findViewById<TextView>(R.id.btnGithub)
        val instagram = view.findViewById<TextView>(R.id.btnInstagram)

        linkedin.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.linkedin.com/in/fitriana-tasya-178821363")))
        }

        github.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/fitriana24si-bit/prak_mobile.git")))
        }

        instagram.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.instagram.com/ftrntsy?utm_source=ig_web_button_share_sheet&igsh=ZDNlZDc0MzIxNw==")))
        }
    }
}