package com.curbmap.android.view


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import com.curbmap.android.R
import com.curbmap.android.SharedCameraAndPreviewViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.fotoapparat.Fotoapparat
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.back
import io.fotoapparat.selector.containsFps
import io.fotoapparat.selector.manualJpegQuality
import io.fotoapparat.selector.on
import io.fotoapparat.view.CameraView
import io.fotoapparat.view.FocusView
import timber.log.Timber.*
import java.util.*
import java.util.concurrent.ExecutionException


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * {link Camera.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the [Camera.newInstance] factory method to
 * create an instance of this fragment.
 */
class Camera : Fragment(), View.OnClickListener, NavController.OnNavigatedListener {


    private val TAG = "Camera"

    private lateinit var viewModel: SharedCameraAndPreviewViewModel
    private lateinit var fotoapparat: Fotoapparat
    private lateinit var focusView: FocusView
    private lateinit var cameraView: CameraView
    private lateinit var button: FloatingActionButton
    private lateinit var progressBar: ProgressBar
    // FIXME: 7/30/18 TODO: Request Permissions
    private val permissionGranted = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SharedCameraAndPreviewViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_camera, container, false)
        this.cameraView = view.findViewById(R.id.camera_view)
        this.focusView = view.findViewById(R.id.focus_view)

        fotoapparat = createFotoapparat()

        this.button = view.findViewById(R.id.camera_capture)
        this.progressBar = view.findViewById(R.id.progress_circular)
        progressBar.visibility = View.INVISIBLE

        button.setOnClickListener { this.onClick(view) }

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        plant(DebugTree())
    }


    override fun onPause() {
        fotoapparat.stop()
        super.onPause()
    }


    override fun onStart() {
        super.onStart()
        if (permissionGranted) {
            fotoapparat.start()
        }
    }

    override fun onStop() {
        fotoapparat.stop()
        super.onStop()
    }

    override fun onNavigated(controller: NavController, destination: NavDestination) {
        //controller.popBackStack();
    }

    private fun createFotoapparat(): Fotoapparat {
        return Fotoapparat
                .with(this.context!!)
                .into(cameraView!!)
                .focusView(focusView)
                .previewScaleType(ScaleType.CenterCrop)
                .lensPosition(back())
                .flash(on())
                .cameraErrorCallback { e -> Toast.makeText(activity, "Camera Error: ", Toast.LENGTH_LONG).show()}
                //.jpegQuality(manualJpegQuality(50))
                .build()
    }

   /* private fun capture() = if (this::viewModel.isInitialized) {
        val result = fotoapparat.takePicture()
        viewModel.setPhotoResult(result)
        true
    } else {
        Toast.makeText(this.context, "Camera->ViewModel Error", Toast.LENGTH_LONG).show()
        false
    }*/

    private fun navigateToPreview(v: View) {
        Navigation.findNavController(v.rootView).navigate(CameraDirections.actionCameraToImagePreview())

        //Navigation.findNavController(v).navigate(R.id.action_camera_to_imagePreview)
    }

    override fun onClick(v: View) {

        if (this::viewModel.isInitialized) {

            val result = fotoapparat.takePicture()

                //viewModel.setPhotoResult(result)
                try {
                    viewModel.getBitmapMutableLiveData().value = result.toBitmap().await().bitmap
                }catch (e:ExecutionException){
                    e(e.message)
                }

            Navigation.findNavController(v).navigate(CameraDirections.actionCameraToImagePreview())
            //navigateToPreview(v)
        } else {
            Toast.makeText(this.context, "Camera->ViewModel Error", Toast.LENGTH_LONG).show()
        }

    }

    fun newInstance(): Camera {
        return Camera()
    }


    /*
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

*/


}


