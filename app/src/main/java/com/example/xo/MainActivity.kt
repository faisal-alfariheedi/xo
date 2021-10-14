package com.example.xo

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import java.lang.System.exit
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    lateinit var trb: Button
    lateinit var tmb: Button
    lateinit var tlb: Button
    lateinit var mrb: Button
    lateinit var mmb: Button
    lateinit var mlb: Button
    lateinit var brb: Button
    lateinit var bmb: Button
    lateinit var blb: Button
    lateinit var tvagai: TextView
    lateinit var tvwin: TextView
    lateinit var cp1: CardView
    lateinit var cp2: CardView
    lateinit var tvp1: TextView
    lateinit var tvp1w:TextView
    lateinit var tvp2w:TextView
    lateinit var tvp2: TextView
    lateinit var buttons: List<Button>
    var p1w:Int=0
    var p2w:Int=0
    lateinit var sp :SharedPreferences
    lateinit var cl : ConstraintLayout
    lateinit var ad : AnimationDrawable
    var turn = true
    var draw = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        cp1.setBackgroundColor(Color.BLUE)
        buttons = listOf(tlb, tmb, trb, mlb, mmb, mrb, blb, bmb, brb)

        for(btn in buttons){onclick(btn)}
    }
    fun init(){
        tlb = findViewById(R.id.topleftbut)
        tmb = findViewById(R.id.topmidbut)
        trb = findViewById(R.id.toprightbut)
        mlb = findViewById(R.id.midleftbut)
        mmb = findViewById(R.id.midmidbut)
        mrb = findViewById(R.id.midrightbut)
        blb = findViewById(R.id.botleftbut)
        bmb = findViewById(R.id.botmidbut)
        brb = findViewById(R.id.botrightbut)
        tvagai = findViewById(R.id.tvagain)
        tvwin = findViewById(R.id.tvwin)
        cp1 = findViewById(R.id.p1)
        cp2 = findViewById(R.id.p2)
        tvp1 = findViewById(R.id.tvP1Text)
        tvp1w=findViewById(R.id.tvP1win)
        tvp2w=findViewById(R.id.tvP2win)
        tvp2 = findViewById(R.id.tvP2Text)
        cp1.setBackgroundColor(Color.BLUE)
        cp2.setBackgroundColor(Color.WHITE)
        saverestore(true)
        cl =findViewById(R.id.main)
        ad =cl.background as AnimationDrawable
        ad.setEnterFadeDuration(1000)
        ad.setExitFadeDuration(2000)
        ad.start()


    }

    fun onclick(button: Button){
        button.setOnClickListener {
            if(!tvwin.isVisible){
                if(button.text.isBlank()){
                    if(turn){
                        button.text = "X"
                        if(wincheck()){
                            p1w++
                            tvp1w.text="$p1w"
                            gameOver(1)
                        }else{
                            turn = false
                            cp1.setBackgroundColor(Color.WHITE)
                            cp2.setBackgroundColor(Color.GREEN)
                        }
                    }else{
                        button.text = "O"
                        if(wincheck()){
                            p2w++
                            tvp2w.text="$p2w"
                            gameOver(2)
                        }else{
                            turn = true
                            cp1.setBackgroundColor(Color.BLUE)
                            cp2.setBackgroundColor(Color.WHITE)
                        }
                    }
                }else{
                    Toast.makeText(this, "Please choose another tile", Toast.LENGTH_LONG).show()
                }
            }
            draw = true
            for(button in buttons){
                if(button.text.isBlank()){draw = false}
            }
            if(draw){gameOver(0)}
        }
    }

    fun gameOver(player: Int){
        if(player>0){
            tvwin.text = "Player $player Wins!"
        }else{
            tvwin.text = "Draw"
        }
        tvwin.isVisible = true
        tvagai.isVisible = true
        tvp1.text = "YES"
        cp1.setOnClickListener { reset() }
        tvp2.text = "NO"
        cp2.setOnClickListener { saverestore(false);exit(0) }
        cp1.setBackgroundColor(Color.GREEN)
        cp2.setBackgroundColor(Color.RED)
        saverestore(false)
    }
    fun reset(){
        tvwin.isVisible = false
        tvagai.isVisible = false
        cp1.setBackgroundColor(Color.BLUE)
        cp2.setBackgroundColor(Color.WHITE)
        for(b in buttons)
            b.text=""
    }
    private fun saverestore(s:Boolean) {
        sp = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        if (s) {
            p1w=sp.getInt("p1w",0)
            p2w=sp.getInt("p2w",0)
            tvp1w.text="$p1w"
            tvp2w.text="$p2w"
        } else {
            with(sp.edit()) {
                putInt("p1w",p1w)
                putInt("p2w",p2w)
                apply()
            }

        }
    }

    fun wincheck(): Boolean{
        if(trb.text == tmb.text && trb.text == tlb.text && trb.text.isNotBlank()){return true}
        if(mrb.text == mmb.text && mrb.text == mlb.text && mrb.text.isNotBlank()){return true}
        if(brb.text == bmb.text && brb.text == blb.text && brb.text.isNotBlank()){return true}
        if(trb.text == mrb.text && trb.text == brb.text && trb.text.isNotBlank()){return true}
        if(tmb.text == mmb.text && tmb.text == bmb.text && tmb.text.isNotBlank()){return true}
        if(tlb.text == mlb.text && tlb.text == blb.text && tlb.text.isNotBlank()){return true}
        if(brb.text == mmb.text && brb.text == tlb.text && brb.text.isNotBlank()){return true}
        if(trb.text == mmb.text && trb.text == blb.text && trb.text.isNotBlank()){return true}
        return false
    }
}
