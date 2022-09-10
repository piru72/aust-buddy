package com.example.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.homepage.calculator.CGPAFragment
import com.example.homepage.homeTab.*
import com.example.homepage.superClass.ReplaceFragment


class HomeFragment : ReplaceFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        val btnTeacher = v.findViewById<Button>(R.id.btn_teacher)
        val btnClub = v.findViewById<Button>(R.id.btn_club)
        val btnGrading = v.findViewById<Button>(R.id.btn_grading)
        val btnSyllabus = v.findViewById<Button>(R.id.btn_syllabus)
        val btnCalender = v.findViewById<Button>(R.id.btn_calender)
        val btnEvents = v.findViewById<Button>(R.id.btn_events)
        val btnCgpa = v.findViewById<Button>(R.id.btn_cgpa)
        val btnMaterial = v.findViewById<Button>(R.id.btn_material)
        val btnRequest = v.findViewById<Button>(R.id.btn_request)

        val currentState = R.id.fragment_home

        btnTeacher.setOnClickListener {
            replaceFragment(WebView("https://iums.aust.edu/ums-web/login/"),currentState)
        }
        btnClub.setOnClickListener {

            replaceFragment(WebView("https://austoj.com/"),currentState)
        }
        btnGrading.setOnClickListener {
            replaceFragment(GradingsFragment(), currentState)
        }
        btnSyllabus.setOnClickListener {
            replaceFragment(WebView("https://www.aust.edu/cse/syllabus#spring2020"),currentState)
        }
        btnCalender.setOnClickListener {
            replaceFragment(WebView("https://www.aust.edu/academics/academic_calendar"),currentState)
        }
        btnEvents.setOnClickListener {
            replaceFragment(EventsFragment(), currentState)
        }
        btnCgpa.setOnClickListener {
            replaceFragment(CGPAFragment(), currentState)
        }
        btnMaterial.setOnClickListener {
            replaceFragment(WebView("https://www.aust.edu/academics/examincation_and_grading_system"),currentState)
        }
        btnRequest.setOnClickListener {
            replaceFragment(RequestFragment(), currentState)
        }

        return v
    }


}