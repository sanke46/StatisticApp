package com.sanke.ilafedoseev.statisticapp

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_tab3.*


class Tab3Fragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null
    lateinit var mDatabase: DatabaseReference
    var mDataBase : DatabaseReference = FirebaseDatabase.getInstance().getReference()
    var mDeviceRef = mDataBase.child("гагарина").child("devices")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var fireBase = FireBaseRealTime("гагарина")
        val array = ArrayList<Like>()
        scrollView.smoothScrollTo(0, 0)

        mDeviceRef.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                array.clear()

                dataSnapshot?.children?.forEach { child : DataSnapshot ->
                    //                    if (child.key.toString() == nameLocation) {
                    mDeviceRef.child(child.key).child("likes").addValueEventListener(object : ValueEventListener {
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
                            val arrayAdapter = ListAdapter(context.applicationContext, array)
                            list.setAdapter(arrayAdapter)

                        }

                    })

                }
            }

        })

        fireBase.allTabLikesAndDislike2(averageInterest2, starPercent1, starPercent22, starPercent32, starPercent42, starPercent52, allReview2)
        fireBase.allTabLikesAndDislike(likesNumber2, dislikesNumber2)

        // delete all in firebase ГАГАРИНА
        deleteAll2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                fireBase.deleteBase()
                fireBase.allTabLikesAndDislike2(averageInterest2, starPercent1, starPercent22, starPercent32, starPercent42, starPercent52, allReview2)
                fireBase.allTabLikesAndDislike(likesNumber2, dislikesNumber2)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_tab3, container, false)
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
         * @return A new instance of fragment Tab3Fragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): Tab3Fragment {
            val fragment = Tab3Fragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
