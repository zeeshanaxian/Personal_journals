package com.bbox.personaljournal.ui.newnote

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bbox.personaljournal.R
import com.bbox.personaljournal.utils.AppUtils
import com.bbox.personaljournal.utils.enums.UserMood
import com.bbox.personaljournal.databinding.FragmentNewNoteBinding
import com.bbox.personaljournal.models.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class NewNoteFragment : Fragment() {

    private var _binding: FragmentNewNoteBinding? = null
    private var selectedMood: UserMood? = null
    private lateinit var viewModel: NewNoteViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        setClickListeners()
        lifecycleScope.launchWhenResumed {
            viewModel.getNotesData()
                .collect {
//                    Toast.makeText(context, "data fetched successfully", Toast.LENGTH_SHORT).show()
                }
        }
        return binding.root

    }


    private fun setClickListeners() {
        binding.viewBad.setOnClickListener {
            setMoodState(UserMood.BAD)
        }

        binding.viewGood.setOnClickListener {
            setMoodState(UserMood.GOOD)
        }

        binding.viewNormal.setOnClickListener {
            setMoodState(UserMood.NORMAL)
        }

        binding.btnSave.setOnClickListener {
            saveJournal()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NewNoteViewModel::class.java]
        viewModel.updateData.observe(requireActivity()) {
            saveNotesData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveNotesData(
        name: String
    ) {
        lifecycleScope.launchWhenResumed {
            viewModel.saveNotesData(
                name = name
            ).collect {
//                Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
            }
        }
    }


    private fun saveJournal() {
        if (selectedMood == null) {
            Toast.makeText(context, getText(R.string.error_mood), Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.etNotes.text.isNullOrEmpty()) {
            Toast.makeText(context, getText(R.string.error_description), Toast.LENGTH_SHORT).show()
            return
        }

        val noteEntry = NoteEntry(
            description = binding.etNotes.text.toString(),
            time = AppUtils.getCurrentTime(),
            selectedMood!!
        )
        lifecycleScope.launchWhenResumed {
            viewModel.addNote(
                noteEntry = noteEntry
            )
                .collect {
                    Toast.makeText(context, getText(R.string.success_msg), Toast.LENGTH_SHORT)
                        .show()

                }
        }
        println("time11111$noteEntry")
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setMoodState(mood: UserMood) {
        selectedMood = mood
        when (mood) {
            UserMood.BAD -> {
                binding.viewBad.background = activity?.getDrawable(R.drawable.bad_mood_selected_bg)
                binding.viewGood.background =
                    activity?.getDrawable(R.drawable.good_mood_unselected_bg)
                binding.viewNormal.background =
                    activity?.getDrawable(R.drawable.normal_mood_unselected_bg)
            }
            UserMood.GOOD -> {
                binding.viewBad.background =
                    activity?.getDrawable(R.drawable.bad_mood_unselected_bg)
                binding.viewGood.background =
                    activity?.getDrawable(R.drawable.good_mood_selected_bg)
                binding.viewNormal.background =
                    activity?.getDrawable(R.drawable.normal_mood_unselected_bg)
            }
            UserMood.NORMAL -> {
                binding.viewBad.background =
                    activity?.getDrawable(R.drawable.bad_mood_unselected_bg)
                binding.viewGood.background =
                    activity?.getDrawable(R.drawable.good_mood_unselected_bg)
                binding.viewNormal.background =
                    activity?.getDrawable(R.drawable.normal_mood_selected_bg)
            }
        }

    }
}


