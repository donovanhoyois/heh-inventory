package be.heh.heh_inventory.ui.usersList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import be.heh.heh_inventory.database.DatabaseHelper
import be.heh.heh_inventory.databinding.FragmentUsersListBinding

class UsersListFragment : Fragment() {
    private var _binding: FragmentUsersListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UsersListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // ViewModel and binding
        viewModel = ViewModelProvider(this).get(UsersListViewModel::class.java)
        _binding = FragmentUsersListBinding.inflate(inflater, container, false)

        // Bind to UI
        viewModel.users.observe(viewLifecycleOwner){
            binding.usersRecyclerView.layoutManager = LinearLayoutManager(this.context)
            binding.usersRecyclerView.adapter = UsersListAdapter(DatabaseHelper.db.userDao().getAll(), activity)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}