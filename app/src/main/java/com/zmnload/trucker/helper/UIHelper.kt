package com.zmnload.trucker.helper

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.location.Address
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.zmnload.trucker.R
import org.koin.dsl.module
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

val uiHelperModule = module {
    factory { UIHelper() }
}
class UIHelper {

    /*fun getAuthKey(): String {
        var data = ByteArray(0)
        try {
            data =
                ("Authorization" + ":" + ApplicationState.token).toByteArray(charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP)
    }*/

    val currentDate: String
        get() = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())


    val imagesUrl: String
        get() = "http://servicedesk.pk/woo_rides/"

    fun getYear(myDate: Date): String {
        return SimpleDateFormat("yyyy", Locale.ENGLISH).format(myDate)
    }


    fun openFile(url: File, context: Context) {
        try {
            var uri: Uri? = null

            if (url.toString().contains(".jpg") || url.toString()
                    .contains(".jpeg") || url.toString().contains(".png")
            ) {
                uri = FileProvider.getUriForFile(
                    context,
                    context.getPackageName() + ".easyphotopicker.fileprovider",
                    url
                )

            } else {
                uri = FileProvider.getUriForFile(
                    context,
                    context.getPackageName() + ".com.dtech.allodoctor.provider",
                    url
                )

            }


            val intent = Intent(Intent.ACTION_VIEW)
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword")
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else if (url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf")
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint")
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel")
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else if (url.toString().contains(".zip")) {
                // ZIP file
                intent.setDataAndType(uri, "application/zip")
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else if (url.toString().contains(".rar")) {
                // RAR file
                intent.setDataAndType(uri, "application/x-rar-compressed")
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else if (url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf")
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav")
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else if (url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif")
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else if (url.toString().contains(".jpg") || url.toString()
                    .contains(".jpeg") || url.toString().contains(".png")
            ) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg")
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain")
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") ||
                url.toString().contains(".mpeg") || url.toString()
                    .contains(".mpe") || url.toString().contains(".mp4") || url.toString()
                    .contains(".avi")
            ) {
                // Video files
                intent.setDataAndType(uri, "video/*")
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                intent.setDataAndType(uri, "*/*").addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                context,
                "No application found which can open the file",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun ConvertDateInToYYMMDD(getDate: String): Date? {
        var convertedDate: Date? = null
        try {
            convertedDate = SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.ENGLISH).parse(getDate)
        } catch (e: Exception) {

        }

        return convertedDate

    }

    fun ConvertDateInToDDMMYY(getDate: String): Date? {
        var convertedDate: Date? = null
        try {
            convertedDate = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(getDate)
        } catch (e: Exception) {

        }

        return convertedDate

    }

    fun getFormattedDate(mDate: Date): String {
        val daysFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return daysFormat.format(mDate)
    }

    fun getFormattedStringFromString(day: Int, month: Int, year: Int): String {
        val dateString = "$year-$month-$day"
        val mDate = Date(dateString)
        val daysFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return daysFormat.format(mDate)
    }

    fun getWidthInPixel(context: Context, dp: Float): Int {
        val metrics = context.resources.displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return px.toInt()
    }


    fun showLongToastInCenter(ctx: Context, message: String) {
        //message = Strings.nullToEmpty( message );
        val toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun showToast(ctx: Context, message: String) {
        //message = Strings.nullToEmpty( message );
        val toast = Toast.makeText(ctx, message, Toast.LENGTH_LONG)
        toast.show()
    }

    fun hideSoftKeyboard(context: Context, editText: EditText) {

        val imm = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)

    }


    fun callAlert(context: Activity, msg: String) {
        val alertDialog = AlertDialog.Builder(context)


        // Setting Dialog Message
        alertDialog.setMessage(msg)

        // Setting OK Button
        alertDialog.setPositiveButton("Call") { dialog, which -> call(context, msg) }

        alertDialog.setNegativeButton("Cancel", null)
        alertDialog.create()

        // Showing Alert Message
        alertDialog.show()
    }

    fun showSoftKeyboard(context: Context, editText: EditText) {

        val imm = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInputFromWindow(editText.windowToken, InputMethodManager.SHOW_IMPLICIT, 0)

    }

    fun hideSoftKeyboard(context: Context, view: View?) {
        val imm = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (view != null)
            imm.hideSoftInputFromWindow(view.windowToken, 0)

    }

    fun locateView(v: View?): Rect? {
        val loc_int = IntArray(2)
        if (v == null)
            return null
        try {
            v.getLocationOnScreen(loc_int)
        } catch (npe: NullPointerException) {
            // Happens when the view doesn't exist on screen anymore.
            return null
        }

        val location = Rect()
        location.left = loc_int[0]
        location.top = loc_int[1]
        location.right = location.left + v.width
        location.bottom = location.top + v.height
        return location
    }

    fun textMarquee(txtView: TextView) {
        // Use this to marquee Textview inside listview

        txtView.ellipsize = TextUtils.TruncateAt.END
        // Enable to Start Scroll

        // txtView.setMarqueeRepeatLimit(-1);
        // txtView.setHorizontallyScrolling(true);
        // txtView.setSelected(true);
    }


    fun animateRise(mLayout: ViewGroup) {

        val set = AnimationSet(true)

        var animation: Animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 250
        set.addAnimation(animation)

        animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            0.0f, Animation.RELATIVE_TO_SELF, -1.0f
        )
        animation.setDuration(500)
        set.addAnimation(animation)

        animation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                mLayout.visibility = View.INVISIBLE
            }
        })

        mLayout.startAnimation(set)

    }

    fun animateFall(mLayout: ViewGroup) {

        val set = AnimationSet(true)

        var animation: Animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 250
        set.addAnimation(animation)

        animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        )
        animation.setDuration(500)
        set.addAnimation(animation)

        mLayout.startAnimation(set)

    }


    fun animateLayoutSlideDown(mLayout: ViewGroup) {

        val set = AnimationSet(true)

        var animation: Animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 250
        set.addAnimation(animation)

        animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        )
        animation.setDuration(150)
        set.addAnimation(animation)

        val controller = LayoutAnimationController(
            set, 0.25f
        )
        mLayout.layoutAnimation = controller

    }

    fun animateLayoutSlideToRight(mLayout: ViewGroup) {

        val set = AnimationSet(true)

        var animation: Animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 750
        set.addAnimation(animation)

        animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
            0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        )

        animation.setDuration(750)
        set.addAnimation(animation)

        val controller = LayoutAnimationController(
            set, 0.25f
        )
        mLayout.layoutAnimation = controller

    }

    fun animateLayoutSlideFromRight(mLayout: ViewGroup) {

        val set = AnimationSet(true)

        var animation: Animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 750
        set.addAnimation(animation)

        animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        )

        animation.setDuration(750)
        set.addAnimation(animation)

        val controller = LayoutAnimationController(
            set, 0.25f
        )
        mLayout.layoutAnimation = controller

    }

    fun animateLayoutSlideToLeft(mLayout: ViewGroup) {

        val set = AnimationSet(true)

        var animation: Animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 750
        set.addAnimation(animation)

        animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF,
            0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        )

        animation.setDuration(750)
        set.addAnimation(animation)

        val controller = LayoutAnimationController(
            set, 0.25f
        )
        mLayout.layoutAnimation = controller

    }


    fun animateFromRight(mLayout: ViewGroup) {

        val set = AnimationSet(true)

        var animation: Animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 250
        set.addAnimation(animation)

        animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        )
        animation.setDuration(500)
        set.addAnimation(animation)

        mLayout.startAnimation(set)

    }


    fun animateToRight(mLayout: ViewGroup) {

        val set = AnimationSet(true)

        var animation: Animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 250
        set.addAnimation(animation)

        animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
            0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        )
        animation.setDuration(500)
        set.addAnimation(animation)

        mLayout.startAnimation(set)

    }



    private fun getPowerOfTwoForSampleRatio(ratio: Double): Int {
        val k = Integer.highestOneBit(Math.floor(ratio).toInt())
        return if (k == 0)
            1
        else {
            k
        }
    }

    fun convertUriToDrawable(context: Context, imageUri: Uri): Drawable? {
        try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            return Drawable.createFromStream(inputStream, imageUri.toString())
        } catch (e: FileNotFoundException) {
            return null
        }

    }

    fun saveImage(_bitmap: Bitmap, fileName: String, context: Context): File {
        val file = File(fileName)
        ValidateFolderExist(file.parent)
        var outStream: FileOutputStream? = null
        try {
            file.createNewFile()
            outStream = FileOutputStream(file)
            _bitmap.compress(Bitmap.CompressFormat.PNG, 90, outStream)
            outStream.flush()
            outStream.close()
            //addImageToGallery(file.getAbsolutePath(), context);
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return file
    }

    fun ValidateFolderExist(folderPath: String) {
        val dir = File(folderPath)
        if (!dir.exists()) {
            dir.mkdirs()
        }
    }


    fun smsShare(context: Activity, phNumber: String, messageBody: String) {
        var sendTo = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            sendTo = "smsto: $phNumber"
        } else {
            sendTo = "sms:$phNumber"
        }

        val sendIntent = Intent(Intent.ACTION_VIEW)
        sendIntent.data = Uri.parse(sendTo)
        sendIntent.putExtra("sms_body", messageBody)

        try {
            context.startActivity(sendIntent)
        } catch (e: Exception) {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(
                    context,
                    "SMS service is not available",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }


    fun sendEmail(
        to: Array<String>?,
        cc: Array<String>,
        body: String,
        ctx: Activity,
        subject: String
    ) {
        val emailIntent = Intent(
            Intent.ACTION_SENDTO,
            Uri.parse("mailto:")
        )

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, body)
        if (to != null)
            emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
        ctx.startActivity(Intent.createChooser(emailIntent, "Send phone via..."))
    }

    fun isEmptyOrNull(string: String?): Boolean {
        return if (string == null) true else string.trim { it <= ' ' }.length <= 0

    }

    fun captilize(name: String): String {
        val chars = name.toLowerCase().toCharArray()
        var found = false
        for (i in chars.indices) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i])
                found = true
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == ' ') { // You can add other chars here
                found = false
            }
        }

        return String(chars)
    }

    fun captilizeAfterDot(name: String): String {
        val chars = name.toLowerCase().toCharArray()
        var found = false
        for (i in chars.indices) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i])
                found = true
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == ' ') { // You can add other chars here
                found = false
            }
        }

        return String(chars)
    }

    fun capitalizeScentance(name: String): String {
        /*  final StringBuilder result = new StringBuilder(name.length());
        String[] words = name.split("\\s");
        for(int i=0,l=words.length;i<l;++i) {
            if (i > 0) result.append(" ");
            result.append(Character.toUpperCase(words[i].charAt(0)))
                    .append(words[i].substring(1));
        }
        StringBuilder sb = new StringBuilder(name);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        sb.setCharAt(name.indexOf(" ") + 1, Character.toUpperCase(sb.charAt(name.indexOf(" ") + 1)));
        sb.setCharAt(name.indexOf(".") + 1, Character.toUpperCase(sb.charAt(name.indexOf(".") + 1)));*/
        val chars = name.toLowerCase().toCharArray()
        var found = false
        for (i in chars.indices) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i])
                found = true
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == ' ') { // You can add other chars here
                found = false
            }
        }

        return String(chars)
    }

    fun getFormattedAddress(address: Address): String {
        val addressName = ""
        /* for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    addressName += address.getAddressLine(i) + " ";
                }*/
        var location = ""
        val city = address.locality
        //String distric = addresses.get(0).getAdminArea();
        var country: String? = ""
        if (address.adminArea != null && address.adminArea.length >= 2) {
            val arr =
                address.adminArea.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (arr != null) {
                if (arr.size > 1) {
                    country = arr[0][0].toString() + arr[1][0].toString()
                    country = country.toUpperCase()
                } else {
                    country = address.adminArea.substring(0, 2)
                    country = country.toUpperCase()
                }
            } else {
                country = country!!.toUpperCase()
            }

        } else
            country = address.adminArea

        val postalCode = address.postalCode
        //  sb.insert(location.indexOf("CA") + 2, ", ");

        //location.replaceAll(" ",", ");
        /*if(address!=null){
                    location += address;
				}*/

        if (city != null && city.length > 0) {
            location += city
        }
        if (country != null && country.length > 0) {
            if (city != null && city.length > 0)
                location += ", $country"
            else
                location += country
        }
        if (postalCode != null && postalCode.length > 0) {
            if (country != null && country.length > 0)
                location += ", $postalCode"
            else
                location += postalCode
        }
        if (location.length == 0)
            location = "N/A"
        return location

    }


    fun isNotNull(str: String?): Boolean {
        return str != null && str.length > 0

    }

    fun faircalculation(mils: Float): Double {
        val price: Double
        // float meters=GMSGeometryLength(routePath);
        //float mils= Float.parseFloat(DistanceCalculator.getDistanceBetweenLocation(startLocation,endLocation));

        if (mils >= 3.00) {
            price = 35.00
        } else if (mils >= 0.34 && mils <= 0.66) {
            price = 7.50
        } else if (mils >= 0.67 && mils <= 0.99) {
            price = 15.00
        } else if (mils >= 1.00 && mils <= 1.99) {
            price = 20.00
        } else if (mils >= 2.00 && mils <= 2.99) {
            price = 30.00
        } else {
            price = 5.00
        }
        return price

    }

    fun call(activity: Activity, number: String) {

        val call = Uri.parse("tel:$number")
        val surf = Intent(Intent.ACTION_DIAL, call)
        activity.startActivity(surf)
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }



    companion object {

        fun getRouteFromCurrent(context: Context) {
            val navigationIntentUri =
                Uri.parse("google.navigation_menu:q=" + 24.6859931 + "," + 46.7019997)//creating intent with latlng
            val mapIntent = Intent(Intent.ACTION_VIEW, navigationIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            context.startActivity(mapIntent)
        }
    }
}