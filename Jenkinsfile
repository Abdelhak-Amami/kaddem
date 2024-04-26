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

        stage("PUBLISH TO NEXUS") {
            steps {
                sh 'mvn deploy'
            }
        }
        stage('Building and push our image') {
            steps {
                script {
                    sh " echo $new_tag"
                    
                    sh " docker build ./ -t hakkou7/kaddem:$new_tag "
                    sh "docker push hakkou7/kaddem:$new_tag"
                }
            }
        }

        stage('deploy our image') {
            steps {
                script {
                    sh "git clone https://github.com/Abdelhak-Amami/kaddem.git"
                    sh "echo $new_taag
                }
            }
        }
    }
}
