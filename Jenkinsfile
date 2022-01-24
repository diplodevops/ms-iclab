@Library('jenkinsfile-shared-library@testmacc') _
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
