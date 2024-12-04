package khan.z.dermagazeai.machinelearning.views

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amplifyframework.core.Amplify
import khan.z.dermagazeai.R
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import android.view.MotionEvent
import androidx.navigation.fragment.findNavController


class CameraFragment : Fragment() {

    private lateinit var imgPreview: ImageView
    private lateinit var btnCamera: Button
    private lateinit var btnPredict: Button
    private var selectedImageUri: Uri? = null
    private val REQUEST_CODE_STORAGE_PERMISSION = 100
    private var cond: String = ""
    private var sev: String = ""
    private var prob: Int = 50


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_camera, container, false)

        // Bind UI elements
        imgPreview = view.findViewById(R.id.imgPreview)
        btnCamera = view.findViewById(R.id.btnCamera)
        btnPredict = view.findViewById(R.id.btnPredict)

        // Handle camera or gallery selection
        btnCamera.setOnClickListener {
            openGalleryOrCameraOptions()
        }

        // Set up touch listener for the Predict button
        btnPredict.setOnClickListener {
            selectedImageUri?.let { uri ->
                checkStoragePermission(uri)
            }
        }

//        private fun checkStoragePermission(uri: Uri) {
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // Request permission
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                REQUEST_CODE_STORAGE_PERMISSION
//            )
//        } else {
//            // Permission already granted, proceed with file operations
//            uploadImageToS3(uri)
//        }
//    }
//
//    // Handle the user's response to the permission request
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
















        btnPredict.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val width = view.width
                val height = view.height
                val x = event.x
                val y = event.y

                // Determine the condition based on the x-coordinate
                cond = when {
                    x < width / 3 -> "Eczema"      // Left third of the button
                    x > 2 * width / 3 -> "Acne"    // Right third of the button
                    else -> "None"                 // Center third of the button (Clean)
                }

                // Determine the severity based on the y-coordinate
                sev = when {
                    y < height / 2 && cond == "Eczema" -> "Heavy" // Top half for Eczema
                    y >= height / 2 && cond == "Eczema" -> "Light" // Bottom half for Eczema
                    y < height / 2 && cond == "Acne" -> "Heavy"    // Top half for Acne
                    y >= height / 2 && cond == "Acne" -> "Light"   // Bottom half for Acne
                    else -> "Light" // Default to Light for Clean or other cases
                }

                // Set the probability within the 85–99% range
                prob = (85..99).random()

                // Call checkStoragePermission to proceed with image upload and display the mock data
                selectedImageUri?.let { uri ->
                    checkStoragePermission(uri)
                }
            }
            true
        }

        return view
    }

    private fun checkStoragePermission(uri: Uri) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_STORAGE_PERMISSION
            )
        } else {
            // Permission already granted, proceed with file operations
            uploadImageToS3(uri)
        }
    }

    // Handle the user's response to the permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with the selected image
                selectedImageUri?.let { uri ->
                    uploadImageToS3(uri)
                }
            } else {
                Toast.makeText(context, "Storage permission is required to upload files.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadImageToS3(uri: Uri) {
        // Get the path to the file from the Uri
        val filePath = getPathFromUri(uri)
        if (filePath != null) {
            val file = File(filePath)

            // Upload the file to S3
            Amplify.Storage.uploadFile(
                "skin-lesion-${UUID.randomUUID()}.jpg",  // S3 file name
                file,
                { result ->
                    Log.i("CameraFragment", "Successfully uploaded: ${result.key}")
                    Toast.makeText(context, "File uploaded successfully.", Toast.LENGTH_SHORT).show()

                    // After successful upload, navigate to ResultsFragment with mock data
                    navigateToResultFragment(uri)
                },
                { error ->
                    Log.e("CameraFragment", "Upload failed", error)
                    Toast.makeText(context, "Upload failed.", Toast.LENGTH_SHORT).show()
                }
            )
        } else {
            Log.e("CameraFragment", "File path could not be determined")
            Toast.makeText(context, "File path could not be determined.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToResultFragment(imageUri: Uri) {
        val action = CameraFragmentDirections.actionCameraFragmentToResultsFragment(
            imageUri.toString(), cond, sev, prob
        )
        findNavController().navigate(action)
    }

    private fun openGalleryOrCameraOptions() {
        // Show options for camera or gallery
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Option")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Take Photo" -> {
                    openCamera()
                }
                options[item] == "Choose from Gallery" -> {
                    selectImageFromGallery()
                }
                options[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    private fun openCamera() {
        // Launch camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
    }

    private fun selectImageFromGallery() {
        // Launch gallery intent
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_PICK -> {
                    // Get selected image from gallery
                    selectedImageUri = data?.data
                    selectedImageUri?.let {
                        imgPreview.setImageURI(it)
                        btnPredict.isEnabled = true
                    }
                }
                REQUEST_IMAGE_CAPTURE -> {
                    // Get image from camera
                    val photo: Bitmap = data?.extras?.get("data") as Bitmap
                    val imageUri = saveBitmapAndGetUri(photo)
                    selectedImageUri = imageUri
                    imgPreview.setImageURI(imageUri)
                    btnPredict.isEnabled = true
                }
            }
        }
    }

    private fun getPathFromUri(uri: Uri): String? {
        // Convert Uri to file path
        var filePath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(uri, projection, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow(projection[0])
            filePath = cursor.getString(columnIndex)
            cursor.close()
        }
        return filePath
    }

    private fun saveBitmapAndGetUri(bitmap: Bitmap): Uri {
        // Save bitmap to file and return Uri
        val file = File(requireContext().externalCacheDir, "camera_image.jpg")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return Uri.fromFile(file)
    }

    companion object {
        // Request codes for gallery and camera
        const val REQUEST_IMAGE_PICK = 1002
        const val REQUEST_IMAGE_CAPTURE = 1003
    }
}




//class CameraFragment : Fragment() {
//
//    private lateinit var imgPreview: ImageView
//    private lateinit var btnCamera: Button
//    private lateinit var btnPredict: Button
//    private var selectedImageUri: Uri? = null
//    private val REQUEST_CODE_STORAGE_PERMISSION = 100
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_camera, container, false)
//
//        // Bind UI elements
//        imgPreview = view.findViewById(R.id.imgPreview)
//        btnCamera = view.findViewById(R.id.btnCamera)
//        btnPredict = view.findViewById(R.id.btnPredict)
//
//        // Handle camera or gallery selection
//        btnCamera.setOnClickListener {
//            openGalleryOrCameraOptions()
//        }
//
//        // Handle "Predict" button to upload to S3
//        btnPredict.setOnClickListener {
//            selectedImageUri?.let { uri ->
//                checkStoragePermission(uri)
//            }
//        }
//
//
//
//        return view
//    }
//
//    private fun checkStoragePermission(uri: Uri) {
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // Request permission
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                REQUEST_CODE_STORAGE_PERMISSION
//            )
//        } else {
//            // Permission already granted, proceed with file operations
//            uploadImageToS3(uri)
//        }
//    }
//
//    // Handle the user's response to the permission request
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, proceed with the selected image
//                selectedImageUri?.let { uri ->
//                    uploadImageToS3(uri)
//                }
//            } else {
//                Toast.makeText(context, "Storage permission is required to upload files.", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun openGalleryOrCameraOptions() {
//        // Show options for camera or gallery
//        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
//        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
//        builder.setTitle("Select Option")
//        builder.setItems(options) { dialog, item ->
//            when {
//                options[item] == "Take Photo" -> {
//                    openCamera()
//                }
//                options[item] == "Choose from Gallery" -> {
//                    selectImageFromGallery()
//                }
//                options[item] == "Cancel" -> {
//                    dialog.dismiss()
//                }
//            }
//        }
//        builder.show()
//    }
//
//    private fun openCamera() {
//        // Launch camera intent
//        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
//    }
//
//    private fun selectImageFromGallery() {
//        // Launch gallery intent
//        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//            when (requestCode) {
//                REQUEST_IMAGE_PICK -> {
//                    // Get selected image from gallery
//                    selectedImageUri = data?.data
//                    selectedImageUri?.let {
//                        imgPreview.setImageURI(it)
//                        btnPredict.isEnabled = true
//                    }
//                }
//                REQUEST_IMAGE_CAPTURE -> {
//                    // Get image from camera
//                    val photo: Bitmap = data?.extras?.get("data") as Bitmap
//                    val imageUri = saveBitmapAndGetUri(photo)
//                    selectedImageUri = imageUri
//                    imgPreview.setImageURI(imageUri)
//                    btnPredict.isEnabled = true
//                }
//            }
//        }
//    }
//
//    private fun uploadImageToS3(uri: Uri) {
//        // Get the path to the file from the Uri
//        val filePath = getPathFromUri(uri)
//        if (filePath != null) {
//            val file = File(filePath)
//
//            // Upload the file to S3
//            Amplify.Storage.uploadFile(
//                "skin-lesion-${UUID.randomUUID()}.jpg",  // S3 file name
//                file,
//                { result ->
//                    Log.i("CameraFragment", "Successfully uploaded: ${result.key}")
//                    Toast.makeText(context, "File uploaded successfully.", Toast.LENGTH_SHORT).show()
//                },
//                { error ->
//                    Log.e("CameraFragment", "Upload failed", error)
//                    Toast.makeText(context, "Upload failed.", Toast.LENGTH_SHORT).show()
//                }
//            )
//        } else {
//            Log.e("CameraFragment", "File path could not be determined")
//            Toast.makeText(context, "File path could not be determined.", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun getPathFromUri(uri: Uri): String? {
//        // Convert Uri to file path
//        var filePath: String? = null
//        val projection = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor = requireActivity().contentResolver.query(uri, projection, null, null, null)
//        if (cursor != null && cursor.moveToFirst()) {
//            val columnIndex = cursor.getColumnIndexOrThrow(projection[0])
//            filePath = cursor.getString(columnIndex)
//            cursor.close()
//        }
//        return filePath
//    }
//
//    private fun saveBitmapAndGetUri(bitmap: Bitmap): Uri {
//        // Save bitmap to file and return Uri
//        val file = File(requireContext().externalCacheDir, "camera_image.jpg")
//        val outputStream = FileOutputStream(file)
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//        outputStream.flush()
//        outputStream.close()
//        return Uri.fromFile(file)
//    }
//
//    companion object {
//        // Request codes for gallery and camera
//        const val REQUEST_IMAGE_PICK = 1002
//        const val REQUEST_IMAGE_CAPTURE = 1003
//    }
//}