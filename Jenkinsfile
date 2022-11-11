pipeline {
    agent any
	stages {
        stage('SonarQube analysis') {
            steps{
                withSonarQubeEnv('Sonar') {
					sh 'mvn clean package sonar:sonar'
                }
            }
        }
        stage("Quality Gate") {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Compile') {
            steps {
                echo 'Compile..'
                sh "./mvnw clean compile -e"
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
				sh "./mvnw clean test -e"
            }
        }
		stage('Building') {
            steps {
                echo 'Testing..'
				sh "./mvnw clean package -e"
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
		stage ('Clean'){
            steps
                {
                    cleanWs()
                }
        }

    }
}
