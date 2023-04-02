package com.mediring.app.survey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.mediring.app.R
import kotlinx.android.synthetic.main.activity_survey.*

class SurveyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        survey_webview.settings.loadWithOverviewMode = true
        survey_webview.settings.useWideViewPort = true
        survey_webview.settings.setSupportZoom(false)
        survey_webview.settings.javaScriptEnabled = true
        survey_webview.settings.javaScriptCanOpenWindowsAutomatically = true
        survey_webview.webViewClient = WebViewClient()

        survey_webview.loadUrl("https://docs.google.com/forms/d/1rmftl8FsDn6Jpxa9I049N0pKAPkS1fDHK7owzP4XKtU/edit")
    }
}