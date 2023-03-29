<img src='https://user-images.githubusercontent.com/89888520/224816395-58e7bfe7-2950-4234-acaf-e3c13d4374cd.png' width='125'>

# Fit Tracking
***Tracking u for a better u***

🌸 App fitness que ayuda a llevar un tracking de tu IMC, peso & kcals diarias 🌸

By *Mafer Vicas*


## Table of Content:
1. [Justificacion](#justificacion)
2. [Objetivo hacia el público](#objetivo)
3. [Lista de requerimientos](#requerimientos)
4. [Diseño en Figma](https://www.figma.com/file/qo4lYXQXlvcSNtgkwIwA3s/Untitled?node-id=0%3A1&t=gPpj2xznpNSbIGyk-1)
5. [Diagrama de Gantt](#diagrama)
6. [Código](#codigo)

   6. Dependencias/Librerias
   6. Clases
   6. Base de datos
   6. Capturas de Pantalla
7. [Video final](#video)

---

### Justificacion: 
6 de 10 personas adultas son obesas y 4 de 10 mujeres padecen la enfermedad (Según el Banco Mundial sobre obesidad en América Latina (2020))
**Y aunque existen miles de apps** para bajar de peso o cuidar de tu salud, la mayoría son de PAGA, ofrecen una versión chafa de prueba o gratuita con miles de limitaciones y NO incluyen todo lo que deberías de saber. Es por eso que se creó 

🌸 _**FitTracking**_ 🌸

---

### Objetivo
Que más allá de conocer y después contar tu ingesta de calorías diaria, la usuaria pueda:
- Sentirse motivada al ver progreso en estadística
- Conocer su ingesta de agua y cumplirla
- Encontrar alguna pasión o gusto dentro de las recomendaciones
- Que pueda crear conciencia sobre su IMC. se preocupe por él 
- Cree un hábito diario de llegar y no rebasar las calorías recomendadas
- Bajar de peso
- Sentirse bien con ella misma

---

### Requerimientos
| Nombre  | Prioridad | Cumplida |
| ------------- |:-------------:| :-------------:|
| Cronómetro      | Alta     | ✅
| Calculadora IMC      | Alta     | ✅
| Tracker Calorías      | Alta    |✅
| Lista de links | Media | ✅
| Seguridad | Alta | ✅
| Estadística | Alta | ✅
| S.O. 11 & 12 | Alta | ✅

Más información [consulta aquí](https://www.canva.com/design/DAE4ptBofTY/8uzENDqkiwpn_UktNvpMqw/view?utm_content=DAE4ptBofTY&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)

---

### Diagrama
Dividido en 3 fases
![DiagramaGantt](https://user-images.githubusercontent.com/89888520/224815279-03cfabdc-2311-452e-bd3d-53985014ec50.jpeg)

---

### Codigo
Dependencias del proyecto:

| Nombre  | Para qué | 
| ------------- |:-------------:| 
| com.google.android.material:material:1.3.0| Elementos XML utilizados     | 
| com.github.PhilJay:MPAndroidChart:v3.1.0-alpha| Gráfica   | 
| com.google.android.gms:play-services-ads:21.0.0 | Anuncios    |

Main Clases:
- **Cronómetro**: Utiliza la clase de 'Chronometer' para poder mostrar los datos en tiempo real con sus 3 métodos (iniciar, pausar, reset)
- **Dashboard**: Cuenta con ImageButtons en la parte de arriba que al darle click muestran un toast con información específica, estos están pre-llenados gracias a la información proporcionada por el usuario que se guarda dentro del xml de SharedPreferences, por otro lado redirecciona a los diferentes widgets gracias a un intent de StartActivity 

| Botón  | Función | 
| ------------- |:-------------:| 
| IMC| Según los datos introducidos y el cálculo hecho por la app, irá a compararlos con los rangos de IMC de la OMS, y arrojará en forma de toast tu en qué nivel se encuentra |
| Agua | Según los datos introducidos y el cálculo hecho por la app

- kcalTracker: Pantalla con conexión a la base de datos, específicamente a la tabla *kcalData*. Aquí podemos:


| Botón  | Función | 
| ------------- |:-------------:| 
| Agregar | De las kcalsRestantes (Que vienen del sharedPreferences, les restará las kcals que ingrese el usuario, para que el resultado de esta resta actualize el valor de kcalsRestantes en SharedPreferences | 
| Fin del día | Primero validará si se cumplió o no la meta de comer las mismas o menos calorías de las recomendadas, pasado de esto, insertará en la base de datos la fecha (como llave primaria para que únicamente se tenga 1 registro por día), las kcals objetivo, las restantes, y un bool de si se cumplió o no el objetivo. Finalmente si se cumplió el objetivo se redirecciona a Congrats View, de lo contrario el usuario verá el dashboard  | 
- NewInformation
- Recomendaciones
- Registration
- SplashScreen
- Statistics
- Congrats View

Base de datos:

Para este proyecto se utilizó SQLite y shared preferences propias de la funcionalidad nativa del celular para guardar información.
En cuanto a la escructura de la base de datos en SQLite es la siguiente:

<img width="507" alt="BD estructura" src="https://user-images.githubusercontent.com/89888520/228380694-8cba556e-0105-4cf1-8fa4-97246c1d578e.png">

Por otro lado, en shared preferences se guarda todo en un archivo xml, el cual contiene los siguientes datos:
- kcals
- kcalsRestantes
- name
- water
- imc
- freqEjercicio

Pantallas: 

<img src='https://user-images.githubusercontent.com/89888520/228430185-7a3e0f0f-e333-42f3-be10-ba1880a6bbb7.png' width='625'>
<img src='https://user-images.githubusercontent.com/89888520/228430189-9456e874-004c-4241-ae2f-a46f8e37e48e.png' width='625'>

---

### Video
