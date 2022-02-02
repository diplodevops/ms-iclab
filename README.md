# Getting Started

##Gradle

###Build
* Compile, test, jar: gradle build

###Run
* Run: gradle bootRun (verificar modo background)

###Test
* Test: curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'


## Maven

### Compile Code
* ./mvnw clean compile -e

### Test Code
* ./mvnw clean test -e

### Jar Code
* ./mvnw clean package -e

### Run Jar
* Local:      ./mvnw spring-boot:run 
* Background: nohup bash mvnw spring-boot:run &

### Testing Application
* curl -X GET 'http://localhost:8080/rest/mscovid/test?msg=testing'


# Jenkins Shared Libraries
- Jenkins Shared Libraries: https://www.jenkins.io/doc/book/pipeline/shared-libraries/

# Uso
- Agregar archivo **_Jenkinsfile_** en la raíz de la rama del proyecto a procesar (sólo como pivote al código del pipeline).
- Registrar Pipeline en **_Jenkins -> Administrar Jenkins -> Configuración Global -> Global Pipeline Libraries_** bajo el nombre **_pipeline_**
- Configurar _Multibranch Pipeline Job_ o _Pipeline Job_ en Jenkins con el repositorio del proyecto a procesar.
