package coid.bcafinance.sistriandroid.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coid.bcafinance.sistriandroid.LoginActivity
import coid.bcafinance.sistriandroid.R
import coid.bcafinance.sistriandroid.adapter.TestDriveAdapter
import coid.bcafinance.sistriandroid.config.RetrofitInstance
import coid.bcafinance.sistriandroid.databinding.FragmentConsumerBinding
import coid.bcafinance.sistriandroid.databinding.FragmentProfileBinding
import coid.bcafinance.sistriandroid.endpoint.QRCodeAPI
import coid.bcafinance.sistriandroid.endpoint.TestDriveAPI
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset
import java.nio.charset.spi.CharsetProvider


class ConsumerFragment : Fragment() {
    private lateinit var testDriveAPI: TestDriveAPI
    private lateinit var qrCodeAPI: QRCodeAPI
    private var _binding: FragmentConsumerBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TestDriveAdapter
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConsumerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        testDriveAPI = RetrofitInstance.retrofit.create(TestDriveAPI::class.java)
        qrCodeAPI = RetrofitInstance.retrofit.create(QRCodeAPI::class.java)



        val authToken = sharedPreferences.getString("token", null)

        if (authToken != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    println("Bearer $authToken")
                    val response = testDriveAPI.getTestDriveList("Bearer $authToken")
                    println("response disini $response")
                    if (response.success) {
                        val testDrives = response.data.content
                        val adapter = TestDriveAdapter(testDrives, object : TestDriveAdapter.OnItemClickListener {
                            override fun onItemClick(testDriveId: Int) {
                                showQRCodeModal(testDriveId)
                            }
                        })
                        recyclerView.adapter = adapter
                    } else {
                        // Handle error
                    }
                } catch (e: Exception) {
                    println(e)
                }
            }
        } else {
            navigateToLoginPage()
        }
    }

    private fun navigateToLoginPage() {
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun showQRCodeModal(testDriveId: Int) {
        val authToken = sharedPreferences.getString("token", null)
        if (authToken != null){
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    val response = qrCodeAPI.getQRCode("Bearer $authToken",testDriveId)
                    if (response.success) {
                        val qrCodeData = response.data
                        showQRCodeDialog(qrCodeData)
                    } else {
                        // Handle error
                    }
                } catch (e: Exception) {
                    println(e)
                }
            }
        }
    }

    private fun showQRCodeDialog(qrCodeData: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("QR Code")

        val qrCodeBitmap = generateQRCodeBitmap(qrCodeData)
        val imageView = ImageView(requireContext())
        imageView.setImageBitmap(qrCodeBitmap)

        builder.setView(imageView)
        builder.setPositiveButton("OK", null)
        builder.show()
    }

    private fun generateQRCodeBitmap(data: String): Bitmap {
        val hints = mapOf(
            EncodeHintType.CHARACTER_SET to Charset.forName("UTF-8").name(),
            EncodeHintType.MARGIN to 1
        )
        val size = 512

        val bitMatrix = QRCodeWriter().encode("http://192.168.137.1:4200/#/api/consumer/in/$data", BarcodeFormat.QR_CODE, size, size, hints)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val pixels = IntArray(width * height)

        for (y in 0 until height) {
            for (x in 0 until width) {
                if (bitMatrix[x, y]) {
                    pixels[y * width + x] = Color.BLACK
                } else {
                    pixels[y * width + x] = Color.WHITE
                }
            }
        }

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }


}