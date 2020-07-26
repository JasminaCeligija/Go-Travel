package com.example.gotravel.presentation.trip_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gotravel.R
import kotlinx.android.synthetic.main.swipe_trip_photo.view.*

class TripPhotosSlideFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.swipe_trip_photo, container, false)
        /*since our views are in fragment_main.xml which is inflated in rootView
          so we have to write rootView.oursomeview
          otherwise it will try to find the view in activity_main.xml so app will crash*/
        //handle swipe/slide
        arguments?.let {
            rootView.image_trip.setImageResource(it.getInt(ARG_IMAGE_RESOURCE))
        }
        /*if (arguments!!.getInt(ARG_SECTION_NUMBER) == 1){
            //set title to title_tv
            rootView.title_tv.text = "Title One"
            //set image to image_iv
            rootView.image_iv.setImageResource(R.drawable.battery)
            //set description to description_tv
            rootView.description_tv.text = "The long description of the Title One"
        }
        if (arguments!!.getInt(ARG_SECTION_NUMBER) == 2){
            //set title to title_tv
            rootView.title_tv.text = "Title Two"
            //set image to image_iv
            rootView.image_iv.setImageResource(R.drawable.cpu)
            //set description to description_tv
            rootView.description_tv.text = "The long description of the Title Two"
        }
        if (arguments!!.getInt(ARG_SECTION_NUMBER) == 3){
            //set title to title_tv
            rootView.title_tv.text = "Title Three"
            //set image to image_iv
            rootView.image_iv.setImageResource(R.drawable.devid)
            //set description to description_tv
            rootView.description_tv.text = "The long description of the Title Three"
        }
        if (arguments!!.getInt(ARG_SECTION_NUMBER) == 4){
            //set title to title_tv
            rootView.title_tv.text = "Title Four"
            //set image to image_iv
            rootView.image_iv.setImageResource(R.drawable.display)
            //set description to description_tv
            rootView.description_tv.text = "The long description of the Title Four"
        }
        if (arguments!!.getInt(ARG_SECTION_NUMBER) == 5){
            //set title to title_tv
            rootView.title_tv.text = "Title Five"
            //set image to image_iv
            rootView.image_iv.setImageResource(R.drawable.memory)
            //set description to description_tv
            rootView.description_tv.text = "The long description of the Title Five"
        } */
        return rootView
    }

    companion object {

        private const val ARG_IMAGE_RESOURCE = "image_resource"

        fun newInstance(imageResource: Int): TripPhotosSlideFragment {
            val fragment =
                TripPhotosSlideFragment()
            val args = Bundle()
            args.putInt(ARG_IMAGE_RESOURCE, imageResource)
            fragment.arguments = args
            return fragment
        }
    }
}
