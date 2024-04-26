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
                    sh " echo $new_tag"

                    sh " docker build ./ -t hakkou7/kaddem:$new_tag "
                   
                   
                }
            }
        }

        stage('push docker  image'){
            steps{
                script {
                     docker.withRegistry('', registryCredential) {
                        sh " docker push hakkou7/kaddem:$new_tag "
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
                    sh "cd kaddem"           
                    sh "git pull origin main"
                    sh "cd deploy && sed -i 's/$previous_commit/$new_tag/1'  deploy.yaml"
                    sh " cd deploy && kubectl apply  -f deploy.yaml --validate=false "
                }
            }
        }
    }
}
