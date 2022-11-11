pipeline {
    agent any

    stages {
        stage('Build') 
        {
            steps {
                echo 'TODO: build'
                sh "./mvnw clean compile -e"
            }
        }
        stage('Test') 
        {
            steps {
                echo 'TODO: test'
                sh "./mvnw clean test -e"
            }
        }
        stage('Package') 
        {
            steps {
                echo 'TODO: package'
                sh "./mvnw clean package -e"
            }
        }
        stage('Run') 
        {
            steps {
                echo 'TODO: running'
                sh "nohup bash mvnw spring-boot:run &"
                sleep 25
            }
        }
        stage('Testing')
        {
             steps { 
                 echo 'TODO: Testing 1 llamada simple'
                 sh "curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=TestingSimple1'"
            }
        }
    }
}
