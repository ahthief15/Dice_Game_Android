package com.example.coursework

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler

import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.color.utilities.Score
import java.util.*



class MainActivity2 : AppCompatActivity() {


    //delay time define time between each frame
    private val delayTime = 20
    private val delayTime1 = 50

    // roll animations is number of animation frames the dice should go through when rolled
    private val rollAnimations = 20
    private val rollAnimations1 = 50

    //dice images contain the dice face images
    private val diceImages = intArrayOf(
        R.drawable.d1, R.drawable.d2, R.drawable.d3,
        R.drawable.d4, R.drawable.d5, R.drawable.d6
    )

    private val random = Random()

    // The below varaibles stores information regarding dices rolled by human and computer
    private lateinit var humandie1: ImageView
    private lateinit var humandie2: ImageView
    private lateinit var humandie3: ImageView
    private lateinit var humandie4: ImageView
    private lateinit var humandie5: ImageView
    private lateinit var computerdie1: ImageView
    private lateinit var computerdie2: ImageView
    private lateinit var computerdie3: ImageView
    private lateinit var computerdie4: ImageView
    private lateinit var computerdie5: ImageView
    private lateinit var totalscore: TextView
    private var isEasyMode = true
    var humandice1 = 0
    var humandice2 = 0
    var humandice3 = 0
    var humandice4 = 0
    var humandice5 = 0
    var computerdice1 = 0
    var computerdice2 = 0
    var computerdice3 = 0
    var computerdice4 = 0
    var computerdice5 = 0
    var humanscore = 0
    var computerscore = 0
    var targetScore = 101
    var reroll = 3
    var die1keep = false
    var die2keep = false
    var die3keep = false
    var die4keep = false
    var die5keep = false
    var humanWins = 0
    var computerWins = 0
    var computerreroll = 2
    var computerdie1keep = false
    var computerdie2keep = false
    var computerdie3keep = false
    var computerdie4keep = false
    var computerdie5keep = false
    var threw = false
    var draw = false



    // The below variable include the throw and score buttons

    private lateinit var throwdie: Button
    private lateinit var scoredie: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main2)

        // the below variable are used to connect with activity xml file

        val setTarget = findViewById<Button>(R.id.setTarget)
        humandie1 = findViewById<ImageView>(R.id.humandie1)
        humandie2 = findViewById<ImageView>(R.id.humandie2)
        humandie3 = findViewById<ImageView>(R.id.humandie3)
        humandie4 = findViewById<ImageView>(R.id.humandie4)
        humandie5 = findViewById<ImageView>(R.id.humandie5)
        computerdie1 = findViewById<ImageView>(R.id.computerdie1)
        computerdie2 = findViewById<ImageView>(R.id.computerdie2)
        computerdie3 = findViewById<ImageView>(R.id.computerdie3)
        computerdie4 = findViewById<ImageView>(R.id.computerdie4)
        computerdie5 = findViewById<ImageView>(R.id.computerdie5)
        throwdie = findViewById(R.id.throwdie)
        scoredie = findViewById(R.id.scoredie)
        totalscore = findViewById(R.id.totalScore)




        // when target button is clicked it changes the the target score
        setTarget.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Change Target Score")

            val input = EditText(this)

            // input validation to ensure only integer is added
            input.inputType = InputType.TYPE_CLASS_NUMBER
            builder.setView(input)

            builder.setPositiveButton("OK") { _, _ ->
                val newScore = input.text.toString().toIntOrNull()
                if (newScore != null && newScore > targetScore) {
                    targetScore = newScore
                } else {
                    Toast.makeText(this, "Invalid target score", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

            builder.show()
        }



        // the below codes are to determine which dice human player wants to keep before rerolling
        humandie1.setOnClickListener {
                humandie1.setBackgroundResource(R.drawable.border)
                die1keep = true
        }
        humandie2.setOnClickListener {
            humandie2.setBackgroundResource(R.drawable.border)
            die2keep = true

        }
        humandie3.setOnClickListener {
                humandie3.setBackgroundResource(R.drawable.border)
                die3keep = true

        }
        humandie4.setOnClickListener {
                humandie4.setBackgroundResource(R.drawable.border)
                die4keep = true

        }
        humandie5.setOnClickListener {
                humandie5.setBackgroundResource(R.drawable.border)
                die5keep = true

            }



        // the below functions take place when throw button is clicked
        throwdie.setOnClickListener {
            val handler = Handler()
            try {
                threw = true // this variable is used to ensure wherther throw button is clicked before scoring
                if(reroll == 3) {
                    Throw()
                    // this function is used to thro human and computer die simulateniusly
                    while (computerscore < targetScore && computerreroll > 0) {
                        showRerollPopup() // this function shows a popup before computer rerolls its dice
                        computerreroll -= 1
                    }
                }

                reroll-=1
               if (draw == false) { // this ensures the reroll option is not enable when there is a draw
                   throwdie.setText("Reroll")
                   if (reroll==1){
                       Humanreroll()
                   } else if (reroll == 0){
                       Humanreroll()
                       handler.postDelayed({
                           Score()
                       }, 2000)
                   }
               }




            } catch (e: Exception) {
                e.printStackTrace()
            }



        }
        // this hold functions when score button is clicked
        scoredie.setOnClickListener {
            try {
                if (threw == true) { // checks whether the dice has been threw once before scoring
                    Score()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // the below switch is used to switch between random and optimised strategy for computer
        val modeSwitch = findViewById<Switch>(R.id.mode_switch)
        modeSwitch.setOnCheckedChangeListener { _, isChecked ->
            isEasyMode = isChecked
            if (isChecked) {
                modeSwitch.text = "Easy Mode"
            } else {
                modeSwitch.text = "Hard Mode"
            }
        }




    }
    // this function holds human dice rereoll code
    fun Humanreroll(){
        val runnable = Runnable{
            for (i in 0 until rollAnimations) {

                // randomly assigns a value to the dice
                humandice1 = if (!die1keep == true){random.nextInt(6) + 1} else{humandice1 }
                humandice2 = if (!die2keep == true){random.nextInt(6) + 1} else{humandice2}
                humandice3 = if (!die3keep == true){random.nextInt(6) + 1} else{humandice3}
                humandice4 = if (!die4keep == true){random.nextInt(6) + 1} else{humandice4}
                humandice5 = if (!die5keep == true){random.nextInt(6) + 1} else{humandice5}



                // sets the die image based on the randomly generated index
                humandie1.setImageResource(diceImages[humandice1 - 1])
                humandie2.setImageResource(diceImages[humandice2 - 1])
                humandie3.setImageResource(diceImages[humandice3 - 1])
                humandie4.setImageResource(diceImages[humandice4 - 1])
                humandie5.setImageResource(diceImages[humandice5 - 1])


                try {
                    Thread.sleep(delayTime.toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        val thread = Thread(runnable)
        thread.start()





    }


    // this function holds the code for  simultaneous throw of human and computer
    fun Throw(){
        val runnable = Runnable{
            for (i in 0 until rollAnimations) {


                humandice1 = random.nextInt(6) + 1
                humandice2 = random.nextInt(6) + 1
                humandice3 = random.nextInt(6) + 1
                humandice4 = random.nextInt(6) + 1
                humandice5 = random.nextInt(6) + 1
                computerdice1 = random.nextInt(6) + 1
                computerdice2 = random.nextInt(6) + 1
                computerdice3 = random.nextInt(6) + 1
                computerdice4 = random.nextInt(6) + 1
                computerdice5 = random.nextInt(6) + 1


                // sets the die image based on the randomly generated index
                humandie1.setImageResource(diceImages[humandice1 - 1])
                humandie2.setImageResource(diceImages[humandice2 - 1])
                humandie3.setImageResource(diceImages[humandice3 - 1])
                humandie4.setImageResource(diceImages[humandice4 - 1])
                humandie5.setImageResource(diceImages[humandice5 - 1])
                computerdie1.setImageResource(diceImages[computerdice1 - 1])
                computerdie2.setImageResource(diceImages[computerdice2 - 1])
                computerdie3.setImageResource(diceImages[computerdice3 - 1])
                computerdie4.setImageResource(diceImages[computerdice4 - 1])
                computerdie5.setImageResource(diceImages[computerdice5 - 1])



                try {
                    Thread.sleep(delayTime.toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        val thread = Thread(runnable)
        thread.start()


    }


   // this function holds the code to show a pop up before computer rerolls
    fun showRerollPopup() {
        var shouldReroll = random.nextBoolean()
        if (!isEasyMode && computerscore<humanscore) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Computer Reroll")
            builder.setMessage("The computer will now reroll its dice. Click OK to continue.")
            builder.setPositiveButton("OK") { _, _ ->
                computerreroll()
            }
            builder.setNegativeButton("Cancel", null)
            builder.show()
        } else if(shouldReroll == true && isEasyMode){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Computer Reroll")
            builder.setMessage("The computer will now reroll its dice. Click OK to continue.")
            builder.setPositiveButton("OK") { _, _ ->
                randomcomputerreroll()
            }
            builder.setNegativeButton("Cancel", null)
            builder.show()
        }
    }

    // this function holds the code to update the score of human and computer player
    fun Score() {

        humanscore += humandice1 + humandice2 + humandice3 + humandice4 + humandice5
        computerscore += computerdice1 + computerdice2 + computerdice3 + computerdice4 + computerdice5

        totalscore.setText("Human Score = " + humanscore +"\n" +"Computer Score = " + computerscore)
        if(humanscore >= targetScore || computerscore >= targetScore) {
            if (humanscore > computerscore) {
                humanWins += 1
                val builder = AlertDialog.Builder(this)
                builder.setTitle("You Win!")
                    .setMessage("Congratulations! You scored over 101.")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                        throwdie.isEnabled = false
                        scoredie.isEnabled = false

                    }
                val dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.holo_green_light)
            } else if (computerscore > humanscore) {
                computerWins += 1
                val builder = AlertDialog.Builder(this)
                builder.setTitle("You Loose!")
                    .setMessage("Better Luck Next Time")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                        throwdie.isEnabled = false
                        scoredie.isEnabled = false


                    }
                val dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.holo_red_light)
            } else if (humanscore == computerscore)  {
                 draw=true
            }


        }

        throwdie.setText("Throw") // once the score function is called it sets the button back to throw to indicate rerolls are over
        reroll = 3
        threw = false // this varaible makees the score not be update until the score button is clicked again
        computerreroll = 2
        //sets all selected dices to keep back to default after reroll is done
        humandie1.background = null
        die1keep = false
        humandie2.background = null
        die2keep = false
        humandie3.background = null
        die3keep = false
        humandie4.background = null
        die4keep = false
        humandie5.background = null
        die5keep = false




    }

    // this function sets all the dices selected to keep back to default



    // this function holds the code for computer rerolling using optimised stategy
    fun computerreroll(){
        if (computerdice1>4){
            computerdie1keep = true
        }
        if (computerdice2>4){
            computerdie2keep = true
        }
        if (computerdice3>4){
            computerdie3keep = true
        }
        if (computerdice4>4){
            computerdie4keep = true
        }
        if (computerdice5>4){
            computerdie5keep = true
        }
        val runnable = Runnable{
            for (i in 0 until rollAnimations1) {

            computerdice1 = if(!computerdie1keep == true) {random.nextInt(6) + 1} else{computerdice1}
            computerdice2 = if(!computerdie2keep == true) {random.nextInt(6) + 1} else{computerdice2}
            computerdice3 = if(!computerdie3keep == true) {random.nextInt(6) + 1} else{computerdice3}
            computerdice4 = if(!computerdie4keep == true) {random.nextInt(6) + 1} else{computerdice4}
            computerdice5 = if(!computerdie5keep == true) {random.nextInt(6) + 1} else{computerdice5}

            computerdie1.setImageResource(diceImages[computerdice1 - 1])
            computerdie2.setImageResource(diceImages[computerdice2 - 1])
            computerdie3.setImageResource(diceImages[computerdice3 - 1])
            computerdie4.setImageResource(diceImages[computerdice4 - 1])
            computerdie5.setImageResource(diceImages[computerdice5 - 1])

            try {
                Thread.sleep(delayTime1.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
            }
            val thread = Thread(runnable)
            thread.start()



        // sets all the computer dice keep to their default value
        computerdie1keep = false
        computerdie2keep = false
        computerdie3keep = false
        computerdie4keep = false
        computerdie4keep = false
        computerreroll = 2
}


    // this function holds the function for random computer strategy
    fun randomcomputerreroll(){

            computerdie1keep = random.nextBoolean()

            computerdie2keep = random.nextBoolean()

            computerdie3keep = random.nextBoolean()

            computerdie4keep = random.nextBoolean()

            computerdie5keep = random.nextBoolean()

        val runnable = Runnable{
            for (i in 0 until rollAnimations1) {

                computerdice1 = if(!computerdie1keep == true) {random.nextInt(6) + 1} else{computerdice1}
                computerdice2 = if(!computerdie2keep == true) {random.nextInt(6) + 1} else{computerdice2}
                computerdice3 = if(!computerdie3keep == true) {random.nextInt(6) + 1} else{computerdice3}
                computerdice4 = if(!computerdie4keep == true) {random.nextInt(6) + 1} else{computerdice4}
                computerdice5 = if(!computerdie5keep == true) {random.nextInt(6) + 1} else{computerdice5}

                computerdie1.setImageResource(diceImages[computerdice1 - 1])
                computerdie2.setImageResource(diceImages[computerdice2 - 1])
                computerdie3.setImageResource(diceImages[computerdice3 - 1])
                computerdie4.setImageResource(diceImages[computerdice4 - 1])
                computerdie5.setImageResource(diceImages[computerdice5 - 1])


                try {
                    Thread.sleep(delayTime1.toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        val thread = Thread(runnable)
        thread.start()



        // sets all the computer dice keep to their default value
        computerdie1keep = false
        computerdie2keep = false
        computerdie3keep = false
        computerdie4keep = false
        computerdie4keep = false
        computerreroll = 2
    }

    // this function stores the total wins value when activity is paused
    override fun onPause() {
        super.onPause()

        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt("human_wins", humanWins)
        editor.putInt("computer_wins", computerWins)
        editor.apply()
        
    }

    // this function stores back the value of total wins when activity is started again
    override fun onResume() {
        super.onResume()

        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        humanWins = prefs.getInt("human_wins", 0)
        computerWins = prefs.getInt("computer_wins", 0)

        // Update the TextViews to display the restored values
        val totalWins = findViewById<TextView>(R.id.totalWins)
        totalWins.text = "H: $humanWins C:$computerWins"


    }




    }





























