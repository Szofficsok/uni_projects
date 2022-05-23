using UnityEngine;

public class PlayerMovement : MonoBehaviour
{

    //Elõre megírt könyvtár, ha rákattintasz a ThirdPersonPlayer object-re azon megtalálod a CharacterController osztályt :)
    //Lehet rajta nagyon sok minden állítani: mennyirel egyen magas az ütközõ dobot, széles Próbálgasd :D
    public CharacterController controller;

    //Kintrõl megadjuk, hogy milyen gyorsan szerenténk hogy a játékos fusson. Itt lehet
    //megadni default értéket de ha az editorba ezt átírjuk, akkor az lesz az alapétrelmezett,
    public float speed = 10f;

    public LayerMask layerToShoot;

    void Start()
    {
        
    }

    void Update()
    {
        //Lekérjük, hogy nyomjuk-e a WASD gombok valamiylen kombinációját
        float horizontal = Input.GetAxisRaw("Horizontal");
        float vertical = Input.GetAxisRaw("Vertical");

        //Ezek együttesébõl számítunk egy vectort, amit normalizálnuk (Azt kerüljük el vele ha átlósan mennénk ne leyünk gyorsabbak :D)
        Vector3 direction = new Vector3(horizontal, 0f, vertical).normalized;

        //Megnézzük, hogy a vektortban van e kraft :D Ha van akkor a játékost elmózdítjuk abba az irányba amibe a vektor mutat :)
        //A Time*deltaTime -ot arra használjuk röviden, hogyha valakinek a játék 60 fps-en fut akkor ne fusso ngyorsabban mint akinek csak 30
        //Mert ugye az Update 60 fps-nél 60x 30-nál 30x hívódik meg, ezzel tudjuk korrigálni (GOOGLE -n érdemes még ránézni :D)
        if (direction.magnitude >= 0.1f)
            controller.Move(direction * speed * Time.deltaTime);


        //A RayCast olyan mintha egy láthatatlan vonalat lõnénk valahova, itt most bármit átlövünk vele de egyébként
        //ehet szûrni, hogy milyen layereket akarunk vele érinteni stb. Ezt most framenként lõ 1-et és a játékost abba az x, z koordináták
        //felé fordtítja ahol átlõttük a földet (GOOGLE itt se tartson vissza bennünket :D)
        RaycastHit hit;
        Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);

        if (Physics.Raycast(ray, out hit, 100, layerToShoot))
        {
            transform.LookAt(new Vector3(hit.point.x, transform.position.y, hit.point.z));
        }
    }
}
