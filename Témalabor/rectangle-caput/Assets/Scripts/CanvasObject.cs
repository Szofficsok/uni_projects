using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class CanvasObject : MonoBehaviour
{
    public GameObject healthBar;
    public GameObject score;
    public GameObject GameOver;
    public GameObject Win;

    // Start is called before the first frame update
    

    // Update is called once per frame
    void Update()
    {
        if(PlayerManager.instance.playerDead)
        {
            GameOver.SetActive(true);
            healthBar.SetActive(false);
            score.SetActive(false);
        }
        if (PlayerManager.instance.playerWon)
        {
            Win.SetActive(true);
            healthBar.SetActive(false);
            score.SetActive(false);
        }
    }
    public void ReturnToMainMenu()
    {
        SceneManager.LoadScene(0);
    }
}
