using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.AI;

public class EnemyAI : MonoBehaviour
{
    NavMeshAgent agent;
    Transform target;
    //public  player;
    float nextTimeToHit = 0f;
    public float health;
    public int score;
    public int damage;
    Animator animator;
    bool dead = false;
    float takeDamageFloat;
    bool takeDamageBool = false;

    // Start is called before the first frame update
    void Start()
    {
        target = PlayerManager.instance.player.transform;
        agent = GetComponent<NavMeshAgent>();
        animator = GetComponentInChildren<Animator>();
        animator.SetBool("isRunning", true);
    }

    // Update is called once per frame
    void Update()
    {
        if (!PlayerManager.instance.playerDead && !dead && Time.time >= nextTimeToHit)
        {
            float distance = Vector3.Distance(target.position, transform.position);
            //Debug.Log(distance);
            
            agent.SetDestination(target.position);
            FaceTarget();
            if (distance - 0.7f <= agent.stoppingDistance && Time.time >= nextTimeToHit)
            {
                agent.SetDestination(agent.transform.position);
                nextTimeToHit = Time.time + 1f;
                takeDamageFloat = Time.time + 0.5f;
                takeDamageBool = true;
                animator.SetBool("isRunning", false);
                animator.SetBool("isAttacking", true);

            }
            else
            {
                animator.SetBool("isAttacking", false);
                animator.SetBool("isRunning", true);
            }
        }
        else
        {
            
            agent.SetDestination(agent.transform.position);
        }
        if (takeDamageBool && Time.time >= takeDamageFloat)
        {
            Attack(damage);
            takeDamageBool = false;
        }

    }
    void FaceTarget()
    {
        Vector3 direction = (target.position - transform.position).normalized;
        Quaternion lookRotation = Quaternion.LookRotation(new Vector3(direction.x, 0, direction.z));
        transform.rotation = Quaternion.Slerp(transform.rotation, lookRotation, Time.deltaTime*5f);
    }
    void Attack(int dmg)
    {
        PlayerManager.instance.player.transform.GetComponent<Player>().TakeDamage(dmg);
    }

    public void TakeDamage(float amount)
    {
        health -= amount;
        if (health <= 0f)
        {
            health = 1000000000000000000000f;
            Die();
        }
    }

    void Die()
    {
        dead = true;
        animator.SetBool("isRunning", false);
        animator.SetBool("isAttacking", false);
        animator.SetBool("isDead", true);
        
        PlayerManager.instance.score += score;
        PlayerManager.instance.killCounter += 1;
        Debug.Log("kc   " + PlayerManager.instance.killCounter);
        Destroy(gameObject, 1f);

        
    }
    
}
