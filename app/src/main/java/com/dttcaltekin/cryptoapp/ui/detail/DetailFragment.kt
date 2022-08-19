package com.dttcaltekin.cryptoapp.ui.detail

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dttcaltekin.cryptoapp.base.BaseFragment
import com.dttcaltekin.cryptoapp.databinding.FragmentDetailBinding
import com.dttcaltekin.cryptoapp.model.detailResponse.CoinDetail
import com.dttcaltekin.cryptoapp.model.detailResponse.DetailResponse
import com.dttcaltekin.cryptoapp.utils.Constants.API_KEY
import com.dttcaltekin.cryptoapp.utils.loadImage
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONObject

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>(
    FragmentDetailBinding::inflate
) {
    override val viewModel by viewModels<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()

    override fun oncreateFinished() {
        viewModel.getDetail(API_KEY, args.symbol)
    }

    override fun initializeListeners() {}

    override fun observeEvents() {
        with(viewModel) {
            detailResponse.observe(viewLifecycleOwner) {
                parseData(it)
            }
            isLoading.observe(viewLifecycleOwner) {
                handleView(it)
            }
            onError.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun parseData(it: DetailResponse?) {
        val gson = Gson()
        val json = gson.toJson(it?.data)
        val jsonObject = JSONObject(json)
        val jsonArray = jsonObject[args.symbol] as JSONArray

        val coin = gson.fromJson(jsonArray.getJSONObject(0).toString(), CoinDetail::class.java)

        coin?.let {
            with(binding) {
                detailCryptoImage.loadImage(it.logo)
                detailCryptoName.text = it.name
                detailCryptoSymbol.text = it.symbol
                cryptoDetail.text = it.description
            }
        }
    }

    private fun handleView(isLoading: Boolean = false) {
        binding.detailGroup.isVisible = !isLoading
        binding.progressBarDetail.isVisible = isLoading
    }

}