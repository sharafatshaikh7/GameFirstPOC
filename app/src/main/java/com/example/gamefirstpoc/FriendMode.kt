package com.example.gamefirstpoc

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils

class FriendMode : AppCompatActivity() {

    lateinit var mFriendPatternLockView: PatternLockView
    lateinit var mTryPatternLockView: PatternLockView
    lateinit var friendPattern: String
    private var tryCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ai_mode)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mFriendPatternLockView = findViewById(R.id.FriendPattern)
        mTryPatternLockView = findViewById(R.id.TryPattern)

        friendPattern = generateFriendPattern()
        Log.d(javaClass.name, "Friend Pattern: $friendPattern")

        mFriendPatternLockView.isInputEnabled = true
        mTryPatternLockView.isInputEnabled = true

        mFriendPatternLockView.addPatternLockListener(mFriendPatternLockViewListener)
        mTryPatternLockView.addPatternLockListener(mTryPatternLockViewListener)
    }

    private val mFriendPatternLockViewListener: PatternLockViewListener =
        object : PatternLockViewListener {
            override fun onStarted() {
                Log.d(javaClass.name, "Friend pattern drawing started")
            }

            override fun onProgress(progressPattern: List<PatternLockView.Dot>) {

            }

            override fun onComplete(pattern: List<PatternLockView.Dot>) {

                friendPattern = PatternLockUtils.patternToString(mFriendPatternLockView, pattern)
                Log.d(javaClass.name, "Friend Pattern Set: $friendPattern")
                mFriendPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT)
            }

            override fun onCleared() {
                }
        }

    private val mTryPatternLockViewListener: PatternLockViewListener =
        object : PatternLockViewListener {
            override fun onStarted() {
                Log.d(javaClass.name, "Try pattern drawing started")
            }

            override fun onProgress(progressPattern: List<PatternLockView.Dot>) {
                }

            override fun onComplete(pattern: List<PatternLockView.Dot>) {
                val enteredPattern = PatternLockUtils.patternToString(mTryPatternLockView, pattern)
                if(enteredPattern == friendPattern || enteredPattern == friendPattern.reversed()) {
                    mTryPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT)
                    Toast.makeText(
                        this@FriendMode,
                        "Congratulations! Try Pattern Matched Friend Pattern",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    mTryPatternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG)
                    Toast.makeText(
                        this@FriendMode,
                        "Oh-Ho Try Pattern mismatched - Try Again !!!",
                        Toast.LENGTH_SHORT
                    ).show()
                    tryCount++
                    if (tryCount >= 3) {

                    }
                }
            }

            override fun onCleared() {

            }
        }

    private fun generateFriendPattern(): String {
        return "123"
    }
}