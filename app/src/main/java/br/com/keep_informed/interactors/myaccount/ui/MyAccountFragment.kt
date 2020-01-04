package br.com.keep_informed.interactors.myaccount.ui


import android.content.Intent
import android.net.Uri.fromParts
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.keep_informed.R
import br.com.keep_informed.databinding.FragmentMyAccountBinding
import br.com.keep_informed.interactors.about.AboutActivity
import br.com.keep_informed.interactors.myaccount.viewmodel.MyAccountViewModel
import br.com.keep_informed.interactors.signin.ui.SignInActivity


class MyAccountFragment : Fragment() {

    lateinit var  binding: FragmentMyAccountBinding

    lateinit var viewModel : MyAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_account,container,false)

        viewModel = ViewModelProviders.of(this).get(MyAccountViewModel::class.java)

        viewModel.userData.observe(this, Observer {
            binding.usernameTextView.text = it
            if (it.isNotEmpty()){
                binding.buttonSignInSignOut.text = getString(R.string.signOut)
                binding.buttonSignInSignOut.setOnClickListener {
                    viewModel.signOut()
                }
            }else{
                binding.buttonSignInSignOut.text = getString(br.com.keep_informed.R.string.label_sign_in)
                binding.buttonSignInSignOut.setOnClickListener {
                    val intent = Intent(requireContext(), SignInActivity::class.java)
                    intent.flags =  Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    requireActivity().finish()
                    startActivity(intent)

                }
            }

        })

        binding.needHelpTextView.setOnClickListener {
            val phone = "+5511981707403"
            val intent = Intent(Intent.ACTION_DIAL, fromParts("tel", phone, null))
            startActivity(intent)
        }

        binding.aboutTextView.setOnClickListener {
            startActivity(Intent(context,AboutActivity::class.java))
        }


        return binding.root
    }


}
