package barissaglam.client.movieapp.base.extension

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun <T> Observable<T>.applySchedulers(): Observable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun String?.simpleDateConvert(newFormat: String): String? {
    if (this.isNullOrEmpty()) return ""
    val dateFormat = SimpleDateFormat("yyyy-dd-MM", Locale.ROOT)

    val myDate: Date?
    try {
        myDate = dateFormat.parse(this)
    } catch (e: ParseException) {
        return ""
    }

    val timeFormat = SimpleDateFormat(newFormat, Locale.ROOT)
    return timeFormat.format(myDate ?: Date())
}