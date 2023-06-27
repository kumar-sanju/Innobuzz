package com.smart.trytheapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class PostDetailsFragment : Fragment() {
    companion object {
        private const val ARG_POST = "post"

        fun newInstance(post: Post): PostDetailsFragment {
            val args = Bundle().apply {
                putParcelable(ARG_POST, post)
            }
            val fragment = PostDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_details, container, false)
        val post: Post? = arguments?.getParcelable(ARG_POST)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val bodyTextView: TextView = view.findViewById(R.id.bodyTextView)
        post?.let {
            titleTextView.text = it.title
            bodyTextView.text = it.body
            Log.d("sanju", "onCreateView: "+ it.title)
        }
        return view
    }
}
