package com.example.quizquiz

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.quizquiz.database.Quiz

class QuizSolveFragment : Fragment() {
    interface QuizSolveListener{
        fun onAnswerSelected(isCorrect: Boolean)
    }
    lateinit var listener: QuizSolveListener
    lateinit var quiz:Quiz

    //onAttach구현
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(parentFragment is QuizSolveListener) {
            listener = parentFragment as QuizSolveListener
        } else {
            throw Exception("QuizStartListener 미구현")
        }
    }
}