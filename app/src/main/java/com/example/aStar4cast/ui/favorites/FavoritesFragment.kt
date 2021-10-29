package com.example.aStar4cast.ui.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.aStar4cast.utils.Communicator
import com.example.aStar4cast.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment(){
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoritesViewModel by activityViewModels()

    private lateinit var thisContext: Context
    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        thisContext = view.context
        communicator = activity as Communicator

        displayFavList()
        setupClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        communicator.passBool(true)
        _binding = null
    }

    private fun displayFavListEdit(){
        viewModel.getDataBaseList(thisContext)
        val cList = binding.lvCitiesList
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(thisContext,
            android.R.layout.simple_list_item_1, viewModel.favorites)
        cList.adapter = arrayAdapter

        cList.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, i, _ ->
            val selectedItemText = adapterView.getItemAtPosition(i)
            viewModel.getDeleteOneRow(thisContext, selectedItemText.toString())
            displayFavListEdit()
        }
    }

    private fun displayFavList(){
        viewModel.getDataBaseList(thisContext)
        val cList = binding.lvCitiesList
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(thisContext,
            android.R.layout.simple_list_item_1, viewModel.favorites)
        cList.adapter = arrayAdapter

        cList.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, i, _ ->
            val selectedItemText = adapterView.getItemAtPosition(i)
            communicator.passData(selectedItemText.toString())
            unToggleUi()
        }
    }

    private fun unToggleUi(){
        binding.btnEditList.isVisible = false
        binding.lvCitiesList.isVisible = false
        binding.btnShowList.isVisible = true
    }

    private fun setupClickListeners(){
        binding.btnEmptyAll.setOnClickListener {
            viewModel.getEmptyAllData(thisContext)
            displayFavListEdit()
        }

        binding.btnEditList.setOnClickListener{
            if(binding.btnEditList.isChecked){
                binding.btnEmptyAll.isVisible = true
                binding.tvDeleteInfo.isVisible = true
                displayFavListEdit()
            } else{
                binding.tvDeleteInfo.isVisible = false
                binding.btnEmptyAll.isVisible = false
                displayFavList()
            }
        }

        binding.btnShowList.setOnClickListener {
            binding.btnEditList.isVisible = true
            binding.lvCitiesList.isVisible = true
            binding.btnShowList.isVisible = false
            communicator.passBool(true)
            displayFavList()
        }
    }
}