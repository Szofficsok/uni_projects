using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class PlayerManager : MonoBehaviour
{

	#region Singleton

	public static PlayerManager instance;
	
	void Awake()
	{
		instance = this;
	}

	#endregion
	public GameObject player;
	public int score;
	public Text text, win, gameOver;
	public bool playerDead = false;
	public int killCounter = 0;
	public bool playerWon = false;
	void Update()
    {
		text.text = score.ToString();
		win.text = "Score: " + score.ToString();
		gameOver.text = "Score: " + score.ToString();
    }


}
