using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraController : MonoBehaviour
{
    //Ha unity editorba r�kattintotok a kamer�ra, akkor ott leglaj�n lesz 1 script
    //Ott madj l�tsz�dnak a param�terek �s az, hogy transformnak bevan h�zva a PlayerGraphics
    //Ezt sim�n meglehet tenni, �gy hogy r�kattitnasz a kamer�ra, megfogod a PlayerGraphics -ot bal oldalt a men�be
    //�s sim�n oda h�zod a kamera scriptj�be.

    //A kamera be�ll�t�saival tudtok �gy m�k�zni, hogy elind�tj�tok a j�t�kot �s akkor bal oldalt a hierarchi�ba
    //r�kattintotok a kamer�ra �s akkor alul �t�rogathatj�tok a param�tereket az offset-nek :) Ha tal�ltok olyat ami tetszik,
    //akkor jobb klikk a scriptre -> copy component, ha le�ll�tj�tok a j�t�kot akkor megint jobb klikk r� -> paste component values

    //Az hogy kinek a Transofrmj�t, akarjuk k�vetni
    public Transform target;

    //A kamera mennyire legyen elmozd�tva a Transfromhoz k�pest
    public Vector3 offset;

    //Mennyire legy�nk t�vol a Transfromhoz k�pest
    public float currentZoom = 10f;

    void LateUpdate()
    {
        //A kamera saj�t poz�cj�t �ll�tjuk m�gikus matematik�val (GOOGLE :D)
        transform.position = target.position - offset * currentZoom;
        transform.LookAt(target.position + Vector3.up);
    }
}
