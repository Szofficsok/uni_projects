using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Spawner : MonoBehaviour
{
    public Transform[] SpawnPoint;
    public GameObject[] Enemy;
    float nextToSpawn = 0;
    int j = 0;
    int waves = 2;
    int currentWave = 1;
    int spawned_enemies = 0;
    int spawnLimit = 8;
    bool spawn = true;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
        if (Time.time >= nextToSpawn && spawned_enemies < currentWave*spawnLimit && spawn)
        {
            Debug.Log("Spawn");
            nextToSpawn = Time.time + 2f;
            for(int i = 0; i < SpawnPoint.Length; i++)
            {
                if (j % 5 == 0)
                {
                    Instantiate(Enemy[1], SpawnPoint[i].position, transform.rotation);
                }
                else if (j % 3 == 0)
                    Instantiate(Enemy[2], SpawnPoint[i].position, transform.rotation);
                else
                    Instantiate(Enemy[0], SpawnPoint[i].position, transform.rotation);

                j++;
                spawned_enemies++;
                Debug.Log(spawned_enemies);
            }
        }
        if (PlayerManager.instance.killCounter == spawnLimit * currentWave )
        {
            currentWave++;
            spawned_enemies = 0;
            PlayerManager.instance.killCounter = 0;
            if (currentWave > waves)
            {
                PlayerManager.instance.playerWon = true;
                spawn = false;
            }
        }
        //Debug.Log("enemi   "+spawned_enemies);
        //Debug.Log("cw    "+currentWave);
    }
}
