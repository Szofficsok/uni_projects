//=============================================================================================
// Mintaprogram: Zold haromszog. Ervenyes 2019. osztol.
//
// A beadott program csak ebben a fajlban lehet, a fajl 1 byte-os ASCII karaktereket tartalmazhat, BOM kihuzando.
// Tilos:
// - mast "beincludolni", illetve mas konyvtarat hasznalni
// - faljmuveleteket vegezni a printf-et kiveve
// - Mashonnan atvett programresszleteket forrasmegjeloles nelkul felhasznalni es
// - felesleges programsorokat a beadott programban hagyni!!!!!!! 
// - felesleges kommenteket a beadott programba irni a forrasmegjelolest kommentjeit kiveve
// ---------------------------------------------------------------------------------------------
// A feladatot ANSI C++ nyelvu forditoprogrammal ellenorizzuk, a Visual Studio-hoz kepesti elteresekrol
// es a leggyakoribb hibakrol (pl. ideiglenes objektumot nem lehet referencia tipusnak ertekul adni)
// a hazibeado portal ad egy osszefoglalot.
// ---------------------------------------------------------------------------------------------
// A feladatmegoldasokban csak olyan OpenGL fuggvenyek hasznalhatok, amelyek az oran a feladatkiadasig elhangzottak 
// A keretben nem szereplo GLUT fuggvenyek tiltottak.
//
// NYILATKOZAT
// ---------------------------------------------------------------------------------------------
// Nev    : Peter Noemi Zsuzsanna
// Neptun : YY1HDA
// ---------------------------------------------------------------------------------------------
// ezennel kijelentem, hogy a feladatot magam keszitettem, es ha barmilyen segitseget igenybe vettem vagy
// mas szellemi termeket felhasznaltam, akkor a forrast es az atvett reszt kommentekben egyertelmuen jeloltem.
// A forrasmegjeloles kotelme vonatkozik az eloadas foliakat es a targy oktatoi, illetve a
// grafhazi doktor tanacsait kiveve barmilyen csatornan (szoban, irasban, Interneten, stb.) erkezo minden egyeb
// informaciora (keplet, program, algoritmus, stb.). Kijelentem, hogy a forrasmegjelolessel atvett reszeket is ertem,
// azok helyessegere matematikai bizonyitast tudok adni. Tisztaban vagyok azzal, hogy az atvett reszek nem szamitanak
// a sajat kontribucioba, igy a feladat elfogadasarol a tobbi resz mennyisege es minosege alapjan szuletik dontes.
// Tudomasul veszem, hogy a forrasmegjeloles kotelmenek megsertese eseten a hazifeladatra adhato pontokat
// negativ elojellel szamoljak el es ezzel parhuzamosan eljaras is indul velem szemben.
//=============================================================================================
#include "framework.h"

const char* const vertexSource = R"(
	#version 330
	precision highp float;

	uniform vec3 wLookAt, wRight, wUp;
	layout(location = 0) in vec2 cCamWindowVertex;
	out vec3 p;

	void main() {
		gl_Position = vec4(cCamWindowVertex, 0, 1);
		p = wLookAt + wRight * cCamWindowVertex.x + wUp * cCamWindowVertex.y;
	}
)";

const char* const fragmentSource = R"(
	#version 330
	precision highp float;

	const vec3 La = vec3(0.5f, 0.6f, 0.6f);
	const vec3 Le = vec3(0.8f, 0.8f, 0.8f);
	const vec3 lightPosition = vec3(0.4f, 0.4f, 0.25f);
	const vec3 ka = vec3(0.8f, 0.2f, 0.6f);
	const float shininess = 500.0f;
	const int melyseg = 5;
	const float epsilon = 0.01f;
	const float PI = 3.1416f;

	struct Hit {
		float t;
		vec3 position, normal;
		int mat;
	};

	struct Ray{
		vec3 start, dir, weight;
	};

	const int objFaces = 12;
	uniform int top;
	uniform vec3 wEye, v[20];
	uniform int planes[objFaces * 3];
	uniform vec3 kd[2], ks[2], F0;

	void getObjPlane(int i, float scale, out vec3 p, out vec3 normal){
		vec3 p1 = v[planes[3 * i] - 1], p2 = v[planes[3 * i + 1] - 1], p3 = v[planes[3 * i + 2] - 1];
		normal = cross(p2 - p1, p3 - p1);
		if (dot(p1, normal) < 0) normal = - normal;
		p = p1 * scale + vec3(0, 0, 0.03f);
	}

	Hit intersectKonvexPolieder (Ray ray, Hit hit) {
		for(int i = 0; i < objFaces; i++) {
			vec3 p1 = vec3(0, 0, 0);
			vec3 normal  = vec3(0, 0, 0);
			getObjPlane(i, 1.0f, p1, normal);
			float ti = abs(dot(normal, ray.dir)) > epsilon ? dot(p1 - ray.start, normal) / dot(normal, ray.dir) : -1;
			if (ti <= epsilon || (ti > hit.t && hit.t > 0)) continue;
			vec3 pintersect = ray.start + ray.dir * ti;
			bool outside = false;
			for (int j = 0; j < objFaces; j++) {
				if (i == j) continue;
				vec3 p11 = vec3(0, 0, 0);
				vec3 n  = vec3(0, 0, 0);
				getObjPlane(j, 1.0f, p11, n);
				getObjPlane(j, 1.0f * 0.9422f, p11, n);
				if (dot(n, pintersect - p11)  > 0) {
					outside = true;
					break;
				}
			}
			hit.t = ti;
			hit.position = pintersect;
			hit.normal = normalize(normal);
			if (!outside) {
				hit.mat = 0;
			}
			else {
				hit.mat = 1;
			}
		}
		return hit;
	}

	float gombreVag (bool maxe, Ray ray){
		vec3 center = vec3(0, 0, 0);
		float radius = 0.3f;
		vec3 dist = ray.start - center;
		float a = dot(ray.dir, ray.dir);
		float b = dot(dist, ray.dir) * 2.0f;
		float c = dot(dist, dist) - radius * radius;
		float discr = b * b - 4.0f * a * c;
		if (discr < 0) {
			return -1.0f;
		}
		float sqrt_discr = sqrt(discr);
		float t1 = (-b + sqrt_discr) / 2.0f / a;
		float t2 = (-b - sqrt_discr) / 2.0f / a;
		if(maxe == true){
			return t1;
		}
		else if(maxe == false){
			return t2;
		}
	}

	Hit masodfokuMegoldo (float a, float b, float c, Ray ray, Hit hit, int mat, float a1, float b1, float c1) {
		float min = gombreVag(false, ray);
		float max = gombreVag(true, ray);
		vec3 n = vec3(0,0,0);
		if(min == -1 || max == -1){}
		else{
			float discr = b * b - 4.0f * a * c;
			if (discr >= 0) {
				float sqrt_discr = sqrt(discr);
				float t1 = (-b + sqrt_discr) / 2.0f / a;
				float t2 = (-b - sqrt_discr) / 2.0f / a;
				if (t1 > max || t1 < min) {
					t1 = -1.0f;
				}
				if (t2 > max || t2 < min) {
					t2 = -1.0f;
				}
				if (t1 > max || t1 < min && (t2 < max && t2 > min)) {
					hit.t = t2;
				}
				if (t2 > max || t2 < min && (t1 < max && t1 > min)) {
					hit.t = t1;
				}
				if ((t1 < max && t1 > min) && (t2 < max && t2 > min)) {
					if(t1 < t2){
						hit.t = t1;
					}
					if(t1 > t2){
						hit.t = t2;
					}
				}
				hit.mat = mat;
				hit.position = ray.start + ray.dir * hit.t;
				n = vec3(-2 * a1 * hit.position.x / c1 , -2 * b1 * hit.position.y / c1, 1);
				hit.normal = normalize(n);
			}
		}
		return hit;
	}

	Hit intersectBogyo (Ray ray, Hit hit, int  mat) {
		const float a1 = 1.7f;
		const float b1 = 1.7f;
		const float c1 = 1.9f;

		float a =  a1 * dot(ray.dir.x, ray.dir.x) + b1 * dot(ray.dir.y, ray.dir.y);
		float b = 2.0f * a1 * dot(ray.dir.x, ray.start.x) + 2.0f * b1 * dot(ray.dir.y, ray.start.y) - c1 * ray.dir.z;
		float c = a1 * dot(ray.start.x, ray.start.x) + b1 * dot(ray.start.y, ray.start.y) - c1 * ray.start.z;

		hit = masodfokuMegoldo(a, b, c, ray, hit, mat, a1, b1, c1);
		return hit;
	}

	Hit elsoIntersect (Ray ray) {
		Hit bestHit;
		bestHit.t = -1;
		bestHit = intersectBogyo(ray, bestHit, 2);
		bestHit = intersectKonvexPolieder(ray, bestHit);
		if (dot(ray.dir, bestHit.normal) > 0)
		{
			bestHit.normal = bestHit.normal * (-1);
		}
		return bestHit;
	}

	vec3 forgat(float theta, vec3 u, vec3 pont)
	{
		u = normalize(u);
		mat3 mx;
		//https://en.wikipedia.org/wiki/Rotation_matrix
		mx[0] = vec3(cos(theta) + u.x * u.x * (1 - cos(theta)), u.x * u.y * (1 - cos(theta)) - u.z * sin(theta), u.x * u.z * (1 - cos(theta)) + u.y * sin(theta));
		mx[1] = vec3(u.y * u.x * (1 - cos(theta)) + u.z * sin(theta), cos(theta) + u.y * u.y * (1 - cos(theta)), u.y * u.z * (1 - cos(theta)) - u.x * sin(theta));
		mx[2] = vec3(u.z * u.x * (1 - cos(theta)) - u.y * sin(theta), u.z * u.y * (1 - cos(theta)) + u.x * sin(theta), cos(theta) + u.z * u.z * (1 - cos(theta)));
		return mx * pont;
	}

	vec3 trace(Ray ray) {
		vec3 outRadiance = vec3(0, 0, 0);
		for (int d = 0; d < melyseg; d++) {
			Hit hit = elsoIntersect(ray);
			if (hit.t < 0) break;
			if (hit.mat == 1) {
				vec3 lightdir = normalize(lightPosition - hit.position);
				float cosTheta = dot(hit.normal, lightdir);
				if (cosTheta > 0) {
					vec3 LeIn = Le / dot(lightPosition - hit.position, lightPosition - hit.position);
					outRadiance += ray.weight * LeIn * kd[hit.mat] * cosTheta;
					vec3 halfway = normalize(-ray.dir + lightdir);
					float cosDelta = dot(hit.normal, halfway);
					if (cosDelta > 0) outRadiance += ray.weight * LeIn * ks[hit.mat] * pow(cosDelta, shininess);
				}
				ray.weight *= ka;
				break;
			}
			if (hit.mat == 2) {
				ray.weight *= F0 + (vec3(1, 1, 1) - F0) * pow(dot(-ray.dir, hit.normal), 5);
			}
			ray.start = hit.position + hit.normal * epsilon;
			ray.dir = reflect(ray.dir, hit.normal);
			if (hit.mat == 0) {
				ray.start = forgat(72 * (PI / 180), hit.normal,  ray.start);
				ray.dir = forgat(72 * (PI / 180), hit.normal, ray.dir);
			}
		}
		outRadiance += ray.weight * La;
		return outRadiance;
	}

	in vec3 p;
	out vec4 fragmentColor;

	void main() {
		Ray ray;
		ray.start = wEye;
		ray.dir = normalize(p - wEye);
		ray.weight = vec3(1, 1, 1);
		fragmentColor = vec4(trace(ray), 1);
	}
)";

struct Camera {
	vec3 eye, lookat, right, pvup, rvup;
	float fov = 45 * (float)M_PI / 180;
public:
	Camera() : eye(0, 1, 1), pvup(0, 0, 1), lookat(0, 0, 0) {
		set();
	}
	void set() {
		vec3 w = eye - lookat;
		float f = length(w);
		right = normalize(cross(pvup, w)) * f * tanf(fov / 2);
		rvup = normalize(cross(w, right)) * f * tanf(fov / 2);
	}
	void Animate(float t) {
		float r = sqrtf(eye.x * eye.x + eye.y * eye.y);
		eye = vec3(r * cos(t) + lookat.x, r * sin(t) + lookat.y, eye.z);
		set();
	}
};

GPUProgram shader;
Camera camera;
bool animate = true;

float Fresnel(float n, float k) {
	return ((n - 1) * (n - 1) + k * k) / ((n + 1) * (n + 1) + k * k);
}

void onInitialization() {
	glViewport(0, 0, windowWidth, windowHeight);

	unsigned int vbo, vao;
	glGenVertexArrays(1, &vao);
	glBindVertexArray(vao);
	glGenBuffers(1, &vbo);
	glBindBuffer(GL_ARRAY_BUFFER, vbo);
	float vertexCoords[] = { -1, -1, 1, -1, 1, 1, -1, 1 };
	glBufferData(GL_ARRAY_BUFFER, sizeof(vertexCoords), vertexCoords, GL_STATIC_DRAW);
	glEnableVertexAttribArray(0);
	glVertexAttribPointer(0, 2, GL_FLOAT, GL_FALSE, 0, NULL);

	shader.create(vertexSource, fragmentSource, "fragmentColor");
	const float g = 0.618f, G = 1.618f;
	std::vector<vec3> v = {
		vec3(0, g, G), vec3(0, -g, G), vec3(0, -g, -G), vec3(0, g, -G), vec3(G, 0, g), vec3(-G, 0, g), vec3(-G, 0, -g), vec3(G, 0, -g), vec3(g, G, 0), vec3(-g, G, 0), vec3(-g, -G, 0), vec3(g, -G, 0),
		vec3(1, 1 ,1), vec3(-1, 1, 1), vec3(-1, -1, 1), vec3(1, -1, 1), vec3(1, -1, -1), vec3(1, 1, -1), vec3(-1, 1, -1), vec3(-1, -1, -1)
	};
	for (int i = 0; i < v.size(); i++)
	{
		shader.setUniform(v[i], "v[" + std::to_string(i) + "]");
	}
	std::vector<int> planes = {
		1, 2, 16,    1, 13, 9,    1, 14, 6,    2, 15, 11,    3, 4, 18,    3, 17, 12,    3, 20, 7,    19, 10, 9,    16, 12, 17,    5, 8, 18,    14, 10, 19,    6, 7, 20
	};
	for (int i = 0; i < planes.size(); i++)
	{
		shader.setUniform(planes[i], "planes[" + std::to_string(i) + "]");
	}
	shader.setUniform(vec3(0.1f, 0.2f, 0.3f), "kd[0]");
	shader.setUniform(vec3(1.5, 0.6f, 0.4f), "kd[1]");
	shader.setUniform(vec3(5, 5, 5), "ks[0]");
	shader.setUniform(vec3(1, 1, 1), "ks[1]");
	shader.setUniform(vec3(Fresnel(0.17, 3.1), Fresnel(0.35, 2.7), Fresnel(1.5, 1.9)), "F0");
}

void onDisplay() {
	glClearColor(0, 0, 0, 0);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	shader.setUniform(camera.eye, "wEye");
	shader.setUniform(camera.lookat, "wLookAt");
	shader.setUniform(camera.right, "wRight");
	shader.setUniform(camera.rvup, "wUp");
	glDrawArrays(GL_TRIANGLE_FAN, 0, 4);
	glutSwapBuffers();
}

void onKeyboard(unsigned char key, int pX, int pY) {
	if (key == 'a') animate = !animate;
}

void onKeyboardUp(unsigned char key, int pX, int pY) { }
void onMouseMotion(int pX, int pY) { }
void onMouse(int button, int state, int pX, int pY) { }

void onIdle() {
	if (animate)
	{
		camera.Animate(glutGet(GLUT_ELAPSED_TIME) / 1000.0f);
	}
	glutPostRedisplay();
}