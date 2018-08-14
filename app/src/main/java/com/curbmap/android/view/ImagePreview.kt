package com.curbmap.android.view


import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.BitmapCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import com.curbmap.android.R
import com.curbmap.android.SharedCameraAndPreviewViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * {link ImagePreview.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the [ImagePreview.newInstance] factory method to
 * create an instance of this fragment.
 */
//private OnFragmentInteractionListener mListener;



class ImagePreview : Fragment(), View.OnClickListener, NavController.OnNavigatedListener {

    private val TAG = "ImagePreview"

    private lateinit var imageView : AppCompatImageView
    private lateinit var bitmap: BitmapCompat

    private lateinit var button: FloatingActionButton

    private lateinit var viewModel: SharedCameraAndPreviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SharedCameraAndPreviewViewModel::class.java)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_image_preview, container, false)
        this.imageView = view.findViewById(R.id.image_preview_holder)
        this.imageView.setImageResource(R.drawable.ic_launcher_foreground)

        val updateImage = Observer<Bitmap> { newName ->
            // Update the UI, in this case, a ImageView.
            imageView.setImageBitmap(newName)
        }


        viewModel.getBitmapMutableLiveData().observe(this, updateImage)

        updateImage.onChanged(viewModel.getBitmap())


        button = view.findViewById(R.id.image_send)
        button.setOnClickListener(this)

        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        /*if (mListener != null) {
            //mListener.onFragmentInteraction(uri);
        }*/
    }




    override fun onDetach() {
        super.onDetach()
        // mListener = null;
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onClick(v: View) {

        if (v.id == R.id.image_send) {
            sendImage()
        }

        Navigation.findNavController(v).navigate(ImagePreviewDirections.actionImagePreviewToCamera())
    }

    override fun onNavigated(controller: NavController, destination: NavDestination) {

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
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/


    private fun sendImage() {
        //boolean
        if (viewModel.imageSent()) {
            Toast.makeText(this.context, "Image Sent", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this.activity, "Please Check Data Connection", Toast.LENGTH_LONG).show()
        }


    }

        fun newInstance(): ImagePreview {
            return ImagePreview()
        }

}








