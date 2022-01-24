@Library('jenkinsfile-shared-library@feature/lab3-sl') _

pipeline {
    agent any
    
	stages{
		stage('Integracion'){
			steps{
				script{
                    integracioncontinua.citest1()
                }
			}
		}
    }
}
