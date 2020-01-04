package br.com.keep_informed.interactors.map.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.keep_informed.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class SearchMapFragment : SupportMapFragment(), OnMapReadyCallback{

    private lateinit var mMap: GoogleMap

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val sydney = LatLng(-23.5460751,  -46.6467509)
        mMap.addMarker(MarkerOptions().position(sydney).title("Edifício jornalista Roberto Marinho"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

}
