package com.example.quiz

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentQuestion: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOption: Int = 0

    private var tv_Question: TextView? = null
    private var iv_Image: ImageView? = null
    private var pb_Progress: ProgressBar? = null
    private var tv_Progress: TextView? = null

    private var tv_Option1: TextView? = null
    private var tv_Option2: TextView? = null
    private var tv_Option3: TextView? = null
    private var tv_Option4: TextView? = null

    private var btn_Submit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mQuestionsList = Constants.getQuestions()

        tv_Question = findViewById(R.id.tv_Question)
        iv_Image = findViewById(R.id.iv_Image)
        pb_Progress = findViewById(R.id.pb_Progress)
        tv_Progress = findViewById(R.id.tv_Progress)

        tv_Option1 = findViewById(R.id.tv_Option1)
        tv_Option2 = findViewById(R.id.tv_Option2)
        tv_Option3 = findViewById(R.id.tv_Option3)
        tv_Option4 = findViewById(R.id.tv_Option4)
        tv_Option1?.setOnClickListener(this)
        tv_Option2?.setOnClickListener(this)
        tv_Option3?.setOnClickListener(this)
        tv_Option4?.setOnClickListener(this)

        btn_Submit = findViewById(R.id.btn_Submit)
        btn_Submit?.setOnClickListener(this)


        setQuestion()
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        mCurrentQuestion = 1
        val question: Question = mQuestionsList!![mCurrentQuestion - 1]

        tv_Question?.text = question.question
        iv_Image?.setImageResource(question.image)
        pb_Progress?.progress = mCurrentQuestion
        tv_Progress?.text = "$mCurrentQuestion/${mQuestionsList!!.size}"

        tv_Option1?.text = question.option1
        tv_Option2?.text = question.option2
        tv_Option3?.text = question.option3
        tv_Option4?.text = question.option4

        if (mCurrentQuestion == mQuestionsList!!.size) {
            btn_Submit?.text = "FINISH"
        } else {
            btn_Submit?.text = "SUBMIT"
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        tv_Option1?.let {
            options.add(0, it)
        }
        tv_Option2?.let {
            options.add(1, it)
        }
        tv_Option3?.let {
            options.add(2, it)
        }
        tv_Option4?.let {
            options.add(3, it)
        }

        for (option in options) {
            option.setTextColor(Color.parseColor("@color/textGrey"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    private fun selectedOptionView(tv_Option: TextView, selectedOptionNum: Int) {
        defaultOptionsView()

        mSelectedOption = selectedOptionNum
        tv_Option.setTextColor(Color.parseColor("@color/design_default_color_primary"))
        tv_Option.typeface = Typeface.DEFAULT_BOLD
        tv_Option.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_Option1 -> {
                tv_Option1?.let {
                    selectedOptionView(it, 1)
                }
            }
            R.id.tv_Option2 -> {
                tv_Option2?.let {
                    selectedOptionView(it, 2)
                }
            }
            R.id.tv_Option3 -> {
                tv_Option3?.let {
                    selectedOptionView(it, 3)
                }
            }
            R.id.tv_Option4 -> {
                tv_Option4?.let {
                    selectedOptionView(it, 4)
                }
            }
            R.id.btn_Submit -> {

            }
        }
    }
}