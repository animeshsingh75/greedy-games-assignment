package com.example.greedygamesassignment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity

object Utils {

    fun TextView.setMessageWithClickableLink(string: String?) {
        val words = string?.split("<a href=\"")
        val link = words?.get(1)?.split("\">")
        val content = words?.get(0)
        val webLink = link?.get(0)
        val linkText = link?.get(1)?.replace("</a>","")
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(webLink)
                startActivity(this@setMessageWithClickableLink.context, intent, null)
            }
        }
        val startIndex = content?.length?.plus(1)
        val endIndex = linkText?.length?.let {
            (startIndex?.plus(it) ?:1) -1
        }
        val spannableString = SpannableString("$content $linkText")
        if (startIndex != null && endIndex!=null) {
            spannableString.setSpan(
                clickableSpan,
                startIndex,
                endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        this.text = spannableString
        this.movementMethod = LinkMovementMethod.getInstance()
        this.highlightColor = Color.TRANSPARENT
    }
}