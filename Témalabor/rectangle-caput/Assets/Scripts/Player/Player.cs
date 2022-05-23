using UnityEngine;

public class Player : MonoBehaviour
{
    public float health = 50f;
    public HealthBar hb;
    private void Start()
    {
        hb.SetMaxHealth(health);
    }
    public void TakeDamage(float amount)
    {
        health -= amount;
        hb.SetHealth(health);

        if (health <= 0f)
        {
            Die();
        }
    }

    void Die()
    {
        PlayerManager.instance.playerDead = true;
        gameObject.SetActive(false);
    }
}
