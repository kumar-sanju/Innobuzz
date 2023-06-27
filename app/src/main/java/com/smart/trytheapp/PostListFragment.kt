package com.smart.trytheapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostListFragment : Fragment() {
    private lateinit var postAdapter: PostAdapter
    private lateinit var postDao: PostDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_list, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        postAdapter = PostAdapter(emptyList()) { post -> showPostDetails(post) }
        recyclerView.adapter = postAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiService = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApiService::class.java)

        val appDatabase = AppDatabase.getInstance(requireContext())
        postDao = appDatabase.postDao()

        CoroutineScope(Dispatchers.IO).launch {
            val posts = apiService.getPosts()
            postDao.insertPosts(posts)
            val savedPosts = postDao.getPosts()
            withContext(Dispatchers.Main) {
                postAdapter.posts = savedPosts
                postAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun showPostDetails(post: Post) {
        val fragmentManager = parentFragmentManager
        val fragment = PostDetailsFragment.newInstance(post)
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
