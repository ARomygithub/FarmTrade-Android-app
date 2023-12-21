package com.bangkit.farmtrade.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.farmtrade.R
import com.bangkit.farmtrade.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        with(binding) {
            profileViewModel.isLoading.observe(viewLifecycleOwner) {
                progressBar.visibility = if(it) View.VISIBLE else View.GONE
            }
            profileViewModel.isEditable.observe(viewLifecycleOwner) {
                profileNameEd.isEnabled = it
                profileEmailEd.isEnabled = it
                profileContactEd.isEnabled = it
                profilePasswordEd.isEnabled = it
                btnChange.text = if(it) getString(R.string.save) else getString(R.string.change)
                btnCancel.visibility = if(it) View.VISIBLE else View.GONE
            }
            btnCancel.setOnClickListener {
                profileViewModel.cancelSave()
                getProfile()
            }
            btnChange.setOnClickListener {
                if(profileViewModel.isEditable.value == true) {
                    profileViewModel.saveProfile(
                        profileNameEd.text.toString(),
                        profileEmailEd.text.toString(),
                        profileContactEd.text.toString(),
                        profilePasswordEd.text.toString()
                    )
                    // dialog success-error
                    profileViewModel.saveResponse.value?.let {response ->
                        AlertDialog.Builder(requireContext()).apply {
                            setTitle(if (response.error) "Edit Profile Error" else "Edit Profile Success")
                            setMessage(response.message)
                            setPositiveButton("OK") { _, _ ->
                            }
                            create()
                            show()
                        }
                    }
                    getProfile()
                } else {
                    profileViewModel.editProfile()
                }
            }
            btnLogout.setOnClickListener {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle("Logout")
                    setMessage("Anda yakin ingin keluar dari akun ini?")
                    setNegativeButton("Yes") { _, _ ->
                        profileViewModel.logout()
                        findNavController().navigate(R.id.action_navigation_profile_to_loginActivity)
                        activity?.finish()
                    }
                    setPositiveButton("No") { _, _ ->
                    }

                    create()
                    show()
                }
            }
        }
        getProfile()
        return binding.root
    }

    private fun getProfile() {
        try {
            profileViewModel.getProfile()
            with(binding) {
                with(profileViewModel) {
                    Glide.with(requireContext())
                        .load(profileResponse.value!!.imageUrl)
                        .into(profileImage)
                    nameLabel.text = profileResponse.value!!.name
                    profileNameEd.setText(profileResponse.value!!.name)
                    profileEmailEd.setText(profileResponse.value!!.email)
                    profileContactEd.setText(profileResponse.value!!.contact)
                    profilePasswordEd.setText(profileResponse.value!!.password)
                }
            }
        } catch (_ : Exception) {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}