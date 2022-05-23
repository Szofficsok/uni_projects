using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraController : MonoBehaviour
{
    //Ha unity editorba rákattintotok a kamerára, akkor ott leglaján lesz 1 script
    //Ott madj látszódnak a paraméterek és az, hogy transformnak bevan húzva a PlayerGraphics
    //Ezt simán meglehet tenni, úgy hogy rákattitnasz a kamerára, megfogod a PlayerGraphics -ot bal oldalt a menübe
    //és simán oda húzod a kamera scriptjébe.

    //A kamera beállításaival tudtok úgy mókázni, hogy elindítjátok a játékot és akkor bal oldalt a hierarchiába
    //rákattintotok a kamerára és akkor alul átírogathatjátok a paramétereket az offset-nek :) Ha találtok olyat ami tetszik,
    //akkor jobb klikk a scriptre -> copy component, ha leállítjátok a játékot akkor megint jobb klikk rá -> paste component values

    //Az hogy kinek a Transofrmját, akarjuk követni
    public Transform target;

    //A kamera mennyire legyen elmozdítva a Transfromhoz képest
    public Vector3 offset;

    //Mennyire legyünk távol a Transfromhoz képest
    public float currentZoom = 10f;

    void LateUpdate()
    {
        //A kamera saját pozícját állítjuk mágikus matematikával (GOOGLE :D)
        transform.position = target.position - offset * currentZoom;
        transform.LookAt(target.position + Vector3.up);
    }
}
