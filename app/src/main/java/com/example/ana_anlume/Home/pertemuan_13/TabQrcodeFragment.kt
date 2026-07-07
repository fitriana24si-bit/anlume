package com.example.ana_anlume.Home.pertemuan_13

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ana_anlume.Home.pertemuan_13.generate.QRGenerator
import com.example.ana_anlume.databinding.FragmentTabQrcodeBinding

class TabQrcodeFragment : Fragment() {

    private var _binding: FragmentTabQrcodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabQrcodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGenerate.setOnClickListener {
            val text = binding.etInput.text.toString().trim()
            if (text.isNotEmpty()) {
                val bitmap = QRGenerator.generateQRCode(text)
                if (bitmap != null) {
                    binding.ivQrCode.setImageBitmap(bitmap)
                    binding.cardQr.visibility = View.VISIBLE
                } else {
                    Toast.makeText(requireContext(), "Gagal membuat QR Code", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.tilInput.error = "Input tidak boleh kosong"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
