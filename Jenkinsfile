pipeline{
    agent any

    stages {
        
        stage('Clean') {
            steps {
                cleanWs()
            }
        }

        stage('Compile Code') {
            steps {
                ./mvnw clean compile -e
            }
        }

        stage('Test Code') 
        {
            steps {
                ./mvnw clean test -e
            }
        }
        
        stage('Jar Code') 
        {
            steps {
                ./mvnw clean package -e
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

