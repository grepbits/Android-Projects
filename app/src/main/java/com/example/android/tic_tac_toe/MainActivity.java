package com.example.android.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView player1score, player2score, playerstatus;
    private Button[] buttons = new Button[9];
    private Button reset;

    private int pl1sccnt, pl2sccnt, roundcount, flag = 0;
    boolean activeplayer;

    //p1->0
    //p2->1
    //empty->2
    int[] gamestate = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningpos = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1score = (TextView) findViewById(R.id.player1_score);
        player2score = (TextView) findViewById(R.id.player2_score);
        playerstatus = (TextView) findViewById(R.id.textView5);

        reset = (Button) findViewById(R.id.button10);
        for (int i = 0; i < 9; i++) {
            String buttonid = "button" + i;
            int resourceId = getResources().getIdentifier(buttonid, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceId);
            buttons[i].setOnClickListener(this);
        }
        roundcount = 0;
        pl1sccnt = 0;
        pl2sccnt = 0;
        activeplayer = true;

    }


    @Override
    public void onClick(View v) {
            if(!((Button)v).getText().toString().equals("")) {
                return;
            }
            if(flag==1)
                return;
            String buttonid=v.getResources().getResourceEntryName(v.getId());
            int gamestateptr=Integer.parseInt(String.valueOf(buttonid.charAt(buttonid.length()-1)));


                if (activeplayer == true) {
                    ((Button) v).setText("X");
                    ((Button) v).setTextColor(Color.parseColor("#FFFFFF"));
                    gamestate[gamestateptr] = 0;
                } else {
                    ((Button) v).setText("O");
                    ((Button) v).setTextColor(Color.parseColor("#FFFFFF"));
                    gamestate[gamestateptr] = 1;
                }

            roundcount++;

            if(checkwinner()&&flag==0)
            {
                if(activeplayer==true)
                {
                    pl1sccnt++;
                    updatepl1sc();
                    Toast.makeText(this,"Player 1 won!",Toast.LENGTH_SHORT).show();
                    flag=1;
                }
                else
                {
                    pl2sccnt++;
                    updatepl1sc();
                    Toast.makeText(this,"Player 2 won!",Toast.LENGTH_SHORT).show();
                    flag=1;
                }
            }else{
                activeplayer= !activeplayer;
                if(roundcount==9)
                {
                    Toast.makeText(this,"No Winner!",Toast.LENGTH_SHORT).show();
                    flag=1;
                }
            }

            if(pl1sccnt>pl2sccnt)
            {
                playerstatus.setText("Player 1 is winning!");
            }
            else
            if(pl1sccnt<pl2sccnt)
            {
                playerstatus.setText("Player 2 is winning!");
            }
            else
            {
                playerstatus.setText("");
            }

            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    roundcount=0;
                    activeplayer=true;
                    for(int i=0;i<buttons.length;i++)
                    {
                        gamestate[i]=2;
                        buttons[i].setText("");
                    }

                    playerstatus.setText("");
                    updatepl1sc();
                    flag=0;

                }
            });
    }

    public boolean checkwinner()
    {
        boolean winnerResult=false;

        for(int [] winningposition:winningpos)
        {
            if(gamestate[winningposition[0]]==gamestate[winningposition[1]]&&
                    gamestate[winningposition[1]]==gamestate[winningposition[2]]
                            &&gamestate[winningposition[0]]!=2)
            {
                winnerResult=true;
            }
        }
        return winnerResult;
    }

    public void updatepl1sc()
    {
        player1score.setText(Integer.toString(pl1sccnt));
        player2score.setText(Integer.toString(pl2sccnt));

    }

    public void playagain()
    {
        roundcount=0;
        activeplayer=true;
        for(int i=0;i<buttons.length;i++)
        {
            gamestate[i]=2;
            buttons[i].setText("");
        }

    }
}