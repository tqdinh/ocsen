package com.example.firstosproject.reviewImage

import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.firstosproject.R
import com.example.firstosproject.Util
import com.example.firstosproject.databinding.FragmentReviewBinding
import com.example.firstosproject.viewmodel.PreviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

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
class ReviewFragment(val fullPath: String, val qrString: String) : DialogFragment() {

    val viewModel: ReviewViewmodel by viewModels()
    private var _binding: FragmentReviewBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    }

    fun setupObserver() {
        lifecycleScope.launch {
            viewModel.bitmapConverted.collect {
                it?.let {
                    withContext(Dispatchers.Main)
                    {

                        Glide.with(this@ReviewFragment).asBitmap()
                            .load(it)
                            .placeholder(circularProgressDrawable)
                            .transform(CenterCrop(), RoundedCorners(convertDpToPixel(9f)))
                            .into(binding.ivResultPhoto)

                        binding.btSave.isEnabled = true
                        binding.btCancel.isEnabled = true
                    }
                }
            }
        }
    }

    private val circularProgressDrawable = CircularProgressDrawable(requireContext()).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }

    fun convertDpToPixel(dp: Float): Int {
        val resources: Resources = getResources()
        val metrics: DisplayMetrics = resources.getDisplayMetrics()
        return (dp * (metrics.densityDpi / 160f)) as Int
    }

    fun setupView() {
        binding.btSave.setOnClickListener({
            viewModel.bitmapConverted.value?.let {
                Util.saveFileBitmapWithPath(fullPath, it)
            }
        })
        binding.btCancel.setOnClickListener({
            val fileToDelete = File(fullPath)
            fileToDelete.delete()
        })
    }

    companion object {

    }
}