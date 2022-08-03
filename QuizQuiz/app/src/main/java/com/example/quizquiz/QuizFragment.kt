package com.example.quizquiz

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.content.AsyncTaskLoader
import com.example.quizquiz.database.Quiz
import com.example.quizquiz.database.QuizDatabase

class QuizFragment : Fragment(),
    QuizStartFragment.QuizStartListener,
    QuizSolveFragment.QuizSolveListener{
    lateinit var db : QuizDatabase
    lateinit var quizList:List<Quiz>
    var currentQuizIdx = 0
    var correctCount = 0

    //QuizSolveListener 구현하고, 맞췄으면 "정답!", 틀렸으면 "오답!" <=로그로 출력
    override fun onAnswerSelected(isCorrect: Boolean) {
        //isCorrect가 true면 correctCount 1씩 증가
        if(isCorrect) correctCount+=1
        //다음 퀴즈로 넘어가기 => currentQuizIdx 1씩 증가
        currentQuizIdx+=1
        //=>QuizSolveFragment를 다시 만들면서 replace(단, 여기로 전달할 퀴즈는 다음 퀴즈)
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,
                QuizSolveFragment.newInstance(quizList[currentQuizIdx]))
            .commit()
    }

    override fun onQuizStart() {
        Log.d("mytag", "시작하기!!!")

        AsyncTask.execute {
            currentQuizIdx = 0
            correctCount = 0
            quizList = db.quizDAO().getAll()

            childFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container,
                    QuizSolveFragment.newInstance(quizList[currentQuizIdx]))
                .commit()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        val view = inflater.inflate(
            R.layout.quiz_fragment,
            container,
            false)

        db = QuizDatabase.getInstance(requireContext())

        childFragmentManager
            .beginTransaction().add(R.id.fragment_container, QuizStartFragment())
            .commit()

        return view
    }
}