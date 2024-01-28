package com.example.gamefirstpoc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.app.AppCompatActivity
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils
import kotlin.random.Random

class mainPage : AppCompatActivity() {
    lateinit var mPatternLockView: PatternLockView
    lateinit var targetPattern: String
    private val usedPatterns = mutableSetOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mPatternLockView = findViewById(R.id.pattern)


        targetPattern = generateRandomPattern()
        Log.d(javaClass.name, "$targetPattern")
        val patternCodeTextView: TextView = findViewById(R.id.PatternCode)
        patternCodeTextView.text = "$targetPattern"

        mPatternLockView.addPatternLockListener(mPatternLockViewListener)
    }

    private val mPatternLockViewListener: PatternLockViewListener =
        object : PatternLockViewListener {
            override fun onStarted() {
                Log.d(javaClass.name, "Pattern drawing started")
            }

            override fun onProgress(progressPattern: List<PatternLockView.Dot>) {
                Log.d(
                    javaClass.name, "Pattern progress: " +
                            (PatternLockUtils.patternToString(mPatternLockView, progressPattern))
                )
            }

            override fun onComplete(pattern: List<PatternLockView.Dot>) {
                val enteredPattern = PatternLockUtils.patternToString(mPatternLockView, pattern)
                val incrementedPattern = enteredPattern.map { (it.toString().toInt() + 1) % 10 }.joinToString("")
                if (incrementedPattern.equals(targetPattern, ignoreCase = true)) {
                    mPatternLockView.setViewMode(mPatternLockView.patternViewMode)
                    Toast.makeText(this@mainPage, "Congratulations! You Won. Pattern Code Changed", Toast.LENGTH_SHORT).show()
                    targetPattern = generateRandomPattern()
                    Log.d(javaClass.name, "New Target Pattern: $targetPattern")
                    val patternCodeTextView: TextView = findViewById(R.id.PatternCode)
                    patternCodeTextView.text = "$targetPattern"
                } else {
                    mPatternLockView.setViewMode(mPatternLockView.patternViewMode)
                    Toast.makeText(this@mainPage, "Oh-Ho Pattern mismatched - Try Again !!!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCleared() {
                Log.d(javaClass.name, "Pattern has been cleared")

            }
        }
    private fun restartActivity() {
        val intent = Intent(this, mainPage::class.java)
        startActivity(intent)
        finish()
    }
    fun generateRandomPattern(): String {
        val patterns = listOf("42516739", "452816739", "259148637", "417528639", "245689731", "159273", "259168437", "427538691", "582316497","2573819","75342619","567294381","43572916", "854639271", "485263917", "159482637", "65739", "5632891","32589","956328",)


        val availablePatterns = patterns.filter { it !in usedPatterns }

        if (availablePatterns.isEmpty()) {

            usedPatterns.clear()

            return patterns.random()
        } else {

            val randomPattern = availablePatterns.random()

            usedPatterns.add(randomPattern)
            return randomPattern
        }
    }
}
