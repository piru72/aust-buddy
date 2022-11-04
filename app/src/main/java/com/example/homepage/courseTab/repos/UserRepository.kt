package com.example.homepage.courseTab.repos

import androidx.lifecycle.MutableLiveData
import com.example.homepage.courseTab.Model.CourseData
import com.google.firebase.database.*

class UserRepository {

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Courses")

    @Volatile
    private var INSTANCE: UserRepository? = null

    fun getInstance(): UserRepository {
        return INSTANCE ?: synchronized(this) {

            val instance = UserRepository()
            INSTANCE = instance
            instance
        }


    }


    fun loadUsers(userList: MutableLiveData<List<CourseData>>) {

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                try {

                    val courseList: List<CourseData> = snapshot.children.map { dataSnapshot ->

                        dataSnapshot.getValue(CourseData::class.java)!!

                    }

                    userList.postValue(courseList)

                } catch (e: Exception) {

                }


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }

}