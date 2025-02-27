package com.maestre.wisdompills.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import com.maestre.wisdompills.R
import com.maestre.wisdompills.databinding.FragmentImagesBinding


class ImagesFragment : Fragment() {

    private lateinit var binding: FragmentImagesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val webView : WebView = binding.webWiewImages
        webView.loadUrl("https://www.freepik.es/fotos-populares")
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK)
        super.onViewCreated(view, savedInstanceState)
    }


}