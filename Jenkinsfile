pipeline {
    agent any

    stages {
        stage('Compile Code') {
            steps {
                sh "./mvnw clean compile -e -DskipTest"
            }
        }
        stage('Test Code') {
            steps {
                sh "./mvnw clean test -e"
            }
        }
        stage('Jar Code') {
            steps {
                sh "./mvnw clean package -e -DskipTest"
            }
        }
        stage('Run Jar') {
            steps {
                //sh "./mvnw spring-boot:run"
                //sh "nohup bash ./mvnw spring-boot:run &"
                sh "./mvnw spring-boot:run &"
            }
        }
        stage('Testing Application') {
            steps { 
                 sh "sleep 10; curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'"
            }
        }
        stage('Good Bye') {
            steps {
                echo 'Profe un 7 plssssss'
            }
        }
    }
}
