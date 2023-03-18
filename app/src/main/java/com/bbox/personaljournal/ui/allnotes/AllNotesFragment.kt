package com.bbox.personaljournal.ui.allnotes

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bbox.personaljournal.R
import com.bbox.personaljournal.data.CallBackEvent
import com.bbox.personaljournal.data.FirstOpenCallBackEvent
import com.bbox.personaljournal.databinding.FragmentAllNotesBinding
import com.bbox.personaljournal.models.AllNotes
import com.bbox.personaljournal.recyclerviews.AllNotesRecyclerViewAdapter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class AllNotesFragment : Fragment() {
    private lateinit var viewModel: AllNotesViewModel
    private var _binding: FragmentAllNotesBinding? = null
    private var allNotesRecyclerViewAdapter: AllNotesRecyclerViewAdapter? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAllNotesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AllNotesViewModel::class.java]
        binding.rvJournals.layoutManager = LinearLayoutManager(context)
        viewModel.allNotesMutable.observe(requireActivity()) {
            if (it != null) {
                val gson = Gson()
                val jsonString = gson.toJson(it)
                saveNotesData(jsonString)
                allNotesRecyclerViewAdapter = AllNotesRecyclerViewAdapter(it)
                binding.rvJournals.adapter = allNotesRecyclerViewAdapter
                allNotesRecyclerViewAdapter?.notifyDataSetChanged()
                viewModel.allNotesMutable.value = null
            }
        }
        getFirstOpen()
        binding.btnNext.setOnClickListener {

            findNavController().navigate(R.id.action_allNotesFragment_to_newNoteFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycleScope.coroutineContext.cancelChildren()
        _binding = null
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun getNotesCallBack(
        event: CallBackEvent
    ) {
        when (event) {
            is CallBackEvent.NotesCachedSuccessObject -> {
            }
            is CallBackEvent.CachedNotesFetchSuccess -> {
                val allNotesString = event.allNotesData
                val gson = Gson()
                val allNotesData = gson.fromJson(allNotesString, AllNotes::class.java)
                allNotesRecyclerViewAdapter = AllNotesRecyclerViewAdapter(allNotesData)
                binding.rvJournals.adapter = allNotesRecyclerViewAdapter
                allNotesRecyclerViewAdapter?.notifyDataSetChanged()

                println("data11::" + event.allNotesData)
                // desired logic goes here
            }
        }
    }

    private fun saveNotesData(
        name: String
    ) {
        lifecycleScope.launchWhenResumed {
            viewModel.saveNotesData(
                name = name
            ).collect() {
                Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getNotesData() {
        lifecycleScope.launch {
            viewModel.getNotesData().collect { event ->
                getNotesCallBack(event)
            }
        }
    }

    private fun getFirstOpen() {
        lifecycleScope.launch {
            viewModel.getFirstOpen().collect { event ->
                firstOpenCallBack(event)
            }
        }
    }

    private fun firstOpenCallBack(event: FirstOpenCallBackEvent) {
        when (event) {
            is FirstOpenCallBackEvent.FirstOpenCachedSuccessObject -> {
            }
            is FirstOpenCallBackEvent.FirstOpenFetchSuccess -> {

                if (!event.isFirstOpen) {
                    saveFirstOpen(true)
                    viewModel.populateData()
                } else {
                    getNotesData()
                }
            }
        }
    }


    private fun saveFirstOpen(isFirstOpen: Boolean) {
        lifecycleScope.launch {
            viewModel.saveFirstOpen(isFirstOpen).collect { event ->
                firstOpenCallBack(event)
            }
        }
    }

}