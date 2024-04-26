pipeline {
    agent any
    environment {
        registry = "hakkou7/kaddem"
        registryCredential = 'dockerhub'
        dockerImage = ''
        new_tag="\$(echo \$GIT_COMMIT | cut -c 1-7)"
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
        stage('Building our image') {
            steps {
                script {
                    sh " echo a=$new_tag"
                    dockerImage = docker.build(registry + "$a")
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
            steps {
                sh 'mvn deploy'
            }
        }
        stage('Cleaning up') {
            steps {
                sh "docker rmi $registry:$new_tag"
            }
        }
        stage('deploy our image') {
            steps {
                script {
                    sh "git clone https://github.com/Abdelhak-Amami/kaddem.git"
                    sh "echo $new_tag"
                }
            }
        }
    }
}
