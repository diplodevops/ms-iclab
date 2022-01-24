@Library('jenkinsfile-shared-library@testmacc') _
import cl.dipdevops.usach.grupo5.Devops
def devops = new Devops()

pipeline {
    agent any
    
	stages{
		stage('Integracion'){
			steps{
				script{
                    devops.citest1()
                }
			}
		}
    }
}
