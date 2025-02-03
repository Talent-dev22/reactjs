pipeline {
      agent any
      stages {
         stage ('Build') {
          steps {
             sh '''cd $WORKSPACE
                   docker build -t reactjsapp:v${BUILD_NUMBER} .'''
             }
           }
           stage('docker upload'){
            steps{
                sh """
                  docker tag reactjsapp:v${BUILD_NUMBER} malleshdevops/devops21:reactjsapp-v${BUILD_NUMBER}
                  docker push malleshdevops/devops21:reactjsapp-v${BUILD_NUMBER}
                """
            }
           }
           stage('push ECR'){
            steps {
                sh '''
                aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin 891612549251.dkr.ecr.us-west-2.amazonaws.com

                docker tag reactjsapp:v${BUILD_NUMBER} 891612549251.dkr.ecr.us-west-2.amazonaws.com/talent/reactjsapp:v${BUILD_NUMBER}
                docker push 891612549251.dkr.ecr.us-west-2.amazonaws.com/talent/reactjsapp:v${BUILD_NUMBER}
       '''
            }
          }
          }
        }

