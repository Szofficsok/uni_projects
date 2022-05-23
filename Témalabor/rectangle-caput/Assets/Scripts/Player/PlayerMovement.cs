using UnityEngine;

public class PlayerMovement : MonoBehaviour
{

    //El�re meg�rt k�nyvt�r, ha r�kattintasz a ThirdPersonPlayer object-re azon megtal�lod a CharacterController oszt�lyt :)
    //Lehet rajta nagyon sok minden �ll�tani: mennyirel egyen magas az �tk�z� dobot, sz�les Pr�b�lgasd :D
    public CharacterController controller;

    //Kintr�l megadjuk, hogy milyen gyorsan szerent�nk hogy a j�t�kos fusson. Itt lehet
    //megadni default �rt�ket de ha az editorba ezt �t�rjuk, akkor az lesz az alap�trelmezett,
    public float speed = 10f;

    public LayerMask layerToShoot;

    void Start()
    {
        
    }

    void Update()
    {
        //Lek�rj�k, hogy nyomjuk-e a WASD gombok valamiylen kombin�ci�j�t
        float horizontal = Input.GetAxisRaw("Horizontal");
        float vertical = Input.GetAxisRaw("Vertical");

        //Ezek egy�ttes�b�l sz�m�tunk egy vectort, amit normaliz�lnuk (Azt ker�lj�k el vele ha �tl�san menn�nk ne ley�nk gyorsabbak :D)
        Vector3 direction = new Vector3(horizontal, 0f, vertical).normalized;

        //Megn�zz�k, hogy a vektortban van e kraft :D Ha van akkor a j�t�kost elm�zd�tjuk abba az ir�nyba amibe a vektor mutat :)
        //A Time*deltaTime -ot arra haszn�ljuk r�viden, hogyha valakinek a j�t�k 60 fps-en fut akkor ne fusso ngyorsabban mint akinek csak 30
        //Mert ugye az Update 60 fps-n�l 60x 30-n�l 30x h�v�dik meg, ezzel tudjuk korrig�lni (GOOGLE -n �rdemes m�g r�n�zni :D)
        if (direction.magnitude >= 0.1f)
            controller.Move(direction * speed * Time.deltaTime);


        //A RayCast olyan mintha egy l�thatatlan vonalat l�n�nk valahova, itt most b�rmit �tl�v�nk vele de egy�bk�nt
        //ehet sz�rni, hogy milyen layereket akarunk vele �rinteni stb. Ezt most framenk�nt l� 1-et �s a j�t�kost abba az x, z koordin�t�k
        //fel� fordt�tja ahol �tl�tt�k a f�ldet (GOOGLE itt se tartson vissza benn�nket :D)
        RaycastHit hit;
        Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);

        if (Physics.Raycast(ray, out hit, 100, layerToShoot))
        {
            transform.LookAt(new Vector3(hit.point.x, transform.position.y, hit.point.z));
        }
    }
}
