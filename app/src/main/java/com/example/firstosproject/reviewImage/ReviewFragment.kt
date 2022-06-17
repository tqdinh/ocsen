package com.example.firstosproject.reviewImage

import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.firstosproject.databinding.FragmentReviewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import android.widget.Toast

import cn.pedant.SweetAlert.SweetAlertDialog
import cn.pedant.SweetAlert.SweetAlertDialog.OnSweetClickListener


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ReviewFragment(
    val fullPath: String,
    val qrString: String,
    val onCancel: ( path: String) -> Unit
) :
    DialogFragment() {

    val viewModel: ReviewViewmodel by viewModels()
    private var _binding: FragmentReviewBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setStyle(STYLE_NO_FRAME, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
        viewModel.mergeBitmapWithRQ(fullPath, qrString)
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.setCancelable(false)
            dialog.window!!.setLayout(width, height)
        }
    }

    fun setupObserver() {
        lifecycleScope.launch {
            viewModel.bitmapConverted.collect {
                it?.let {
                    withContext(Dispatchers.Main)
                    {
                        Glide.with(this@ReviewFragment).asBitmap()
                            .load(it)
                            .fitCenter()
                            .into(binding.ivResultPhoto)

                        binding.btSave.isEnabled = true
                        binding.btCancel.isEnabled = true
                    }
                }
            }
        }
    }


    fun convertDpToPixel(dp: Float): Int {
        val resources: Resources = getResources()
        val metrics: DisplayMetrics = resources.getDisplayMetrics()
        return (dp * (metrics.densityDpi / 160f)).toInt()
    }

    fun setupView() {
        binding.btSave.setOnClickListener({
            dismiss()
        })
        binding.btCancel.setOnClickListener({
            onCancel.invoke( fullPath)
            dismiss()
        })
    }

    companion object {

    }
}