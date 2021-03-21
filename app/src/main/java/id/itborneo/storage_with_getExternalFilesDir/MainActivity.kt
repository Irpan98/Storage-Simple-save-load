package id.itborneo.storage_with_getExternalFilesDir

import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import id.itborneo.tutlane_androidexternalstoragewithexamples.R
import id.itborneo.tutlane_androidexternalstoragewithexamples.databinding.ActivityMainBinding
import java.io.*

class StringMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val filename = "SampleFile.txt"
    private val filepath = "MyFileStorage"
    private var myExternalFile: File? = null
    var myData = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBinding()
        binding.saveExternalStorage.setOnClickListener {
            actionSave()
        }


        binding.getExternalStorage.setOnClickListener {
            actionLoad()
        }

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            binding.saveExternalStorage.setEnabled(false)
        } else {
            myExternalFile = File(getExternalFilesDir(filepath), filename)
        }
    }

    private fun actionLoad() {
        try {
            val fis = FileInputStream(myExternalFile)
            val _in = DataInputStream(fis)
            val br = BufferedReader(InputStreamReader(_in))
            var strLine: String? = null
            while (br.readLine().also { strLine = it } != null) {
                myData += strLine
            }
            _in.close()
        } catch (e: IOException) {
            e.printStackTrace()

        }

        binding.myInputText.setText(myData)
        binding.response.text = "SampleFile.txt data retrieved from external storage"

    }

    private fun actionSave() {
        try {
            val fos = FileOutputStream(myExternalFile)
            fos.write(binding.myInputText.text.toString().toByteArray())
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        binding.myInputText.setText("")
        binding.response.text = "SampleFile.txt saed to External Storage ..."

    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun isExternalStorageReadOnly(): Boolean {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState
    }

    private fun isExternalStorageAvailable(): Boolean {
        val extStorageState = Environment.getExternalStorageState()
        return (Environment.MEDIA_MOUNTED == extStorageState)
    }

}