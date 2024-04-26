pipeline {
    agent any
    environment {
        registry = "hakkou7/kaddem"
        registryCredential = 'dockerhub'
        dockerImage = ''
    }
    stages {
        stage ('maven sonar') {
            steps{
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

        stage('Building our image') {
            steps {
                script {
                    sh "echo $( echo $GITL_COMMIT | cut -c 1-7 )"
                    dockerImage = docker.build(registry + "$( echo $GITL_COMMIT | cut -c 1-7 )")
                }
            }
        }
        stage('push our image') {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }

        stage("PUBLISH TO NEXUS") {
            steps { sh 'mvn deploy'
            }
             
        }
        stage('Cleaning up') {
            steps {
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }

        stage('deploy our image') {
            steps {
                script {
                    sh "git clone  https://github.com/Abdelhak-Amami/kaddem.git"
                    sh " echo  $previous_tag "
                }
            }
        }
    }
}
