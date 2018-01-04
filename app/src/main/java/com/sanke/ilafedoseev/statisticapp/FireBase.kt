package com.sanke.ilafedoseev.statisticapp

import android.widget.TextView
import com.google.firebase.database.*

class FireBaseRealTime(var nameLocation: String ) {
    lateinit var listofStarsCounter : ArrayList<Int>
    lateinit var listOfLikesInTab : ArrayList<Int>
    lateinit var listOfStatistic : ArrayList<Int>

    var mDataBase : DatabaseReference = FirebaseDatabase.getInstance().getReference()
    var mDeviceRef = mDataBase.child(nameLocation).child("devices")
    var mTabloRef = mDataBase.child("tablo")
    var mDeviceLikesRef = mDataBase.child(nameLocation).child("devices").child("sam").child("likes")
    var mTabloLikesRef = mDataBase.child(nameLocation).child("tablo").child("tabloOne").child("likes")
    var mTabloLikesRef2 = mDataBase.child(nameLocation).child("tablo")


    fun allTabLikesAndDislike2(text: TextView, star1: TextView, star2: TextView, star3: TextView, star4: TextView, star5: TextView, allReview : TextView) {
        var count : Int
        var sumAllvalue : Int

        mDataBase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot : DataSnapshot?) {
                for (childName: DataSnapshot in dataSnapshot?.children!!) {
                    if (childName.key.toString() == nameLocation) {
                        mDataBase.child(childName.key.toString()).child("devices").addValueEventListener(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError?) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onDataChange(p0: DataSnapshot?) {
                                listofStarsCounter = ArrayList<Int>(listOf(0, 0, 0, 0, 0))
                                count = 0
                                sumAllvalue = 0

                                for (child: DataSnapshot in p0?.children!!) {

                                    mDataBase.child(childName.key.toString()).child("devices").child(child.key.toString()).child("likes").addValueEventListener(object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError?) {
                                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                        }

                                        override fun onDataChange(p0: DataSnapshot?) {

                                            p0?.children?.forEach { child: DataSnapshot ->

                                                val objectMap: MutableMap<String, Any>
                                                objectMap = child!!.value as MutableMap<String, Any>


                                                for (entary in objectMap) {
                                                    if (entary.key.equals("value")) {
                                                        when (entary.value.toString()) {
                                                            "1" -> listofStarsCounter.set(0, listofStarsCounter.get(0) + 1)
                                                            "2" -> listofStarsCounter.set(1, listofStarsCounter.get(1) + 1)
                                                            "3" -> listofStarsCounter.set(2, listofStarsCounter.get(2) + 1)
                                                            "4" -> listofStarsCounter.set(3, listofStarsCounter.get(3) + 1)
                                                            "5" -> listofStarsCounter.set(4, listofStarsCounter.get(4) + 1)
                                                        }
                                                        count++
                                                        sumAllvalue += entary.value.toString().toInt()
                                                    }
                                                }
                                            }
                                            try {
                                                text.text = "%.1f".format(sumAllvalue.toDouble() / count.toDouble())
                                                allReview.text = "За все время : " + count.toString()
                                                star1.text = "%.1f".format((100.0 / count.toDouble()) * listofStarsCounter.get(0).toDouble()) + "%"
                                                star2.text = "%.1f".format((100.0 / count.toDouble()) * listofStarsCounter.get(1).toDouble()) + "%"
                                                star3.text = "%.1f".format((100.0 / count.toDouble()) * listofStarsCounter.get(2).toDouble()) + "%"
                                                star4.text = "%.1f".format((100.0 / count.toDouble()) * listofStarsCounter.get(3).toDouble()) + "%"
                                                star5.text = "%.1f".format((100.0 / count.toDouble()) * listofStarsCounter.get(4).toDouble()) + "%"
                                            } catch (e: ArithmeticException) {

                                            }
                                        }
                                    })
                                }
                            }

                        })
                    }
                }
            }

        })
    }

    fun allTabLikesAndDislike(tabLikeNumber: TextView, tabDislikeNumber: TextView) {
        mTabloLikesRef2.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(dataSnapshot : DataSnapshot?) {

                listOfLikesInTab = ArrayList<Int>(listOf(0,0))
                dataSnapshot?.children?.forEach { child : DataSnapshot ->
                    mTabloLikesRef2.child(child.key).child("likes").addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(dataSnapshot2: DataSnapshot?) {
                            dataSnapshot2?.children?.forEach { child2: DataSnapshot ->
                                when (child2.child("value").value.toString()) {
                                    "true" -> listOfLikesInTab.set(0, listOfLikesInTab.get(0) + 1)
                                    "false" -> listOfLikesInTab.set(1, listOfLikesInTab.get(1) + 1)
                                }

                            }

                            tabLikeNumber.text = listOfLikesInTab.get(0).toString()
                            tabDislikeNumber.text = listOfLikesInTab.get(1).toString()
                        }
                    })
                }
            }

        })
    }

    fun allTabLikesAndDislike3(array : ArrayList<Like>) {

        mDeviceRef.addValueEventListener(object : ValueEventListener {

            var ar : ArrayList<Like> = ArrayList()

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {


                dataSnapshot?.children?.forEach { child : DataSnapshot ->
//                    if (child.key.toString() == nameLocation) {
                    mDeviceRef.child(child.key).child("likes").addValueEventListener(object : ValueEventListener{
                        override fun onCancelled(p0: DatabaseError?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(dataSnapshot2: DataSnapshot?) {
                            dataSnapshot2?.children?.forEach { child2: DataSnapshot ->
                                println(child2.child("date").value)
                                println(child2.child("value").value)
                                array.add(Like(child2.child("date").value.toString(),child2.child("value").value.toString().toInt()))

                            }

                        }

                    })

                    }
                }

            })
        }


    }

