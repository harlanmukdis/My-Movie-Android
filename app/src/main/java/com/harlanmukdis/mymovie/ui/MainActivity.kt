package com.harlanmukdis.mymovie.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import com.harlanmukdis.mymovie.MyMovieApp
import com.harlanmukdis.mymovie.R
import com.harlanmukdis.mymovie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val app = MyMovieApp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private lateinit var progressDialog: Dialog
    private lateinit var progressBar: ProgressBar

    fun showCustomLoading(context: Context, isShowing: Boolean) {
        if (isShowing) {
            progressDialog = Dialog(context)
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progressDialog.setContentView(R.layout.progress_dialog)
            progressDialog.setCancelable(false)
            progressDialog.window!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT)
            )
            progressBar = progressDialog
                .findViewById(R.id.progressBar) as ProgressBar
            progressBar.indeterminateDrawable.setColorFilter(
                Color.parseColor("#ff6700"),
                PorterDuff.Mode.MULTIPLY
            )
            progressDialog.show()
        } else if (!isShowing) {
            progressDialog.dismiss()
        }
    }

}