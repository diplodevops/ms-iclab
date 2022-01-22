pipeline {
    agent any
    environment{
        NEXUS_USER = credentials('usernexusadmin')
        NEXUS_PASSWORD = credentials('passnexusadmin')
        VERSION = '0.0.14'
        FINAL_VERSION = '1.0.0'
    }
    stages {
        stage("-1: logs"){
            steps {
                sh "echo 'branchname: '" + BRANCH_NAME
                sh 'printenv'
            }
        }
        stage("01: Validate Not Master Executions"){
            //validaciones iniciales
            // expresion regular solicitada release-v\d+-\d+-\d+
            // tambien validar que no ejecute en master

            when {
                anyOf {
                          expression { BRANCH_NAME == 'master' }
                          expression { BRANCH_NAME == 'main' }
                }
              
            }
            steps {
                sh "echo  'Rama invalida'"
                script{
                    error("Invalid Branch Name" + BRANCH_NAME )
                }   
            }
        }
        stage("02: Validate Branch Name"){
            //validaciones iniciales
            // expresion regular solicitada release-v\d+-\d+-\d+

            //Validar el tipo de rama a ejecutar (feature, develop o release)
            
            when {
                allOf {
                    not { expression { BRANCH_NAME ==~ /feature\/.*/ } }
                    not { expression { BRANCH_NAME ==~ /develop\/.*/ } }
                    not { expression { BRANCH_NAME ==~ /release\/.*/ } }
                }
                
            }
            steps {
                sh "echo  'Nombre Rama Invalido'"
                script{
                    error("Invalid Branch Name" + BRANCH_NAME)
                }   
            }
        }

        stage("03: Validate Maven Files"){
            when {
                anyOf {
                          not { expression { fileExists ('pom.xml') }}
                          not { expression { fileExists ('mvnw') }}
                }
                
            }
            steps {
                sh "echo  'Faltan archivos Maven en su estructura'"
                script{
                    error("file dont exist :( ")
                }   
            }
        }
        
        stage("1: Compile"){
            //- Compilar el código con comando maven
            steps {
                script {
                sh "echo 'Compile Code!'"
                // Run Maven on a Unix agent.
                //sh "mvn clean compile -e"
                }
            }
        }
        stage("2: Unit Test"){
        //- Testear el código con comando maven
            steps {
                script {
                sh "echo 'Test Code!'"
                // Run Maven on a Unix agent.
                //sh "mvn clean test -e"
                }
            }
        }
        stage("3: Build jar"){
        //- Generar artefacto del código compilado.
            steps {
                script {
                sh "echo 'Build .Jar!'"
                // Run Maven on a Unix agent.
                sh "mvn clean package -e"
                }
            }
        }
        stage("4: SonarQube"){
        //- Generar análisis con sonar para cada ejecución
        //- Cada ejecución debe tener el siguiente formato de nombre: QUE ES EL NOMBRE DE EJECUCIÓN ??
            //- {nombreRepo}-{rama}-{numeroEjecucion} ejemplo:
            //- ms-iclab-feature-estadomundial(Si está usando el CRUD ms-iclab-feature-[nombre de su crud])
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    sh "echo 'SonarQube'"
                    sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=githubfull'
                }
            }
            post {
                //- Subir el artefacto creado al repositorio privado de Nexus.
                //- Ejecutar este paso solo si los pasos anteriores se ejecutan de manera correcta.
                success {
                    nexusPublisher nexusInstanceId: 'nexus', 
                    nexusRepositoryId: 'devops-usach-nexus', 
                    packages: [[$class: 'MavenPackage', 
                        mavenAssetList: [[classifier: '', 
                                        extension: '',
                                        filePath: 'build/DevOpsUsach2020-0.0.1.jar']],
                        mavenCoordinate: [artifactId: 'DevOpsUsach2020', 
                                        groupId: 'com.devopsusach2020', 
                                        packaging: 'jar', 
                                        version: VERSION]]]
                }
            }
        }
        stage("6: gitCreateRelease"){
        //- Crear rama release cuando todos los stages anteriores estén correctamente ejecutados.
        //- Este stage sólo debe estar disponible para la rama develop.
            steps {
                script {
                sh "echo 'gitCreateRelease'"
                }
            }
        }
        ///////Despliegue continuo
        stage("7: gitDiff"){
            //- Mostrar por pantalla las diferencias entre la rama release en curso y la rama
            //master.(Opcional)
            steps {
                script {
                sh "echo 'gitDiff'"
                }
            }
        }
        stage("8: nexusDownload"){
            //- Descargar el artefacto creado al workspace de la ejecución del pipeline.
            steps {                   
                sh 'sleep 5 '
                sh 'curl -X GET -u $NEXUS_USER:$NEXUS_PASSWORD http://nexus:8081/repository/devops-usach-nexus/com/devopsusach2020/DevOpsUsach2020/$VERSION/DevOpsUsach2020-$VERSION.jar -O'
            }
        }
        stage("9: run"){
            //- Ejecutar artefacto descargado.
            steps {
                sh 'nohup java -jar DevOpsUsach2020-$VERSION.jar & >/dev/null'
            }
        }
        stage("9: test"){
            //- Realizar llamado a microservicio expuesto en local para cada uno de sus
            //métodos y mostrar los resultados.
            steps {
               sh "sleep 30 && curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'"
            }
        }
        stage("9: gitMergeMaster"){
            //- Realizar merge directo hacia la rama master.
            //- Ejecutar sólo si todo lo demás resulta de forma exitosa.

            steps {
                script {
                sh "echo 'gitMergeMaster'"
                }
            }
        }
        stage("10: gitMergeDevelop"){
            //- Realizar merge directo hacia rama develop.
            //- Ejecutar sólo si todo lo demás resulta de forma exitosa
            steps {
                script {
                sh "echo 'gitMergeDevelop'"
                }
            }
        }
        stage("11: gitTagMaster"){
            //- Crear tag de rama release en rama master.
            //- Ejecutar sólo si todo lo demás resulta de forma exitosa.
            steps {
                script {
                sh "echo 'gitTagMaster'"
                }
            }
        }
    }
    post {
        always {
            sh "echo 'Siempre se ejecuta'"
        }
        success {
            sh "echo 'Solo en caso de exito'"
        }
        failure {
            sh "echo 'Solo en caso de error'"
        }
    }
}