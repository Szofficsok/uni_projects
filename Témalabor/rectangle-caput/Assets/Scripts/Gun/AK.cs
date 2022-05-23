using System.Collections;
using UnityEngine;

public class AK : MonoBehaviour
{
    public float damage = 20f;
    public float range = 100f;
    //public float force = 30f;
    public float firerate = 15;

    public Camera fpsCam;

    private float nextTimeToFire = 0f;

    public GameObject bullet;
    public Transform muzzle;
    public float fadeDuration = 0.1f;
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
        RaycastHit hit;

        if (Physics.Raycast(fpsCam.transform.position, fpsCam.transform.forward, out hit, range))
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
            CreateBullet(fpsCam.transform.position + fpsCam.transform.forward * range);
        }
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