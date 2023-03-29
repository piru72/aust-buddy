package com.example.homepage.app

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.homepage.R
import com.example.homepage.utils.models.UserAllData
import com.example.homepage.databinding.FragmentHomeBinding
import com.example.homepage.utils.helpers.ReplaceFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.util.*


class HomeFragment : ReplaceFragment() {
    private var fragmentBinding: FragmentHomeBinding? = null
    private val viewBinding get() = fragmentBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        container?.removeAllViews()
        fragmentBinding = FragmentHomeBinding.inflate(inflater, container, false)

        // Getting the users email
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email.toString()

        // Getting the users department and making a database reference with it
        setInformation(email)
        val selectedDepartment = getShortDepartment().uppercase(Locale.ROOT)
        val modifiedEmail = email.replace('.', '-')
        var userSemester = "Not given"
        var userSection = "Not given"
        val path = "/user-details/$modifiedEmail"
        val databaseReference = FirebaseDatabase.getInstance().getReference(path)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val post = dataSnapshot.getValue<UserAllData>()

                if (post != null) {
                    userSemester = post.userSemester.toString()
                    userSection = post.userSection.toString()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

                makeToast("Error loading data")
            }
        }
        databaseReference.addValueEventListener(postListener)



        viewBinding.btnPlaza.setOnClickListener {

            // Providing warning if semester not given
            if (userSemester == "Not given") {

                // POPUP to know the preference of the user
                val rootLayout = layoutInflater.inflate(R.layout.popup_update_semester, null)

                val laterButton = rootLayout.findViewById<Button>(R.id.btnUpdateLater)
                val nowButton = rootLayout.findViewById<Button>(R.id.btnUpdateNow)

                val popupWindow = PopupWindow(
                    rootLayout,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, true
                )

                popupWindow.update()
                popupWindow.elevation = 20.5F
                popupWindow.showAtLocation(

                    viewBinding.fragmentHome, // Location to display popup window
                    Gravity.CENTER, // Exact position of layout to display popup
                    0, // X offset
                    -500// Y offset
                )

                laterButton.setOnClickListener {
                    val action =
                        HomeFragmentDirections.actionNavigationHomeToNavigationDepartmentSemesterChooser()
                    findNavController().navigate(action)
                    popupWindow.dismiss()
                }
                nowButton.setOnClickListener {
                    val action = HomeFragmentDirections.actionNavigationHomeToEditProfileFragment2()
                    findNavController().navigate(action)
                    popupWindow.dismiss()

                }
            } else {
                val action = HomeFragmentDirections.actionNavigationHomeToViewCourses2(
                    "$selectedDepartment/$userSemester",
                    "view"
                )
                findNavController().navigate(action)
            }
        }




        viewBinding.btnBuses.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToBusFragment()
            val action2 = HomeFragmentDirections.actionNavigationHomeToYoutubeFragment("97h0MN9W7Hc","reference")
            val action3 = HomeFragmentDirections.actionNavigationHomeToYouTubeVideoFragment()
            findNavController().navigate(action3)
        }


        viewBinding.btnRequest.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToRequestFragment()
            findNavController().navigate(action)
        }

//        viewBinding.btnStoreDashboard.setOnClickListener {
//            val action = HomeFragmentDirections.actionNavigationHomeToStoreDashboardFragment()
//            findNavController().navigate(action)
//        }

        viewBinding.btnFavouriteTeachers.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToTeachersFragment(
                "user-favouriteTeachers",
                "view"
            )
            findNavController().navigate(action)
            // TODO ADD TEACHERS OPTION TO HERE FOR OWN DEPARTMENT

//            val defaultDatabaseViewPath = "teachers-list/" + getShortDepartment()
//            // If users email contains a department the user will be automatically guided to his / her departments teacher list
//            val action = if (defaultDatabaseViewPath != getShortDepartment())
//                HomeFragmentDirections.actionNavigationHomeToTeachersFragment(
//                    defaultDatabaseViewPath,
//                    "sourceHome"
//                )
//            else
//                HomeFragmentDirections.actionNavigationHomeToDepartmentChooserFragment()
//
//            findNavController().navigate(action)
        }
        viewBinding.btnRoutine.setOnClickListener {

            if (userSection == "Not given" || userSemester == "Not given" || userSection == "" || userSemester == "") {

                // POPUP to know the preference of the user
                val rootLayout = layoutInflater.inflate(R.layout.popup_update_semester, null)

                val laterButton = rootLayout.findViewById<Button>(R.id.btnUpdateLater)
                val nowButton = rootLayout.findViewById<Button>(R.id.btnUpdateNow)
                val warningMessage = rootLayout.findViewById<TextView>(R.id.textViewWarningMessage)

                if (userSemester == "Not given" || userSemester == "")
                    warningMessage.text = "Semester Not found!!\n Provide semester please!"
                else
                    warningMessage.text = "Section Not found!!\n Provide section please!"


                val popupWindow = PopupWindow(
                    rootLayout,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, true
                )

                popupWindow.update()
                popupWindow.elevation = 20.5F
                popupWindow.showAtLocation(

                    viewBinding.fragmentHome, // Location to display popup window
                    Gravity.CENTER, // Exact position of layout to display popup
                    0, // X offset
                    -500// Y offset
                )

                laterButton.visibility = View.GONE
                nowButton.setOnClickListener {
                    val action = HomeFragmentDirections.actionNavigationHomeToEditProfileFragment2()
                    findNavController().navigate(action)
                    popupWindow.dismiss()

                }
            } else {
                val action = HomeFragmentDirections.actionNavigationHomeToRoutineFragment()
                findNavController().navigate(action)

            }

        }

        viewBinding.btnShortCuts.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToShortCutFragment()
            findNavController().navigate(action)

        }


        return viewBinding.root
    }


}