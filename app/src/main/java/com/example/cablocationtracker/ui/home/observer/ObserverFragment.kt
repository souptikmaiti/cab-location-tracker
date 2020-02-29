package com.example.cablocationtracker.ui.home.observer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cablocationtracker.R
import com.example.cablocationtracker.data.models.SmallLocation
import com.example.cablocationtracker.data.models.User
import com.example.cablocationtracker.ui.base.BaseFragment
import com.example.cablocationtracker.ui.home.HomeActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.text.SimpleDateFormat
import java.util.*


class ObserverFragment : BaseFragment() {

    lateinit var homeActivity: HomeActivity
    lateinit var observerViewModel: ObserverViewModel
    var googleMap: GoogleMap ?= null
    var mapView: MapView?= null
    lateinit var targetUser: User

    companion object {
        @JvmStatic
        fun newInstance(): ObserverFragment {
            return ObserverFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_observer, container, false)

        mapView = rootView.findViewById(R.id.map_view) as MapView
        mapView?.onCreate(savedInstanceState)
        mapView?.onResume()

        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeActivity = activity as HomeActivity
        initViewModel()
        init()
        if(homeActivity != null && homeActivity.targetUser != null ) {
            targetUser = homeActivity.targetUser!!
            observerViewModel.getLocation(targetUser.id!!)
        }
    }

    private fun init() {

    }

    private fun initViewModel() {
        observerViewModel = ViewModelProviders.of(this).get(ObserverViewModel::class.java)
        observerViewModel._locationData.observe(viewLifecycleOwner, Observer {
            showLocation(it)
        })
    }

    private fun showLocation(loc: SmallLocation?) {
        loc?.let {
            mapView?.getMapAsync(OnMapReadyCallback {gMap->
                googleMap = gMap
                googleMap?.isMyLocationEnabled = true

                val targetLocation = LatLng(loc.latitude!!, loc.longitude!!)

                val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm")
                googleMap?.clear()
                googleMap?.addMarker(MarkerOptions().position(targetLocation).title(targetUser.userName).snippet(dateFormat.format(loc.currTime!!)))

                //val cameraPosition = CameraPosition.Builder().target(targetLocation).zoom(12f).build()
                //googleMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            })
        }
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
}
