using System.Collections;
using UnityEngine;

public class KacsaVadasz : MonoBehaviour
{
    public float damage = 25f;
    public float range = 100f;
    //public float force = 30f;
    public int bullets= 6;
    public float spread = 5f;
    public Camera fpsCam;
    public float firerate = 1;
    private float nextTimeToFire = 0f;
    public GameObject bullet;
    public Transform muzzle;
    public float fadeDuration = 0.2f;

    // Update is called once per frame
    void Update()
    {
        if (Input.GetButton("Fire1") && Time.time >= nextTimeToFire)
        {
            nextTimeToFire = Time.time + 1f / firerate;
            Shoot();
        }
    }

    void Shoot()
    {
        
        for (int i = 0; i < bullets; i++)
        {
            RaycastHit hit;
            Vector3 shootingDirection = GetShootingDirection();
            
            if (Physics.Raycast(fpsCam.transform.position, shootingDirection, out hit, range))
            {
                //Debug.Log(hit.transform.name);

                EnemyAI enemy = hit.transform.GetComponent<EnemyAI>();
                if (enemy != null)
                {
                    enemy.TakeDamage(damage);
                }
                CreateBullet(hit.point);
                /*
                if (hit.rigidbody != null)
                {
                    hit.rigidbody.AddForce(-hit.normal * force);
                }*/
            }
            else
            {
                CreateBullet(fpsCam.transform.position + shootingDirection * range);
            }
            
        }
    }
    Vector3 GetShootingDirection()
    {
        Vector3 targetPos = fpsCam.transform.position + fpsCam.transform.forward * range;
        targetPos = new Vector3(
            targetPos.x + Random.Range(-spread, spread),
            targetPos.y,
            targetPos.z + Random.Range(-spread, spread)
            ); ;
        Vector3 direction = targetPos - fpsCam.transform.position;
        return direction.normalized;
    }

    void CreateBullet(Vector3 end)
    {
        LineRenderer lr = Instantiate(bullet).GetComponent<LineRenderer>();
        lr.SetPositions(new Vector3[2] { muzzle.position, end });
        StartCoroutine(FadeBullet(lr));

        Destroy(lr.gameObject, 2f);
    }

    IEnumerator FadeBullet(LineRenderer lr)
    {
        float alpha = 1;
        while (alpha > 0)
        {
            alpha -= Time.deltaTime / fadeDuration;
            lr.startColor = new Color(lr.startColor.r, lr.startColor.g, lr.startColor.b, alpha);
            lr.endColor = new Color(lr.endColor.r, lr.endColor.g, lr.endColor.b, alpha);
            yield return null;
        }
    }
}