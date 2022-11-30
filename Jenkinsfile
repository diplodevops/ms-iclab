def GIT_COMMIT_USERNAME

pipeline {
    agent any
    tools 
    {
        maven 'maven_env'
    }
    parameters
    {
        gitParameter name: 'git_tag', defaultValue: '0.0.1', type: 'PT_TAG' , sortMode:'DESCENDING_SMART'   
        booleanParam(name: 'Release_Version' , defaultValue: false , description: 'Enviar hacia el Repositorio Nexus y realiza versionamiento ')
        
        
    }
    stages {
        stage('Init Env Variables')
        {
            steps
            {
                script
                {           
                GIT_COMMIT_USERNAME = sh (script: 'git show -s --pretty=%an', returnStdout: true ).trim()                
                sh "printenv" 
                echo "${GIT_COMMIT_USERNAME}"
                echo "${params.git_tag}"
                }
            }
        } 
        stage(' Compile ') {
            steps {
                echo 'TODO: build'
                sh "./mvnw clean compile -e"
            }
        }
        stage('Test') {
            steps {
                echo 'TODO: test'
                sh "./mvnw clean test -e"
            }
        }
        stage('Jar') {
            steps {
                echo 'TODO: package'
                sh "./mvnw clean package -e"
            }
        }
        stage('Sonarque')
        {
            steps{
                withSonarQubeEnv(credentialsId: 'sonar_token', installationName: 'sonarqube_env')
                {
                sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
                }
            }
        }
        
        stage ('Send to Nexus new Version')
        {
            when
            {
                expression
                {
                    params.Release_Version

                }
            }
            steps 
            {
                echo "TODO: Maven Install to version ${params.git_tag}"
                sh "./mvnw versions:set -DnewVersion=${params.git_tag}"
                sh "./mvnw clean package -e"
                sh "./mvnw clean install" 
                nexusPublisher nexusInstanceId: 'nexus_docker', nexusRepositoryId: 'devops-usach-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: "${WORKSPACE}/build/DevOpsUsach2020-${params.git_tag}.jar"]], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: "${params.git_tag}"]]]
            }
        }
        stage ('Download Nexus Jar-Run-Test')
        {
            when
            {
                expression
                {
                    params.Release_Version
                }
            }
	        steps
		    {
                sh "mkdir -p ${WORKSPACE}/tmp"
   		        sh "curl http://nexus:8081/repository/devops-usach-nexus/com/devopsusach2020/DevOpsUsach2020/${params.git_tag}/DevOpsUsach2020-${params.git_tag}.jar --output ${WORKSPACE}/tmp/DevOpsUsach2020-${params.git_tag}.jar"
                sh "java -jar ${WORKSPACE}/tmp/DevOpsUsach2020-${params.git_tag}.jar &"
                sh "sleep 30"
		        sh "curl -X GET  http://localhost:8081/rest/mscovid/test?msg=testing"
                echo "Stopping App"
            }
	      
       }
    }
    post
        {
            success
            {
                slackSend channel: 'C04CJ6KN37F', color: '#17FF00', message: "Build Success: ${GIT_COMMIT_USERNAME}[Grupo7][Pipeline IC/CD][Rama: ${env.JOB_NAME}][Version: ${params.git_tag}][Stage: build][Resultado:Éxito/Success]"
            }
            failure
            {
                slackSend channel: 'C04CJ6KN37F', color: '#FF0000', message: "Build Fallido: ${GIT_COMMIT_USERNAME}[Grupo7][Pipeline IC/CD][Rama: ${env.JOB_NAME}][Version: ${params.git_tag}][Stage: build][Resultado:Ejecucion Fallida](<${env.BUILD_URL}|Open>)"
            }
        }
}