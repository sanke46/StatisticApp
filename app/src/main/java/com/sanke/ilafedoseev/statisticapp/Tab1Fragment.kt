package com.sanke.ilafedoseev.statisticapp

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_tab1.*


class Tab1Fragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null
    lateinit var mDatabase: DatabaseReference
    var mDataBase : DatabaseReference = FirebaseDatabase.getInstance().getReference()
    var mDeviceRef = mDataBase.child("service").child("devices")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        listLikes.add(Like("12-05-18", 5))
//        listLikes.add(Like("12-06-18", 2))
        var fireBase = FireBaseRealTime("service")
//        val list_adapter = ListAdapter(context.applicationContext, listLikes)

//        list_view.adapter = list_adapter

        val array = ArrayList<Like>()

        mDeviceRef.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                array.clear()

                dataSnapshot?.children?.forEach { child : DataSnapshot ->
                    //                    if (child.key.toString() == nameLocation) {
                    mDeviceRef.child(child.key).child("likes").addValueEventListener(object : ValueEventListener{
                        override fun onCancelled(p0: DatabaseError?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(dataSnapshot2: DataSnapshot?) {

                            dataSnapshot2?.children?.forEach { child2: DataSnapshot ->
//                                println(child2.child("date").value)
//                                println(child2.child("value").value)
                                array.add(Like(child2.child("date").value.toString(), child2.child("value").value.toString().toInt()))


                            }
                            array.reverse()
                            val arrayAdapter = ListAdapter(context.applicationContext,array)
                            list_view.setAdapter(arrayAdapter)

                        }

                    })

                }
            }

        })

        fireBase.allTabLikesAndDislike2(averageInterest, starPercent, starPercent2, starPercent3, starPercent4, starPercent5, allReview)
        fireBase.allTabLikesAndDislike(likesNumber, dislikesNumber)

        // delete all in firebase SEVICE
        deleteAll.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                fireBase.deleteBase()
                fireBase.allTabLikesAndDislike2(averageInterest, starPercent, starPercent2, starPercent3, starPercent4, starPercent5, allReview)
                fireBase.allTabLikesAndDislike(likesNumber, dislikesNumber)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_tab1, container, false)

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

//    override fun onAttach(context: Context?) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            mListener = context
//        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Tab1Fragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): Tab1Fragment {
            val fragment = Tab1Fragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
