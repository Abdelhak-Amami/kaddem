pipeline {
    agent any
    environment {
        registry = "hakkou7/kaddem"
        registryCredential = 'dockerhub'
        dockerImage = ''
        
        new_tag="\$(echo \$GIT_COMMIT | cut -c 1-7)"
        previous_tag="\$(echo \$GIT_PREVIOUS_COMMIT | cut -c 1-7)"
    }
    stages {
        stage ('maven sonar') {
            steps {
                sh 'mvn clean'
                sh 'mvn compile'
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=admin1'
            }
        }
        stage ('maven build') {
            steps {
                sh 'mvn package'
            }
        }

        stage("PUBLISH TO NEXUS") {
            steps {
                sh 'mvn deploy'
            }
        }
        stage('Building docker  image') {
            steps {
                script {

                    sh " docker build ./ -t hakkou7/kaddem:abdelhak "
                   
                   
                }
            }
        }

        stage('push docker  image'){
            steps{
                script {
                     docker.withRegistry('', registryCredential) {
                        sh " docker push hakkou7/kaddem:abdelhak "
                    }
                }
            }
        }
        stage('cleaning image'){
            steps{
                script {
                     
                        sh " docker rmi hakkou7/kaddem:$new_tag "
                    
                }
            }
        }
        stage('deploy our image') {
            steps {
                script {

                    
                    sh " kubectl delete pods pods -l app.kubernetes.io/name=spring-deploy  "
                }
            }
        }
    }
}
