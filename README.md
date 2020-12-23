# Getting Started

## Windows

### Compile Code Test  Jar Code
* gradle clean build

### Run Jar
* Local:  gradle bootRun  


### Testing Application
* Abrir navegador: http://localhost:8081/rest/mscovid/test?msg=testing

### Testing Application
* curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'

# Jenkins Shared Libraries
- Jenkins Shared Libraries: https://www.jenkins.io/doc/book/pipeline/shared-libraries/

# Uso
- Agregar archivo **_Jenkinsfile_** en la raíz de la rama del proyecto a procesar (sólo como pivote al código del pipeline).
- Registrar Pipeline en **_Jenkins -> Administrar Jenkins -> Configuración Global -> Global Pipeline Libraries_** bajo el nombre **_pipeline_**
- Configurar _Multibranch Pipeline Job_ o _Pipeline Job_ en Jenkins con el repositorio del proyecto a procesar.
