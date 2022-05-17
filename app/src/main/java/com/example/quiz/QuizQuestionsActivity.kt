package com.example.quiz

import android.annotation.SuppressLint
import android.content.Intent
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
    /**
     * Create variables for each view in the layout
     * */
    private var mCurrentQuestion: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOption: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null

    private var tvQuestion: TextView? = null
    private var ivImage: ImageView? = null
    private var pbProgress: ProgressBar? = null
    private var tvProgress: TextView? = null

    private var tvOption1: TextView? = null
    private var tvOption2: TextView? = null
    private var tvOption3: TextView? = null
    private var tvOption4: TextView? = null

    private var btnSubmit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        /**
         * Initialize variables
         * */
        mQuestionsList = Constants.getQuestions()
        mUserName = intent.getStringExtra(Constants.USER_NAME)

        tvQuestion = findViewById(R.id.tv_Question)
        ivImage = findViewById(R.id.iv_Image)
        pbProgress = findViewById(R.id.pb_Progress)
        tvProgress = findViewById(R.id.tv_Progress)

        tvOption1 = findViewById(R.id.tv_Option1)
        tvOption2 = findViewById(R.id.tv_Option2)
        tvOption3 = findViewById(R.id.tv_Option3)
        tvOption4 = findViewById(R.id.tv_Option4)

        btnSubmit = findViewById(R.id.btn_Submit)

        setQuestion()

        tvOption1?.setOnClickListener(this)
        tvOption2?.setOnClickListener(this)
        tvOption3?.setOnClickListener(this)
        tvOption4?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)
    }

    /**
     * Setting the question to UI components.
     */
    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        // Getting the question from the list with the help of current position.
        val question: Question = mQuestionsList!![mCurrentQuestion - 1]
        defaultOptionsView()
        if (mCurrentQuestion == mQuestionsList!!.size) {
            btnSubmit?.text = "FINISH"
        } else {
            btnSubmit?.text = "SUBMIT"
        }
        // Setting the current progress in the progressbar using the position of question
        pbProgress?.progress = mCurrentQuestion
        // Setting up the progress text
        tvProgress?.text = "$mCurrentQuestion" + "/" + (pbProgress?.max?.plus(1))

        // Now set the current question and the options in the UI
        tvQuestion?.text = question.question
        ivImage?.setImageResource(question.image)
        tvOption1?.text = question.option1
        tvOption2?.text = question.option2
        tvOption3?.text = question.option3
        tvOption4?.text = question.option4
    }

    /**
     * Set default options view when the new question is loaded,
     * or when the answer is reselected.
     */
    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        tvOption1?.let {
            options.add(0, it)
        }
        tvOption2?.let {
            options.add(1, it)
        }
        tvOption3?.let {
            options.add(2, it)
        }
        tvOption4?.let {
            options.add(3, it)
        }

        for (option in options) {
            option.setTextColor(Color.parseColor("#6D6875"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this@QuizQuestionsActivity,
                R.drawable.default_option_border_bg
            )
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_Option1 -> {
                tvOption1?.let {
                    selectedOptionView(it, 1)
                }
            }
            R.id.tv_Option2 -> {
                tvOption2?.let {
                    selectedOptionView(it, 2)
                }
            }
            R.id.tv_Option3 -> {
                tvOption3?.let {
                    selectedOptionView(it, 3)
                }
            }
            R.id.tv_Option4 -> {
                tvOption4?.let {
                    selectedOptionView(it, 4)
                }
            }
            R.id.btn_Submit -> {
                if (mSelectedOption == 0) {
                    mCurrentQuestion++
                    when {
                        mCurrentQuestion <= mQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(
                                this@QuizQuestionsActivity,
                                ResultActivity::class.java
                            )
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentQuestion - 1)
                    if (question!!.correctAnswer != mSelectedOption) {
                        answerView(mSelectedOption, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentQuestion == mQuestionsList!!.size) {
                        btnSubmit?.text = "FINISH"
                    } else {
                        btnSubmit?.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOption = 0
                }
            }
        }
    }

    /**
     * Set the view of selected option view.
     */
    private fun selectedOptionView(tv_Option: TextView, selectedOptionNum: Int) {
        defaultOptionsView()

        mSelectedOption = selectedOptionNum
        tv_Option.setTextColor(Color.parseColor("#FFBB86FC"))
        tv_Option.setTypeface(tv_Option.typeface, Typeface.BOLD)
        tv_Option.background = ContextCompat.getDrawable(
            this@QuizQuestionsActivity,
            R.drawable.selected_option_border_bg
        )
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                tvOption1?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
            2 -> {
                tvOption2?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
            3 -> {
                tvOption3?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
            4 -> {
                tvOption4?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
        }
    }
}