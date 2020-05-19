package barissaglam.client.movieapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import barissaglam.client.movieapp.R
import com.google.android.material.button.MaterialButton


class BaseView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var loadingView: View? = null
    private var emptyView: View? = null
    private var errorView: View? = null

    /** EmptyView View ve Değişkenleri **/
    private var imageResIdOfEmptyView: Int? = null
    private var textOfEmptyView: String? = null
    private var imageViewOfEmptyView: ImageView? = null
    private var textViewOfEmptyView: TextView? = null

    /** ErrorView View ve Değişkenleri **/
    private var imageResIdOfErrorView: Int? = null
    private var textOfErrorView: String? = null
    private var buttonTextOfErrorView: String? = null
    private var imageViewOfErrorView: ImageView? = null
    private var textViewOfErrorView: TextView? = null
    private var buttonOfErrorView: MaterialButton? = null

    private var baseViewListener: BaseViewListener? = null

    init {
        val inflater = LayoutInflater.from(getContext())

        attrs.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.BaseView)

            /** LoadingView Tanımlaması **/
            val loadingViewResId = a.getResourceId(R.styleable.BaseView_loadingView, R.layout.layout_loading)
            if (loadingViewResId != -1) {
                val inflatedLoadingView = inflater.inflate(loadingViewResId, this, false)
                this.loadingView = inflatedLoadingView
            }

            /** EmptyView Tanımlaması **/
            val emptyViewResId = a.getResourceId(R.styleable.BaseView_emptyView, R.layout.layout_empty)
            if (emptyViewResId != -1) {
                val inflatedEmptyView = inflater.inflate(emptyViewResId, this, false)
                this.emptyView = inflatedEmptyView

                /** Eğer Kullanıcı Varsayılan EmptyView'i Kullanmak İstiyorsa **/
                if (emptyViewResId == R.layout.layout_empty) {
                    /** EmptyView Değişken Tanımlamaları **/
                    this.imageResIdOfEmptyView = a.getResourceId(R.styleable.BaseView_imageOfEmptyView, R.drawable.ic_mood_bad_black_24dp)
                    this.textOfEmptyView = a.getString(R.styleable.BaseView_textOfEmptyView) ?: "Something went wrong."

                    /** EmptyView View Tanımlamaları **/
                    this.imageViewOfEmptyView = emptyView?.findViewById(R.id.image)
                    this.textViewOfEmptyView = emptyView?.findViewById(R.id.text)

                    initEmptyViewFields()
                }
            }

            /** ErrorView Tanımlaması **/
            val errorViewResId = a.getResourceId(R.styleable.BaseView_errorView, R.layout.layout_error)
            if (errorViewResId != -1) {
                val inflatedErrorView = inflater.inflate(errorViewResId, this, false)
                this.errorView = inflatedErrorView

                /** Eğer Kullanıcı Varsayılan EmptyView'i Kullanmak İstiyorsa **/
                if (errorViewResId == R.layout.layout_error) {
                    /** EmptyView Değişken Tanımlamaları **/
                    this.imageResIdOfErrorView = a.getResourceId(R.styleable.BaseView_imageOfErrorView, R.drawable.ic_error_outline_black_24dp)
                    this.textOfErrorView = a.getString(R.styleable.BaseView_textOfErrorView) ?: "here is error view."
                    this.buttonTextOfErrorView = a.getString(R.styleable.BaseView_buttonTextOfErrorView) ?: "Retry"

                    /** EmptyView View Tanımlamaları **/
                    this.imageViewOfErrorView = errorView?.findViewById(R.id.image)
                    this.textViewOfErrorView = errorView?.findViewById(R.id.text)
                    this.buttonOfErrorView = errorView?.findViewById(R.id.button)

                    initErrorViewFields(a.getBoolean(R.styleable.BaseView_buttonVisibilityOfErrorView, true))
                }
            }

            a.recycle()
        }
    }

    /**
     * Kullanıcıdan Alınan/Default Kabul Edilen Değerlerin, EmptyView İçindeki İlgili Viewlere Eklenmesi
     */
    private fun initEmptyViewFields() {
        imageViewOfEmptyView?.setImageResource(imageResIdOfEmptyView!!)
        textViewOfEmptyView?.text = textOfEmptyView
    }

    /**
     * Kullanıcıdan Alınan/Default Kabul Edilen Değerlerin, EmptyView İçindeki İlgili Viewlere Eklenmesi
     */
    private fun initErrorViewFields(buttonVisibility: Boolean) {
        imageViewOfErrorView?.setImageResource(imageResIdOfErrorView!!)
        textViewOfErrorView?.text = textOfErrorView
        buttonOfErrorView?.text = buttonTextOfErrorView

        buttonOfErrorView?.isVisible = buttonVisibility

        /**
         * Kullanıcı EmptyView İçindeki Button'a Click Vermek İstiyorsa BaseViewListener Interface'ini Implement Etmeli*
         */
        buttonOfErrorView?.setOnClickListener { baseViewListener?.onBaseButtonClick() }
    }

    /** LoadingView Gösterilmek İstendiğinde İlgili Layout BaseView İçerisine Eklenir. Eğer Halihazırda Bir EmptyView Görünüyorsa Kaldırılır, Başka View Varsa Gizlenir.
     * Eğer Empty View Varsa Ve Kaldırılmazsa Ya Da Diğer View'lar Gizlenmezse, LoadingView İle Üst Üste Gelir.
     */
    fun showLoading() {
        if (loadingView?.parent == null) this.addView(loadingView, loadingView?.layoutParams)
        if (emptyView?.parent == this) this.removeView(emptyView)
        if (errorView?.parent == this) this.removeView(errorView)
        setOtherChildVisibility(false, loadingView)
    }

    /**
     * Her Koşulda İçerik, Loading İşleminden Sonra Gösterileceği İçin, İlgili LoadingView Kaldırılır Ve Loading İşlemi Sırasında Gizlenen Diğer View'lar Gösterilir
     */
    fun showContent() {
        if (loadingView?.parent == this) this.removeView(loadingView)
        setOtherChildVisibility(true, null)
    }

    /**
     * Gösterilmek İstenen İçerik Boş Olduğu Durumlarda EmptyView Görünür. LoadingView Kaldırılır, Başka View Varsa Gizlenir.
     */
    fun showEmpty() {
        if (loadingView?.parent == this) this.removeView(loadingView)
        if (emptyView?.parent == null) this.addView(emptyView, emptyView?.layoutParams)
        setOtherChildVisibility(false, emptyView)
    }

    /**
     * Gösterilmek İstenen İçerik Boş Olduğu Durumlarda EmptyView Görünür. LoadingView Kaldırılır, Başka View Varsa Gizlenir.
     */
    fun showError() {
        if (loadingView?.parent == this) this.removeView(loadingView)
        if (errorView?.parent == null) this.addView(errorView, errorView?.layoutParams)
        setOtherChildVisibility(false, errorView)
    }

    /**
     * Bu Fonksiyon Kullanıcının LoadingView/EmptyView Gösterimi Sırasında, BaseView'ın Childi Olan Diğer View'lar İle Üst Üste Gelmemesi İçin Yazıldı.
     */

    private fun setOtherChildVisibility(state: Boolean, view: View?) {
        for (i in 0 until this.childCount)
            if (getChildAt(i) != view)
                getChildAt(i).isVisible = state
    }

    fun setBaseViewListener(baseViewListener: BaseViewListener) {
        this.baseViewListener = baseViewListener
    }

    fun setErrorText(message: String?) {
        textViewOfErrorView?.text = message
    }

}

interface BaseViewListener {
    fun onBaseButtonClick()
}