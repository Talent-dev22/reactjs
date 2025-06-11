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
                  docker tag reactjsapp:v${BUILD_NUMBER} malleshdevops/dev22:reactjsapp-v${BUILD_NUMBER}
                  docker push malleshdevops/dev22:reactjsapp-v${BUILD_NUMBER}
                """
            }
           }
           stage('push ECR'){
            steps {
                sh '''
                aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin 759449706669.dkr.ecr.us-west-2.amazonaws.com

                docker tag reactjsapp:v${BUILD_NUMBER} 759449706669.dkr.ecr.us-west-2.amazonaws.com/reactjs/react22:v${BUILD_NUMBER}
                docker push 759449706669.dkr.ecr.us-west-2.amazonaws.com/reactjs/react22:v${BUILD_NUMBER}
       '''
            }
          }
          }
        }

