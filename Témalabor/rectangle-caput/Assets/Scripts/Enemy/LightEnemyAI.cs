using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.AI;

public class LightEnemyAI : MonoBehaviour
{
    NavMeshAgent agent;
    Transform target;
    //public  player;
    float nextTimeToHit = 0f;

    // Start is called before the first frame update
    void Start()
    {
        target = PlayerManager.instance.player.transform;
        agent = GetComponent<NavMeshAgent>();
    }

    // Update is called once per frame
    void Update()
    {
        if (!PlayerManager.instance.playerDead)
        {
            float distance = Vector3.Distance(target.position, transform.position);
            Debug.Log(distance);
            agent.SetDestination(target.position);
            FaceTarget();
            if (distance - 1f <= agent.stoppingDistance && Time.time >= nextTimeToHit)
            {
                nextTimeToHit = Time.time + 0.5f;
                Attack(5);
            }
        }
        else
        {
            agent.SetDestination(agent.transform.position);
        }
    }
    void FaceTarget()
    {
        Vector3 direction = (target.position - transform.position).normalized;
        Quaternion lookRotation = Quaternion.LookRotation(new Vector3(direction.x, 0, direction.z));
        transform.rotation = Quaternion.Slerp(transform.rotation, lookRotation, Time.deltaTime * 5f);
    }
    void Attack(int dmg)
    {
        PlayerManager.instance.player.transform.GetComponent<Player>().TakeDamage(dmg);
    }
}
