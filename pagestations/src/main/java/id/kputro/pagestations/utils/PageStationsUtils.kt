package id.kputro.pagestations.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import id.kputro.pagestations.module.Log
import java.net.URLEncoder

@Suppress("unused")
object PageStationsUtils {

  @Suppress("unused")
  fun Context.doDialPhoneNumber(phoneNumber: String) {
    if (phoneNumber.isEmpty()) return
    val uri = Uri.parse("tel:$phoneNumber")
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = uri
    ContextCompat.startActivity(this, intent, null)
  }

  @Suppress("unused")
  fun Context.doWriteEmail(to: String, subject: String, body: String) {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.apply {
      putExtra(Intent.EXTRA_SUBJECT, subject)
      putExtra(Intent.EXTRA_TEXT, body)
      data = Uri.parse("mailto:$to")
      addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    ContextCompat.startActivity(this, intent, null)
  }

  @Suppress("unused")
  fun Context.doSendWhatsApp(phone: String, message: String) {
    val isStartWithZero = phone.startsWith('0', true)
    val replacedFirstZeroPhone = phone.replaceFirst("0", "+62")
    val mPhone = if (isStartWithZero) replacedFirstZeroPhone else phone
    val mIntent = Intent(Intent.ACTION_VIEW)
    val mEncodedMessage = URLEncoder.encode(message, "UTF-8")
    val mUrl = "https://api.whatsapp.com/send?phone=$mPhone&text=$mEncodedMessage"
    mIntent.setPackage("com.whatsapp")
    mIntent.data = Uri.parse(mUrl)
    try {
      ContextCompat.startActivity(this, mIntent, null)
    } catch (e: Exception) {
      Log.e("doSendWhatsApp error: $e")
    }
  }

  @Suppress("unused")
  fun Context.doShareText(mText: String, mTitle: String?) {
    if (mText.isEmpty()) return
    val mIntent = Intent().apply {
      action = Intent.ACTION_SEND
      putExtra(Intent.EXTRA_TEXT, mText)
      type = "text/plain"
    }
    val mShareIntent = Intent.createChooser(mIntent, mTitle)
    ContextCompat.startActivity(this, mShareIntent, null)
  }

  @Suppress("unused")
  fun String.getUriObject(): Uri? {
    if (this.isEmpty()) return null
    return try {
      Uri.parse(this)
    } catch (e: IllegalStateException) {
      e.printStackTrace()
      null
    }
  }

}