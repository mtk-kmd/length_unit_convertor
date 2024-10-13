package com.example.unitconvertor

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Conversion factors
    private val unitConversionMap = mapOf(
        "Metre" to 1.0,
        "Millimetre" to 1000.0,
        "Mile" to 0.000621371,
        "Foot" to 3.28084
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etInputValue: EditText = findViewById(R.id.et_input_value)
        val spinnerInputUnit: Spinner = findViewById(R.id.spinner_input_unit)
        val spinnerOutputUnit: Spinner = findViewById(R.id.spinner_output_unit)
        val btnConvert: Button = findViewById(R.id.btn_convert)
        val tvResult: TextView = findViewById(R.id.tv_result)

        // Load units into the spinners
        val units = resources.getStringArray(R.array.length_units)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputUnit.adapter = adapter
        spinnerOutputUnit.adapter = adapter

        btnConvert.setOnClickListener {
            val inputText = etInputValue.text.toString()

            // Validate input
            if (inputText.isEmpty()) {
                Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inputValue = inputText.toDoubleOrNull()
            if (inputValue == null) {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inputUnit = spinnerInputUnit.selectedItem.toString()
            val outputUnit = spinnerOutputUnit.selectedItem.toString()

            // Convert the input value
            val result = convertLength(inputValue, inputUnit, outputUnit)

            tvResult.text = "Result: $result $outputUnit"
        }
    }

    private fun convertLength(value: Double, inputUnit: String, outputUnit: String): Double {
        val inputFactor = unitConversionMap[inputUnit] ?: 1.0
        val outputFactor = unitConversionMap[outputUnit] ?: 1.0
        return value * inputFactor / outputFactor
    }
}
