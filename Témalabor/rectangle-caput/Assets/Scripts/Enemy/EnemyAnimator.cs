using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.AI;

public class EnemyAnimator : MonoBehaviour
{
    Animator animator;
    NavMeshAgent agent;
    public float attack;
    public float die;

    // Start is called before the first frame update
    void Start()
    {
        agent = GetComponent<NavMeshAgent>();
        animator = GetComponentInChildren<Animator>();
    }

    // Update is called once per frame
    void Update()
    {
        float speed = agent.velocity.magnitude / agent.speed;
        animator.SetFloat("speed", speed,.1f,Time.deltaTime);
        animator.SetFloat("attack", attack,.1f,Time.deltaTime);
        animator.SetFloat("die", die,.1f,Time.deltaTime);
    }
}
