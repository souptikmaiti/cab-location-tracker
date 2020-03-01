package com.example.cablocationtracker.ui.home.observer


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cablocationtracker.R
import com.example.cablocationtracker.data.models.SmallLocation
import com.example.cablocationtracker.data.models.User
import com.example.cablocationtracker.ui.base.BaseFragment
import com.example.cablocationtracker.ui.home.HomeActivity
import com.example.cablocationtracker.util.Toaster
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.text.SimpleDateFormat


class ObserverFragment : BaseFragment() {

    lateinit var homeActivity: HomeActivity
    lateinit var observerViewModel: ObserverViewModel
    var googleMap: GoogleMap ?= null
    var mapView: MapView?= null
    var startObserving = false
    lateinit var targetUser: User

    companion object {
        @JvmStatic
        fun newInstance(): ObserverFragment {
            return ObserverFragment()
        }
        const val LOCATION_REQUEST = 345
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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                startObserving = true
            }else{
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST)
            }
        }
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
            if(startObserving == true) {
                showLocation(it)
            }
        })
    }

    private fun showLocation(loc: SmallLocation?) {
        loc?.let {
            mapView?.getMapAsync(OnMapReadyCallback {gMap->
                googleMap = gMap
                googleMap?.isMyLocationEnabled = true

                val targetLocation = LatLng(loc.latitude!!, loc.longitude!!)

                val dateFormat = SimpleDateFormat("dd.MMM.yyyy HH:mm:ss")
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            LOCATION_REQUEST -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startObserving = true
                }else{
                    Toaster.showLong(context!!, "Permission required")
                    startObserving = false
                }
            }
        }
    }
}
