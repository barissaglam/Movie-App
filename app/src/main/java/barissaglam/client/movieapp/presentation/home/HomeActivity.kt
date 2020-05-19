package barissaglam.client.movieapp.presentation.home

import android.app.Activity
import android.content.Intent
import barissaglam.client.movieapp.R
import barissaglam.client.movieapp.base.view.BaseActivity
import barissaglam.client.movieapp.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {
    override val layoutResourceId: Int = R.layout.activity_home
    override val classTypeOfViewModel: Class<HomeViewModel> = HomeViewModel::class.java


    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, HomeActivity::class.java))
        }
    }
}